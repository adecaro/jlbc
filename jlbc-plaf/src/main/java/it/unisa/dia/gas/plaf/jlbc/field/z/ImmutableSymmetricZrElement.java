package it.unisa.dia.gas.plaf.jlbc.field.z;

import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class ImmutableSymmetricZrElement extends SymmetricZrElement {

    public ImmutableSymmetricZrElement(SymmetricZrElement SymmetricZrElement) {
        super(SymmetricZrElement);
        this.immutable = true;
    }

    @Override
    public SymmetricZrElement set(Element value) {
        return duplicate().set(value);    
    }

    @Override
    public SymmetricZrElement set(int value) {
        return duplicate().set(value);    
    }

    @Override
    public SymmetricZrElement set(BigInteger value) {
        return duplicate().set(value);    
    }

    @Override
    public SymmetricZrElement twice() {
        return duplicate().twice();    
    }

    @Override
    public SymmetricZrElement mul(int z) {
        return duplicate().mul(z);    
    }

    @Override
    public SymmetricZrElement setToZero() {
        return duplicate().setToZero();    
    }

    @Override
    public SymmetricZrElement setToOne() {
        return duplicate().setToOne();    
    }

    @Override
    public SymmetricZrElement setToRandom() {
        return duplicate().setToRandom();    
    }

    @Override
    public SymmetricZrElement setFromHash(byte[] source, int offset, int length) {
        return duplicate().setFromHash(source, offset, length);    
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
    public SymmetricZrElement square() {
        return duplicate().square();    
    }

    @Override
    public SymmetricZrElement invert() {
        return duplicate().invert();    
    }

    @Override
    public SymmetricZrElement halve() {
        return duplicate().halve();    
    }

    @Override
    public SymmetricZrElement negate() {
        return duplicate().negate();    
    }

    @Override
    public SymmetricZrElement add(Element element) {
        return duplicate().add(element);    
    }

    @Override
    public SymmetricZrElement sub(Element element) {
        return duplicate().sub(element);    
    }

    @Override
    public SymmetricZrElement div(Element element) {
        return duplicate().div(element);    
    }

    @Override
    public SymmetricZrElement mul(Element element) {
        return duplicate().mul(element);    
    }

    @Override
    public SymmetricZrElement mul(BigInteger n) {
        return duplicate().mul(n);    
    }

    @Override
    public SymmetricZrElement mulZn(Element z) {
        return duplicate().mulZn(z);    
    }

}
