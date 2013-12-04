package it.unisa.dia.gas.crypto.jlbc.fhe.ltv;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Sampler;
import it.unisa.dia.gas.plaf.jlbc.field.poly.PolyModField;
import it.unisa.dia.gas.plaf.jlbc.field.z.SymmetricZrField;
import it.unisa.dia.gas.plaf.jlbc.sampler.gpv08.GPV08FieldSampler;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class Test {

    @org.junit.Test
    public void testBESBGV11Engine() {
        try {
            // Setup
                    SecureRandom random = new SecureRandom();
//                    12,
            BigInteger q = new BigInteger("144115188076060673");
                    int d = 32;
//                    2,
                    int sigma = 8;
//                    BigInteger.valueOf(1024)

            Field Rq = new PolyModField<Field>(random, new SymmetricZrField(random, q), d);
            Sampler sampler = new GPV08FieldSampler(12, random, sigma, Rq);

            // KeyGen
            Element g = sampler.sample().getImmutable();
            Element sk = null;
            Element skInv = null;
            while (true) {
            try {
                sk = sampler.sample().mul(2).add(g.getField().newOneElement());
                skInv = sk.duplicate().invert();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
            sk = sk.getImmutable();
            Element pk = g.mul(skInv).mul(2).getImmutable();


            // Enc
            Element e = sampler.sample().mul(2);
            Element s = sampler.sample().getImmutable();

            Element m = Rq.newOneElement();
            Element ct = pk.mul(s).add(e).add(m);

            // Dec
            Element mPrime = sk.mul(ct).mod(BigInteger.valueOf(2));
            System.out.println("mPrime = " + mPrime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
