package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.engine;

import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.*;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Vector;
import it.unisa.dia.gas.plaf.jlbc.util.io.ElementObjectInput;
import it.unisa.dia.gas.plaf.jlbc.util.io.ElementObjectOutput;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11LeveledEngine implements AsymmetricBlockCipher {

    private CipherParameters param;
    private int inputBlockSize, outputBlockSize;
    private int decryptionLevel;


    public void init(boolean forEncryption, CipherParameters param) {
        if (forEncryption) {
            if (!(param instanceof BGV11LeveledPublicKeyParameters) &&
                    !(param instanceof BGV11LeveledAddParameters) &&
                    !(param instanceof BGV11LeveledMulParameters))
                throw new IllegalArgumentException("SHEBV11bPublicKeyParameters are required for encryption.");
        } else {
            if (!(param instanceof BGV11LeveledSecretKeyParameters))
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
        if (param instanceof BGV11LeveledPublicKeyParameters) {
            // Encrypt under input level parameters
            BGV11LeveledPublicKeyParameters pk = ((BGV11LeveledPublicKeyParameters) param);
            BGV11LeveledParameters parameters = pk.getInputLevelParameters();

            // Load the message
            Element m = parameters.getRq().newElement();
            m.setFromBytes(in, inOff);

            // Choose the randomness
            Element r = parameters.getRtN().newRandomElement();

            // Encrypt
            Element c0 = m.add(ip(pk.getInputLevelB(), r));
            Element c1 = ip(pk.getInputLevelA(), r);

            // Move to bytes
            return new ElementObjectOutput().writeInt(pk.getInputLevel()).writeElements(c0, c1).toByteArray();
        } else if (param instanceof BGV11LeveledSecretKeyParameters) {
            // Dec
            BGV11LeveledSecretKeyParameters sk = ((BGV11LeveledSecretKeyParameters) param);

            // Load level
            ElementObjectInput reader = new ElementObjectInput(in, inOff, len);
            int level = reader.readInt();
            BGV11LeveledParameters parameters = sk.getParametersAt(level);
            Element s = sk.getSecretAt(level);

            // Load the ciphertext
            reader.setField(parameters.getRq());
            Element[] c = reader.readElements(2);

            this.decryptionLevel = level;

            // Decrypt
            return mod(c[0].add(c[1].mul(s)), parameters.getT()).toBytes();
        } else if (param instanceof BGV11LeveledAddParameters) {
            // Add
            BGV11LeveledPublicKeyParameters pk = ((BGV11LeveledAddParameters) param).getPk();

            ElementObjectInput reader = new ElementObjectInput(in, inOff, len);

            // Load left part
            int leftLevel = reader.readInt();
            BGV11LeveledParameters leftParams = pk.getParametersAt(leftLevel);
            reader.setField(leftParams.getRq());
            Element[] left = reader.readElements(2);

            // Load right
            int rightLevel = reader.readInt();
            BGV11LeveledParameters rightParams = pk.getParametersAt(rightLevel);
            reader.setField(rightParams.getRq());
            Element[] right = reader.readElements(2);

            // Check levels
            int currentLevel = leftLevel;
            if (leftLevel != rightLevel) {
                if (leftLevel < rightLevel) {
                    right = pk.refresh(rightLevel, leftLevel, right);
                } else {
                    left = pk.refresh(leftLevel, rightLevel, left);
                    currentLevel = rightLevel;
                }
            }
            int nextLevel = currentLevel - 1;

            // Sum
            left[0].add(right[0]);
            left[1].add(right[1]);

            // Move to bytes a refreshed ciphertext
            return new ElementObjectOutput().writeInt(nextLevel).writeElements(
                    pk.refresh(currentLevel, left)
//                    left
            ).toByteArray();
        } else if (param instanceof BGV11LeveledMulParameters) {
            // Mul
            BGV11LeveledPublicKeyParameters pk = ((BGV11LeveledMulParameters) param).getPk();

            ElementObjectInput reader = new ElementObjectInput(in, inOff, len);

            // Load left part
            int leftLevel = reader.readInt();
            BGV11LeveledParameters leftParams = pk.getParametersAt(leftLevel);
            reader.setField(leftParams.getRq());
            Element[] left = reader.readElements(2);

            // Load right
            int rightLevel = reader.readInt();
            BGV11LeveledParameters rightParams = pk.getParametersAt(rightLevel);
            reader.setField(rightParams.getRq());
            Element[] right = reader.readElements(2);

            // Check levels
            int currentLevel = leftLevel;
            if (leftLevel != rightLevel) {
                if (leftLevel < rightLevel) {
                    right = pk.refresh(rightLevel, leftLevel, right);
                } else {
                    left = pk.refresh(leftLevel, rightLevel, left);
                    currentLevel = rightLevel;
                }
            }
            int nextLevel = currentLevel - 1;

            // Mul
            Element[] product = new Element[3];
            product[0] = left[0].duplicate().mul(right[0]);
            product[1] = left[0].duplicate().mul(right[1]).add(left[1].duplicate().mul(right[0]));
            product[2] = left[1].duplicate().mul(right[1]);

            // Move to bytes a refreshed ciphertext
            return new ElementObjectOutput().writeInt(nextLevel).writeElements(
                    pk.refresh(currentLevel, product)
            ).toByteArray();
        }

        throw new IllegalStateException("Invalid parameters.");
    }


    protected Element ip(Element l, Element r) {
        Vector lv = (Vector) l;
        Vector rv = (Vector) r;

        Element result = lv.getAt(0).getField().newZeroElement();
        for (int i = 0, length = Math.min(lv.getSize(), rv.getSize()); i < length; i++) {
            result.add(lv.getAt(i).duplicate().mul(rv.getAt(i)));
        }

        return result;
    }

    protected Element mod(Element m, BigInteger order) {
        Vector v = (Vector) m;
        for (int i = 0; i < v.getSize(); i++)
            v.getAt(i).set(v.getAt(i).toBigInteger().mod(order));

        return v;
    }

    public int getDecryptionLevel() {
        return decryptionLevel;
    }
}
