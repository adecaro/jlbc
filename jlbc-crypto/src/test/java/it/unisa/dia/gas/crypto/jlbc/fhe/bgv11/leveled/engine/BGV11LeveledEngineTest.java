package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.engine;

import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.generators.BGV11LeveledKeyPairGenerator;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.generators.BGV11LeveledKeyPairGeneratorParametersGenerator;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.BGV11LeveledAddParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.BGV11LeveledMulParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.BGV11LeveledPublicKeyParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.BGV11LeveledSecretKeyParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jlbc.util.io.IOUtils;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11LeveledEngineTest {

    protected BGV11LeveledEngine engine;

    @Before
    public void init() {
        engine = new BGV11LeveledEngine();
    }

    @Test
    public void testBESBGV11Engine() {
        AsymmetricCipherKeyPair keyPair = genKey(new SecureRandom(),
                64,
                5,
                4,
                BigInteger.valueOf(71),
                8);

        BGV11LeveledPublicKeyParameters pk = (BGV11LeveledPublicKeyParameters) keyPair.getPublic();
        BGV11LeveledSecretKeyParameters sk = (BGV11LeveledSecretKeyParameters) keyPair.getPrivate();

        int a = 3, b = 3, c = 2, d = 3, zero = 0;

        byte[] ct_A = enc(pk, a);
        Assert.assertEquals(a, dec(sk, ct_A));

        byte[] ct_B = enc(pk, b);
        Assert.assertEquals(b, dec(sk, ct_B));

        byte[] ct_C = enc(pk, c);
        Assert.assertEquals(c, dec(sk, ct_C));

        byte[] ct_D = enc(pk, d);
        Assert.assertEquals(d, dec(sk, ct_D));

        byte[] ct_Zero = enc(pk, zero);
        Assert.assertEquals(zero, dec(sk, ct_Zero));

        Assert.assertEquals(c, dec(sk, add(pk, ct_Zero, add(pk, ct_Zero, ct_C))));

        byte[] ct_AAddB = add(pk, ct_A, ct_B);
        Assert.assertEquals((a + b), dec(sk, ct_AAddB));

        byte[] ct_AMulB = mul(pk, ct_A, ct_B);
        Assert.assertEquals((a * b), dec(sk, ct_AMulB));

        byte[] ct_AAddB_Add_AMulB = add(pk, ct_AAddB, ct_AMulB);
        Assert.assertEquals((a + b) + (a * b), dec(sk, ct_AAddB_Add_AMulB));

        byte[] ct_AAddB_Add_AMulB_Add_C = add(pk, ct_AAddB_Add_AMulB, ct_C);
        Assert.assertEquals((a + b) + (a * b) + c, dec(sk, ct_AAddB_Add_AMulB_Add_C));

        byte[] ct_AAddB_Add_AMulB_Add_C_MUL_D = mul(pk, ct_AAddB_Add_AMulB_Add_C, ct_D);
        Assert.assertEquals((((a + b) + (a * b)) + c) * d, dec(sk, ct_AAddB_Add_AMulB_Add_C_MUL_D));
    }

    private byte[] add(BGV11LeveledPublicKeyParameters pk, byte[] ctA, byte[] ctB) {
        engine.init(true, new BGV11LeveledAddParameters(pk));
        byte[] message = IOUtils.toByteArray(ctA, ctB);

        long start = System.currentTimeMillis();
        try {
            return engine.processBlock(message, 0, message.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("BGV11LeveledEngineTest.add " + (end - start));
        }
    }

    private byte[] mul(BGV11LeveledPublicKeyParameters pk, byte[] ctA, byte[] ctB) {
        engine.init(true, new BGV11LeveledMulParameters(pk));
        byte[] message = IOUtils.toByteArray(ctA, ctB);

        long start = System.currentTimeMillis();
        try {
            return engine.processBlock(message, 0, message.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("BGV11LeveledEngineTest.mul " + (end - start));
        }
    }

    protected AsymmetricCipherKeyPair genKey(SecureRandom random, int strength, int L, int d, BigInteger t, int sigma) {
        BGV11LeveledKeyPairGenerator keyPairGenerator = new BGV11LeveledKeyPairGenerator();
        keyPairGenerator.init(
                new BGV11LeveledKeyPairGeneratorParametersGenerator(
                        random, strength, L, d, t, sigma
                ).generate()
        );
        long start = System.currentTimeMillis();
        try {
            return keyPairGenerator.generateKeyPair();
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("BGV11LeveledEngineTest.genKey " + (end - start));
        }
    }


    protected byte[] enc(BGV11LeveledPublicKeyParameters pk, int value) {
        engine.init(true, pk);
        byte[] message = pk.getInputLevelParameters().getRq().newElement().set(value).toBytes();

        long start = System.currentTimeMillis();
        try {
            return engine.processBlock(message, 0, message.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("BGV11LeveledEngineTest.enc " + (end - start));
        }
    }

    protected int dec(BGV11LeveledSecretKeyParameters sk, byte[] ct) {
        engine.init(false, sk);

        long start = System.currentTimeMillis();
        long end = 0;
        try {
            byte[] message = engine.processBlock(ct, 0, ct.length);

            end = System.currentTimeMillis();

            Element result = sk.getParametersAt(engine.getDecryptionLevel()).getRq().newElement();
            result.setFromBytes(message);

            return result.toBigInteger().intValue();
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("BGV11LeveledEngineTest.dec " + (end - start));
        }
    }

}
