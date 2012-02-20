package it.unisa.dia.gas.crypto.jlbc.fhe.bv11b.params;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Sampler;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class SHEBV11bParameters {

    private BigInteger q;
    
    private Sampler sampler;
    private Field Rq, Zq;
    private Element tInverse;

    private BigInteger t;
    private int lub;

    public SHEBV11bParameters(BigInteger q, Sampler sampler, Field Rq, Field Zq, Element tInverse, BigInteger t, int lub) {
        this.q = q;
        this.sampler = sampler;
        this.Rq = Rq;
        this.Zq = Zq;
        this.tInverse = tInverse;
        this.t = t;
        this.lub = lub;
    }

    public Sampler getSampler() {
        return sampler;
    }

    public Field getRq() {
        return Rq;
    }

    public Field getZq() {
        return Zq;
    }

    public Element gettInverse() {
        return tInverse;
    }

    public BigInteger getT() {
        return t;
    }

    public int getLub() {
        return lub;
    }

    public Element sample() {
        return sampler.sample();
    }
}
