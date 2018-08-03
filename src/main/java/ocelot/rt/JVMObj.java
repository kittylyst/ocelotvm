package ocelot.rt;

import ocelot.JVMValue;
import ocelot.classfile.OCKlassParser;

/**
 * The class that implements a heap-allocated JVM object
 *
 * @author ben
 */
public final class JVMObj {
    private final OCObjMeta mark = new OCObjMeta();
    private final OCKlass klass;
    private final long id;
    private final JVMValue[] fields;

    static JVMObj of(OCKlass klass, long id) {
        final JVMObj out = new JVMObj(klass, id);
        return out;
    }

    private JVMObj(OCKlass klass, long id_) {
        this.klass = klass;
        id = id_;
        fields = new JVMValue[klass.numFields()];
    }

    @Override
    public String toString() {
        return "JVMObj{" + "mark=" + mark + ", meta=" + klass + ", id=" + id + '}';
    }

    public long getId() {
        return id;
    }

    public OCObjMeta getMark() {
        return mark;
    }

    public OCKlass getKlass() {
        return klass;
    }

    public JVMValue getField(OCField f) {
        return fields[klass.getFieldOffset(f)];
    }

    public void putField(OCField f, JVMValue val) {
        fields[klass.getFieldOffset(f)] = val;
    }
}
