package it.unisa.dia.gas.jpbc;

/**
 * This interface represents an algebraic structure defined
 * over another.
 *
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 * @since 1.0.0
 * @see Field
 */
public interface FieldOver<F extends Field, E extends Element> extends Field<E> {

    /**
     * Returns the target field.
     *
     * @return the target field.
     */
    F getTargetField();

}
