package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11LeveledAddParameters extends BGV11LeveledKeyParameters {

    private BGV11LeveledPublicKeyParameters pk;


    public BGV11LeveledAddParameters(BGV11LeveledPublicKeyParameters pk) {
        super(false, pk.getParameters());

        this.pk = pk;
    }


    public BGV11LeveledPublicKeyParameters getPk() {
        return pk;
    }
}
