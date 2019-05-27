package ocelot;

/**
 *
 * @author ben
 */
public enum Opcode {
    BIPUSH(0x10, 1),
    BREAKPOINT(0xca),
    DUP(0x59),
    DUP_X1(0x5a),
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
