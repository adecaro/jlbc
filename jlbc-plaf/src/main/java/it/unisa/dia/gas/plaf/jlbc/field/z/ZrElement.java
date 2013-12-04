package it.unisa.dia.gas.plaf.jlbc.field.z;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.plaf.jlbc.util.Arrays;
import it.unisa.dia.gas.plaf.jlbc.util.math.BigIntegerUtils;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class ZrElement extends AbstractZElement {

//    protected BigInteger value;
    protected BigInteger order;


    public ZrElement(Field field) {
        super(field);

        this.value = BigInteger.ZERO;
        this.order = field.getOrder();
    }

    public ZrElement(Field field, BigInteger value) {
        super(field);

        this.value = value;
        this.order = field.getOrder();
    }

    public ZrElement(ZrElement zrElement) {
        super(zrElement.getField());

        this.value = zrElement.value;
        this.order = zrElement.field.getOrder();
    }


    @Override
    public Element getImmutable() {
        if (isImmutable())
            return this;

        return new ImmutableZrElement(this);
    }

    public ZrElement duplicate() {
        return new ZrElement(this);
    }

    public ZrElement set(Element value) {
        this.value = ((AbstractZElement) value).value.mod(order);

        return this;
    }

    public ZrElement set(int value) {
        this.value = BigInteger.valueOf(value).mod(order);

        return this;
    }

    public ZrElement set(BigInteger value) {
        this.value = value.mod(order);

        return this;
    }

    public boolean isZero() {
        return BigInteger.ZERO.equals(value);
    }

    public boolean isOne() {
        return BigInteger.ONE.equals(value);
    }

    public ZrElement twice() {
//        this.value = value.multiply(BigIntegerUtils.TWO).mod(order);
        this.value = value.add(value).mod(order);

        return this;
    }

    public ZrElement mul(int z) {
        this.value = this.value.multiply(BigInteger.valueOf(z)).mod(order);

        return this;
    }

    public ZrElement setToZero() {
        this.value = BigInteger.ZERO;

        return this;
    }

    public ZrElement setToOne() {
        this.value = BigInteger.ONE;

        return this;
    }

    public ZrElement setToRandom() {
        this.value = BigIntegerUtils.getRandom(order, field.getRandom());

        return this;
    }

    public ZrElement setFromHash(byte[] source, int offset, int length) {
        int i = 0, n, count = (order.bitLength() + 7) / 8;
        byte[] buf = new byte[count];

        byte counter = 0;
        boolean done = false;

        for (;;) {
            if (length >= count - i) {
                n = count - i;
                done = true;
            } else n = length;

            System.arraycopy(source, offset, buf, i, n);
            i += n;

            if (done)
                break;

            buf[i] = counter;
            counter++;
            i++;

            if (i == count) break;
        }
        assert (i == count);

        //mpz_import(z, count, 1, 1, 1, 0, buf);
        BigInteger z = new BigInteger(1, buf);

        while (z.compareTo(order) > 0) {
            z = z.divide(BigIntegerUtils.TWO);
        }

        this.value = z;

        return this;
    }

    public int setFromBytes(byte[] source) {
        return setFromBytes(source, 0);
    }

    public int setFromBytes(byte[] source, int offset) {
        byte[] buffer = Arrays.copyOf(source, offset, field.getLengthInBytes());
        value = new BigInteger(1, buffer).mod(order);

        return buffer.length;
    }

    public ZrElement square() {
//        value = value.modPow(BigIntegerUtils.TWO, order);
        value = value.multiply(value).mod(order);

        return this;
    }

    public ZrElement invert() {
        value = value.modInverse(order);

        return this;
    }

    public ZrElement halve() {
        value = value.multiply(((ZrField) field).twoInverse).mod(order);

        return this;
    }

    public ZrElement negate() {
        if (isZero()) {
            value = BigInteger.ZERO;
            return this;
        }

        value = order.subtract(value);

        return this;
    }

    public ZrElement add(Element element) {
        value = value.add(((AbstractZElement)element).value).mod(order);

        return this;
    }

    public ZrElement sub(Element element) {
        value = value.subtract(((ZrElement)element).value).mod(order);

        return this;
    }

    public ZrElement div(Element element) {
        value = value.multiply(((ZrElement)element).value.modInverse(order)).mod(order);

        return this;
    }

    public ZrElement mul(Element element) {
        value = value.multiply(((AbstractZElement)element).value).mod(order);

        return this;
    }

    public ZrElement mul(BigInteger n) {
        this.value = this.value.multiply(n).mod(order);

        return this;
    }

    public ZrElement mulZn(Element z) {
        this.value = this.value.multiply(z.toBigInteger()).mod(order);

        return this;
    }


    public boolean isEqual(Element e) {
        return this == e || value.compareTo(((ZrElement) e).value) == 0;

    }

    public BigInteger toBigInteger() {
        return value;
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = value.toByteArray();

        if (bytes.length > field.getLengthInBytes()) {
            // strip the zero prefix
            if (bytes[0] == 0 && bytes.length == field.getLengthInBytes() + 1) {
                // Remove it
                bytes = Arrays.copyOfRange(bytes, 1, bytes.length);
            } else
                throw new IllegalStateException("result has more than FixedLengthInBytes.");
        } else if (bytes.length < field.getLengthInBytes()) {
            byte[] result = new byte[field.getLengthInBytes()];
            System.arraycopy(bytes, 0, result, field.getLengthInBytes() - bytes.length, bytes.length);
            return result;
        }
        return bytes;
    }

    public int sign() {
        if (isZero())
            return 0;

        if (field.isOrderOdd()) {
            return BigIntegerUtils.isOdd(value) ? 1 : -1;
        } else {
            return value.add(value).compareTo(order);
        }
    }

    public String toString() {
        return value.toString();
    }

    public boolean equals(Object o) {
        if (o instanceof ZrElement)
            return isEqual((Element) o);
        return isEqual((ZrElement) o);
    }

    @Override
    public Element mod(BigInteger n) {
        this.value = this.value.mod(n);

        return this;
    }
}
