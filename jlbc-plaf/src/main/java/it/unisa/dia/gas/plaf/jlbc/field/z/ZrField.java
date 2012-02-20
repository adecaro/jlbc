package it.unisa.dia.gas.plaf.jlbc.field.z;

import it.unisa.dia.gas.plaf.jlbc.field.base.AbstractField;
import it.unisa.dia.gas.plaf.jlbc.util.math.BigIntegerUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class ZrField extends AbstractField<ZrElement> {
    protected BigInteger order;
    protected ZrElement nqr;
    protected int fixedLengthInBytes;
    protected BigInteger twoInverse;


    public ZrField(BigInteger order) {
        this(new SecureRandom(), order, null);
    }

    public ZrField(Random random, BigInteger order) {
        this(random, order, null);
    }

    public ZrField(BigInteger order, BigInteger nqr) {
        this(new SecureRandom(), order, nqr);
    }

    public ZrField(Random random, BigInteger order, BigInteger nqr) {
        super(random);
        this.order = order;
        this.orderIsOdd = BigIntegerUtils.isOdd(order);

        this.fixedLengthInBytes = (order.bitLength() + 7) / 8;

//        this.twoInverse = BigIntegerUtils.TWO.modInverse(order);

        if (nqr != null)
            this.nqr = newElement().set(nqr);
    }


    public ZrElement newElement() {
        return new ZrElement(this);
    }

    public BigInteger getOrder() {
        return order;
    }

    public int getLengthInBytes() {
        return fixedLengthInBytes;
    }
    
}
