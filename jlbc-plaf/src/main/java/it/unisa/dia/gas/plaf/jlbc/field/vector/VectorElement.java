package it.unisa.dia.gas.plaf.jlbc.field.vector;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jlbc.field.base.AbstractVectorElement;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class VectorElement<E extends Element> extends AbstractVectorElement<E> {
    protected VectorField field;


    public VectorElement(VectorField field) {
        super(field);
        this.field = field;

        for (int i = 0; i < field.n; i++) {
            coeff.add((E) field.getTargetField().newElement());
        }
    }

    public VectorElement(VectorField field, List<E> coeff) {
        super(field);
        this.field = field;

        this.coeff = coeff;
    }

    public VectorElement(VectorField field, Element[] coeff) {
        super(field);
        this.field = field;


        for (Element element : coeff) {
            this.coeff.add((E) element);
        }
    }

    public VectorField getField() {
        return field;
    }

    public VectorElement<E> duplicate() {
        List<Element> duplicatedCoeff = new ArrayList<Element>(coeff.size());

        for (Element element : coeff) {
            duplicatedCoeff.add(element.duplicate());
        }

        return new VectorElement(field, duplicatedCoeff);
    }

    public Element getImmutable() {
        return new ImmutableVectorElement<E>(this);
    }

    public VectorElement<E> set(Element e) {
        VectorElement<E> element = (VectorElement<E>) e;

        for (int i = 0; i < coeff.size(); i++) {
            coeff.get(i).set(element.coeff.get(i));
        }

        return this;
    }

    public VectorElement<E> set(int value) {
        coeff.get(0).set(value);

        for (int i = 1; i < field.n; i++) {
            coeff.get(i).setToZero();
        }

        return this;
    }

    public VectorElement<E> set(BigInteger value) {
        coeff.get(0).set(value);

        for (int i = 1; i < field.n; i++) {
            coeff.get(i).setToZero();
        }

        return this;
    }

    public VectorElement<E> setToRandom() {
        for (int i = 0; i < field.n; i++) {
            coeff.get(i).setToRandom();
        }

        return this;
    }

    public VectorElement<E> setFromHash(byte[] source, int offset, int length) {
        for (int i = 0; i < field.n; i++) {
            coeff.get(i).setFromHash(source, offset, length);
        }

        return this;
    }

    public VectorElement<E> setToZero() {
        for (int i = 0; i < field.n; i++) {
            coeff.get(i).setToZero();
        }

        return this;
    }

    public boolean isZero() {
        for (int i = 0; i < field.n; i++) {
            if (!coeff.get(i).isZero())
                return false;
        }
        return true;
    }

    public VectorElement<E> setToOne() {
        coeff.get(0).setToOne();

        for (int i = 1; i < field.n; i++) {
            coeff.get(i).setToZero();
        }

        return this;
    }

    public boolean isOne() {
        if (!coeff.get(0).isOne())
            return false;

        for (int i = 1; i < field.n; i++) {
            if (!coeff.get(i).isZero())
                return false;
        }

        return true;
    }

    public VectorElement<E> map(Element e) {
        coeff.get(0).set(e);
        for (int i = 1; i < field.n; i++) {
            coeff.get(i).setToZero();
        }

        return this;
    }

    public VectorElement<E> twice() {
        for (int i = 0; i < field.n; i++) {
            coeff.get(i).twice();
        }

        return this;
    }

    public VectorElement<E> square() {
        for (int i = 0; i < field.n; i++) {
            coeff.get(i).square();
        }

        return this;
    }

    public VectorElement<E> invert() {
        throw new IllegalStateException("Not implemented yet!!!");
    }

    public VectorElement<E> negate() {
        for (Element e : coeff) {
            e.negate();
        }

        return this;
    }

    public VectorElement<E> add(Element e) {
        VectorElement<E> element = (VectorElement<E>) e;

        for (int i = 0; i < field.n; i++) {
            coeff.get(i).add(element.coeff.get(i));
        }

        return this;
    }

    public VectorElement<E> sub(Element e) {
        VectorElement<E> element = (VectorElement<E>) e;

        for (int i = 0; i < field.n; i++) {
            coeff.get(i).sub(element.coeff.get(i));
        }

        return this;
    }

    public VectorElement<E> mul(Element e) {
        if (field.getTargetField().equals(e.getField())) {
            for (int i = 0; i < field.n; i++) {
                coeff.get(i).mul(e);
            }
        }

        return this;
    }

    public VectorElement<E> mul(int z) {
        for (int i = 0; i < field.n; i++) {
            coeff.get(i).mul(z);
        }

        return this;
    }

    public VectorElement<E> mul(BigInteger n) {
        for (int i = 0; i < field.n; i++) {
            coeff.get(i).mul(n);
        }

        return this;
    }

    public int sign() {
        throw new IllegalStateException("Not implemented yet!!!");
    }

    public boolean isEqual(Element e) {
        VectorElement<E> element = (VectorElement<E>) e;

        for (int i = 0; i < field.n; i++) {
            if (!coeff.get(i).isEqual(element.coeff.get(i)))
                return false;
        }

        return true;
    }

    public int setFromBytes(byte[] source) {
        return setFromBytes(source, 0);
    }

    public int setFromBytes(byte[] source, int offset) {
        int len = offset;
        for (int i = 0, size = coeff.size(); i < size; i++) {
            len+=coeff.get(i).setFromBytes(source, len);
        }
        return len-offset;
    }

    public byte[] toBytes() {
        byte[] buffer = new byte[field.getLengthInBytes()];
        int targetLB = field.getTargetField().getLengthInBytes();

        for (int len = 0, i = 0, size = coeff.size(); i < size; i++, len += targetLB) {
            byte[] temp = coeff.get(i).toBytes();
            System.arraycopy(temp, 0, buffer, len, targetLB);
        }
        return buffer;
    }

    public BigInteger toBigInteger() {
        return coeff.get(0).toBigInteger();
    }


    public String toString() {
        StringBuffer buffer = new StringBuffer("[");
        for (Element e : coeff) {
            buffer.append(e).append(", ");
        }
        buffer.append("]");
        return buffer.toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof VectorElement)
            return isEqual((Element) obj);
        return super.equals(obj);
    }

}