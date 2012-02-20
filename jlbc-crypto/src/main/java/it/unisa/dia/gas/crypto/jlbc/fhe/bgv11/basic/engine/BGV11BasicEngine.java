package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.engine;

import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params.BGV11BasicPublicKeyParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params.BGV11BasicSecretKeyParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Vector;
import it.unisa.dia.gas.plaf.jlbc.util.ElementUtils;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11BasicEngine implements AsymmetricBlockCipher {

    private CipherParameters param;
    private int inputBlockSize, outputBlockSize;


    public void init(boolean forEncryption, CipherParameters param) {
        if (forEncryption) {
            if (!(param instanceof BGV11BasicPublicKeyParameters))
                throw new IllegalArgumentException("SHEBV11bPublicKeyParameters are required for encryption.");
        } else {
            if (!(param instanceof BGV11BasicSecretKeyParameters))
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
        if (param instanceof BGV11BasicPublicKeyParameters) {
            // Enc
            BGV11BasicPublicKeyParameters pk = ((BGV11BasicPublicKeyParameters) param);

            // Load the message
            Element m = pk.getParameters().getRq().newElement();
            m.setFromBytes(in, inOff);

            // Choose the randomness
            Element r = pk.getParameters().getRtN().newRandomElement();

            // Encrypt
            Element c0 = m.add(ip(pk.getB(), r));
            Element c1 = ip(pk.getA(), r);

            return ElementUtils.toByteArray(c0, c1);
        } else if (param instanceof BGV11BasicSecretKeyParameters) {
            // Dec
            BGV11BasicSecretKeyParameters sk = ((BGV11BasicSecretKeyParameters) param);

            // Load the ciphertext
            Field Rq = sk.getParameters().getRq();
            Element[] c = new Element[2];
            c[0] = Rq.newElement();
            inOff += c[0].setFromBytes(in, inOff);
            c[1] = Rq.newElement();
            inOff += c[1].setFromBytes(in, inOff);

            Element s = sk.getS();

            // Decrypt
            return mod(
                    c[0].add(c[1].mul(s)),
                    sk.getParameters().getT()
            ).toBytes();
        }

        throw new IllegalStateException("Invalid parameters.");
    }

    private Element ip(Element l, Element r) {
        Vector lv = (Vector) l;
        Vector rv = (Vector) r;

        Element result = lv.getAt(0).getField().newZeroElement();
        for (int i = 0, length = Math.min(lv.getSize(), rv.getSize()); i < length; i++) {
            result.add(lv.getAt(i).duplicate().mul(rv.getAt(i)));
        }

        return result;
    }


    public Element mod(Element m, BigInteger order) {
        Vector v = (Vector) m;
        for (int i = 0; i < v.getSize(); i++)
            v.getAt(i).set(v.getAt(i).toBigInteger().mod(order));

        return v;
    }

}
