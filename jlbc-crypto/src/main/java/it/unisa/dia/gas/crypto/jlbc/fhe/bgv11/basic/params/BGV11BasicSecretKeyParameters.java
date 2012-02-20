package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params;


import it.unisa.dia.gas.jpbc.Element;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11BasicSecretKeyParameters extends BGV11BasicKeyParameters {

    private Element s;
    
    public BGV11BasicSecretKeyParameters(BGV11BasicParameters parameters, Element s) {
        super(true, parameters);
        
        this.s = s;
    }


    public Element getS() {
        return s;
    }
}
