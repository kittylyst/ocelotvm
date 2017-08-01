package ocelot;

import static ocelot.JVMType.*;

/**
 *
 * @author ben
 */
public final class LocalVars {

    private final JVMValue[] vars = new JVMValue[256];
    private final EvaluationStack stack;
    
    public LocalVars(final EvaluationStack eval) {
        stack = eval;
    }
    
    void iinc(byte offset, byte amount) {
        JVMValue localVar = vars[offset & 0xff];
        // Type check...
        if (localVar.type != I) 
            throw new IllegalStateException("Wrong type "+ localVar.type +" encountered at local var: "+ (offset & 0xff) +" should be I");
        
        // FIXME Overflow...?
        vars[offset & 0xff] = new JVMValue(I, amount + localVar.value);
    }

    void iload(byte b) {
        load(b, I);
    }

    void istore(byte b) {
        store(b, I);
    }

    void aload(byte b) {
        load(b, A);
    }

    void astore(byte b) {
        store(b, A);
    }

    void dload(byte b) {
        load(b, D);
    }

    void dstore(byte b) {
        store(b, D);
    }

    private void load(byte b, JVMType type) {
        JVMValue v = vars[b & 0xff].copy();
        // FIXME Type-check
        stack.push(v);
    }

    private void store(byte b, JVMType type) {
        JVMValue val = stack.pop();
        // FIXME Type-check
        vars[b & 0xff] = val;
    }

}
