package it.unisa.dia.gas.plaf.jlbc.field.vector;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.plaf.jlbc.field.base.AbstractFieldOver;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class VectorField<F extends Field> extends AbstractFieldOver<F, VectorElement> {
    protected int n, lenInBytes;


    public VectorField(Random random, F targetField, int n) {
        super(random, targetField);

        this.n = n;
        this.lenInBytes = n * targetField.getLengthInBytes();
    }


    public VectorElement newElement() {
        return new VectorElement(this);
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

    public Element newElement(Element[] elements) {
        return new VectorElement(this, elements);
    }

    public Element union(Element a, Element b) {
        VectorElement va = (VectorElement) a;
        VectorElement vb = (VectorElement) b;

        VectorField f = new VectorField(random, targetField, va.getSize() + vb.getSize());
        VectorElement r = f.newElement();
        int counter = 0;

        for (int i = 0; i < va.getSize(); i++)
            r.getAt(counter++).set(va.getAt(i));

        for (int i = 0; i < vb.getSize(); i++)
            r.getAt(counter++).set(vb.getAt(i));

        return r;
    }

}
