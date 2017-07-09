package ocelot.classfile;

/**
 *
 * @author ben
 */
public final class CPItem {

    private final CPType type;

    private CPItem(CPType t) {
        type = t;
    }

    public static CPItem of(CPType t) {
        return new CPItem(t);
    }

    public CPType getType() {
        return type;
    }
}
