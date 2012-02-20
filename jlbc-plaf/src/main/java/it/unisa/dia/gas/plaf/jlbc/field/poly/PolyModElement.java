package it.unisa.dia.gas.plaf.jlbc.field.poly;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Polynomial;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class PolyModElement<E extends Element> extends AbstractPolyElement<E> {
    protected PolyModField field;


    public PolyModElement(PolyModField field) {
        super(field);
        this.field = field;

        for (int i = 0; i < field.n; i++) {
            coeff.add((E) field.getTargetField().newElement());
        }
    }

    public PolyModElement(PolyModField field, List<E> coeff) {
        super(field);
        this.field = field;

        this.coeff = coeff;
    }


    @Override
    public Element getImmutable() {
        return new ImmutablePolyModElement<E>(this);
    }

    public PolyModField getField() {
        return field;
    }

    public PolyModElement<E> duplicate() {
        List<Element> duplicatedCoeff = new ArrayList<Element>(coeff.size());

        for (Element element : coeff) {
            duplicatedCoeff.add(element.duplicate());
        }

        return new PolyModElement(field, duplicatedCoeff);
    }

    public PolyModElement<E> set(Element e) {
        PolyModElement<E> element = (PolyModElement<E>) e;

        for (int i = 0; i < coeff.size(); i++) {
            coeff.get(i).set(element.coeff.get(i));
        }

        return this;
    }

    public PolyModElement<E> set(int value) {
        coeff.get(0).set(value);

        for (int i = 1; i < field.n; i++) {
            coeff.get(i).setToZero();
        }

        return this;
    }

    public PolyModElement<E> set(BigInteger value) {
        coeff.get(0).set(value);

        for (int i = 1; i < field.n; i++) {
            coeff.get(i).setToZero();
        }

        return this;
    }

    public PolyModElement<E> setToRandom() {
        for (int i = 0; i < field.n; i++) {
            coeff.get(i).setToRandom();
        }

        return this;
    }

    public PolyModElement<E> setFromHash(byte[] source, int offset, int length) {
        for (int i = 0; i < field.n; i++) {
            coeff.get(i).setFromHash(source, offset, length);
        }

        return this;
    }

    public PolyModElement<E> setToZero() {
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

    public PolyModElement<E> setToOne() {
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

    public PolyModElement<E> map(Element e) {
        coeff.get(0).set(e);
        for (int i = 1; i < field.n; i++) {
            coeff.get(i).setToZero();
        }

        return this;
    }

    public PolyModElement<E> twice() {
        for (int i = 0; i < field.n; i++) {
            coeff.get(i).twice();
        }

        return this;
    }

    public PolyModElement<E> square() {
        switch (field.n) {
            case 3:
                PolyModElement<E> p0 = field.newElement();
                Element c0 = field.getTargetField().newElement();
                Element c2 = field.getTargetField().newElement();

                Element c3 = p0.coeff.get(0);
                Element c1 = p0.coeff.get(1);

                c3.set(coeff.get(0)).mul(coeff.get(1));
                c1.set(coeff.get(0)).mul(coeff.get(2));
                coeff.get(0).square();

                c2.set(coeff.get(1)).mul(coeff.get(2));
                c0.set(coeff.get(2)).square();
                coeff.get(2).set(coeff.get(1)).square();

                coeff.get(1).set(c3).add(c3);

                c1.add(c1);

                coeff.get(2).add(c1);
                p0.set(field.xpwr[1]);
                p0.polymodConstMul(c0);
                add(p0);

                c2.add(c2);
                p0.set(field.xpwr[0]);
                p0.polymodConstMul(c2);
                add(p0);

                return this;

            default:
                squareInternal();
        }
        return this;
    }

    public PolyModElement<E> invert() {
        setFromPolyTruncate(polyInvert(field.irreduciblePoly.getField().newElement().setFromPolyMod(this)));
        return this;
    }

    public PolyModElement<E> negate() {
        for (Element e : coeff) {
            e.negate();
        }

        return this;
    }

    public PolyModElement<E> add(Element e) {
        PolyModElement<E> element = (PolyModElement<E>) e;

        for (int i = 0; i < field.n; i++) {
            coeff.get(i).add(element.coeff.get(i));
        }

        return this;
    }

    public PolyModElement<E> sub(Element e) {
        PolyModElement<E> element = (PolyModElement<E>) e;

        for (int i = 0; i < field.n; i++) {
            coeff.get(i).sub(element.coeff.get(i));
        }

        return this;
    }

    public PolyModElement<E> mul(Element e) {
        Polynomial<E> element = (Polynomial<E>) e;

        switch (field.n) {
            case 3:
                PolyModElement<E> p0 = field.newElement();
                Element c3 = field.getTargetField().newElement();
                Element c4 = field.getTargetField().newElement();

                kar_poly_2(coeff, c3, c4, coeff, element.getCoefficients(), p0.coeff);

                p0.set(field.xpwr[0]).polymodConstMul(c3);
                add(p0);
                p0.set(field.xpwr[1]).polymodConstMul(c4);
                add(p0);

                return this;

            default:

                Element[] high = new Element[field.n - 1];
                for (int i = 0, size = field.n - 1; i < size; i++) {
                    high[i] = field.getTargetField().newElement().setToZero();
                }
                PolyModElement<E> prod = field.newElement();
                p0 = field.newElement();
                Element c0 = field.getTargetField().newElement();

                for (int i = 0; i < field.n; i++) {
                    int ni = field.n - i;

                    int j = 0;
                    for (; j < ni; j++) {
                        c0.set(coeff.get(i)).mul(element.getCoefficient(j));
                        prod.coeff.get(i + j).add(c0);
                    }
                    for (; j < field.n; j++) {
                        c0.set(coeff.get(i)).mul(element.getCoefficient(j));
                        high[j - ni].add(c0);
                    }
                }

                for (int i = 0, size = field.n - 1; i < size; i++) {
                    p0.set(field.xpwr[i]).polymodConstMul(high[i]);
                    prod.add(p0);
                }

                set(prod);

                return this;
        }
    }

    public PolyModElement<E> mul(int z) {
        for (int i = 0; i < field.n; i++) {
            coeff.get(i).mul(z);
        }

        return this;
    }

    public PolyModElement<E> mul(BigInteger n) {
        for (int i = 0; i < field.n; i++) {
            coeff.get(i).mul(n);
        }

        return this;
    }

    public int sign() {
        int res = 0;
        for (int i = 0, size = coeff.size(); i < size; i++) {
            res = coeff.get(i).sign();
            if (res != 0)
                break;
        }
        return res;
    }

    public boolean isEqual(Element e) {
        PolyModElement<E> element = (PolyModElement<E>) e;

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
            len += coeff.get(i).setFromBytes(source, len);
        }
        return len - offset;
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
        StringBuilder buffer = new StringBuilder("[");
        for (Element e : coeff) {
            buffer.append(e).append(", ");
        }
        buffer.append("]");
        return buffer.toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof PolyModElement)
            return isEqual((Element) obj);
        return super.equals(obj);
    }


    public PolyModElement<E> setFromPolyTruncate(PolyElement<E> element) {
        int n = element.getCoefficients().size();
        if (n > field.n)
            n = field.n;

        int i = 0;
        for (; i < n; i++) {
            coeff.get(i).set(element.getCoefficients().get(i));
        }

        for (; i < field.n; i++) {
            coeff.get(i).setToZero();
        }

        return this;
    }

    public PolyModElement<E> polymodConstMul(Element e) {
        //a lies in R, e in R[x]
        for (int i = 0, n = coeff.size(); i < n; i++) {
            coeff.get(i).mul(e);
        }

        return this;
    }


    protected void squareInternal() {
        List<E> dst;
        List<E> src = coeff;

        int n = field.n;

        PolyModElement<E> prod, p0;
        Element c0;
        int i, j;

        Element high[] = new Element[n - 1];

        for (i = 0; i < n - 1; i++) {
            high[i] = field.getTargetField().newElement().setToZero();
        }

        prod = field.newElement();
        dst = prod.coeff;
        p0 = field.newElement();
        c0 = field.getTargetField().newElement();

        for (i = 0; i < n; i++) {
            int twicei = 2 * i;

            c0.set(src.get(i)).square();

            if (twicei < n) {
                dst.get(twicei).add(c0);
            } else {
                high[twicei - n].add(c0);
            }

            for (j = i + 1; j < n - i; j++) {
                c0.set(src.get(i)).mul(src.get(j));
                c0.add(c0);
                dst.get(i + j).add(c0);
            }

            for (; j < n; j++) {
                c0.set(src.get(i)).mul(src.get(j));
                c0.add(c0);
                high[i + j - n].add(c0);
            }
        }

        for (i = 0; i < n - 1; i++) {
            p0.set(field.xpwr[i]).polymodConstMul(high[i]);
            prod.add(p0);
        }

        set(prod);
    }

    /**
     * Karatsuba for degree 2 polynomials
     *
     * @param dst
     * @param c3
     * @param c4
     * @param s1
     * @param s2
     * @param scratch
     */
    protected void kar_poly_2(List<E> dst, Element c3, Element c4, List<E> s1, List<E> s2, List<E> scratch) {
        Element c01, c02, c12;

        c12 = scratch.get(0);
        c02 = scratch.get(1);
        c01 = scratch.get(2);

        c3.set(s1.get(0)).add(s1.get(1));
        c4.set(s2.get(0)).add(s2.get(1));
        c01.set(c3).mul(c4);
        c3.set(s1.get(0)).add(s1.get(2));
        c4.set(s2.get(0)).add(s2.get(2));
        c02.set(c3).mul(c4);
        c3.set(s1.get(1)).add(s1.get(2));
        c4.set(s2.get(1)).add(s2.get(2));
        c12.set(c3).mul(c4);
        dst.get(1).set(s1.get(1)).mul(s2.get(1));

        //constant term
        dst.get(0).set(s1.get(0)).mul(s2.get(0));

        //coefficient of x^4
        c4.set(s1.get(2)).mul(s2.get(2));

        //coefficient of x^3
        c3.set(dst.get(1)).add(c4);
        c3.set(c12.duplicate().sub(c3));

        //coefficient of x^2
        dst.get(2).set(c4).add(dst.get(0));
        c02.sub(dst.get(2));
        dst.get(2).set(dst.get(1)).add(c02);

        //coefficient of x
        c01.sub(dst.get(0));
        dst.set(1, (E) c01.duplicate().sub(dst.get(1)));
    }

    protected PolyElement polyInvert(PolyElement f) {
        PolyField<Field> polyField = f.getField();

        PolyElement q = polyField.newElement();

        PolyElement b0 = polyField.newZeroElement();
        PolyElement b1 = polyField.newOneElement();
        PolyElement b2 = polyField.newElement();

        PolyElement r0 = field.irreduciblePoly.duplicate();
        PolyElement r1 = f.duplicate();
        PolyElement r2 = polyField.newElement();

        Element inv = f.getField().getTargetField().newElement();

        while (true) {
            PolyUtils.div(q, r2, r0, r1);
            if (r2.isZero())
                break;

            b2.set(b1).mul(q);
            b2.set(b0.duplicate().sub(b2));

            b0.set(b1);
            b1.set(b2);

            r0.set(r1);
            r1.set(r2);
        }

        inv.set(r1.getCoefficient(0)).invert();
        return PolyUtils.constMul(inv, b1);
    }

}