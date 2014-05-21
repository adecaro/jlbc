package it.unisa.dia.gas.plaf.jlbc.sampler.gpv08;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jlbc.field.vector.MatrixElement;
import it.unisa.dia.gas.plaf.jlbc.field.vector.MatrixField;
import it.unisa.dia.gas.plaf.jlbc.field.vector.VectorField;
import it.unisa.dia.gas.plaf.jlbc.field.z.ZrField;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public class HardLatticePowerOfTwo extends PrimitiveLatticePowerOfTwo {

    protected Element A;
    protected Element barA;
    protected Element R;

    public HardLatticePowerOfTwo(SecureRandom random, int n, int k, Samp<BigInteger> sampler) {
        super(random, n, k, sampler);

        // Construct Parity-check matrix
        // 1. Choose barA random in Z_q[n x barM]
        int barM = 2 * n;
        int w = n * k;
        int m = barM + w;

        MatrixField<ZrField> hatAField = new MatrixField<ZrField>(random, Zq, n,n);
        Element In = hatAField.newIdentity();
        System.out.println("In = " + In);
        Element hatA = hatAField.newRandomElement();
        System.out.println("hatA = " + hatA);

        barA = hatAField.union(In, hatA);
        System.out.println("barA = " + barA);

        // 2. Sample R from Z[barM x w] using distribution D

        R = sample(barM, w);
        System.out.println("R = " + R);

        // 3. Compute G-barA R
        Element A1 = G.duplicate().sub(barA.duplicate().mul(R));

        A = hatAField.union(barA, A1);

        System.out.println("A = " + A);
        System.out.println("R = " + R);
    }


    private Element sample(int n, int m) {
        MatrixField<ZrField> RField = new MatrixField<ZrField>(random, Zq, n, m);
        MatrixElement R = RField.newElement();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                R.getAt(i,j).set(sampler.sample());
            }
        }

        return R;
    }


    @Override
    public Element sampleD(Element syndrome) {
        Element z = super.sampleD(syndrome);

        Element z1 = R.mul(z);

        return ((VectorField)z1.getField()).union(z1,z);
    }

    @Override
    public Element decode(Element x) {
        return A.mul(x);
    }



    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        HardLatticePowerOfTwo lattice = new HardLatticePowerOfTwo(random, 10, 6, new ZGaussianSampler(100, random, 4));

        Element syndrome = lattice.getSyndromeField().newRandomElement();
        System.out.println("syndrome = " + syndrome);

        Element x = lattice.sampleD(syndrome);
        System.out.println("x = " + x);

        Element u = lattice.decode(x);
        System.out.println("u = " + u);
    }

}
