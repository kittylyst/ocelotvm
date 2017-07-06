package ocelot;

/**
 *
 * @author ben
 */
public enum Opcode {

    ACONST_NULL(0x01) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ALOAD(0x19) {
                @Override
                public byte numParams() {
                    return 1;
                }
            },
    ALOAD_0(0x2a) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ALOAD_1(0x2b) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ASTORE(0x53) {
                @Override
                public byte numParams() {
                    return 1;
                }
            },
    ASTORE_0(0x4b) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ASTORE_1(0x4c) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    BREAKPOINT(0xca) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    DUP(0x59) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    GETSTATIC(0xb2) {
                @Override
                public byte numParams() {
                    return 2;
                }
            },
    GOTO(0xa7) {
                @Override
                public byte numParams() {
                    return 2;
                }
            },
    IADD(0x60) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    IAND(0x7e) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ICONST_M1(0x02) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ICONST_0(0x03) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ICONST_1(0x04) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ICONST_2(0x05) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ICONST_3(0x06) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ICONST_4(0x07) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ICONST_5(0x08) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    IDIV(0x6c) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    IFEQ(0x99) {
                @Override
                public byte numParams() {
                    return 2;
                }
            },
    IFGE(0x9c) {
                @Override
                public byte numParams() {
                    return 2;
                }
            },
    IFGT(0x9d) {
                @Override
                public byte numParams() {
                    return 2;
                }
            },
    IFLE(0x9e) {
                @Override
                public byte numParams() {
                    return 2;
                }
            },
    IFLT(0x9b) {
                @Override
                public byte numParams() {
                    return 2;
                }
            },
    IFNE(0x9a) {
                @Override
                public byte numParams() {
                    return 2;
                }
            },
    IINC(0x84) {
                @Override
                public byte numParams() {
                    return 2;
                }
            },
    IMPDEP1(0xfe) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    IMPDEP2(0xff) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    IMUL(0x68) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    INEG(0x74) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    IOR(0x80) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ISUB(0x64) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ILOAD(0x15) {
                @Override
                public byte numParams() {
                    return 1;
                }
            },
    ILOAD_0(0x1a) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ILOAD_1(0x1b) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ILOAD_2(0x1c) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ILOAD_3(0x1d) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    INVOKEVIRTUAL(0xb6) {
                @Override
                public byte numParams() {
                    return 2;
                }
            },
    IRETURN(0xac) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ISTORE(0x36) {
                @Override
                public byte numParams() {
                    return 1;
                }
            },
    ISTORE_0(0x3b) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ISTORE_1(0x3c) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ISTORE_2(0x3d) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    ISTORE_3(0x3e) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    JSR(0xa8) {
                @Override
                public byte numParams() {
                    return 2;
                }
            },
    JSR_W(0xc9) {
                @Override
                public byte numParams() {
                    return 2;
                }
            },
    LDC(0x12) {
                @Override
                public byte numParams() {
                    return 1;
                }
            },
    NOP(0x00) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    POP(0x57) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    POP2(0x58) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    RET(0xa9) {
                @Override
                public byte numParams() {
                    return 1;
                }
            },
    RETURN(0xb1) {
                @Override
                public byte numParams() {
                    return 0;
                }
            };

    public abstract byte numParams();
    private final int opcode;

    public int getOpcode() {
        return opcode;
    }

    public byte B() {
        return (byte) opcode;
    }

    private Opcode(final int b) {
        opcode = b;
    }
}
