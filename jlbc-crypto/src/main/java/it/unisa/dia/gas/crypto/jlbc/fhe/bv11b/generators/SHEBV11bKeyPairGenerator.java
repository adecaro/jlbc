package it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.generators;

import it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.params.SHEBV11bKeyPairGeneratorParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.params.SHEBV11bParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.params.SHEBV11bPublicKeyParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.params.SHEBV11bSecretKeyParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Sampler;
import it.unisa.dia.gas.plaf.jlbc.field.poly.PolyModField;
import it.unisa.dia.gas.plaf.jlbc.field.z.SymmetricZrField;
import it.unisa.dia.gas.plaf.jlbc.field.z.ZField;
import it.unisa.dia.gas.plaf.jlbc.field.z.ZrField;
import it.unisa.dia.gas.plaf.jlbc.sampler.GPV08.GPV08FieldSampler;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class SHEBV11bKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {

    private SHEBV11bKeyPairGeneratorParameters parameters;

    private BigInteger q;
    private BigInteger t;

    private Field R, Rq, Zqas, Zq;
    private Element tInv;
    private int lub;

    private Sampler sampler;


    public void init(KeyGenerationParameters param) {
        this.parameters = (SHEBV11bKeyPairGeneratorParameters) param;

        Random random = param.getRandom();
        int n = parameters.getN();
        q = parameters.getQ();
        t = parameters.getT();

        R = new PolyModField(random, new ZField(random), n);
        Rq = new PolyModField(random, (Zq = new SymmetricZrField(random, q)), n);

        Zqas = new ZrField(random, q);
        tInv = Zqas.newElement(t).invert();
        lub = (int) (Math.log(q.doubleValue()) / Math.log(t.intValue())) + 1;
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        this.sampler = new GPV08FieldSampler(parameters.getStrength(), parameters.getRandom(), parameters.getSigma(), Rq);

        // Generate secret key
        Element secretKey = sampler.sample();
        
        // Generate public key
        Element error = sampler.sample();
        Element a1 = Rq.newRandomElement();
        Element a0 = a1.duplicate().mul(secretKey).add(error.mul(t)).negate();

        // Keys for relinearization
        BigInteger tt = BigInteger.ONE;
        Element sSquare = secretKey.duplicate().square();
        Element[] as = new Element[lub];
        Element[] bs = new Element[lub];

        for (int i = 0; i < lub; i++) {
            as[i] = Rq.newRandomElement();

            error = sampler.sample();
            bs[i] = as[i].duplicate().mul(secretKey).add(error.mul(t)).negate().add(
                    sSquare.duplicate().mul(tt)
            );
            tt = tt.multiply(t);
        }

        SHEBV11bParameters parameters = new SHEBV11bParameters(
                q, sampler, Rq, Zqas, tInv, t, lub
        );
        
        return new AsymmetricCipherKeyPair(
                new SHEBV11bPublicKeyParameters(parameters, a0, a1, as, bs),
                new SHEBV11bSecretKeyParameters(parameters, secretKey)
        );
        
    }
}
