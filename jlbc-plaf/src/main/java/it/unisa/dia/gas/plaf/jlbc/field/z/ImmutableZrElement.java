package it.unisa.dia.gas.plaf.jlbc.field.z;

import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class ImmutableZrElement extends ZrElement {

    public ImmutableZrElement(ZrElement zrElement) {
        super(zrElement);
        this.immutable = true;
    }

    @Override
    public ZrElement set(Element value) {
        return duplicate().set(value);    
    }

    @Override
    public ZrElement set(int value) {
        return duplicate().set(value);    
    }

    @Override
    public ZrElement set(BigInteger value) {
        return duplicate().set(value);    
    }

    @Override
    public ZrElement twice() {
        return duplicate().twice();    
    }

    @Override
    public ZrElement mul(int z) {
        return duplicate().mul(z);    
    }

    @Override
    public ZrElement setToZero() {
        return duplicate().setToZero();    
    }

    @Override
    public ZrElement setToOne() {
        return duplicate().setToOne();    
    }

    @Override
    public ZrElement setToRandom() {
        return duplicate().setToRandom();    
    }

    @Override
    public ZrElement setFromHash(byte[] source, int offset, int length) {
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
    public ZrElement square() {
        return duplicate().square();    
    }

    @Override
    public ZrElement invert() {
        return duplicate().invert();    
    }

    @Override
    public ZrElement halve() {
        return duplicate().halve();    
    }

    @Override
    public ZrElement negate() {
        return duplicate().negate();    
    }

    @Override
    public ZrElement add(Element element) {
        return duplicate().add(element);    
    }

    @Override
    public ZrElement sub(Element element) {
        return duplicate().sub(element);    
    }

    @Override
    public ZrElement div(Element element) {
        return duplicate().div(element);    
    }

    @Override
    public ZrElement mul(Element element) {
        return duplicate().mul(element);    
    }

    @Override
    public ZrElement mul(BigInteger n) {
        return duplicate().mul(n);    
    }

    @Override
    public ZrElement mulZn(Element z) {
        return duplicate().mulZn(z);    
    }

}
