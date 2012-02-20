package it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.params;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class SHEBV11bAddParameters extends SHEBV11bKeyParameters {

    private SHEBV11bPublicKeyParameters pk;


    public SHEBV11bAddParameters(SHEBV11bPublicKeyParameters pk) {
        super(false, pk.getParameters());

        this.pk = pk;
    }


    public SHEBV11bPublicKeyParameters getPk() {
        return pk;
    }
}
