package ocelot.classfile;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.*;
import ocelot.InterpMain;
import ocelot.JVMType;
import ocelot.rt.OCField;
import ocelot.rt.OCKlass;
import ocelot.rt.OCMethod;

/**
 *
 * @author ben
 */
public final class OCKlassParser {

//    private 
    private final byte[] clzBytes;
    private final String filename;

    private int major = 0;
    private int minor = 0;

    private int poolItemCount = 0;
    private final static CPType[] table = new CPType[256];
    private int current = 0;

    private int flags = 0;
    private int thisClzIndex;
    private int superClzIndex;
    private CPEntry[] items;
    private int[] interfaces;
    private CPField[] fields;
    private CPMethod[] methods;
    private CPAttr[] attributes;

    public static final int ACC_PUBLIC = 0x0001;      // Declared public; may be accessed from outside its package.
    public static final int ACC_PRIVATE = 0x0002;      // Declared private; usable only within the defining class.
    public static final int ACC_PROTECTED = 0x0004;      // Declared protected; may be accessed within subclasses.
    public static final int ACC_STATIC = 0x0008;      // Declared static

    public static final int ACC_FINAL = 0x0010;       // Declared final; no subclasses allowed.
    public static final int ACC_SUPER = 0x0020;       // (Class) Treat superclass methods specially when invoked by the invokespecial instruction.
    public static final int ACC_VOLATILE = 0x0040;       // (Field) Declared volatile; cannot be cached.
    public static final int ACC_TRANSIENT = 0x0080;       // (Field) Declared transient; not written or read by a persistent object manager.
    public static final int ACC_INTERFACE = 0x0200;   // (Class) Is an interface, not a class.
    public static final int ACC_ABSTRACT = 0x0400;    // (Class) Declared abstract; must not be instantiated.
    public static final int ACC_SYNTHETIC = 0x1000;   // Declared synthetic; not present in the source code.
    public static final int ACC_ANNOTATION = 0x2000;  // Declared as an annotation type.
    public static final int ACC_ENUM = 0x4000; 	      // Declared as an enum type. 

    // Method-only constants
    public static final int ACC_SYNCHRONIZED = 0x0020;       // (Method) Declared synchronized; invocation is wrapped by a monitor use.
    public static final int ACC_BRIDGE = 0x0040;       // (Method) A bridge, generated by the compiler.
    public static final int ACC_VARARGS = 0x0080;       // (Method) Declared with variable number of arguments.
    public static final int ACC_NATIVE = 0x0100;       // (Method) Declared native; implemented in a language other than Java.
    public static final int ACC_ABSTRACT_M = 0x0400;       // (Method) Declared abstract; no implementation is provided.
    public static final int ACC_STRICT = 0x0800;       // (Method) Declared strictfp; floating-point mode is FP-strict.

    OCKlassParser(byte[] buf, String fName) {
        filename = fName;
        clzBytes = buf;
    }

    static {
        for (CPType cp : CPType.values()) {
            table[cp.getValue()] = cp;
        }
        // Sanity check
        int count = 0;
        for (int i = 0; i < 256; i++) {
            if (table[i] != null)
                count++;
        }
        final int numCPTypes = CPType.values().length;
        if (count != numCPTypes) {
            throw new IllegalStateException("Constant pool sanity check failed: " + count + " types found, should be " + numCPTypes);
        }
    }

    void parse() throws ClassNotFoundException {
        current = 0;
        parseHeader();
        parseConstantPool();
        parseBasicTypeInfo();
        parseFields();
        parseMethods();
//        parseAttributes();
    }

    /**
     * Convenience ctor for the case where we want an OCKlass object, but we
     * are just parsing, not making the type active
     * 
     * @param buf
     * @param fName
     * @return
     * @throws ClassNotFoundException 
     */
    public static OCKlass of(byte[] buf, String fName) throws ClassNotFoundException {
        return of(null, buf, fName);
    }

    /**
     * Parse a class and make it active (running the static <clinit> method)
     * 
     * @param interpreter
     * @param buf
     * @param fName
     * @return
     * @throws ClassNotFoundException 
     */
    public static OCKlass of(final InterpMain interpreter, byte[] buf, String fName) throws ClassNotFoundException {
        OCKlassParser self = new OCKlassParser(buf, fName);
        self.parse();
        OCKlass klass = self.klass();
        if (interpreter != null) {
            // FIXME - Is this really a singleton or should this be an instance call
            InterpMain.getRepo().add(klass);
            klass.callClInit(interpreter);
        }
        return klass;
    }

    void parseHeader() {
        if ((clzBytes[current++] != (byte) 0xca) || (clzBytes[current++] != (byte) 0xfe)
                || (clzBytes[current++] != (byte) 0xba) || (clzBytes[current++] != (byte) 0xbe)) {
            throw new IllegalArgumentException("Input file does not have correct magic number");
        }
        minor = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        major = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        poolItemCount = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
    }

    void parseConstantPool() throws ClassNotFoundException {
        items = new CPEntry[poolItemCount - 1];
        for (short i = 1; i < poolItemCount; i++) {
            int entry = clzBytes[current++] & 0xff;
            CPType tag = table[entry];
            if (tag == null) {
                throw new ClassNotFoundException("Unrecognised tag byte: " + entry + " encountered at position " + current + ". Stopping the parse.");
            }

            CPEntry item = null;
//            System.out.println("Tag seen: "+ tag);    
            // Create item based on tag
            switch (tag) {
                case UTF8: // String prefixed by a uint16 indicating the number of bytes in the encoded string which immediately follows
                    int len = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                    String str = new String(clzBytes, current, len, Charset.forName("UTF8"));
                    item = CPEntry.of(i, tag, str);
                    current += len;
                    break;
                case INTEGER: // Integer: a signed 32-bit two's complement number in big-endian format
                    int i2 = ((int) clzBytes[current++] << 24) + ((int) clzBytes[current++] << 16) + ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                    item = CPEntry.of(i, tag, i2);
                    break;
                case FLOAT: // Float: a 32-bit single-precision IEEE 754 floating-point number
                    int i3 = ((int) clzBytes[current++] << 24) + ((int) clzBytes[current++] << 16) + ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                    float f = Float.intBitsToFloat(i3);
                    item = CPEntry.of(i, tag, f);
                    break;
                case LONG: // Long: a signed 64-bit two's complement number in big-endian format (takes two slots in the constant pool table)
                    int i4 = ((int) clzBytes[current++] << 24) + ((int) clzBytes[current++] << 16) + ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                    int i5 = ((int) clzBytes[current++] << 24) + ((int) clzBytes[current++] << 16) + ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                    long l = ((long) i4 << 32) + (long) i5;
                    item = CPEntry.of(i, tag, l);
                    break;
                case DOUBLE: // Double: a 64-bit double-precision IEEE 754 floating-point number (takes two slots in the constant pool table)
                    i4 = ((int) clzBytes[current++] << 24) + ((int) clzBytes[current++] << 16) + ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                    i5 = ((int) clzBytes[current++] << 24) + ((int) clzBytes[current++] << 16) + ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                    l = ((long) i4 << 32) + (long) i5;
                    item = CPEntry.of(i, tag, Double.longBitsToDouble(l));
                    break;
                case CLASS: // Class reference: an uint16 within the constant pool to a UTF-8 string containing the fully qualified class name
                    int ref = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                    item = CPEntry.of(i, tag, new Ref(ref));
                    break;
                case STRING: // String reference: an uint16 within the constant pool to a UTF-8 string
                    int ref2 = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                    item = CPEntry.of(i, tag, new Ref(ref2));
                    break;
                case FIELDREF: // Field reference: two uint16 within the pool, 1st pointing to a Class reference, 2nd to a Name and Type descriptor
                case METHODREF: // Method reference: two uint16s within the pool, 1st pointing to a Class reference, 2nd to a Name and Type descriptor
                case INTERFACE_METHODREF: // Interface method reference: 2 uint16 within the pool, 1st pointing to a Class reference, 2nd to a Name and Type descriptor
                    int cpIndex = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                    int nameAndTypeIndex = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                    item = CPEntry.of(i, tag, new Ref(cpIndex), new Ref(nameAndTypeIndex));
                    break;
                case NAMEANDTYPE: // Name and type descriptor: 2 uint16 to UTF-8 strings, 1st representing a name (identifier), 2nd a specially encoded type descriptor
                    int nameRef = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                    int typeRef = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                    item = CPEntry.of(i, tag, new Ref(nameRef), new Ref(typeRef));
                    break;
                default:
                    throw new ClassNotFoundException("Reached impossible Constant Pool Tag.");
            }
            items[i - 1] = item;
        }
    }

    void parseBasicTypeInfo() {
        flags = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        thisClzIndex = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        superClzIndex = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];

        int count = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        interfaces = new int[count];
        for (int i = 0; i < count; i++) {
            interfaces[i] = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        }
    }

    void parseFields() {
        int fCount = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];

        this.fields = new CPField[fCount];
        CPField f = null;

        for (int idx = 0; idx < fCount; idx++) {
            int fFlags = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
            int name_idx = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
            int desc_idx = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
            int attrs_count = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
            f = new CPField(className(), fFlags, name_idx, desc_idx, attrs_count);

            for (int aidx = 0; aidx < f.getAttrs().length; aidx++) {
                f.setAttr(aidx, parseAttribute(f));
            }
            fields[idx] = f;
        }

    }

    private OCKlass klass() {
        final OCKlass out = new OCKlass(className(), superClassName());
        for (CPMethod cpm : methods) {
            final OCMethod ocm = new OCMethod(className(), cpm.signature, cpm.nameAndType, cpm.flags, cpm.buf);
            out.addDefinedMethod(ocm);
        }

        for (CPField cpf : fields) {
            final OCField ocf = new OCField(out, cpf.name, cpf.type, cpf.flags);
            out.addDefinedField(ocf);
        }

        for (CPEntry cpe : items) {
            int classIndex, nameTypeIndex;
            String className, nameAndType;
            switch (cpe.getType()) {
                case FIELDREF:
                    classIndex = cpe.getRef().getOther();
                    className = resolveAsString(classIndex);
                    nameTypeIndex = cpe.getRef2().getOther();
                    nameAndType = resolveAsString(nameTypeIndex);
                    out.addCPFieldRef(cpe.getIndex(), className + "." + nameAndType);
                    break;
                case METHODREF:
                    classIndex = cpe.getRef().getOther();
                    className = resolveAsString(classIndex);
                    nameTypeIndex = cpe.getRef2().getOther();
                    nameAndType = resolveAsString(nameTypeIndex);
                    out.addCPMethodRef(cpe.getIndex(), className + "." + nameAndType);
                    break;
                case CLASS:
                    classIndex = cpe.getRef().getOther();
                    className = resolveAsString(classIndex);
                    out.addCPKlassRef(cpe.getIndex(), className);
                    break;
            }

        }

        for (CPField field : fields) {
            OCField f = new OCField(out, field.name, field.type, field.flags);
            out.addField(f);
        }

        return out;
    }

    class CPBase {

        protected final String className;
        protected int flags;
        protected int nameIndex;
        protected int descIndex;
        protected CPAttr[] attrs;

        CPBase(int fFlags, int name_idx, int desc_idx, int attrCount) {
            flags = fFlags;
            nameIndex = name_idx;
            descIndex = desc_idx;
            attrs = new CPAttr[attrCount];
            className = OCKlassParser.this.className();
        }

        public int getFlags() {
            return flags;
        }

        public int getNameIndex() {
            return nameIndex;
        }

        public int getDescIndex() {
            return descIndex;
        }

        public CPAttr[] getAttrs() {
            return attrs;
        }

        public String getClassName() {
            return className;
        }

        public void setAttr(int i, CPAttr attr) {
            attrs[i] = attr;
        }
    }

    public class CPField extends CPBase {

        private String klassName;
        private JVMType type;
        private int nameIdx;
        private int descIdx;
        private String name;

        public CPField(String className, int fFlags, int name_idx, int desc_idx, int attrs_count) {
            super(fFlags, name_idx, desc_idx, attrs_count);
            klassName = className;
            this.nameIdx = name_idx;
            this.descIdx = desc_idx;

            name = resolveAsString(nameIdx);
            String desc = resolveAsString(descIdx);
            if (desc.startsWith("L")) {
                type = JVMType.valueOf("A");
            } else {
                type = JVMType.valueOf(desc);
            }

            System.out.println(name + " " + desc);
        }

        @Override
        public String toString() {
            return "CPField{" + "flags=" + flags + ", nameIndex=" + nameIndex + ", descIndex=" + descIndex + ", attrs=" + Arrays.toString(attrs) + '}';
        }

    }

    public class CPMethod extends CPBase {

        private byte[] buf;
        private final String nameAndType;
        private final String signature;

        CPMethod(int mFlags, int nameIdx, int descIdx, int attrCount) {
            super(mFlags, nameIdx, descIdx, attrCount);
            signature = OCKlassParser.this.resolveAsString(descIndex);
            nameAndType = OCKlassParser.this.resolveAsString(nameIndex) + ":" + OCKlassParser.this.resolveAsString(descIndex);
        }

        @Override
        public String toString() {
            return "CPMethod{" + "flags=" + flags + ", nameIndex=" + nameIndex + ", descIndex=" + descIndex + ", attrs=" + Arrays.toString(attrs) + '}';
        }

        private void setBytecode(byte[] b) {
            buf = b;
        }

        byte[] getBuf() {
            return buf;
        }
    }

    void parseMethods() {
        int mCount = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        methods = new CPMethod[mCount];

        for (int idx = 0; idx < mCount; idx++) {
            int mFlags = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
            int name_idx = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
            int desc_idx = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
            int attrs_count = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
            CPMethod m = new CPMethod(mFlags, name_idx, desc_idx, attrs_count);

            for (int aidx = 0; aidx < m.getAttrs().length; aidx++) {
                m.setAttr(aidx, parseAttribute(m));
            }

            methods[idx] = m;
        }
    }

    void parseAttributes() {
        int attributes_count = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        attributes = new CPAttr[attributes_count];

        for (int aidx = 0; aidx < attributes_count; aidx++) {
            int mFlags = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
            int name_idx = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
            int desc_idx = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
            int attrs_count = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
            CPBase b = new CPBase(mFlags, name_idx, desc_idx, attrs_count);

            attributes[aidx] = parseAttribute(b);
        }

    }

    CPAttr parseAttribute(CPBase b) {
        int nameCPIdx = ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        int attrLen = ((int) clzBytes[current++] << 24) + ((int) clzBytes[current++] << 16) + ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
        int endIndex = current + attrLen;

        // Now check to see what type of attribute it is...
        String s = getCPEntry(nameCPIdx).getStr();

        // E.g. for fields....
        //
//        The attributes defined by this specification as appearing in the attributes table of a field_info structure are ConstantValue (§4.7.2), Synthetic (§4.7.8), Signature (§4.7.9), Deprecated (§4.7.15), RuntimeVisibleAnnotations (§4.7.16) and RuntimeInvisibleAnnotations (§4.7.17). 
        // FIXME
        switch (s) {
            case "ConstantValue":
                if (b instanceof CPMethod) {
                    CPMethod m = (CPMethod) b;
                    String methDesc = resolveAsString(m.nameIndex) + ":" + resolveAsString(m.descIndex);
                    throw new IllegalArgumentException("Method " + methDesc + " cannot be a constant");
                }
                // FIXME
                current += 2;
                break;
            case "Code":
                if (b instanceof CPField) {
                    CPField f = (CPField) b;
                    String fieldDesc = resolveAsString(f.nameIndex) + ":" + resolveAsString(f.descIndex);
                    throw new IllegalArgumentException("Field " + fieldDesc + " cannot contain code");
                }
                final CPMethod m = (CPMethod) b;
//    u2 max_stack;
//    u2 max_locals;
                // Don't care about stack depth or locals
                current += 4;
//    u4 code_length;
//    u1 code[code_length];                
                int codeLen = ((int) clzBytes[current++] << 24) + ((int) clzBytes[current++] << 16) + ((int) clzBytes[current++] << 8) + (int) clzBytes[current++];
                byte[] bytecode = Arrays.copyOfRange(clzBytes, current, current + codeLen);
                m.setBytecode(bytecode);
//    u2 exception_table_length;
//    {   u2 start_pc;
//        u2 end_pc;
//        u2 handler_pc;
//        u2 catch_type;
//    } exception_table[exception_table_length];
//    u2 attributes_count;
//    attribute_info attributes[attributes_count];
                break;
            case "Exceptions":
                System.err.println("Encountered exception handlers in bytecode - skipping");
                break;
            default:
                throw new IllegalArgumentException("Input file has unhandled Attribute type: " + s);
        }
        // Skip to the end
        current = endIndex;

        return new CPAttr(nameCPIdx);
    }

    ///////////////////////////////////
    //
    // Helper methods
    public byte[] getClzBytes() {
        return clzBytes;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPoolItemCount() {
        return poolItemCount;
    }

    public int getFlags() {
        return flags;
    }

    public boolean isAbstract() {
        return (flags & ACC_ABSTRACT) > 0;
    }

    public boolean isAnnotation() {
        return (flags & ACC_ANNOTATION) > 0;
    }

    public boolean isEnum() {
        return (flags & ACC_ENUM) > 0;
    }

    public boolean isFinal() {
        return (flags & ACC_FINAL) > 0;
    }

    public boolean isInterface() {
        return (flags & ACC_INTERFACE) > 0;
    }

    public boolean isPublic() {
        return (flags & ACC_PUBLIC) > 0;
    }

    public boolean isSuper() {
        return (flags & ACC_SUPER) > 0;
    }

    public boolean isSynthetic() {
        return (flags & ACC_SYNTHETIC) > 0;
    }

    public List<CPEntry> getInterfaces() {
        List<CPEntry> out = new ArrayList<>();
        for (int i : interfaces) {
            out.add(getCPEntry(i));
        }
        return out;
    }

    public List<CPField> getFields() {
        List<CPField> out = new ArrayList<>();
        for (CPField f : fields) {
            out.add(f);
        }
        return out;
    }

    public List<CPMethod> getMethods() {
        List<CPMethod> out = new ArrayList<>();
        for (CPMethod r : methods) {
            out.add(r);
        }
        return out;
    }

    public CPEntry getCPEntry(int i) {
        return items[i - 1]; // CP is 1-based
    }

    public CPEntry getThisClass() {
        return getCPEntry(thisClzIndex);
    }

    public CPEntry getSuperClass() {
        return getCPEntry(superClzIndex);
    }

    public String className() {
        return resolveAsString(thisClzIndex);
    }

    public String superClassName() {
        return resolveAsString(superClzIndex);
    }


    public String resolveAsString(int i) {
        final CPEntry top = items[i - 1];

        CPEntry other = null;
        int left, right = 0;
        switch (top.getType()) {
            case UTF8:
                return top.getStr();
            case INTEGER:
                return "" + top.getNum().intValue();
            case FLOAT:
                return "" + top.getNum().floatValue();
            case LONG:
                return "" + top.getNum().longValue();
            case DOUBLE:
                return "" + top.getNum().doubleValue();
            case CLASS:
            case STRING:
                other = items[top.getRef().getOther() - 1];
                // Verification - could check type is STRING here
                return other.getStr();
            case FIELDREF:
            case METHODREF:
            case INTERFACE_METHODREF:
            case NAMEANDTYPE:
                left = top.getRef().getOther();
                right = top.getRef2().getOther();
                return resolveAsString(left) + top.getType().separator() + resolveAsString(right);
            default:
                throw new RuntimeException("Reached impossible Constant Pool Tag: " + top);
        }
    }

    @Override
    public String toString() {
        return "OcelotClass{" + "filename=" + filename + ", major=" + major + ", minor=" + minor + ", poolItemCount=" + poolItemCount + ", flags=" + flags + ", thisClzIndex=" + thisClzIndex + ", superClzIndex=" + superClzIndex + ", items=" + items + ", interfaces=" + interfaces + ", fields=" + fields + ", methods=" + methods + ", attributes=" + attributes + '}';
    }

    // Maybe some useful techniques in ASM ?
//    public OCKlassParser scan(String cName) throws IOException {
//        final Path clzPath = classNameToPath(cName);
//        final byte[] buf = Files.readAllBytes(clzPath);
//        OCKlassParser out = null;
//        try (final InputStream in = Files.newInputStream(clzPath)) {
//            try {
//                final ClassReader cr = new ClassReader(in);
//                out = new OCKlassParser(buf, clzPath.toString());
//            } catch (Exception e) {
//                throw new IOException("Could not read class file " + clzPath, e);
//            }
//        }
//        return out;
//    }
    public static Path classNameToPath(String classname) {
        return null;
    }

}
