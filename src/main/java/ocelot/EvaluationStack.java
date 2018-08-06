package ocelot;

import java.util.Stack;
import static ocelot.JVMValue.entry;
import static ocelot.JVMValue.entryRef;

/**
 *
 * @author ben
 */
public class EvaluationStack extends Stack<JVMValue> {

    void iconst(int i) {
        push(entry(i));
    }

    void iadd() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int add = (int) ev1.value + (int) ev2.value;
        push(entry(add));
    }

    void idiv() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int div = (int) ev2.value / (int) ev1.value;
        push(entry(div));
    }

    void imul() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int mul = (int) ev1.value * (int) ev2.value;
        push(entry(mul));
    }

    void irem() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int rem = (int) ev2.value % (int) ev1.value;
        push(entry(rem));
    }

    void isub() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int sub = (int) ev1.value - (int) ev2.value;
        push(entry(sub));
    }

    void dup() {
        JVMValue ev = peek().copy();
        push(ev);
    }

    void ineg() {
        JVMValue ev = pop();
        push(entry(-ev.value));
    }

    void iand() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int and = (int) ev1.value & (int) ev2.value;
        push(entry(and));
    }

    void ior() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int or = (int) ev1.value | (int) ev2.value;
        push(entry(or));
    }

    void aconst_null() {
        push(entryRef(0L));
    }

    void dupX1() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        push(ev1.copy());
        push(ev2);
        push(ev1);
    }

    void dconst(double d) {
        push(entry(d));
    }

    void dadd() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        double add = Double.longBitsToDouble(ev1.value) + Double.longBitsToDouble(ev2.value);
        push(entry(add));
    }

    void i2d() {
        JVMValue ev1 = pop();
        double castTo = (double)ev1.value;
        push(entry(castTo));
    }

    void dsub() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        double sub = Double.longBitsToDouble(ev1.value) - Double.longBitsToDouble(ev2.value);
        push(entry(sub));
    }

}
