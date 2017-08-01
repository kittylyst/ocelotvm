package ocelot;

import java.util.Stack;

/**
 *
 * @author ben
 */
public class EvaluationStack extends Stack<JVMValue> {

    void iconst(int i) {
        push(new JVMValue(JVMType.I, i));
    }

    void iadd() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int add = (int) ev1.value + (int) ev2.value;
        push(new JVMValue(JVMType.I, add));
    }

    void idiv() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int div = (int) ev1.value / (int) ev2.value;
        push(new JVMValue(JVMType.I, div));
    }

    void imul() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int mul = (int) ev1.value * (int) ev2.value;
        push(new JVMValue(JVMType.I, mul));
    }

    void isub() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int sub = (int) ev1.value - (int) ev2.value;
        push(new JVMValue(JVMType.I, sub));
    }

    void dup() {
        JVMValue ev = peek().copy();
        push(ev);
    }

    void ineg() {
        JVMValue ev = pop();
        push(new JVMValue(JVMType.I, -ev.value));
    }

    void iand() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int and = (int) ev1.value & (int) ev2.value;
        push(new JVMValue(JVMType.I, and));
    }

    void ior() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int or = (int) ev1.value | (int) ev2.value;
        push(new JVMValue(JVMType.I, or));
    }

    void aconst_null() {
        push(new JVMValue(JVMType.A, 0L));
    }

    void dupX1() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        push(ev1.copy());
        push(ev2);
        push(ev1);
    }

    public void dadd() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        double add = Double.longBitsToDouble(ev1.value) + Double.longBitsToDouble(ev2.value);
        push(new JVMValue(JVMType.D, Double.doubleToLongBits(add)));
    }

    public void dsub() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        double sub = Double.longBitsToDouble(ev1.value) - Double.longBitsToDouble(ev2.value);
        push(new JVMValue(JVMType.D, Double.doubleToLongBits(sub)));
    }

    public void ddiv() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        double div = Double.longBitsToDouble(ev1.value) / Double.longBitsToDouble(ev2.value);
        push(new JVMValue(JVMType.D, Double.doubleToLongBits(div)));
    }
    
    public void dmul() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        double mul = Double.longBitsToDouble(ev1.value) * Double.longBitsToDouble(ev2.value);
        push(new JVMValue(JVMType.D, Double.doubleToLongBits(mul)));
    }

    public void dneg() {
        JVMValue ev = pop();
        push(new JVMValue(ev.type, ev.value ^ 0x8000000000000000L));
    }

    public void dconst(double d) {
        push(new JVMValue(JVMType.D, Double.doubleToLongBits(d)));
    }
}
