package ocelot.rt;

import ocelot.classfile.OCKlassParser;

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
    private final OCKlassParser meta;
    private final long id; 
    
    private JVMObj(OCKlassParser klass, long id_) {
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

    public OCKlassParser getMeta() {
        return meta;
    }
    
    
}
