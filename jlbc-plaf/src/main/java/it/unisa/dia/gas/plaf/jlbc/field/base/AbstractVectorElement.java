package it.unisa.dia.gas.plaf.jlbc.field.base;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.FieldOver;
import it.unisa.dia.gas.jpbc.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public abstract class AbstractVectorElement<E extends Element> extends AbstractElement implements Vector<E> {

    protected FieldOver field;
    protected List<E> coeff;


    protected AbstractVectorElement(FieldOver field) {
        super(field);

        this.field = field;
        this.coeff = new ArrayList<E>();
    }


    public E getAt(int index) {
        return coeff.get(index);
    }

    public int getSize() {
        return coeff.size();
    }

}