package it.unisa.dia.gas.plaf.jlbc.field.poly;

import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class ImmutablePolyModElement<E extends Element> extends PolyModElement<E> {

    public ImmutablePolyModElement(PolyModElement<E> element) {
        super(element.getField());

        coefficients.clear();
        for (int i = 0; i < field.n; i++) {
            coefficients.add((E) element.getCoefficient(i).getImmutable());
        }
        this.immutable = true;
    }


    @Override
    public Element getImmutable() {
        return this;
    }

    @Override
    public PolyModElement<E> set(Element e) {
        return duplicate().set(e);    
    }

    @Override
    public PolyModElement<E> set(int value) {
        return duplicate().set(value);    
    }

    @Override
    public PolyModElement<E> set(BigInteger value) {
        return duplicate().set(value);    
    }

    @Override
    public PolyModElement<E> setToRandom() {
        return duplicate().setToRandom();    
    }

    @Override
    public PolyModElement<E> setFromHash(byte[] source, int offset, int length) {
        return duplicate().setFromHash(source, offset, length);    
    }

    @Override
    public PolyModElement<E> setToZero() {
        return duplicate().setToZero();    
    }

    @Override
    public PolyModElement<E> setToOne() {
        return duplicate().setToOne();    
    }

    @Override
    public PolyModElement<E> map(Element e) {
        return duplicate().map(e);    
    }

    @Override
    public PolyModElement<E> twice() {
        return duplicate().twice();    
    }

    @Override
    public PolyModElement<E> square() {
        return duplicate().square();    
    }

    @Override
    public PolyModElement<E> invert() {
        return duplicate().invert();    
    }

    @Override
    public PolyModElement<E> negate() {
        return duplicate().negate();    
    }

    @Override
    public PolyModElement<E> add(Element e) {
        return duplicate().add(e);    
    }

    @Override
    public PolyModElement<E> sub(Element e) {
        return duplicate().sub(e);    
    }

    @Override
    public PolyModElement<E> mul(Element e) {
        return duplicate().mul(e);    
    }

    @Override
    public PolyModElement<E> mul(int z) {
        return duplicate().mul(z);    
    }

    @Override
    public PolyModElement<E> mul(BigInteger n) {
        return duplicate().mul(n);    
    }

    @Override
    public int setFromBytes(byte[] source) {
        return duplicate().setFromBytes(source);    
    }

    @Override
    public int setFromBytes(byte[] source, int offset) {
        return duplicate().setFromBytes(source, offset);    
    }

    @Override
    public Element halve() {
        return duplicate().halve();   
    }

    @Override
    public Element div(Element element) {
        return duplicate().div(element);   
    }

    @Override
    public Element mulZn(Element z) {
        return duplicate().mulZn(z);   
    }

}
