package it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.params;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class SHEBV11bKeyParameters extends AsymmetricKeyParameter {

    private SHEBV11bParameters parameters;


    public SHEBV11bKeyParameters(boolean privateKey, SHEBV11bParameters parameters) {
        super(privateKey);
        this.parameters = parameters;
    }


    public SHEBV11bParameters getParameters() {
        return parameters;
    }
}
