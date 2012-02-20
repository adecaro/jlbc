package it.unisa.dia.gas.jpbc;

/**
 * This element represents a matrix through its coordinates.
 *
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 * @since 1.2.0
 */
public interface Matrix<E extends Element> extends Element {

    int getN();
    
    int getM();

    E getAt(int row, int col);

    Vector<E> mul(Vector<E> v);
    
}
