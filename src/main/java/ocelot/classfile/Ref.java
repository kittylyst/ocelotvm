package ocelot.classfile;

/**
 * Represents a reference to another entry in the constant pool
 * 
 * @author ben
 */
public class Ref {

    private final int other;
    
    public Ref(int other) {
        this.other = other;
    }

    public int getOther() {
        return other;
    }
}
