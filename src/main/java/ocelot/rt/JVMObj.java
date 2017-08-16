package ocelot.rt;

import ocelot.classfile.OCKlass;

/**
 * This class should be public to allow the new opcode to create it
 * 
 * @author ben
 */
public class JVMObj {

    static JVMObj of(String klzStr, long andIncrement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private final OCObjMeta mark = new OCObjMeta();
    private final OCKlass meta;
    private final long id; 
    
    private JVMObj(OCKlass klass, long id_) {
        meta = klass;
        id = id_;
    }

    @Override
    public String toString() {
        return "JVMObj{" + "mark=" + mark + ", meta=" + meta + ", id=" + id + '}';
    }

    public long getId() {
        return id;
    }

    public OCObjMeta getMark() {
        return mark;
    }

    public OCKlass getMeta() {
        return meta;
    }
    
    
}
