package ocelot.rt;

import ocelot.JVMType;

public class OCField {

    private final String name;
    private final JVMType type;
    private final int flags;

    public OCField(final String name, final JVMType type, final int flags) {
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
}
