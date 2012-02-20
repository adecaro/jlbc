package it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.basic.params;

import it.unisa.dia.gas.jpbc.Field;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class BGV11BasicParameters {

    private BigInteger q, t;
    private int d, N;
    private Field Rq, RtN;


    public BGV11BasicParameters(BigInteger q, BigInteger t, int d, int N, Field Rq, Field RtN) {
        this.q = q;
        this.t = t;
        this.d = d;
        this.N = N;
        this.Rq = Rq;
        this.RtN = RtN;
    }


    public BigInteger getQ() {
        return q;
    }

    public BigInteger getT() {
        return t;
    }

    public int getD() {
        return d;
    }

    public Field getRq() {
        return Rq;
    }

    public Field getRtN() {
        return RtN;
    }
}
