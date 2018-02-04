package ocelot.rt;

import ocelot.classfile.OCKlassParser;

/**
 * 
 * @author ben
 */
public class JVMObj {

    static JVMObj of(OCKlass klass, long id) {
        return new JVMObj(klass, id);
    }
    private final OCObjMeta mark = new OCObjMeta();
    private final OCKlass klass;
    private final long id; 
    
    private JVMObj(OCKlass klass, long id_) {
        this.klass = klass;
        id = id_;
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
    
}
