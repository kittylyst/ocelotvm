package ocelot.rt;

import static ocelot.Opcode.*;
import static ocelot.classfile.OCKlassParser.ACC_PUBLIC;

/**
 * @author ben
 */
public class OCMethod {

    private final String className;
    private final String nameAndType;
    private final byte[] bytecode;
    private final String signature;
    private final int flags;
    private int numParams = -1;

    private static final byte[] JUST_RETURN = {RETURN.B()};

    public static final OCMethod OBJ_INIT = new OCMethod("java/lang/Object", "()V", "<init>:()V", ACC_PUBLIC, JUST_RETURN);

    public OCMethod(final String klassName, final String sig, final String nameType, final int fls, final byte[] buf) {
        signature = sig;
        nameAndType = nameType;
        bytecode = buf;
        flags = fls;
        className = klassName;
    }

    public String getClassName() {
        return className;
    }

    public String getNameAndType() {
        return nameAndType;
    }

    public String getSignature() {
        return signature;
    }

    public int getFlags() {
        return flags;
    }

    public int numParams() {
        if (numParams > -1)
            return numParams;
        numParams = 0;
        char[] chars = signature.toCharArray();
        OUTER:
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            switch (c) {
                case '(':
                    break;
                case 'Z':
                case 'B':
                case 'S':
                case 'C':
                case 'I':
                case 'J':
                case 'F':
                case 'D':
                    numParams++;
                    break;
                case 'L':
                    while (chars[i] != ';') {
                        ++i;
                    }
                    numParams++;
                    break;
                case ')':
                    break OUTER;
                default:
                    throw new IllegalStateException("Saw illegal char: " + c + " in type descriptors");
            }
        }
        return numParams;
    }

    public byte[] getBytecode() {
        return bytecode;
    }

    @Override
    public String toString() {
        return "OCMethod{" + "className=" + className + ", nameAndType=" + nameAndType + ", bytecode=" + bytecode + ", signature=" + signature + ", flags=" + flags + ", numParams=" + numParams + '}';
    }

}
