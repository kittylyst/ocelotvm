package ocelot;

import static ocelot.JVMType.*;

/**
 *
 * @author ben
 */
public final class LocalVars {

    private final JVMValue[] vars = new JVMValue[256];

    public LocalVars() {
    }

    void iinc(byte offset, byte amount) {
        JVMValue localVar = vars[offset & 0xff];
        // Type check...
        if (localVar.type != I)
            throw new IllegalStateException("Wrong type " + localVar.type + " encountered at local var: " + (offset & 0xff) + " should be I");

        // FIXME Overflow...?
        vars[offset & 0xff] = new JVMValue(I, amount + localVar.value);
    }

    JVMValue iload(byte b) {
        // Type-check...
        return vars[b & 0xff].copy();
    }

    void istore(byte b, JVMValue val) {
        // Type-check...
        vars[b & 0xff] = val;
    }

    JVMValue aload(byte b) {
        // Type-check...
        return vars[b & 0xff].copy();
    }

    void astore(byte b, JVMValue val) {
        // FIXME Type-check
        vars[b & 0xff] = val;
    }

    public void setup(JVMValue[] vals) {
        System.arraycopy(vals, 0, vars, 0, vals.length);
    }
}
