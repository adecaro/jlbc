package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11LeveledKeyParameters extends AsymmetricKeyParameter {

    private BGV11LeveledParameters[] parameters;

    public BGV11LeveledKeyParameters(boolean privateKey, BGV11LeveledParameters[] parameters) {
        super(privateKey);

        this.parameters = parameters;
    }

    public int getInputLevel() {
        return parameters.length - 1;
    }

    public BGV11LeveledParameters[] getParameters() {
        return parameters;
    }

    public BGV11LeveledParameters getParametersAt(int index) {
        return parameters[index];
    }

    public BGV11LeveledParameters getInputLevelParameters() {
        return parameters[parameters.length - 1];
    }

}
