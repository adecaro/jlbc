package it.unisa.dia.gas.plaf.jlbc.field.vector;

import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class ImmutableVectorElement<E extends Element> extends VectorElement<E> {
    
    public ImmutableVectorElement(VectorElement element) {
        super(element.field);
        this.field = element.field;

        this.coeff.clear();
        for (int i = 0; i < field.n; i++)
            coeff.add((E) element.getAt(i).getImmutable());

        this.immutable = true;
    }

    @Override
    public VectorElement set(Element e) {
        return duplicate().set(e);    
    }

    @Override
    public VectorElement set(int value) {
        return duplicate().set(value);    
    }

    @Override
    public VectorElement set(BigInteger value) {
        return duplicate().set(value);    
    }

    @Override
    public VectorElement twice() {
        return duplicate().twice();    
    }

    @Override
    public VectorElement setToZero() {
        return duplicate().setToZero();    
    }

    @Override
    public VectorElement setToOne() {
        return duplicate().setToOne();    
    }

    @Override
    public VectorElement setToRandom() {
        return duplicate().setToRandom();    
    }

    @Override
    public int setFromBytes(byte[] source, int offset) {
        return duplicate().setFromBytes(source, offset);    
    }

    @Override
    public VectorElement square() {
        return duplicate().square();    
    }

    @Override
    public VectorElement invert() {
        return duplicate().invert();    
    }

    @Override
    public VectorElement negate() {
        return duplicate().negate();    
    }

    @Override
    public VectorElement add(Element e) {
        return duplicate().add(e);    
    }

    @Override
    public VectorElement mul(Element e) {
        return duplicate().mul(e);    
    }

    @Override
    public VectorElement mul(BigInteger n) {
        return duplicate().mul(n);    
    }

    @Override
    public VectorElement mulZn(Element e) {
        return (VectorElement) duplicate().mulZn(e);
    }

    @Override
    public VectorElement setFromHash(byte[] source, int offset, int length) {
        return duplicate().setFromHash(source, offset, length);    
    }

    @Override
    public int setFromBytes(byte[] source) {
        return duplicate().setFromBytes(source);    
    }

    @Override
    public VectorElement sub(Element element) {
        return duplicate().sub(element);    
    }

    @Override
    public Element div(Element element) {
        return duplicate().div(element);
    }

    @Override
    public VectorElement mul(int z) {
        return duplicate().mul(z);    
    }

}
