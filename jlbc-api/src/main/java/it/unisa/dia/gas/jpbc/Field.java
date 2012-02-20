package it.unisa.dia.gas.jpbc;

import java.math.BigInteger;

/**
 * Represents an algebraic structure.
 *
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 * @since 1.0.0
 */
public interface Field<E extends Element> {

    /**
     * Returns a new element which lies in this field.
     *
     * @return a new element which lies in this field.
     * @since 1.0.0
     */
    E newElement();

    /**
     * Returns a new element whose value is passed as parameter.
     *
     * @param value the value of the new element.
     * @return a new element whose value is passed as parameter.
     * @see Element#set(int)
     * @since 1.0.0
     */
    E newElement(int value);

    /**
     * Returns a new element whose value is passed as parameter.
     * 
     * @param value the value of the new element.
     * @return a new element whose value is passed as parameter.
     * @see Element#set(java.math.BigInteger)
     * @since 1.0.0
     */
    E newElement(BigInteger value);

    /**
     * Returns a new element whose value is zero.
     *
     * @return a new element whose value is zero.
     * @since 1.0.0
     */
    E newZeroElement();

    /**
     * Returns a new element whose value is one.
     *
     * @return a new element whose value is one.
     * @since 1.0.0
     */
    E newOneElement();

    /**
     * Returns a new random element.
     *
     * @return a new random element.
     * @since 1.0.0
     */
    E newRandomElement();

    /**
     * Returns the order of this field.
     *
     * @return the order of this field. Returns 0 for infinite order.
     * @since 1.0.0
     */
    BigInteger getOrder();

    /**
     * Returns <tt>true></tt> if the order is odd,
     * false otherwise.
     *
     * @return <tt>true></tt> if the order is odd,
     * false otherwise.
     * @since 1.2.0
     */
    boolean isOrderOdd();

    /**
     * Returns the length in bytes needed to represent an element of this Field.
     *
     * @return the length in bytes needed to represent an element of this Field.
     * @since 1.0.0
     */
    int getLengthInBytes();

    /**
     * Computes the component-wise twice
     * in one shot.
     *
     * @param elements the vector of elements to be twiced.
     * @return elements twiced.
     * @since 1.1.0
     */
    Element[] twice(Element[] elements);

    /**
     * Computes the component-wise addition between a and b
     * in one shot.
     *
     * @param a an array of elements of the field
     * @param b another array of elements of the field to be added to a
     * @return the vector a modified by adding b.
     * @since 1.1.0
     */
    Element[] add(Element[] a, Element[] b);

}
