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
        JVMValue v = vars[b & 0xff].copy();
        // Type-check...
        stack.push(v);
    }

    void istore(byte b) {
        JVMValue val = stack.pop();
        // Type-check...
        vars[b & 0xff] = val;
    }

    void aload(byte b) {
        JVMValue v = vars[b & 0xff].copy();
        // Type-check...
        stack.push(v);
    }

    void astore(byte b) {
        JVMValue val = stack.pop();
        // FIXME Type-check
        vars[b & 0xff] = val;
    }

}
