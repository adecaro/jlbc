package it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.engine;

import it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.params.*;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Polynomial;
import it.unisa.dia.gas.jpbc.Vector;
import it.unisa.dia.gas.plaf.jlbc.util.ElementUtils;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class SHEBV11bEngine implements AsymmetricBlockCipher {

    private CipherParameters param;
    private int inputBlockSize, outputBlockSize;


    public void init(boolean forEncryption, CipherParameters param) {
        if (forEncryption) {
            if (!(param instanceof SHEBV11bPublicKeyParameters) &&
                    !(param instanceof SHEBV11bAddParameters) &&
                    !(param instanceof SHEBV11bMulParameters))
                throw new IllegalArgumentException("SHEBV11bPublicKeyParameters are required for encryption.");
        } else {
            if (!(param instanceof SHEBV11bSecretKeyParameters))
                throw new IllegalArgumentException("SHEBV11bSecretKeyParameters are required for decryption.");
        }

        this.param = param;

        // TODO: set inpuBlockSize and outputBlockSize
    }

    public int getInputBlockSize() {
        return inputBlockSize;
    }

    public int getOutputBlockSize() {
        return outputBlockSize;
    }

    public byte[] processBlock(byte[] in, int inOff, int len) throws InvalidCipherTextException {
        if (param instanceof SHEBV11bPublicKeyParameters) {
            // Enc

            SHEBV11bPublicKeyParameters pk = ((SHEBV11bPublicKeyParameters) param);
            SHEBV11bParameters parameters = pk.getParameters();

            // Load the message
            Element m = parameters.getRq().newElement();
            m.setFromBytes(in, inOff);

            // Choose the randomness
            Element u = parameters.sample();
            Element f = parameters.sample().mul(parameters.getT());
            Element g = parameters.sample().mul(parameters.getT());

            // Encrypt
            Element c0 = pk.getA0().mul(u).add(g).add(m);
            Element c1 = pk.getA1().mul(u).add(f);

            return ElementUtils.toByteArray(c0, c1);
        } else if (param instanceof SHEBV11bAddParameters) {
            // Add
            SHEBV11bPublicKeyParameters pk = ((SHEBV11bAddParameters) param).getPk();
            SHEBV11bParameters parameters = pk.getParameters();

            // Load left part
            Element[] left = new Element[2];
            left[0] = parameters.getRq().newElement();
            inOff += left[0].setFromBytes(in, inOff);
            left[1] = parameters.getRq().newElement();
            inOff += left[1].setFromBytes(in, inOff);

            // Load right part
            Element[] right = new Element[2];
            right[0] = parameters.getRq().newElement();
            inOff += right[0].setFromBytes(in, inOff);
            right[1] = parameters.getRq().newElement();
            inOff += right[1].setFromBytes(in, inOff);

            // Sum
            for (int i = 0; i < 1; i++) {
                left[0].add(right[0]);
                left[1].add(right[1]);
            }
            return ElementUtils.toByteArray(left[0], left[1]);
        } else if (param instanceof SHEBV11bMulParameters) {
            // Mul

            SHEBV11bPublicKeyParameters pk = ((SHEBV11bMulParameters) param).getPk();
            SHEBV11bParameters parameters = pk.getParameters();

            // Load left
            Element[] left = new Element[2];
            left[0] = parameters.getRq().newElement();
            inOff += left[0].setFromBytes(in, inOff);
            left[1] = parameters.getRq().newElement();
            inOff += left[1].setFromBytes(in, inOff);

            // Load right
            Element[] right = new Element[2];
            right[0] = parameters.getRq().newElement();
            inOff += right[0].setFromBytes(in, inOff);
            right[1] = parameters.getRq().newElement();
            inOff += right[1].setFromBytes(in, inOff);

            // Multiply
            Element c0 = left[0].duplicate().mul(right[0]);
            Element c1 = left[0].duplicate().mul(right[1]).add(left[1].duplicate().mul(right[0]));
            Element c2 = left[1].duplicate().mul(right[1]);

            // Relinearize
            Element[] c2s = decompose(parameters, c2);

            Element c1Lin = c1.duplicate();
            Element c0Lin = c0.duplicate();
            for (int i = 0; i < parameters.getLub(); i++) {
                c1Lin.add(c2s[i].duplicate().mul(pk.getAAt(i)));
                c0Lin.add(c2s[i].duplicate().mul(pk.getBAt(i)));
            }

            return ElementUtils.toByteArray(c0Lin, c1Lin);
        } else if (param instanceof SHEBV11bSecretKeyParameters) {
            // Dec

            SHEBV11bSecretKeyParameters sk = ((SHEBV11bSecretKeyParameters) param);
            SHEBV11bParameters parameters = sk.getParameters();

            // Load the ciphertext
            Element[] elements = new Element[2];
            elements[0] = parameters.getRq().newElement();
            inOff += elements[0].setFromBytes(in, inOff);
            elements[1] = parameters.getRq().newElement();
            inOff += elements[1].setFromBytes(in, inOff);

            // Decrypt
            return mod(elements[0].add(elements[1].mul(sk.getSecretKey())), parameters.getT()).toBytes();
        }

        throw new IllegalStateException("Invalid parameters.");
    }


    public Element mod(Element m, BigInteger order) {
        // Note that m is in Rq then
        // it can be interpreted like a Polynomial<Element>
        
        for (Element element : ((Polynomial<Element>) m).getCoefficients())
            element.set(element.toBigInteger().mod(order));

        return m;
    }


    public Element[] decompose(SHEBV11bParameters params, Element e) {
        // Note that e is in Rq then
        // it can be interpreted as an Vector<Element> 
        
        Vector<Element> ringElement = (Vector<Element>) e;
        
        Vector[] decomposition = new Vector[params.getLub()];
        for (int i = 0; i < decomposition.length; i++)
            decomposition[i] = (Vector) e.getField().newElement();

        for (int i = 0, n = ringElement.getSize(); i < n; i++) {
            Element element = ringElement.getAt(i);

            Element cursor = params.getZq().newElement().set(element);
            int j = 0;
            while (true) {
                BigInteger reminder = cursor.toBigInteger().mod(params.getT());

                decomposition[j++].getAt(i).set(reminder);
                cursor = cursor.sub(cursor.getField().newElement(reminder)).mul(params.gettInverse());

                if (cursor.isZero())
                    break;
            }
        }

        return decomposition;
    }

}
