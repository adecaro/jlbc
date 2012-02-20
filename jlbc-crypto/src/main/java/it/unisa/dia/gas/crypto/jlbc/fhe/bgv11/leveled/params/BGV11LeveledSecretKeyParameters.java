package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params;


import it.unisa.dia.gas.jpbc.Element;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11LeveledSecretKeyParameters extends BGV11LeveledKeyParameters {

    private Element[] secrets;

    public BGV11LeveledSecretKeyParameters(BGV11LeveledParameters[] parameters, Element[] secrets) {
        super(true, parameters);
        
        this.secrets = secrets;
    }

    public Element getSecretAt(int index) {
        return secrets[index];
    }
    
    public Element getInputLevelSecret() {
        return secrets[secrets.length - 1];
    }

}
