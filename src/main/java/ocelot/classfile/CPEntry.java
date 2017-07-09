package ocelot.classfile;

/**
 *
 * @author ben
 */
public final class CPEntry {

    private final CPType type;
    private final Number num;
    private final String str;
    private final Ref ref;
    private final Ref ref2;

    private CPEntry(CPType t, Number n, String s, Ref r, Ref r2) {
        type = t;
        num = n;
        str = s;
        ref = r;
        ref2 = r2;
    }

    public static CPEntry of(CPType t, Number num) {
        return new CPEntry(t, num, num.toString(), null, null);
    }

    public static CPEntry of(CPType t, String s) {
        return new CPEntry(t, null, s, null, null);
    }

    public static CPEntry of(CPType t, Ref r) {
        return new CPEntry(t, null, null, r, null);
    }

    public static CPEntry of(CPType t, Ref r, Ref r2) {
        return new CPEntry(t, null, null, r, r2);
    }

    public CPType getType() {
        return type;
    }

    public Number getNum() {
        return num;
    }

    public String getStr() {
        return str;
    }

    public Ref getRef() {
        return ref;
    }

    public Ref getRef2() {
        return ref2;
    }

    @Override
    public String toString() {
        return "CPEntry{" + "type=" + type + ", num=" + num + ", str=" + str + ", ref=" + ref + ", ref2=" + ref2 + '}';
    }
    
    
}
