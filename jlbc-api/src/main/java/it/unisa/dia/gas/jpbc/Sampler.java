package it.unisa.dia.gas.jpbc;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public interface Sampler {

    Element sample();

    Element sample(int n);
    
    Element sample(int rows, int cols);

}
