package it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.params;

import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class SHEBV11bSecretKeyParameters extends SHEBV11bKeyParameters {

    private Element secretKey;


    public SHEBV11bSecretKeyParameters(SHEBV11bParameters parameters,
                                       Element secretKey) {
        super(true, parameters);

        this.secretKey = secretKey;
    }


    public Element getSecretKey() {
        return secretKey;
    }

}
