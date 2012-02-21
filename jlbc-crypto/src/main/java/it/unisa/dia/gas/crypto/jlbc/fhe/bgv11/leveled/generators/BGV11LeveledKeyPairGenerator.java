package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.generators;

import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.BGV11LeveledKeyPairGeneratorParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.BGV11LeveledParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.BGV11LeveledPublicKeyParameters;
import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.BGV11LeveledSecretKeyParameters;
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
public class BGV11LeveledKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {

    private BGV11LeveledKeyPairGeneratorParameters parameters;

    private int L, d;
    private BigInteger t;

    public void init(KeyGenerationParameters param) {
        this.parameters = (BGV11LeveledKeyPairGeneratorParameters) param;

        this.L = parameters.getL();
        this.d = parameters.getD();
        this.t = parameters.getT();
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        if (parameters == null)
            throw new IllegalStateException("Generator not initialized!");

        Random random = parameters.getRandom();

        BGV11LeveledParameters[] pps = new BGV11LeveledParameters[L + 1];
        
        Element[] secrets = new Element[L + 1];

        Element[] As = new Element[L + 1];
        Element[] bs = new Element[L + 1];

        Element[] switchAs = new Element[L];
        Element[] switchBs = new Element[L];

        Element[] sDoublePrime = null;

        for (int j = L; j >= 0; j--) {
            // Init parameters
            BigInteger q = parameters.getQAt(j);
            int N = parameters.getNAt(j);

            Field Rq = new PolyModField(random, new SymmetricZrField(random, q), d);
            Field Rt = new PolyModField(random, new ZrField(random, t), d);

            Field RqN = new VectorField(random, Rq, N);
            Field RtN = new VectorField(random, Rt, N);
            Sampler sampler = new GPV08FieldSampler(parameters.getStrength(), parameters.getRandom(), parameters.getSigma(), Rq);

            Field Zq = new ZrField(random, q);
            Element tInv = Zq.newElement(t).invert();
            int lub = (int) (Math.log(q.doubleValue()) / Math.log(t.intValue())) + 1;

            // Generate secret key
            secrets[j] = sampler.sample();

            // Generate public key
            As[j] = RqN.newRandomElement();
            bs[j] = As[j].duplicate().mul(secrets[j]).add(sampler.sample(N).mul(t));
            As[j].negate();

            pps[j] = new BGV11LeveledParameters(q, t, tInv, d, N, Rq, RtN, Zq, lub);

            if (j != L) {
                // Compute switch key
                int switchN = sDoublePrime.length * lub;
                Field RqNLogQ = new VectorField(random, Rq, switchN);

                switchAs[j] = RqNLogQ.newRandomElement();
                switchBs[j] = switchAs[j].duplicate().mul(secrets[j]).add(sampler.sample(switchN).mul(t)).add(pps[j].powerOfToVector(sDoublePrime));
                switchAs[j].negate();
            }

            // Compute the decomposition of the tensor product of s with itself
            sDoublePrime = pps[j].decompose(
                    secrets[j].getField().newOneElement(),
                    secrets[j],
                    secrets[j].duplicate().square()
            );
        }

        return new AsymmetricCipherKeyPair(
                new BGV11LeveledPublicKeyParameters(pps, bs, As, switchBs, switchAs),
                new BGV11LeveledSecretKeyParameters(pps, secrets)
        );
    }

}
