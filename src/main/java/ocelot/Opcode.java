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
    ARETURN(0xb0),
    ASTORE(0x53, 1),
    ASTORE_0(0x4b),
    ASTORE_1(0x4c),
    BIPUSH(0x10, 1),
    BREAKPOINT(0xca),
    DUP(0x59),
    DUP_X1(0x5a),
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
    IFNONNULL(0xc7, 2),
    IFNULL(0xc6, 2),
    IINC(0x84, 2),
    ILOAD(0x15, 1),
    ILOAD_0(0x1a),
    ILOAD_1(0x1b),
    ILOAD_2(0x1c),
    ILOAD_3(0x1d),
    IMPDEP1(0xfe),
    IMPDEP2(0xff),
    IMUL(0x68),
    INEG(0x74),
    IOR(0x80),
    IREM(0x70),
    IRETURN(0xac),
    ISTORE(0x36, 1),
    ISTORE_0(0x3b),
    ISTORE_1(0x3c),
    ISTORE_2(0x3d),
    ISTORE_3(0x3e),
    ISUB(0x64),
    JSR(0xa8, 2),
    JSR_W(0xc9, 2),
    LDC(0x12, 1),
    NOP(0x00),
    POP(0x57),
    POP2(0x58),
    RET(0xa9, 1),
    RETURN(0xb1),
    SIPUSH(0x11, 2),
    SWAP(0x5f);

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
