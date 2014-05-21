package it.unisa.dia.gas.plaf.jlbc.sampler.gpv08;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.plaf.jlbc.field.vector.MatrixElement;
import it.unisa.dia.gas.plaf.jlbc.field.vector.MatrixField;
import it.unisa.dia.gas.plaf.jlbc.field.vector.VectorElement;
import it.unisa.dia.gas.plaf.jlbc.field.vector.VectorField;
import it.unisa.dia.gas.plaf.jlbc.field.z.ZrField;
import it.unisa.dia.gas.plaf.jlbc.util.math.BigIntegerUtils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public class PrimitiveLatticePowerOfTwo {

    protected SecureRandom random;
    protected int n, k;
    protected Samp<BigInteger> sampler;

    protected VectorElement g; // primitiv vector
    protected MatrixElement G; // parity-check matrix

    protected Field syndromeField;

    protected ZrField Zq;
    protected VectorField<ZrField> preimageField;

    public PrimitiveLatticePowerOfTwo(SecureRandom random, int n, int k, Samp<BigInteger> sampler) {
        this.n = n;
        this.k = k;
        this.sampler = sampler;

        int q = 1 << k;

        this.Zq = new ZrField(q);
        this.syndromeField = new VectorField<ZrField>(random, Zq, n);

        // Construct primitive G
        VectorField<ZrField> gField = new VectorField<ZrField>(random, Zq, k);
        this.g = gField.newElement();
        long value = 1;
        for (int i = 0; i < k; i++) {
            this.g.getAt(i).set(BigInteger.valueOf(value));
            value = value << 1;
        }

        MatrixField<ZrField> GField = new MatrixField<ZrField>(random, Zq, n, n * k);
        this.G = GField.newDiagonalElement(g);

        this.preimageField = new VectorField<ZrField>(random, Zq, n * k);

        System.out.println("g = " + this.g);
        System.out.println("G = " + this.G);
    }


    public Element sampleD(Element syndrome) {
        VectorElement vSyndrome = (VectorElement) syndrome;
        if (vSyndrome.getSize() != n)
            throw new IllegalArgumentException("Invalid syndrome length.");

        VectorElement r = preimageField.newElement();

        for (int i = 0, base = 0; i < n; i++) {

            BigInteger u = vSyndrome.getAt(i).toBigInteger();

            for (int j = 0; j < k; j++) {
                BigInteger xj = sampleZ(u);
                r.getAt(base + j).set(xj);

                u = u.subtract(xj).divide(BigIntegerUtils.TWO);
            }

            base += k;
        }

        return r;
    }

    public Field getZq() {
        return Zq;
    }

    public Field getSyndromeField() {
        return syndromeField;
    }

    public Element decode(Element x) {
        return G.mul(x);
    }


    private BigInteger sampleZ(BigInteger u) {
        boolean uLSB = u.testBit(0);
        while (true) {
            BigInteger x = sampler.sample();
            if (x.testBit(0) ==  uLSB)
                return x;
        }
    }

    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        PrimitiveLatticePowerOfTwo primitiveLattice = new PrimitiveLatticePowerOfTwo(random, 10, 6, new ZGaussianSampler(100, random, 4));

        Element syndrome = primitiveLattice.getSyndromeField().newRandomElement();
        System.out.println("syndrome = " + syndrome);

        Element x = primitiveLattice.sampleD(syndrome);
        System.out.println("x = " + x);

        Element u = primitiveLattice.decode(x);
        System.out.println("u = " + u);

    }

}
