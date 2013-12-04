package it.unisa.dia.gas.plaf.jlbc.field.poly;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.FieldOver;
import it.unisa.dia.gas.jpbc.Polynomial;
import it.unisa.dia.gas.plaf.jlbc.field.base.AbstractElement;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public abstract class AbstractPolyElement<E extends Element> extends AbstractElement implements Polynomial<E> {

    protected FieldOver field;
    protected List<E> coefficients;


    protected AbstractPolyElement(FieldOver field) {
        super(field);

        this.field = field;
        this.coefficients = new ArrayList<E>();
    }


    public int getSize() {
        return coefficients.size();
    }

    public E getAt(int index) {
        return coefficients.get(index);
    }

    public List<E> getCoefficients() {
        return coefficients;
    }

    public E getCoefficient(int index) {
        return coefficients.get(index);
    }

    public int getDegree() {
        return coefficients.size();
    }

    public Element mod(BigInteger n) {
        for (E coefficient : coefficients) {
            coefficient.mod(n);
        }

        return this;
    }

}