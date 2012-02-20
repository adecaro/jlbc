package it.unisa.dia.gas.plaf.jlbc.field.poly;

import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.plaf.jlbc.field.base.AbstractFieldOver;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class PolyField<F extends Field> extends AbstractFieldOver<F, PolyElement> {


    public PolyField(Random random, Field targetField) {
        super(random, (F) targetField);
    }

    public PolyField(F targetField) {
        super(new SecureRandom(), targetField);
    }


    public PolyElement newElement() {
        return new PolyElement(this);
    }

    public BigInteger getOrder() {
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public int getLengthInBytes() {
        throw new IllegalStateException("Not Implemented yet!!!");
    }

}
