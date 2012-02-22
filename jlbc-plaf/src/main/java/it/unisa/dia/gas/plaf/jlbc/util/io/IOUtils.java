package it.unisa.dia.gas.plaf.jlbc.util.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class IOUtils {

    public static byte[] toByteArray(byte[]... bytesList) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            for (byte[] bytes : bytesList) {
                out.write(bytes);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return out.toByteArray();
    }

}
