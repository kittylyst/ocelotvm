package ocelot.classfile;

import org.objectweb.asm.ClassReader;

/**
 *
 * @author ben
 */
public final class ClassEntry {

    private final byte[] clzBytes;
    private final String file_name;

    private int major = 0;
    private int minor = 0;

    private int poolItems = 0;
    private int current = 0;

    public ClassEntry(byte[] buf, String fName) {
        file_name = fName;
        clzBytes = buf;
    }

    void init(ClassReader cr) {
        poolItems = cr.getItemCount();
    }

    public void parseHeader() {
        if ((clzBytes[current++] != (byte) 0xca) || (clzBytes[current++] != (byte) 0xfe)
                || (clzBytes[current++] != (byte) 0xba) || (clzBytes[current++] != (byte) 0xbe)) {
            throw new IllegalArgumentException("Input file does not have correct magic number");
        }
        minor = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        major = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        poolItems = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
    }

    public byte[] getClzBytes() {
        return clzBytes;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPoolItems() {
        return poolItems;
    }
    
    @Override
    public String toString() {
        return "ClassEntry{" + "file_name=" + file_name + ", major=" + major + ", minor=" + minor + ", poolItems=" + poolItems + '}';
    }

    
}
