package it.unisa.dia.gas.plaf.jlbc.util.io;

import it.unisa.dia.gas.jpbc.Element;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class ElementObjectOutput {
    private DataOutput dOut;
    private ByteArrayOutputStream bOut;

    
    public ElementObjectOutput() {
        this.bOut = new ByteArrayOutputStream();
        this.dOut = new DataOutputStream(bOut);
    }

    
    public byte[] toByteArray() {
        return bOut.toByteArray();
    }
    

    public ElementObjectOutput write(int b)  {
        try {
            dOut.write(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectOutput write(byte[] b)  {
        try {
            dOut.write(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectOutput write(byte[] b, int off, int len)  {
        try {
            dOut.write(b, off, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectOutput writeBoolean(boolean v)  {
        try {
            dOut.writeBoolean(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectOutput writeByte(int v)  {
        try {
            dOut.writeByte(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectOutput writeShort(int v)  {
        try {
            dOut.writeShort(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectOutput writeChar(int v)  {
        try {
            dOut.writeChar(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectOutput writeInt(int v)  {
        try {
            dOut.writeInt(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectOutput writeLong(long v)  {
        try {
            dOut.writeLong(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectOutput writeFloat(float v)  {
        try {
            dOut.writeFloat(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectOutput writeDouble(double v)  {
        try {
            dOut.writeDouble(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectOutput writeBytes(String s)  {
        try {
            dOut.writeBytes(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectOutput writeChars(String s)  {
        try {
            dOut.writeChars(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectOutput writeUTF(String s)  {
        try {
            dOut.writeUTF(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }


    public ElementObjectOutput writeElement(Element element)  {
        if (element == null)
            writeInt(0);
        else {
            byte[] bytes = element.toBytes();
            writeInt(bytes.length);
            write(bytes);
        }
        return this;
    }

    public ElementObjectOutput writeElements(Element... elements)  {
        for (Element e : elements)
            writeElement(e);
        return this;
    }

}