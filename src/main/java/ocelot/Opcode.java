package ocelot;

/**
 *
 * @author ben
 */
public enum Opcode {

    ACONST_NULL(0x01),
    ALOAD(0x19, 1),
    ALOAD_0(0x2a),
    ALOAD_1(0x2b),
    ASTORE(0x53, 1),
    ASTORE_0(0x4b),
    ASTORE_1(0x4c),
    BIPUSH(0x10, 1),
    BREAKPOINT(0xca),
    DADD(0x63),
    DALOAD(0x31),
    DASTORE(0x52),
    DCMPG(0x98),
    DCMPL(0x97),
    DCONST_0(0x0e),
    DCONST_1(0x0f),
    DDIV(0x6f),
    DLOAD(0x18),
    DLOAD_0(0x26),
    DLOAD_1(0x27),
    DLOAD_2(0x28),
    DLOAD_3(0x29),
    DMUL(0x6b),
    DNEG(0x77),
    DREM(0x73),
    DRETURN(0xaf),
    DSTORE(0x39),
    DSTORE_0(0x47),
    DSTORE_1(0x48),
    DSTORE_2(0x49),
    DSTORE_3(0x4a),
    DSUB(0x67),
    DUP(0x59),
    GETSTATIC(0xb2, 2),
    GOTO(0xa7, 2),
    IADD(0x60),
    IAND(0x7e),
    ICONST_M1(0x02),
    ICONST_0(0x03),
    ICONST_1(0x04),
    ICONST_2(0x05),
    ICONST_3(0x06),
    ICONST_4(0x07),
    ICONST_5(0x08),
    IDIV(0x6c),
    IF_ICMPEQ(0x9f, 2),
    IFEQ(0x99, 2),
    IFGE(0x9c, 2),
    IFGT(0x9d, 2),
    IFLE(0x9e, 2),
    IFLT(0x9b, 2),
    IFNE(0x9a, 2),
    IINC(0x84, 2),
    IMPDEP1(0xfe),
    IMPDEP2(0xff),
    IMUL(0x68),
    INEG(0x74),
    IOR(0x80),
    ISUB(0x64),
    ILOAD(0x15, 1),
    ILOAD_0(0x1a),
    ILOAD_1(0x1b),
    ILOAD_2(0x1c),
    ILOAD_3(0x1d),
    INVOKESTATIC(0xb8, 2),
    INVOKEVIRTUAL(0xb6, 2),
    IRETURN(0xac),
    ISTORE(0x36, 1),
    ISTORE_0(0x3b),
    ISTORE_1(0x3c),
    ISTORE_2(0x3d),
    ISTORE_3(0x3e),
    JSR(0xa8, 2),
    JSR_W(0xc9, 2),
    LDC(0x12, 1),
    NOP(0x00),
    POP(0x57),
    POP2(0x58),
    RET(0xa9, 1),
    RETURN(0xb1),
    DUP_X1(0x5a);

    public byte numParams() {
        return numParams;
    }
    private final int opcode;
    private final byte numParams;

    public int getOpcode() {
        return opcode;
    }

    public byte B() {
        return (byte) opcode;
    }

    private Opcode(final int b) {
        this(b, 0);
    }

    private Opcode(final int b, final int p) {
        opcode = b;
        numParams = (byte) p;
    }
}
