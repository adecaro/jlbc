package it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.engine;

import it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.generators.SHEBV11bKeyPairGenerator;
import it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.params.*;
import it.unisa.dia.gas.jpbc.Element;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class SHEBV11bEngineTest {

    @Test
    public void testSHEBV11bEngine() {
        try {
            // Setup
            SHEBV11bKeyPairGenerator keyPairGenerator = new SHEBV11bKeyPairGenerator();
            keyPairGenerator.init(new SHEBV11bKeyPairGeneratorParameters(
                    new SecureRandom(),
                    12,
                    32,
                    new BigInteger("144115188076060673"),
//                    new BigInteger(95, 12, new SecureRandom()),
                    BigInteger.valueOf(1024),
                    8
            ));
            
            long start = System.currentTimeMillis();
            AsymmetricCipherKeyPair keyPair = keyPairGenerator.generateKeyPair();
            long end = System.currentTimeMillis();
            System.out.println("elapsed = " + (end-start));

            SHEBV11bPublicKeyParameters pk = (SHEBV11bPublicKeyParameters) keyPair.getPublic();
            SHEBV11bSecretKeyParameters sk = (SHEBV11bSecretKeyParameters)  keyPair.getPrivate();
            SHEBV11bParameters parameters = pk.getParameters();

            // Enc
            SHEBV11bEngine engine = new SHEBV11bEngine();
            engine.init(true, pk);

            byte[] m1  = parameters.getRq().newElement().set(2).toBytes();
            start = System.currentTimeMillis();
            byte[] ct1 = engine.processBlock(m1, 0, m1.length);
            end = System.currentTimeMillis();
            System.out.println("elapsed = " + (end-start));

            byte[] m2  = parameters.getRq().newElement().set(3).toBytes();
            start = System.currentTimeMillis();
            byte[] ct2 = engine.processBlock(m2, 0, m2.length);
            end = System.currentTimeMillis();
            System.out.println("elapsed = " + (end-start));

            // Dec
            engine.init(false, sk);
            Assert.assertTrue(Arrays.equals(m1, engine.processBlock(ct1, 0, ct1.length)));
            Assert.assertTrue(Arrays.equals(m2, engine.processBlock(ct2, 0, ct2.length)));

            // Add
            engine.init(true, new SHEBV11bAddParameters(pk));
            byte[] buffer = toByteArray(ct1, ct2);
            start = System.currentTimeMillis();
            byte[] ctSum = engine.processBlock(buffer, 0, buffer.length);
            end = System.currentTimeMillis();
            System.out.println("elapsed = " + (end-start));

            engine.init(false, sk);
            Element sum = parameters.getRq().newElement();
            sum.setFromBytes(engine.processBlock(ctSum, 0, ctSum.length));
            Assert.assertTrue(sum.toBigInteger().equals(BigInteger.valueOf(5)));

            // Mul
            engine.init(true, new SHEBV11bMulParameters(pk));
            buffer = toByteArray(ct1, ct2);
            start = System.currentTimeMillis();
            byte[] ctMul = engine.processBlock(buffer, 0, buffer.length);
            end = System.currentTimeMillis();
            System.out.println("elapsed = " + (end-start));

            engine.init(false, sk);
            Element mul = parameters.getRq().newElement();
            mul.setFromBytes(engine.processBlock(ctMul, 0, ctMul.length));
            Assert.assertTrue(mul.toBigInteger().equals(BigInteger.valueOf(6)));
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        }
    }


    public byte[] toByteArray(byte[]... bytesList) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            for (byte[] bytes : bytesList) {
                out.write(bytes);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unbelievable");
        }
        return out.toByteArray();
    }


}
