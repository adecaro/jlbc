package it.unisa.dia.gas.plaf.jlbc.field.z;

import it.unisa.dia.gas.plaf.jlbc.field.base.AbstractField;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class ZField extends AbstractField<ZElement> {

    public ZField() {
        this(new SecureRandom());
    }

    public ZField(Random random) {
        super(random);
    }


    public ZElement newElement() {
        return new ZElement(this);
    }

    public BigInteger getOrder() {
        return BigInteger.ZERO;
    }

    public int getLengthInBytes() {
        return -1;
    }
    
}
