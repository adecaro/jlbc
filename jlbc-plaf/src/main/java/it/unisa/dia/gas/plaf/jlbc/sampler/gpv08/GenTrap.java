package it.unisa.dia.gas.plaf.jlbc.sampler.gpv08;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Sampler;
import it.unisa.dia.gas.plaf.jlbc.field.vector.MatrixField;
import it.unisa.dia.gas.plaf.jlbc.field.vector.VectorElement;
import it.unisa.dia.gas.plaf.jlbc.field.vector.VectorField;
import it.unisa.dia.gas.plaf.jlbc.field.z.ZrField;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public class GenTrap {


    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();

        int n = 10;
        int k = 6;
        int q = 1 << k;

        int barM = 2 * n;
        int w = n * k;
        int m = barM + w;

        ZrField Zq = new ZrField(q);

        // Construct primitive G
        VectorField<ZrField> gField = new VectorField<ZrField>(random, Zq, k);
        VectorElement gTemp = gField.newElement();
        long value = 1;
        for (int i=0; i < k; i++) {
            gTemp.getAt(i).set(BigInteger.valueOf(value));
            value = value << 1;
        }
        Element g = gTemp;

        System.out.println("g = " + g);

        MatrixField<ZrField> GField = new MatrixField<ZrField>(random, Zq, n,n*k);
        Element G = GField.newDiagonalElement(g);

        System.out.println("G = " + G);

        Sampler gSampler = new GPV08ZSampler(100, random, 5, Zq);

        // Construct Parity-check matrix
        // 1. Choose barA random in Z_q[n x barM]

        MatrixField<ZrField> hatAField = new MatrixField<ZrField>(random, Zq, n,n);
        Element In = hatAField.newIdentity();
        System.out.println("In = " + In);
        Element hatA = hatAField.newRandomElement();
        System.out.println("hatA = " + hatA);

        Element barA = hatAField.union(In, hatA);
        System.out.println("barA = " + barA);

        // 2. Sample R from Z[barM x w] using distribution D

        Element R = gSampler.sample(barM, w);
        System.out.println("R = " + R);

        // 3. Compute G-barA R
        Element A1 = G.duplicate().sub(barA.duplicate().mul(R));

        Element A = hatAField.union(barA, A1);
        // Return barA||A_1, R

        System.out.println("A = " + A);
        System.out.println("R = " + R);
    }


}
