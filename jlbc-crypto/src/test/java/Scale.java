import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jlbc.field.z.SymmetricZrField;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class Scale {


    public static void main(String[] args) {

        Random random = new SecureRandom();

        BigInteger r = BigInteger.valueOf(16);
        BigInteger q, p;

        BigInteger mu = BigInteger.valueOf(
                Math.round(Math.log(64) / Math.log(2)) + Math.round(Math.log(4) / Math.log(2))
        );

        while (true) {
            q = BigInteger.probablePrime(mu.multiply(BigInteger.valueOf(10)).intValue(), random);

            if (q.mod(r).equals(BigInteger.ONE))
                break;
        }

        while (true) {
            p = BigInteger.probablePrime(mu.multiply(BigInteger.valueOf(9)).intValue(), random);

            if (p.mod(r).equals(BigInteger.ONE))
                break;
        }


        System.out.println("q = " + q);
        System.out.println("p = " + p);

        SymmetricZrField zq = new SymmetricZrField(q);
        SymmetricZrField zp = new SymmetricZrField(p);

        MathContext mc = new MathContext(256, RoundingMode.DOWN);
        for (int i = 0; i < 1; i++) {
            Element xE = zq.newRandomElement();
            Element xScaledE = zp.newElement().set(
                    new BigDecimal(xE.toBigInteger().multiply(p), mc).divide(new BigDecimal(q, mc), mc).toBigInteger()
            );

            System.out.println("xE = " + xE);
            System.out.println("xScaledE = " + xScaledE);

            System.out.println("xE.mod(r) = " + xE.toBigInteger().mod(r));
            while (true) {
                System.out.println("xScaledE.mod(r) = " + xScaledE.toBigInteger().mod(r));
                if (!xE.toBigInteger().mod(r).equals(xScaledE.toBigInteger().mod(r)))
                   xScaledE.add(zp.newOneElement());
                else
                    break;
            }
            System.out.println("xScaledE = " + xScaledE);


            for (int j = 0; j < 64000; j++) {
                Element sq = zq.newElement(j);
                Element sp = zp.newElement(j);

                if (!xE.duplicate().mul(sq).toBigInteger().mod(r).equals(xScaledE.duplicate().mul(sp).toBigInteger().mod(r))) {
                    System.out.println("Found error:" + sq + " - " + sp);
                    break;
                }
            }


/*            BigInteger x = new BigInteger(q.bitLength(), random).mod(q);
            BigInteger xScaled = new BigDecimal(x.multiply(p), mc).divide(new BigDecimal(q, mc), mc).toBigInteger().mod(q);
            xScaled = xScaled.mod(p);

            System.out.println("x = " + x);
            System.out.println("xScaled = " + xScaled);
            System.out.println("x.mod(r) = " + x.mod(r));
            while (true) {
                System.out.println("xScaled.mod(r) = " + xScaled.mod(r));
                if (!x.mod(r).equals(xScaled.mod(r)))
                    xScaled = xScaled.add(BigInteger.ONE).mod(p);
                else
                    break;
            }
            System.out.println("xScaled = " + xScaled);

            System.out.println("x.mod(r) = " + x.mod(r));
            System.out.println("xScaled.mod(r) = " + xScaled.mod(r));

            for (int j = 0; j < 64000; j++) {
                BigInteger s = new BigInteger("" + j);

                if (!x.multiply(s).mod(q).mod(r).equals(xScaled.multiply(s).mod(p).mod(r))) {
                    System.out.println("Found error:" + s);
                    break;
                }
            }
            */
        }

    }


}
