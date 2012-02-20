package it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.params;

import org.bouncycastle.crypto.KeyGenerationParameters;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class SHEBV11bKeyPairGeneratorParameters extends KeyGenerationParameters {

    private int n;
    private BigInteger q;
    private BigInteger t;
    private int sigma;


    public SHEBV11bKeyPairGeneratorParameters(SecureRandom random, int strength, int n, BigInteger q, BigInteger t, int sigma) {
        super(random, strength);

        this.n = n;
        this.q = q;
        this.t = t;
        this.sigma = sigma;
    }


    public int getSigma() {
        return sigma;
    }

    public int getN() {
        return n;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getT() {
        return t;
    }
}
