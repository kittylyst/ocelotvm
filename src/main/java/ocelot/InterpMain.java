package ocelot;

import java.util.Arrays;
import ocelot.classfile.OcelotClass;
import ocelot.rt.ClassRepository;

/**
 *
 * @author ben
 */
public final class InterpMain {

    private static final Opcode[] table = new Opcode[256];

    private final ClassRepository repo;
    
    
    public InterpMain(ClassRepository classes) {
        repo = classes;
    }
    
    static {
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
            throw new IllegalStateException("Opcode sanity check failed: " + count + " opcodes found, should be " + numOpcodes);
        }
    }

    public JVMValue execMethod(OcelotClass.CPMethod meth) {
        return execMethod(meth.getClassName(), meth.getNameAndType(), meth.getBuf());
    }
    
    public JVMValue execMethod(String klassName, String desc, final byte[] instr) {
        if (instr == null || instr.length == 0)
            return null;

        final EvaluationStack eval = new EvaluationStack();
        final LocalVars lvt = new LocalVars(eval);
        final String currentKlass = klassName;

        int current = 0;
        LOOP:
        while (true) {
            byte b = instr[current++];
            Opcode op = table[b & 0xff];
            if (op == null) {
                System.err.println("Unrecognised opcode byte: " + (b & 0xff) + " encountered at position " + (current - 1) + ". Stopping.");
                System.exit(1);
            }
            byte num = op.numParams();
            JVMValue v, v2;
            int jumpTo;
            switch (op) {
                case ACONST_NULL:
                    eval.aconst_null();
                    break;
                case ALOAD:
                    lvt.aload(instr[current++]);
                    break;
                case ALOAD_0:
                    lvt.aload((byte) 0);
                    break;
                case ALOAD_1:
                    lvt.aload((byte) 1);
                    break;
                case ASTORE:
                    lvt.astore(instr[current++]);
                    break;
                case ASTORE_0:
                    lvt.astore((byte) 0);
                    break;
                case ASTORE_1:
                    lvt.astore((byte) 1);
                    break;
                case BIPUSH:
                    eval.iconst((int)instr[current++]);
                    break;
                case DADD:
                    eval.dadd();
                    break;
                case DCMPG:
                    eval.dcmpg();
                    break;
                case DCMPL:
                    eval.dcmpl();
                    break;
                case DCONST_0:
                    eval.dconst(0.0);
                    break;
                case DCONST_1:
                    eval.dconst(1.0);
                    break;
                case DDIV:
                    eval.ddiv();
                    break;
                case DLOAD:
                    lvt.dload(instr[current++]);
                    break;
                case DLOAD_0:
                    lvt.dload((byte) 0);
                    break;
                case DLOAD_1:
                    lvt.dload((byte) 1);
                    break;
                case DLOAD_2:
                    lvt.dload((byte) 2);
                    break;
                case DLOAD_3:
                    lvt.dload((byte) 3);
                    break;
                case DMUL:
                    eval.dmul();
                    break;
                case DNEG:
                    eval.dneg();
                    break;
                case DREM:
                    eval.drem();
                    break;
                case DRETURN:
                    return eval.pop();
                case DSTORE:
                    lvt.dstore(instr[current++]);
                    break;
                case DSTORE_0:
                    lvt.dstore((byte) 0);
                    break;
                case DSTORE_1:
                    lvt.dstore((byte) 1);
                    break;
                case DSTORE_2:
                    lvt.dstore((byte) 2);
                    break;
                case DSTORE_3:
                    lvt.dstore((byte) 3);
                    break;
                case DSUB:
                    eval.dsub();
                    break;
                case DUP:
                    eval.dup();
                    break;
                case GOTO:
//                    System.out.println(current + " += " + (instr[current] << 8) + " + " + instr[current + 1]);
                    current += 2 + ((int) instr[current] << 8) + (int) instr[current + 1];
                    break;
                case IADD:
                    eval.iadd();
                    break;
                case IAND:
                    eval.iand();
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
                case ICONST_4:
                    eval.iconst(4);
                    break;
                case ICONST_5:
                    eval.iconst(5);
                    break;
                case ICONST_M1:
                    eval.iconst(-1);
                    break;
                case IDIV:
                    eval.idiv();
                    break;
                case IF_ICMPEQ:
                    v = eval.pop();
                    v2 = eval.pop();
                    jumpTo = ((int) instr[current++] << 8) + (int) instr[current++];
                    if (v.value == v2.value) {
                        current += jumpTo - 1; // The -1 is necessary as we've already inc'd current
                    }
                    break;
                case IFEQ:
                    v = eval.pop();
                    jumpTo = ((int) instr[current++] << 8) + (int) instr[current++];
                    if (v.value == 0L) {
                        current += jumpTo - 1; // The -1 is necessary as we've already inc'd current
                    }
                    break;
                case IFGE:
                    v = eval.pop();
                    jumpTo = ((int) instr[current++] << 8) + (int) instr[current++];
                    if (v.value >= 0L) {
                        current += jumpTo - 1; // The -1 is necessary as we've already inc'd current
                    }
                    break;
                case IFGT:
                    v = eval.pop();
                    jumpTo = ((int) instr[current++] << 8) + (int) instr[current++];
                    if (v.value > 0L) {
                        current += jumpTo - 1; // The -1 is necessary as we've already inc'd current
                    }
                    break;
                case IFLE:
                    v = eval.pop();
                    jumpTo = ((int) instr[current++] << 8) + (int) instr[current++];
                    if (v.value <= 0L) {
                        current += jumpTo - 1; // The -1 is necessary as we've already inc'd current
                    }
                    break;
                case IFLT:
                    v = eval.pop();
                    jumpTo = ((int) instr[current++] << 8) + (int) instr[current++];
                    if (v.value < 0L) {
                        current += jumpTo - 1; // The -1 is necessary as we've already inc'd current
                    }
                    break;
                case IFNE:
                    v = eval.pop();
                    jumpTo = ((int) instr[current] << 8) + (int) instr[current + 1];
                    if (v.value != 0L) {
                        current += jumpTo - 1;  // The -1 is necessary as we've already inc'd current
                    }
                    break;
                case IINC:
                    lvt.iinc(instr[current++], instr[current++]);
                    break;
                case ILOAD:
                    lvt.iload(instr[current++]);
                    break;
                case ILOAD_0:
                    lvt.iload((byte) 0);
                    break;
                case ILOAD_1:
                    lvt.iload((byte) 1);
                    break;
                case ILOAD_2:
                    lvt.iload((byte) 2);
                    break;
                case ILOAD_3:
                    lvt.iload((byte) 3);
                    break;
                case IMUL:
                    eval.imul();
                    break;
                case INEG:
                    eval.ineg();
                    break;
                case INVOKESTATIC:
                    // FIXME
                    int lookup = ((int) instr[current++] << 8) + (int) instr[current++];
                    OcelotClass.CPMethod meth = repo.lookupInCP(currentKlass, (short)lookup);
                    final JVMValue ret = execMethod(meth);
                    eval.push(ret);
                    break;
                case IOR:
                    eval.ior();
                    break;
                case IRETURN:
                    return eval.pop();
                case ISTORE:
                    lvt.istore(instr[current++]);
                    break;
                case ISTORE_0:
                    lvt.istore((byte) 0);
                    break;
                case ISTORE_1:
                    lvt.istore((byte) 1);
                    break;
                case ISTORE_2:
                    lvt.istore((byte) 2);
                    break;
                case ISTORE_3:
                    lvt.istore((byte) 3);
                    break;
                case ISUB:
                    eval.isub();
                    break;
                case NOP:
                    break;
                case POP:
                    eval.pop();
                    break;
                case POP2:
                    JVMValue discard = eval.pop();
                    if (discard.type == JVMType.J || discard.type == JVMType.D) {
                        break;
                    }
                    eval.pop();
                    break;
                // Disallowed opcodes
                case BREAKPOINT:
                case IMPDEP1:
                case IMPDEP2:
                case JSR:
                case JSR_W:
                case RET:
                    throw new IllegalArgumentException("Illegal opcode byte: " + (b & 0xff) + " encountered at position " + (current - 1) + ". Stopping.");
                // Dummy implementation
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
                case DUP_X1:
                    eval.dupX1();
                    break;
                default:
                    System.err.println("Saw " + op + " - that can't happen. Stopping.");
                    System.exit(1);
            }
        }
    }

}
