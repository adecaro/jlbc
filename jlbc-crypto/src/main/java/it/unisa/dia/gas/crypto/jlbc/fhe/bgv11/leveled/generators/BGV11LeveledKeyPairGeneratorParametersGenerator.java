package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.generators;

import it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.BGV11LeveledKeyPairGeneratorParameters;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11LeveledKeyPairGeneratorParametersGenerator {

    private SecureRandom random;
    private int strength;
    private int L, d;
    private BigInteger t;
    private int sigma;


    public BGV11LeveledKeyPairGeneratorParametersGenerator(SecureRandom random, int strenght,
                                                           int L, int d, BigInteger t, int sigma) {
        this.random = random;
        this.strength = strenght;
        this.L = L;
        this.d = d;
        this.t = t;
        this.sigma = sigma;
    }

    public BGV11LeveledKeyPairGeneratorParameters generate() {
        BigInteger[] qs = new BigInteger[L + 1];
        int[] Ns = new int[L + 1];
        BigInteger dd = BigInteger.valueOf(d);

        int mu = 8 * (int) Math.round(Math.log(strength) / Math.log(2) + Math.log(L) / Math.log(2));
        for (int j = L; j >= 0; j--) {
            while (true) {
                qs[j] = BigInteger.probablePrime((j + 1) * mu, random);
                if (qs[j].mod(t).equals(BigInteger.ONE) && qs[j].mod(dd).equals(BigInteger.ONE))
                    break;
            }
            Ns[j] = (int) Math.ceil(3 * (Math.log(qs[j].doubleValue()) / Math.log(2)));
        }

        return new BGV11LeveledKeyPairGeneratorParameters(
                random, strength,
                L, d, sigma, t, qs, Ns
        );
    }

}
