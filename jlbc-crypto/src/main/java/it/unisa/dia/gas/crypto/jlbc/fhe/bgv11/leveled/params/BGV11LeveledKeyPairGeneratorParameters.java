package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params;

import org.bouncycastle.crypto.KeyGenerationParameters;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11LeveledKeyPairGeneratorParameters extends KeyGenerationParameters {

    private int L, d;
    private BigInteger t;
    private int sigma;

    private int Ns[];
    private BigInteger[] qs;

    public BGV11LeveledKeyPairGeneratorParameters(SecureRandom random, int strength,
                                                  int L, int d, int sigma, BigInteger t,
                                                  BigInteger[] qs, int[] Ns) {
        super(random, strength);

        this.L = L;
        this.d = d;
        this.sigma = sigma;
        this.t = t;
        this.qs = qs;
        this.Ns = Ns;
    }

    public BigInteger getQAt(int index) {
        return qs[index];
    }

    public int getD() {
        return d;
    }

    public int getL() {
        return L;
    }

    public int getNAt(int index) {
        return Ns[index];
    }

    public int getSigma() {
        return sigma;
    }

    public BigInteger getT() {
        return t;
    }
}
