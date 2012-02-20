package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11BasicKeyParameters extends AsymmetricKeyParameter {

    private BGV11BasicParameters parameters;

    public BGV11BasicKeyParameters(boolean privateKey, BGV11BasicParameters parameters) {
        super(privateKey);

        this.parameters = parameters;
    }


    public BGV11BasicParameters getParameters() {
        return parameters;
    }
}
