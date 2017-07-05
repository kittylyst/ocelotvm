package ocelot;

/**
 *
 * @author ben
 */
public enum Opcode {

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
    ASTORE(0x53) {
                @Override
                public byte numParams() {
                    return 1;
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
    IDIV(0x6c) {
                @Override
                public byte numParams() {
                    return 0;
                }
            },
    IINC(0x84) {
                @Override
                public byte numParams() {
                    return 2;
                }
            },
    IMUL(0x68) {
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
        return (byte)opcode;
    }

    private Opcode(final int b) {
        opcode = b;
    }
}
