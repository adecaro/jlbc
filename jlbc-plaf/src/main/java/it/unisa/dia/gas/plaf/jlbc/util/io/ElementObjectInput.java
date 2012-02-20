package it.unisa.dia.gas.plaf.jlbc.util.io;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class ElementObjectInput {

    private ByteArrayInputStream bIn;
    private DataInput dIn;
    private Field field;


    public ElementObjectInput(byte[] in, int offset, int len) {
        this.bIn = new ByteArrayInputStream(in, offset, len);
        this.dIn = new DataInputStream(bIn);
    }


    public ElementObjectInput readFully(byte[] b) {
        try {
            dIn.readFully(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ElementObjectInput readFully(byte[] b, int off, int len) {
        try {
            dIn.readFully(b, off, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public int skipBytes(int n) {
        try {
            return dIn.skipBytes(n);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean readBoolean() {
        try {
            return dIn.readBoolean();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte readByte() {
        try {
            return dIn.readByte();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int readUnsignedByte() {
        try {
            return dIn.readUnsignedByte();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public short readShort() {
        try {
            return dIn.readShort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int readUnsignedShort() {
        try {
            return dIn.readUnsignedShort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public char readChar() {
        try {
            return dIn.readChar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int readInt() {
        try {
            return dIn.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public long readLong() {
        try {
            return dIn.readLong();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public float readFloat() {
        try {
            return dIn.readFloat();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double readDouble() {
        try {
            return dIn.readDouble();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readLine() {
        try {
            return dIn.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readUTF() {
        try {
            return dIn.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Element[] readElements(int num) {
        Element[] result = new Element[num];
        for (int i = 0; i < num; i++) {
            result[i] = readElement();
        }
        return result;
    }

    public Element readElement()  {
        int length = readInt();
        if (length > 0) {
            byte[] buf = new byte[length];
            readFully(buf);
            
            Element e = field.newElement();
            e.setFromBytes(buf);
            return e;
        }
        return null;
    }
}
