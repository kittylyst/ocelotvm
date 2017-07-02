package ocelot;

/**
 * Holds the type of the evaluation stack entry, and a bit pattern that
 * corresponds to the actual value. For objects, the bit pattern is an ID into
 * into the heap structure.
 * 
 * @author ben
 */
class JVMValue {
    final JVMType type;
    final long value;

    JVMValue(JVMType t, long bits) {
        this.type = t;
        this.value = bits;
    }
}
