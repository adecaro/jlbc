package it.unisa.dia.gas.plaf.jlbc.field.poly;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.FieldOver;
import it.unisa.dia.gas.jpbc.Polynomial;
import it.unisa.dia.gas.jpbc.Vector;
import it.unisa.dia.gas.plaf.jlbc.field.base.AbstractElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public abstract class AbstractPolyElement<E extends Element> extends AbstractElement implements Polynomial<E> {

    protected FieldOver field;
    protected List<E> coeff;


    protected AbstractPolyElement(FieldOver field) {
        super(field);

        this.field = field;
        this.coeff = new ArrayList<E>();
    }


    public int getSize() {
        return coeff.size();
    }

    public E getAt(int index) {
        return coeff.get(index);
    }

    public List<E> getCoefficients() {
        return coeff;
    }

    public E getCoefficient(int index) {
        return coeff.get(index);
    }

    public int getDegree() {
        return coeff.size();
    }

}