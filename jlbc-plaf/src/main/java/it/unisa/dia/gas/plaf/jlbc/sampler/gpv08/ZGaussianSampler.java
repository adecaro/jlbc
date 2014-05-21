package it.unisa.dia.gas.plaf.jlbc.sampler.gpv08;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public class ZGaussianSampler implements Samp<BigInteger> {

    protected SecureRandom random;

    private int sigma;
    private double sigmaSquare;

    private BigInteger tofk, left, right;

    public ZGaussianSampler(int strength, SecureRandom random, int sigma) {
        if (random == null)
            random = new SecureRandom();

        this.random = random;
        this.sigma = sigma;
        this.sigmaSquare = Math.pow(sigma, 2);
        this.tofk = BigInteger.valueOf(Math.round(Math.log(strength) / Math.log(2)));

        BigInteger offset = tofk.multiply(BigInteger.valueOf(sigma));
        left = offset.negate();
        right = offset;

    }


    public BigInteger sample() {
        while (true) {
            BigInteger x = left.add(BigInteger.valueOf((long) (random.nextDouble() * (right.subtract(left)).longValue())));

            double rhoS = Math.exp(-Math.PI * Math.pow(x.longValue(), 2) / sigmaSquare);
            if (random.nextDouble() < rhoS)
                return x;
        }
    }
}
