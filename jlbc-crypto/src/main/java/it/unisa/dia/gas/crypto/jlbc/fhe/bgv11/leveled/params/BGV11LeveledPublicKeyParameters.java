package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.FieldOver;
import it.unisa.dia.gas.jpbc.Vector;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11LeveledPublicKeyParameters extends BGV11LeveledKeyParameters {

    private Element[] bs;
    private Element[] As;

    private Element[] switchBs;
    private Element[] switchAs;


    public BGV11LeveledPublicKeyParameters(BGV11LeveledParameters[] parameters,
                                           Element[] bs, Element[] As,
                                           Element[] switchBs, Element[] switchAs) {
        super(false, parameters);

        this.bs = bs;
        this.As = As;
        this.switchBs = switchBs;
        this.switchAs = switchAs;
    }


    public Element getInputLevelB() {
        return bs[bs.length - 1];
    }

    public Element getInputLevelA() {
        return As[As.length - 1];
    }
    

    public Element[] refresh(int currentLevel, Element[] elements) {
        BGV11LeveledParameters current = getParametersAt(currentLevel);
        BGV11LeveledParameters next = getParametersAt(currentLevel - 1);

        return switchKey(currentLevel - 1, scale(current, next, current.powerOf(elements)));
    }

    public Element[] refresh(int currentLevel, int toLevel, Element[] elements) {
        for (int i = currentLevel; i > toLevel; i--) {
            elements = refresh(i, elements);
        }
        return elements;
    }

    protected Element ip(Element[] l, Element r) {
        Element result = l[0].getField().newZeroElement();
        Vector rv = (Vector) r;
        for (int i = 0, length = Math.min(l.length, rv.getSize()); i < length; i++) {
            result.add(l[i].duplicate().mul(rv.getAt(i)));
        }

        return result;
    }

    protected Element[] switchKey(int toLevel, Element... elements) {
        BGV11LeveledParameters next = getParametersAt(toLevel);
        Element[] decomposition = next.decompose(elements);

        Element[] result = new Element[2];
        result[0] = ip(decomposition, switchBs[toLevel]);
        result[1] = ip(decomposition, switchAs[toLevel]);
        return result;
    }

    protected Element[] scale(BGV11LeveledParameters current, BGV11LeveledParameters next, Element... elements) {
        BigInteger q = current.getQ();
        BigInteger p = next.getQ();
        BigInteger t = current.getT();

        Field zp = ((FieldOver) next.getRq()).getTargetField();

        MathContext mc = new MathContext(256, RoundingMode.DOWN);

        Element[] result = new Element[elements.length];

        for (int j = 0; j < elements.length; j++) {
            Element element = elements[j];
            // Each element is a vector.
            Vector v = (Vector) element;
            Vector vScaled = (Vector) next.getRq().newElement();

            // scale
            for (int i = 0; i < v.getSize(); i++) {
                Element xE = v.getAt(i);
                Element xScaledE = zp.newElement().set(
                        new BigDecimal(xE.toBigInteger().multiply(p), mc).divide(new BigDecimal(q, mc), mc).toBigInteger()
                );

                // TODO: improve this...
                while (true) {
                    if (!xE.toBigInteger().mod(t).equals(xScaledE.toBigInteger().mod(t)))
                        xScaledE.add(zp.newOneElement());
                    else
                        break;
                }
                vScaled.getAt(i).set(xScaledE);
            }
            result[j] = vScaled;
        }

        return result;
    }
    
}
