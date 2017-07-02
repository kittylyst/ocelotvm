package ocelot;

import java.util.Arrays;

/**
 *
 * @author ben
 */
public class InterpMain {

    private Opcode[] table = new Opcode[256];

    public void init() {
        for (Opcode op : Opcode.values()) {
            table[op.getOpcode()] = op;
        }
        // Sanity check
        int count = 0;
        for (int i = 0; i < 256; i++) {
            if (table[i] != null)
                count++;
        }
        final int numOpcodes = Opcode.values().length;
        if (count != numOpcodes) {
            throw new IllegalStateException("Opcode sanity check failed: "+ count +" opcodes found, should be "+ numOpcodes);
        }
    }

    public JVMValue execMethod(final byte[] instr) {
//         System.out.println(Arrays.toString(table));
        if (instr == null || instr.length == 0)
            return null;

        EvaluationStack eval = new EvaluationStack();

        int current = 0;
        LOOP:
        while (true) {
            byte b = instr[current++];
            Opcode op = table[b & 0xff];
            if (op == null) {
                System.err.println("Unrecognised opcode byte: " + (b & 0xff) + " encountered. Stopping.");
                System.exit(1);
            }
            byte num = op.numParams();
            switch (op) {
                case IADD:
                    eval.iadd();
                    break;
                case ICONST_0:
                    eval.iconst(0);
                    break;
                case ICONST_1:
                    eval.iconst(1);
                    break;
                case ICONST_2:
                    eval.iconst(2);
                    break;
                case ICONST_3:
                    eval.iconst(3);
                    break;
                case ICONST_M1:
                    eval.iconst(-1);
                    break;
                case IDIV:
                    eval.idiv();
                    break;
                case IINC:
                    iinc(instr[current++]);
                    break;
                case ILOAD:
                    iload(instr[current++]);
                    break;
                case IMUL:
                    eval.imul();
                    break;
                case IRETURN:
                    return eval.pop();
                case ISTORE:
                    istore(instr[current++]);
                    break;
                case ISUB:
                    eval.isub();
                    break;
                // Dummy implementation
                case ALOAD:
                case ALOAD_0:
                case ASTORE:
                case GETSTATIC:
                case INVOKEVIRTUAL:
                case LDC:
                    System.out.print("Executing " + op + " with param bytes: ");
                    for (int i = current; i < current + num; i++) {
                        System.out.print(instr[i] + " ");
                    }
                    current += num;
                    System.out.println();
                    break;
                case RETURN:
                    return null;
                default:
                    System.err.println("Saw " + op + " - that can't happen. Stopping.");
                    System.exit(1);
            }
        }
    }

    void iinc(byte b) {

    }

    private void iload(byte b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void istore(byte b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
