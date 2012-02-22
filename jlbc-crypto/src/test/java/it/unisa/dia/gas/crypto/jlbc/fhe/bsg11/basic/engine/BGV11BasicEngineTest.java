package it.unisa.dia.gas.crypto.jlbc.fhe.bsg11.basic.engine;

import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.engine.BGV11BasicEngine;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.generators.BGV11BasicKeyPairGenerator;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params.BGV11BasicKeyPairGeneratorParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params.BGV11BasicPublicKeyParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params.BGV11BasicSecretKeyParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11BasicEngineTest {

    @Test
    public void testBESBGV11Engine() {
        try {
            // Setup
            BGV11BasicKeyPairGenerator keyPairGenerator = new BGV11BasicKeyPairGenerator();
            keyPairGenerator.init(new BGV11BasicKeyPairGeneratorParameters(
                    new SecureRandom(),
                    12,
                    new BigInteger("144115188076060673"),
                    32,
                    2,
                    8,
                    BigInteger.valueOf(1024)
            ));
            
            long start = System.currentTimeMillis();
            AsymmetricCipherKeyPair keyPair = keyPairGenerator.generateKeyPair();
            long end = System.currentTimeMillis();
            System.out.println("elapsed = " + (end-start));
            BGV11BasicPublicKeyParameters pk = (BGV11BasicPublicKeyParameters) keyPair.getPublic();
            BGV11BasicSecretKeyParameters sk = (BGV11BasicSecretKeyParameters)  keyPair.getPrivate();

            // Enc
            BGV11BasicEngine engine = new BGV11BasicEngine();
            engine.init(true, pk);

            byte[] m1  = pk.getParameters().getRq().newElement().set(2).toBytes();
            start = System.currentTimeMillis();
            byte[] ct1 = engine.processBlock(m1, 0, m1.length);
            end = System.currentTimeMillis();
            System.out.println("elapsed = " + (end-start));

            byte[] m2  = pk.getParameters().getRq().newElement().set(3).toBytes();
            start = System.currentTimeMillis();
            byte[] ct2 = engine.processBlock(m2, 0, m2.length);
            end = System.currentTimeMillis();
            System.out.println("elapsed = " + (end-start));

            // Dec
            engine.init(false, sk);
            Assert.assertTrue(Arrays.equals(m1, engine.processBlock(ct1, 0, ct1.length)));
            Assert.assertTrue(Arrays.equals(m2, engine.processBlock(ct2, 0, ct2.length)));
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        }
    }

}
