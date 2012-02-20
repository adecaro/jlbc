package it.unisa.dia.gas.plaf.jlbc.field.vector;

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
}
