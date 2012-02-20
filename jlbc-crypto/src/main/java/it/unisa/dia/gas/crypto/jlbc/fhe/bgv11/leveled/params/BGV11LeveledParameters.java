package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Vector;
import it.unisa.dia.gas.plaf.jlbc.field.vector.VectorField;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11LeveledParameters {

    private BigInteger q, t;
    private Element tInv;
    private int d, N;
    private Field Rq, RtN, Zq;
    private int lub;


    public BGV11LeveledParameters(BigInteger q, BigInteger t, Element tInv, int d, int N, Field Rq, Field RtN, Field Zq, int lub) {
        this.q = q;
        this.t = t;
        this.tInv = tInv;
        this.d = d;
        this.N = N;
        this.Rq = Rq;
        this.RtN = RtN;
        this.Zq = Zq;
        this.lub = lub;
    }

    public Element powerOfToVector(Element... elements) {
        Element[] result = powerOf(elements);

        VectorField temp = new VectorField(null, elements[0].getField(), result.length);
        return temp.newElement(result);
    }

    public Element[] powerOf(Element... elements) {
        Element[] powerOf = new Element[lub * elements.length];
        for (int i = 0, index = 0; i < elements.length; i++, index += lub) {
            Element[] temp = powerOf(elements[i]);
            System.arraycopy(temp, 0, powerOf, index, lub);
        }
        return powerOf;
    }

    public Element[] powerOf(Element e) {
        // Note that e is in Rq then
        // it can be interpreted as an Vector<Element>

        Element[] powerOf = new Vector[lub];
        BigInteger multiplier = BigInteger.ONE;
        for (int i = 0; i < powerOf.length; i++) {
            powerOf[i] = e.duplicate().mul(multiplier);
            multiplier = multiplier.multiply(t);
        }

        return powerOf;
    }

    public Element[] decompose(Element... elements) {
        Element[] result = new Element[lub * elements.length];

        for (int i = 0, index = 0; i < elements.length; i++, index += lub) {
            Element[] decomposition = decompose(elements[i]);
            System.arraycopy(decomposition, 0, result, index, lub);
        }

        return result;
    }

    public Element[] decompose(Element e) {
        // Note that e is in Rq then
        // it can be interpreted as an Vector<Element>

        Vector<Element> ringElement = (Vector<Element>) e;

        Vector[] decomposition = new Vector[lub];
        for (int i = 0; i < decomposition.length; i++)
            decomposition[i] = (Vector) e.getField().newElement();

        for (int i = 0, n = ringElement.getSize(); i < n; i++) {
            Element element = ringElement.getAt(i);

            Element cursor = Zq.newElement().set(element);
            int j = 0;
            while (true) {
                BigInteger reminder = cursor.toBigInteger().mod(t);

                decomposition[j++].getAt(i).set(reminder);
                cursor = cursor.sub(cursor.getField().newElement(reminder)).mul(tInv);

                if (cursor.isZero())
                    break;
            }
        }

        return decomposition;
    }



    public BigInteger getQ() {
        return q;
    }

    public BigInteger getT() {
        return t;
    }

    public Element gettInv() {
        return tInv;
    }

    public int getD() {
        return d;
    }

    public int getN() {
        return N;
    }

    public Field getRq() {
        return Rq;
    }

    public Field getRtN() {
        return RtN;
    }

    public Field getZq() {
        return Zq;
    }

    public int getLub() {
        return lub;
    }
}
