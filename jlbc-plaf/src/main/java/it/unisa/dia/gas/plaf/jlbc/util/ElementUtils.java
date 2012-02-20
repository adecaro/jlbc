package it.unisa.dia.gas.plaf.jlbc.util;

import it.unisa.dia.gas.jpbc.Element;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class ElementUtils {

    public static byte[] toByteArray(Element... elements) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            for (Element element : elements) {
                out.write(element.toBytes());
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unbelievable");
        }
        return out.toByteArray();
    }


    public static void toByteArray(OutputStream out, Element... elements) {
        try {
            for (Element element : elements) {
                out.write(element.toBytes());
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unbelievable");
        }
    }
}
