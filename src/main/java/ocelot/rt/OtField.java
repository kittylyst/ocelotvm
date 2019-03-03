package ocelot.rt;

import ocelot.JVMType;

public class OtField {

    private final String name;
    private final JVMType type;
    private final int flags;
    private final OtKlass klass;
    
    public OtField(final OtKlass klz, final String name, final JVMType type, final int flags) {
        klass = klz;
        this.name = name;
        this.type = type;
        this.flags = flags;
    }

    public String getName() {
        return name;
    }

    public JVMType getType() {
        return type;
    }

    public int getFlags() {
        return flags;
    }

    public OtKlass getKlass() {
        return klass;
    }
}
