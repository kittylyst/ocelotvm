package ocelot.rt;

/**
 * This class should be public to allow the new opcode to create it
 * 
 * @author ben
 */
public class JVMObj {
    private final JVMObjMetadata mark = new JVMObjMetadata();
    private final JVMTypeMetadata meta;
    private final long id; 
    
    public JVMObj(String toCreate, long id_) {
        meta = ClassRepository.newTypeMetadata(toCreate);
        id = id_;
    }

    @Override
    public String toString() {
        return "JVMObj{" + "mark=" + mark + ", meta=" + meta + ", id=" + id + '}';
    }

    JVMTypeMetadata getTypeMetadata() {
        return meta;
    }

    public long getId() {
        return id;
    }
    
    
}
