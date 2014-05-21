package it.unisa.dia.gas.plaf.jlbc.field.vector;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.plaf.jlbc.field.base.AbstractFieldOver;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class MatrixField<F extends Field> extends AbstractFieldOver<F, MatrixElement> {
    protected int n, m, lenInBytes;


    public MatrixField(Random random, F targetField, int n, int m) {
        super(random, targetField);

        this.n = n;
        this.m = m;
        this.lenInBytes = n * m * targetField.getLengthInBytes();
    }


    public MatrixElement newElement() {
        return new MatrixElement(this);
    }

    public BigInteger getOrder() {
        throw new IllegalStateException("Not implemented yet!!!");
    }

    public int getLengthInBytes() {
        return lenInBytes;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public Element newIdentity() {
        MatrixElement m = new MatrixElement(this);

        for (int i = 0; i < n; i++) {
            m.getAt(i, i).setToOne();
        }
        return m;
    }

    public Element union(Element a, Element b) {
        MatrixElement a1 = (MatrixElement) a;
        MatrixElement b1 = (MatrixElement) b;

        MatrixField f = new MatrixField(random, targetField, a1.getField().n,
                a1.getField().m + b1.getField().m);

        MatrixElement c = f.newElement();
        for (int i = 0; i < f.n; i++) {
            for (int j = 0; j < a1.getField().m; j++) {
                c.getAt(i,j).set(a1.getAt(i, j));
            }

            for (int j = 0; j < b1.getField().m; j++) {
                c.getAt(i,a1.getField().m+j).set(b1.getAt(i, j));
            }
        }


        return c;

    }

    public MatrixElement newDiagonalElement(Element g) {
        MatrixElement r = newElement();

        if (g instanceof VectorElement) {
            VectorElement vg = (VectorElement) g;

            int col = 0;
            for (int row = 0; row < n; row ++) {

                for (int k = 0; k < vg.getSize(); k++) {
                    r.getAt(row, col).set(vg.getAt(k));
                    col += 1;
                }
            }

        }

        return r;
    }
}
