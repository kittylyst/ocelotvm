package ocelot.rt;

import ocelot.JVMValue;

/**
 * The class that implements a heap-allocated JVM object
 *
 * @author ben
 */
public final class OtObj {
    private final OtMeta mark = new OtMeta();
    private final OtKlass klass;
    private final long id;
    private final JVMValue[] fields;

    static OtObj of(OtKlass klass, long id) {
        final OtObj out = new OtObj(klass, id);
        return out;
    }

    private OtObj(OtKlass klass, long id_) {
        this.klass = klass;
        id = id_;
        fields = new JVMValue[klass.numFields()];
    }

    @Override
    public String toString() {
        return "OtObj{" + "mark=" + mark + ", meta=" + klass + ", id=" + id + '}';
    }

    public long getId() {
        return id;
    }

    public OtMeta getMark() {
        return mark;
    }

    public OtKlass getKlass() {
        return klass;
    }

    public JVMValue getField(OtField f) {
        return fields[klass.getFieldOffset(f)];
    }

    public void putField(OtField f, JVMValue val) {
        fields[klass.getFieldOffset(f)] = val;
    }
}
