package ocelot.rt;

import ocelot.Builder;

/**
 * The per-type metadata, effectively the klassOop Uses Builder pattern
 *
 * @author ben
 */
class JVMTypeMetadata {

    private final String name;
    private final JVMTypeMetadata parent;
    private final Object[] storage;

    private JVMTypeMetadata(Bldr b) {
        name = b.name;
        parent = b.parent;
        storage = b.storage;
    }

    @Override
    public String toString() {
        return "JVMTypeMetadata{" + "name=" + name + ", parent=" + parent + ", storage=" + storage + '}';
    }

    
    
    static class Bldr implements Builder<JVMTypeMetadata> {

        private String name;
        private JVMTypeMetadata parent;
        private Object[] storage;

        @Override
        public JVMTypeMetadata build() {
            return new JVMTypeMetadata(this);
        }

        Bldr parent(JVMTypeMetadata p) {
            parent = p;
            return this;
        }

        Bldr storage(int b) {
            storage = new Object[b];
            return this;
        }

        Bldr name(String n) {
            name = n;
            return this;
        }

    }
}
