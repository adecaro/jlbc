package it.unisa.dia.gas.plaf.jlbc.sampler.gpv08;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class GPV08DSampler {

    protected Random random;

    private int sigma;
    private double sigmaSquare;

    private BigInteger tofk;

    public GPV08DSampler(int strength, Random random, int sigma) {
        if (random == null)
            random = new SecureRandom();

        this.random = random;
        this.sigma = sigma;
        this.sigmaSquare = Math.pow(sigma, 2);
        this.tofk = BigInteger.valueOf(Math.round(Math.log(strength) / Math.log(2)));
    }


    public BigInteger sampleZ(BigInteger c) {
        BigInteger offset = tofk.multiply(BigInteger.valueOf(sigma));

        BigInteger left = c.subtract(offset);
        BigInteger right = c.add(offset);

        while (true) {
            BigInteger x = left.add(BigInteger.valueOf((long) (random.nextDouble() * (right.subtract(left)).longValue())));

            double rhoS = Math.exp(-Math.PI * Math.pow(x.subtract(c).longValue(), 2) / sigmaSquare);

            if (random.nextDouble() < rhoS) {
                return x;
            }
        }
    }

    public BigInteger[] sampleD(int n) {
        BigInteger[] v = new BigInteger[n];

        BigInteger c = BigInteger.ZERO;
        v[n - 1] = BigInteger.ZERO;
        for (int i = n - 2; i >= 0; i--) {
            BigInteger z = sampleZ(c);
            c = c.subtract(z);
            v[i] = v[i + 1].add(z);
        }

        return v;
    }

}
