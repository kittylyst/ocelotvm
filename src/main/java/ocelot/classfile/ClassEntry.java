package ocelot.classfile;

/**
 *
 * @author ben
 */
public final class ClassEntry {

    private final byte[] clzBytes;
    private final String file_name;

    private int major = 0;
    private int minor = 0;

    private int poolItems = 0;
    private int current = 0;
    private Item[] items;

    private final static CPType[] table = new CPType[256];

    public ClassEntry(byte[] buf, String fName) {
        file_name = fName;
        clzBytes = buf;
    }

    static {
        for (CPType op : CPType.values()) {
            table[op.getOpcode()] = op;
        }
        // Sanity check
        int count = 0;
        for (int i = 0; i < 256; i++) {
            if (table[i] != null)
                count++;
        }
//        final int numOpcodes = Opcode.values().length;
//        if (count != numOpcodes) {
//            throw new IllegalStateException("Opcode sanity check failed: " + count + " opcodes found, should be " + numOpcodes);
//        }

    }

    public void parseHeader() {
        if ((clzBytes[current++] != (byte) 0xca) || (clzBytes[current++] != (byte) 0xfe)
                || (clzBytes[current++] != (byte) 0xba) || (clzBytes[current++] != (byte) 0xbe)) {
            throw new IllegalArgumentException("Input file does not have correct magic number");
        }
        minor = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        major = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        poolItems = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
    }

    void parseConstantPool(int const_pool_count, byte[] this_file) {
        int MAX_ITEMS = const_pool_count - 1;
        int table_size_bytes = 0;
        int i;
        byte tag_byte;
        Ref r;

        this.items = new Item[MAX_ITEMS]; // calloc(MAX_ITEMS, sizeof(Class));
        for (i = 1; i <= MAX_ITEMS; i++) {
            tag_byte = clzBytes[current++];

            if (tag_byte < MIN_CPOOL_TAG || tag_byte > MAX_CPOOL_TAG) {
                fprintf(stderr, "Tag byte '%d' is outside permitted range %u to %u\n", tag_byte, MIN_CPOOL_TAG, MAX_CPOOL_TAG);
                table_size_bytes = 0;
                break; // fail fast
            }

            String s;
            uint16_t ptr_idx = i - 1;
            Item item = this.items + ptr_idx; // FIXME
            CPType tag = tag_byte;

            // Populate item based on tag_byte
            switch (tag) {
                case STRING_UTF8: // String prefixed by a uint16 indicating the number of bytes in the encoded string which immediately follows
                    fread( & s.length, sizeof(s.length), 1, this_file.file);
                    s.length = be16toh(s.length);
                    s.value = malloc(sizeof(char) * s.length);
                    fread(s.value, sizeof(char), s.length, this_file.file);
                    item.value.string = s;
                    table_size_bytes += 2 + s.length;
                    break;
                case INTEGER: // Integer: a signed 32-bit two's complement number in big-endian format
                    fread( & item.value.integer, sizeof(item.value.integer), 1, this_file.file);
                    item.value.integer = be32toh(item.value.integer);
                    table_size_bytes += 4;
                    break;
                case FLOAT: // Float: a 32-bit single-precision IEEE 754 floating-point number
                    fread( & item.value.flt, sizeof(item.value.flt), 1, this_file.file);
                    item.value.flt = be32toh(item.value.flt);
                    table_size_bytes += 4;
                    break;
                case LONG: // Long: a signed 64-bit two's complement number in big-endian format (takes two slots in the constant pool table)
                    fread( & item.value.lng.high, sizeof(item.value.lng.high), 1, this_file.file); // 4 bytes
                    fread( & item.value.lng.low, sizeof(item.value.lng.low), 1, this_file.file); // 4 bytes
                    item.value.lng.high = be32toh(item.value.lng.high);
                    item.value.lng.low = be32toh(item.value.lng.low);
                    // 8-byte consts take 2 pool entries
                    ++i;
                    table_size_bytes += 8;
                    break;
                case DOUBLE: // Double: a 64-bit double-precision IEEE 754 floating-point number (takes two slots in the constant pool table)
                    fread( & item.value.dbl.high, sizeof(item.value.dbl.high), 1, this_file.file); // 4 bytes
                    fread( & item.value.dbl.low, sizeof(item.value.dbl.low), 1, this_file.file); // 4 bytes
                    item.value.dbl.high = be32toh(item.value.dbl.high);
                    item.value.dbl.low = be32toh(item.value.dbl.low);
                    // 8-byte consts take 2 pool entries
                    ++i;
                    table_size_bytes += 8;
                    break;
                case CLASS: // Class reference: an uint16 within the constant pool to a UTF-8 string containing the fully qualified this name
                    fread( & r.this_idx, sizeof(r.this_idx), 1, this_file.file);
                    r.this_idx = be16toh(r.this_idx);
                    item.value.ref = r;
                    table_size_bytes += 2;
                    break;
                case STRING: // String reference: an uint16 within the constant pool to a UTF-8 string
                    fread( & r.this_idx, sizeof(r.this_idx), 1, this_file.file);
                    r.this_idx = be16toh(r.this_idx);
                    item.value.ref = r;
                    table_size_bytes += 2;
                    break;
                case FIELD: // Field reference: two uint16 within the pool, 1st pointing to a Class reference, 2nd to a Name and Type descriptor
				/* FALL THROUGH TO METHOD */
                case METHOD: // Method reference: two uint16s within the pool, 1st pointing to a Class reference, 2nd to a Name and Type descriptor
				/* FALL THROUGH TO INTERFACE_METHOD */
                case INTERFACE_METHOD: // Interface method reference: 2 uint16 within the pool, 1st pointing to a Class reference, 2nd to a Name and Type descriptor
                    fread( & r.this_idx, sizeof(r.this_idx), 1, this_file.file);
                    fread( & r.name_idx, sizeof(r.name_idx), 1, this_file.file);
                    r.this_idx = be16toh(r.this_idx);
                    r.name_idx = be16toh(r.name_idx);
                    item.value.ref = r;
                    table_size_bytes += 4;
                    break;
                case NAME: // Name and type descriptor: 2 uint16 to UTF-8 strings, 1st representing a name (identifier), 2nd a specially encoded type descriptor
                    fread( & r.this_idx, sizeof(r.this_idx), 1, this_file.file);
                    fread( & r.name_idx, sizeof(r.name_idx), 1, this_file.file);
                    r.this_idx = be16toh(r.this_idx);
                    r.name_idx = be16toh(r.name_idx);
                    item.value.ref = r;
                    table_size_bytes += 4;
                    break;
                default:
                    fprintf(stderr, "Found tag byte '%d' but don't know what to do with it\n", tag_byte);
                    item = NULL;
                    break;
            }
            if (item != NULL)
                this.items[i - 1] =  * item;
        }
        this.pool_size_bytes = table_size_bytes;
    }

    public byte[] getClzBytes() {
        return clzBytes;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPoolItems() {
        return poolItems;
    }

    @Override
    public String toString() {
        return "ClassEntry{" + "file_name=" + file_name + ", major=" + major + ", minor=" + minor + ", poolItems=" + poolItems + '}';
    }

}
