package it.unisa.dia.gas.plaf.jlbc.field.base;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.FieldOver;

import java.util.Random;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public abstract class AbstractFieldOver<F extends Field, E extends Element> extends AbstractField<E> implements FieldOver<F, E> {
    protected F targetField;

    protected AbstractFieldOver(Random random, F targetField) {
        super(random);
        this.targetField = targetField;
    }

//    public AbstractFieldOver(F targetField) {
//        this(new SecureRandom(), targetField);
//    }


    public F getTargetField() {
        return targetField;
    }

}
