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
    DCONST_0(0x0e),
    DCONST_1(0x0f),
    DLOAD_0(0x26),
    DLOAD_1(0x27),
    DRETURN(0xaf),
    DSUB(0x67),
    DUP(0x59),
    DUP_X1(0x5a),
    GETFIELD(0xb4, 2),
    GETSTATIC(0xb2, 2),
    GOTO(0xa7, 2),
    I2D(0x87),
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
    INVOKESPECIAL(0xb7, 2),
    INVOKESTATIC(0xb8, 2),
    INVOKEVIRTUAL(0xb6, 2),
    IRETURN(0xac),
    ISTORE(0x36, 1),
    ISTORE_0(0x3b),
    ISTORE_1(0x3c),
    ISTORE_2(0x3d),
    ISTORE_3(0x3e),
    MONITORENTER(0xc2),
    MONITOREXIT(0xc3),
    NEW(0xbb, 2),
    JSR(0xa8, 2),
    JSR_W(0xc9, 2),
    LDC(0x12, 1),
    NOP(0x00),
    POP(0x57),
    POP2(0x58),
    PUTFIELD(0xb5, 2),
    PUTSTATIC(0xb3, 2),
    RET(0xa9, 1),
    RETURN(0xb1),
    SIPUSH(0x11, 2);

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
