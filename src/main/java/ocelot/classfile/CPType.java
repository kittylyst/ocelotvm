package ocelot.classfile;

/**
 *
 * @author ben
 */
public enum CPType {
    // Correct as of Java 7
    UTF8(1),
    INTEGER(3),
    FLOAT(4),
    LONG(5),
    DOUBLE(6),
    CLASS(7),
    STRING(8),
    FIELDREF(9),
    METHODREF(10),
    INTERFACE_METHODREF(11),
    NAMEANDTYPE(12),
    METHODHANDLE(15),
    METHODTYPE(16),
    INVOKEDYNAMIC(18);

    private final int value;

    public int getValue() {
        return value;
    }

    public byte B() {
        return (byte) value;
    }

    private CPType(final int b) {
        value = b;
    }

}
