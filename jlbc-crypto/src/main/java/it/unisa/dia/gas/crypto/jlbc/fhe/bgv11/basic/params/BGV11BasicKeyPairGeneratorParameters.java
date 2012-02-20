package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params;

import org.bouncycastle.crypto.KeyGenerationParameters;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11BasicKeyPairGeneratorParameters extends KeyGenerationParameters {

    private BigInteger q;
    private int d;
    private int N;
    private int sigma;
    private BigInteger t;

    public BGV11BasicKeyPairGeneratorParameters(SecureRandom random, int strength,
                                                BigInteger q, int d, int N, int sigma, BigInteger t) {
        super(random, strength);
        this.q = q;
        this.d = d;
        this.N = N;
        this.sigma = sigma;
        this.t = t;
    }


    public BigInteger getQ() {
        return q;
    }

    public int getD() {
        return d;
    }

    public int getN() {
        return N;
    }

    public int getSigma() {
        return sigma;
    }

    public BigInteger getT() {
        return t;
    }
}
