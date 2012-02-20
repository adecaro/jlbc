package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params;

import it.unisa.dia.gas.jpbc.Element;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11BasicPublicKeyParameters extends BGV11BasicKeyParameters {

    private Element b;
    private Element A;

    public BGV11BasicPublicKeyParameters(BGV11BasicParameters parameters, Element b, Element A) {
        super(false, parameters);

        this.b = b.getImmutable();
        this.A = A.getImmutable();
    }


    public Element getB() {
        return b;
    }

    public Element getA() {
        return A;
    }
}
