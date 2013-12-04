package it.unisa.dia.gas.plaf.jlbc.field.vector;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.FieldOver;
import it.unisa.dia.gas.jpbc.Vector;
import it.unisa.dia.gas.plaf.jlbc.field.base.AbstractElement;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class MatrixElement<E extends Element> extends AbstractElement {
    protected MatrixField field;

    protected Element[][] matrix;

    public MatrixElement(MatrixField field) {
        super(field);
        this.field = field;

        this.matrix = new Element[field.n][field.m];

        for (int i = 0; i < field.n; i++) {
            for (int j = 0; j < field.m; j++) {
                matrix[i][j] = field.getTargetField().newElement();
            }
        }
    }


    public E getAt(int row, int col) {
        return (E) matrix[row][col];
    }


    public MatrixField getField() {
        return field;
    }

    public MatrixElement<E> duplicate() {
//        List<Element> duplicatedCoeff = new ArrayList<Element>(coefficients.size());
//
//        for (Element element : coefficients) {
//            duplicatedCoeff.add(element.duplicate());
//        }
//
//        return new MatrixElement(field, duplicatedCoeff);
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public Element getImmutable() {
//        return new ImmutableVectorElement<E>(this);
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> set(Element e) {
//        MatrixElement<E> element = (MatrixElement<E>) e;
//
//        for (int i = 0; i < coefficients.size(); i++) {
//            coefficients.get(i).set(element.coefficients.get(i));
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> set(int value) {
//        coefficients.get(0).set(value);
//
//        for (int i = 1; i < field.n; i++) {
//            coefficients.get(i).setToZero();
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> set(BigInteger value) {
//        coefficients.get(0).set(value);
//
//        for (int i = 1; i < field.n; i++) {
//            coefficients.get(i).setToZero();
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> setToRandom() {
//        for (int i = 0; i < field.n; i++) {
//            coefficients.get(i).setToRandom();
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> setFromHash(byte[] source, int offset, int length) {
//        for (int i = 0; i < field.n; i++) {
//            coefficients.get(i).setFromHash(source, offset, length);
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> setToZero() {
//        for (int i = 0; i < field.n; i++) {
//            coefficients.get(i).setToZero();
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public boolean isZero() {
//        for (int i = 0; i < field.n; i++) {
//            if (!coefficients.get(i).isZero())
//                return false;
//        }
//        return true;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> setToOne() {
//        coefficients.get(0).setToOne();
//
//        for (int i = 1; i < field.n; i++) {
//            coefficients.get(i).setToZero();
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public boolean isOne() {
//        if (!coefficients.get(0).isOne())
//            return false;
//
//        for (int i = 1; i < field.n; i++) {
//            if (!coefficients.get(i).isZero())
//                return false;
//        }
//
//        return true;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> map(Element e) {
//        coefficients.get(0).set(e);
//        for (int i = 1; i < field.n; i++) {
//            coefficients.get(i).setToZero();
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> twice() {
//        for (int i = 0; i < field.n; i++) {
//            coefficients.get(i).twice();
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> square() {
//        for (int i = 0; i < field.n; i++) {
//            coefficients.get(i).square();
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> invert() {
        throw new IllegalStateException("Not implemented yet!!!");
    }

    public MatrixElement<E> negate() {
        for (int i = 0; i < field.n; i++) {
            for (int j = 0; j < field.m; j++) {
                matrix[i][j].negate();
            }
        }

        return this;
    }

    public MatrixElement<E> add(Element e) {
//        MatrixElement<E> element = (MatrixElement<E>) e;
//
//        for (int i = 0; i < field.n; i++) {
//            coefficients.get(i).add(element.coefficients.get(i));
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> sub(Element e) {
//        MatrixElement<E> element = (MatrixElement<E>) e;
//
//        for (int i = 0; i < field.n; i++) {
//            coefficients.get(i).sub(element.coefficients.get(i));
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public Element mul(Element e) {
        if (field.getTargetField().equals(e.getField())) {
            Element result = e.duplicate();
            if (field.n == 1) {
                for (int j = 0; j < field.m; j++) {
                    result.add(matrix[0][j]);
                }
            } else if (field.m == 1) {
                for (int i = 0; i < field.n; i++) {
                    result.add(matrix[i][0]);
                }
            } else
                throw new IllegalArgumentException("Cannot multiply this way.");

            return result;
        } else if (e instanceof Vector) {
            Vector ve = (Vector) e;
            if (field.getTargetField().equals(((FieldOver) ve.getField()).getTargetField())) {
                if (ve.getSize() == 1 ){
                    Element result = ve.getAt(0).duplicate();
                    if (field.n == 1) {
                        for (int j = 0; j < field.m; j++) {
                            result.add(matrix[0][j]);
                        }
                    } else if (field.m == 1) {
                        for (int i = 0; i < field.n; i++) {
                            result.add(matrix[i][0]);
                        }
                    } else
                        throw new IllegalArgumentException("Cannot multiply this way.");

                    return result;
                }
            }
            throw new IllegalStateException("Not Implemented yet!!!");
        }

        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> mul(int z) {
//        for (int i = 0; i < field.n; i++) {
//            coefficients.get(i).mul(z);
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public MatrixElement<E> mul(BigInteger n) {
//        for (int i = 0; i < field.n; i++) {
//            coefficients.get(i).mul(n);
//        }
//
//        return this;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public int sign() {
        throw new IllegalStateException("Not implemented yet!!!");
    }

    public boolean isEqual(Element e) {
//        MatrixElement<E> element = (MatrixElement<E>) e;
//
//        for (int i = 0; i < field.n; i++) {
//            if (!coefficients.get(i).isEqual(element.coefficients.get(i)))
//                return false;
//        }
//
//        return true;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public int setFromBytes(byte[] source) {
        return setFromBytes(source, 0);
    }

    public int setFromBytes(byte[] source, int offset) {
//        int len = offset;
//        for (int i = 0, size = coefficients.size(); i < size; i++) {
//            len+=coefficients.get(i).setFromBytes(source, len);
//        }
//        return len-offset;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public byte[] toBytes() {
//        byte[] buffer = new byte[field.getLengthInBytes()];
//        int targetLB = field.getTargetField().getLengthInBytes();
//
//        for (int len = 0, i = 0, size = coefficients.size(); i < size; i++, len += targetLB) {
//            byte[] temp = coefficients.get(i).toBytes();
//            System.arraycopy(temp, 0, buffer, len, targetLB);
//        }
//        return buffer;
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public BigInteger toBigInteger() {
        return matrix[0][0].toBigInteger();
    }


    public String toString() {
//        StringBuffer buffer = new StringBuffer("[");
//        for (Element e : coefficients) {
//            buffer.append(e).append(", ");
//        }
//        buffer.append("]");
//        return buffer.toString();
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public boolean equals(Object obj) {
        if (obj instanceof MatrixElement)
            return isEqual((Element) obj);
        return super.equals(obj);
    }

}