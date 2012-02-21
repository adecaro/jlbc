package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.generators;

import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params.BGV11BasicKeyPairGeneratorParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params.BGV11BasicParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params.BGV11BasicPublicKeyParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params.BGV11BasicSecretKeyParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Sampler;
import it.unisa.dia.gas.plaf.jlbc.field.poly.PolyModField;
import it.unisa.dia.gas.plaf.jlbc.field.vector.VectorField;
import it.unisa.dia.gas.plaf.jlbc.field.z.SymmetricZrField;
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
public class BGV11BasicKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {

    private BGV11BasicKeyPairGeneratorParameters parameters;

    private BigInteger q, t;
    private int d, N;
    private Field Rq, RtN;

    private Sampler sampler;
    private Field RqN;
    
    public void init(KeyGenerationParameters param) {
        this.parameters = (BGV11BasicKeyPairGeneratorParameters) param;

        Random random = param.getRandom();

        q = parameters.getQ();
        t = parameters.getT();
        d = parameters.getD();
        N = parameters.getN();

        Rq = new PolyModField(random, new SymmetricZrField(random, q), d);

        RqN = new VectorField(random, Rq, N);
        RtN = new VectorField(random, new PolyModField(random, new ZrField(random, t), d), N);
        sampler = new GPV08FieldSampler(parameters.getStrength(), parameters.getRandom(), parameters.getSigma(), Rq);
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        if (parameters == null)
            throw new IllegalStateException("Generator not initialized!");

        // Generate secret key
        Element s = sampler.sample();

        // Generate public key
        Element A = RqN.newRandomElement();
        Element b = A.duplicate().mul(s).add(sampler.sample(N).mul(t));
        A.negate();

        BGV11BasicParameters parameters = new BGV11BasicParameters(q, t, d, N, Rq, RtN);

        return new AsymmetricCipherKeyPair(
                new BGV11BasicPublicKeyParameters(parameters, b, A),
                new BGV11BasicSecretKeyParameters(parameters, s)
        );
    }

}
