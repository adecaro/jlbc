package it.unisa.dia.gas.plaf.jlbc.sampler.gpv08;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Sampler;
import it.unisa.dia.gas.jpbc.Vector;
import it.unisa.dia.gas.plaf.jlbc.field.vector.MatrixElement;
import it.unisa.dia.gas.plaf.jlbc.field.vector.MatrixField;
import it.unisa.dia.gas.plaf.jlbc.field.vector.VectorField;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class GPV08FieldSampler extends GPV08DSampler implements Sampler {

    private Field baseField;


    public GPV08FieldSampler(int strength, Random random, int sigma) {
        super(strength, random, sigma);
    }

    public GPV08FieldSampler(int strength, Random random, int sigma, Field baseField) {
        super(strength, random, sigma);
        this.baseField = baseField;
    }


    public Element sample() {
        return sampleDFromField(baseField);
    }

    public Element sample(int n) {
        VectorField<Field> vf = new VectorField<Field>(random, baseField, n);

        Vector result = vf.newElement();
        for (int i = 0; i < n; i++) {
            result.getAt(i).set(sample());
        }
        return result;
    }

    public Element sample(int rows, int cols) {
        MatrixField<Field> field = new MatrixField<Field>(random, baseField, rows, cols);

        MatrixElement result = field.newElement();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.getAt(i, j).set(sample());
            }
        }

        return result;
    }

    public Element sampleDFromField(Field field) {
        Vector element = (Vector) field.newElement();

        BigInteger[] coefficients = sampleD(element.getSize());
        for (int i = 0; i < element.getSize(); i++) {
            element.getAt(i).set(coefficients[i]);
        }

        return element;
    }

}
