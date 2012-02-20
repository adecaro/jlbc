package it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.params;

import it.unisa.dia.gas.jpbc.Element;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class SHEBV11bPublicKeyParameters extends SHEBV11bKeyParameters {

    private Element a0, a1;
    private Element as[], bs[];


    public SHEBV11bPublicKeyParameters(SHEBV11bParameters parameters,
                                       Element a0, Element a1, Element[] as, Element[] bs) {
        super(false, parameters);

        this.a0 = a0.getImmutable();
        this.a1 = a1.getImmutable();
        this.as = as;
        this.bs = bs;
    }

    public Element getA0() {
        return a0;
    }

    public Element getA1() {
        return a1;
    }
    
    public Element getAAt(int index) {
        return as[index];
    }

    public Element getBAt(int index) {
        return bs[index];
    }
    
}
