package it.unisa.dia.gas.plaf.jlbc.field.base;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public abstract class AbstractField<E extends Element> implements Field<E> {

    protected boolean orderIsOdd = false;
    protected Random random;


    protected AbstractField(Random random) {
        this.random = random;
    }


    public E newElement(int value) {
        E e = newElement();
        e.set(value);

        return e;
    }

    public E newElement(BigInteger value) {
        E e = newElement();
        e.set(value);

        return e;
    }

    public E newZeroElement() {
        E e = newElement();
        e.setToZero();

        return e;
    }

    public E newOneElement() {
        E e = newElement();
        e.setToOne();

        return e;
    }

    public E newRandomElement() {
        E e = newElement();
        e.setToRandom();

        return e;
    }

    public boolean isOrderOdd() {
        return orderIsOdd;
    }

    public Element[] twice(Element[] elements) {
        for (Element element : elements) {
            element.twice();
        }

        return elements;
    }

    public Element[] add(Element[] a, Element[] b) {
        for (int i = 0; i < a.length; i++) {
            a[i].add(b[i]);
        }

        return a;
    }

    public Random getRandom() {
        return random;
    }
}
