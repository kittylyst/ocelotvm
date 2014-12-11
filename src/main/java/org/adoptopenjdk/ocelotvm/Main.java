package org.adoptopenjdk.ocelotvm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;
import static org.adoptopenjdk.ocelotvm.JVMPrimVsRefType.*;
import static org.adoptopenjdk.ocelotvm.Opcodes.*;
import org.adoptopenjdk.ocelotvm.runtime.ClassRepository;
import org.adoptopenjdk.ocelotvm.runtime.JVMHeap;

/**
 */
public class Main {

    private final Stack<JVMValue> interpStack;
    // Currently executing class, ie. which constant pool to look in
    private String currentClass = "LMain;";
    private short bcOffset = 0;
    private byte[] bytecodes;

    public void printStack() {
        System.out.println(interpStack);
    }

    public JVMValue topOfStack() {
        return interpStack.peek();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        byte[] buffy = Files.readAllBytes(Paths.get(args[0]));
        Main m = new Main();
        m.interpret(buffy);
    }

    public Main() {
        interpStack = new Stack<>();
    }

    public void interpret(byte[] buffy) {
        loadBytecodes(buffy);
        interpret();
    }

    void loadBytecodes(byte[] buffy) {
        bytecodes = buffy;
        bcOffset = 0;
    }

    void interpret() {
        while (bcOffset < bytecodes.length) {
            doSingleOpCode();
        }
    }

    void doSingleOpCode() {
        switch (bytecodes[bcOffset++]) {
            case OP_NOP:
                do_op_nop();
                break;
            case OP_ACONST_NULL:
                do_op_aconst_null();
                break;
            case OP_ICONST_M1:
                do_op_iconst_m1();
                break;
            case OP_ICONST_0:
                do_op_iconst_0();
                break;
            case OP_ICONST_1:
                do_op_iconst_1();
                break;
            case OP_ICONST_2:
                do_op_iconst_2();
                break;
            case OP_ICONST_3:
                do_op_iconst_3();
                break;
            case OP_ICONST_4:
                do_op_iconst_4();
                break;
            case OP_ICONST_5:
                do_op_iconst_5();
                break;
            case OP_LCONST_0:
                do_op_lconst_0();
                break;
            case OP_LCONST_1:
                do_op_lconst_1();
                break;
            case OP_FCONST_0:
                do_op_fconst_0();
                break;
            case OP_FCONST_1:
                do_op_fconst_1();
                break;
            case OP_FCONST_2:
                do_op_fconst_2();
                break;
            case OP_DCONST_0:
                do_op_dconst_0();
                break;
            case OP_DCONST_1:
                do_op_dconst_1();
                break;
            case OP_BIPUSH:
                do_op_bipush();
                break;
            case OP_SIPUSH:
                do_op_sipush();
                break;
            case OP_LDC:
                do_op_ldc();
                break;
            case OP_LDC_W:
                do_op_ldc_w();
                break;
            case OP_LDC2_W:
                do_op_ldc2_w();
                break;
            case OP_ILOAD:
                do_op_iload();
                break;
            case OP_LLOAD:
                do_op_lload();
                break;
            case OP_FLOAD:
                do_op_fload();
                break;
            case OP_DLOAD:
                do_op_dload();
                break;
            case OP_ALOAD:
                do_op_aload();
                break;
            case OP_ILOAD_0:
                do_op_iload_0();
                break;
            case OP_ILOAD_1:
                do_op_iload_1();
                break;
            case OP_ILOAD_2:
                do_op_iload_2();
                break;
            case OP_ILOAD_3:
                do_op_iload_3();
                break;
            case OP_LLOAD_0:
                do_op_lload_0();
                break;
            case OP_LLOAD_1:
                do_op_lload_1();
                break;
            case OP_LLOAD_2:
                do_op_lload_2();
                break;
            case OP_LLOAD_3:
                do_op_lload_3();
                break;
            case OP_FLOAD_0:
                do_op_fload_0();
                break;
            case OP_FLOAD_1:
                do_op_fload_1();
                break;
            case OP_FLOAD_2:
                do_op_fload_2();
                break;
            case OP_FLOAD_3:
                do_op_fload_3();
                break;
            case OP_DLOAD_0:
                do_op_dload_0();
                break;
            case OP_DLOAD_1:
                do_op_dload_1();
                break;
            case OP_DLOAD_2:
                do_op_dload_2();
                break;
            case OP_DLOAD_3:
                do_op_dload_3();
                break;
            case OP_ALOAD_0:
                do_op_aload_0();
                break;
            case OP_ALOAD_1:
                do_op_aload_1();
                break;
            case OP_ALOAD_2:
                do_op_aload_2();
                break;
            case OP_ALOAD_3:
                do_op_aload_3();
                break;
            case OP_IALOAD:
                do_op_iaload();
                break;
            case OP_LALOAD:
                do_op_laload();
                break;
            case OP_FALOAD:
                do_op_faload();
                break;
            case OP_DALOAD:
                do_op_daload();
                break;
            case OP_AALOAD:
                do_op_aaload();
                break;
            case OP_BALOAD:
                do_op_baload();
                break;
            case OP_CALOAD:
                do_op_caload();
                break;
            case OP_SALOAD:
                do_op_saload();
                break;
            case OP_ISTORE:
                do_op_istore();
                break;
            case OP_LSTORE:
                do_op_lstore();
                break;
            case OP_FSTORE:
                do_op_fstore();
                break;
            case OP_DSTORE:
                do_op_dstore();
                break;
            case OP_ASTORE:
                do_op_astore();
                break;
            case OP_ISTORE_0:
                do_op_istore_0();
                break;
            case OP_ISTORE_1:
                do_op_istore_1();
                break;
            case OP_ISTORE_2:
                do_op_istore_2();
                break;
            case OP_ISTORE_3:
                do_op_istore_3();
                break;
            case OP_LSTORE_0:
                do_op_lstore_0();
                break;
            case OP_LSTORE_1:
                do_op_lstore_1();
                break;
            case OP_LSTORE_2:
                do_op_lstore_2();
                break;
            case OP_LSTORE_3:
                do_op_lstore_3();
                break;
            case OP_FSTORE_0:
                do_op_fstore_0();
                break;
            case OP_FSTORE_1:
                do_op_fstore_1();
                break;
            case OP_FSTORE_2:
                do_op_fstore_2();
                break;
            case OP_FSTORE_3:
                do_op_fstore_3();
                break;
            case OP_DSTORE_0:
                do_op_dstore_0();
                break;
            case OP_DSTORE_1:
                do_op_dstore_1();
                break;
            case OP_DSTORE_2:
                do_op_dstore_2();
                break;
            case OP_DSTORE_3:
                do_op_dstore_3();
                break;
            case OP_ASTORE_0:
                do_op_astore_0();
                break;
            case OP_ASTORE_1:
                do_op_astore_1();
                break;
            case OP_ASTORE_2:
                do_op_astore_2();
                break;
            case OP_ASTORE_3:
                do_op_astore_3();
                break;
            case OP_IASTORE:
                do_op_iastore();
                break;
            case OP_LASTORE:
                do_op_lastore();
                break;
            case OP_FASTORE:
                do_op_fastore();
                break;
            case OP_DASTORE:
                do_op_dastore();
                break;
            case OP_AASTORE:
                do_op_aastore();
                break;
            case OP_BASTORE:
                do_op_bastore();
                break;
            case OP_CASTORE:
                do_op_castore();
                break;
            case OP_SASTORE:
                do_op_sastore();
                break;
            case OP_POP:
                do_op_pop();
                break;
            case OP_POP2:
                do_op_pop2();
                break;
            case OP_DUP:
                do_op_dup();
                break;
            case OP_DUP_X1:
                do_op_dup_x1();
                break;
            case OP_DUP_X2:
                do_op_dup_x2();
                break;
            case OP_DUP2:
                do_op_dup2();
                break;
            case OP_DUP2_X1:
                do_op_dup2_x1();
                break;
            case OP_DUP2_X2:
                do_op_dup2_x2();
                break;
            case OP_SWAP:
                do_op_swap();
                break;
            case OP_IADD:
                do_op_iadd();
                break;
            case OP_LADD:
                do_op_ladd();
                break;
            case OP_FADD:
                do_op_fadd();
                break;
            case OP_DADD:
                do_op_dadd();
                break;
            case OP_ISUB:
                do_op_isub();
                break;
            case OP_LSUB:
                do_op_lsub();
                break;
            case OP_FSUB:
                do_op_fsub();
                break;
            case OP_DSUB:
                do_op_dsub();
                break;
            case OP_IMUL:
                do_op_imul();
                break;
            case OP_LMUL:
                do_op_lmul();
                break;
            case OP_FMUL:
                do_op_fmul();
                break;
            case OP_DMUL:
                do_op_dmul();
                break;
            case OP_IDIV:
                do_op_idiv();
                break;
            case OP_LDIV:
                do_op_ldiv();
                break;
            case OP_FDIV:
                do_op_fdiv();
                break;
            case OP_DDIV:
                do_op_ddiv();
                break;
            case OP_IREM:
                do_op_irem();
                break;
            case OP_LREM:
                do_op_lrem();
                break;
            case OP_FREM:
                do_op_frem();
                break;
            case OP_DREM:
                do_op_drem();
                break;
            case OP_INEG:
                do_op_ineg();
                break;
            case OP_LNEG:
                do_op_lneg();
                break;
            case OP_FNEG:
                do_op_fneg();
                break;
            case OP_DNEG:
                do_op_dneg();
                break;
            case OP_ISHL:
                do_op_ishl();
                break;
            case OP_LSHL:
                do_op_lshl();
                break;
            case OP_ISHR:
                do_op_ishr();
                break;
            case OP_LSHR:
                do_op_lshr();
                break;
            case OP_IUSHR:
                do_op_iushr();
                break;
            case OP_LUSHR:
                do_op_lushr();
                break;
            case OP_IAND:
                do_op_iand();
                break;
            case OP_LAND:
                do_op_land();
                break;
            case OP_IOR:
                do_op_ior();
                break;
            case OP_LOR:
                do_op_lor();
                break;
            case OP_IXOR:
                do_op_ixor();
                break;
            case OP_LXOR:
                do_op_lxor();
                break;
            case OP_IINC:
                do_op_iinc();
                break;
            case OP_I2L:
                do_op_i2l();
                break;
            case OP_I2F:
                do_op_i2f();
                break;
            case OP_I2D:
                do_op_i2d();
                break;
            case OP_L2I:
                do_op_l2i();
                break;
            case OP_L2F:
                do_op_l2f();
                break;
            case OP_L2D:
                do_op_l2d();
                break;
            case OP_F2I:
                do_op_f2i();
                break;
            case OP_F2L:
                do_op_f2l();
                break;
            case OP_F2D:
                do_op_f2d();
                break;
            case OP_D2I:
                do_op_d2i();
                break;
            case OP_D2L:
                do_op_d2l();
                break;
            case OP_D2F:
                do_op_d2f();
                break;
            case OP_I2B:
                do_op_i2b();
                break;
            case OP_I2C:
                do_op_i2c();
                break;
            case OP_I2S:
                do_op_i2s();
                break;
            case OP_LCMP:
                do_op_lcmp();
                break;
            case OP_FCMPL:
                do_op_fcmpl();
                break;
            case OP_FCMPG:
                do_op_fcmpg();
                break;
            case OP_DCMPL:
                do_op_dcmpl();
                break;
            case OP_DCMPG:
                do_op_dcmpg();
                break;
            case OP_IFEQ:
                do_op_ifeq();
                break;
            case OP_IFNE:
                do_op_ifne();
                break;
            case OP_IFLT:
                do_op_iflt();
                break;
            case OP_IFGE:
                do_op_ifge();
                break;
            case OP_IFGT:
                do_op_ifgt();
                break;
            case OP_IFLE:
                do_op_ifle();
                break;
            case OP_IF_ICMPEQ:
                do_op_if_icmpeq(bytecodes[bcOffset++], bytecodes[bcOffset++]);
                break;
            case OP_IF_ICMPNE:
                do_op_if_icmpne(bytecodes[bcOffset++], bytecodes[bcOffset++]);
                break;
            case OP_IF_ICMPLT:
                do_op_if_icmplt(bytecodes[bcOffset++], bytecodes[bcOffset++]);
                break;
            case OP_IF_ICMPGE:
                do_op_if_icmpge();
                break;
            case OP_IF_ICMPGT:
                do_op_if_icmpgt();
                break;
            case OP_IF_ICMPLE:
                do_op_if_icmple();
                break;
            case OP_IF_ACMPEQ:
                do_op_if_acmpeq(bytecodes[bcOffset++], bytecodes[bcOffset++]);
                break;
            case OP_IF_ACMPNE:
                do_op_if_acmpne(bytecodes[bcOffset++], bytecodes[bcOffset++]);
                break;
            case OP_GOTO:
                do_op_goto(bytecodes[bcOffset++], bytecodes[bcOffset++]);
                break;
            case OP_JSR:
                do_op_jsr();
                break;
            case OP_RET:
                do_op_ret();
                break;
            case OP_TABLESWITCH:
                do_op_tableswitch();
                break;
            case OP_LOOKUPSWITCH:
                do_op_lookupswitch();
                break;
            case OP_IRETURN:
                do_op_ireturn();
                break;
            case OP_LRETURN:
                do_op_lreturn();
                break;
            case OP_FRETURN:
                do_op_freturn();
                break;
            case OP_DRETURN:
                do_op_dreturn();
                break;
            case OP_ARETURN:
                do_op_areturn();
                break;
            case OP_RETURN:
                do_op_return();
                break;
            case OP_GETSTATIC:
                do_op_getstatic();
                break;
            case OP_PUTSTATIC:
                do_op_putstatic();
                break;
            case OP_GETFIELD:
                do_op_getfield();
                break;
            case OP_PUTFIELD:
                do_op_putfield();
                break;
            case OP_INVOKEVIRTUAL:
                do_op_invokevirtual();
                break;
            case OP_INVOKESPECIAL:
                do_op_invokespecial();
                break;
            case OP_INVOKESTATIC:
                do_op_invokestatic();
                break;
            case OP_INVOKEINTERFACE:
                do_op_invokeinterface();
                break;
            case OP_NEW:
                do_op_new(bytecodes[bcOffset++], bytecodes[bcOffset++]);
                break;
            case OP_NEWARRAY:
                do_op_newarray();
                break;
            case OP_ANEWARRAY:
                do_op_anewarray();
                break;
            case OP_ARRAYLENGTH:
                do_op_arraylength();
                break;
            case OP_ATHROW:
                do_op_athrow();
                break;
            case OP_CHECKCAST:
                do_op_checkcast();
                break;
            case OP_INSTANCEOF:
                do_op_instanceof();
                break;
            case OP_MONITORENTER:
                do_op_monitorenter();
                break;
            case OP_MONITOREXIT:
                do_op_monitorexit();
                break;
            case OP_WIDE:
                do_op_wide();
                break;
            case OP_MULTIANEWARRAY:
                do_op_multianewarray();
                break;
            case OP_IFNULL:
                do_op_ifnull();
                break;
            case OP_IFNONNULL:
                do_op_ifnonnull();
                break;
            case OP_GOTO_W:
                do_op_goto_w();
                break;
            case OP_JSR_W:
                do_op_jsr_w();
                break;
            case OP_LDC_QUICK:
                do_op_ldc_quick();
                break;
            case OP_LDC_W_QUICK:
                do_op_ldc_w_quick();
                break;
            case OP_GETFIELD_QUICK:
                do_op_getfield_quick();
                break;
            case OP_PUTFIELD_QUICK:
                do_op_putfield_quick();
                break;
            case OP_GETFIELD2_QUICK:
                do_op_getfield2_quick();
                break;
            case OP_PUTFIELD2_QUICK:
                do_op_putfield2_quick();
                break;
            case OP_GETSTATIC_QUICK:
                do_op_getstatic_quick();
                break;
            case OP_PUTSTATIC_QUICK:
                do_op_putstatic_quick();
                break;
            case OP_GETSTATIC2_QUICK:
                do_op_getstatic2_quick();
                break;
            case OP_PUTSTATIC2_QUICK:
                do_op_putstatic2_quick();
                break;
            case OP_INVOKEVIRTUAL_QUICK:
                do_op_invokevirtual_quick();
                break;
            case OP_INVOKENONVIRTUAL_QUICK:
                do_op_invokenonvirtual_quick();
                break;
            case OP_INVOKESUPER_QUICK:
                do_op_invokesuper_quick();
                break;
            case OP_GETFIELD_QUICK_REF:
                do_op_getfield_quick_ref();
                break;
            case OP_PUTFIELD_QUICK_REF:
                do_op_putfield_quick_ref();
                break;
            case OP_GETSTATIC_QUICK_REF:
                do_op_getstatic_quick_ref();
                break;
            case OP_PUTSTATIC_QUICK_REF:
                do_op_putstatic_quick_ref();
                break;
            case OP_GETFIELD_THIS_REF:
                do_op_getfield_this_ref();
                break;
            case OP_INVOKEVIRTUAL_QUICK_W:
                do_op_invokevirtual_quick_w();
                break;
            case OP_GETFIELD_QUICK_W:
                do_op_getfield_quick_w();
                break;
            case OP_PUTFIELD_QUICK_W:
                do_op_putfield_quick_w();
                break;
            case OP_GETFIELD_THIS:
                do_op_getfield_this();
                break;
            case OP_LOCK:
                do_op_lock();
                break;
            case OP_ALOAD_THIS:
                do_op_aload_this();
                break;
            case OP_INVOKESTATIC_QUICK:
                do_op_invokestatic_quick();
                break;
            case OP_NEW_QUICK:
                do_op_new_quick();
                break;
            case OP_ANEWARRAY_QUICK:
                do_op_anewarray_quick();
                break;
            case OP_CHECKCAST_QUICK:
                do_op_checkcast_quick();
                break;
            case OP_INSTANCEOF_QUICK:
                do_op_instanceof_quick();
                break;
            case OP_MULTIANEWARRAY_QUICK:
                do_op_multianewarray_quick();
                break;
            case OP_INVOKEINTERFACE_QUICK:
                do_op_invokeinterface_quick();
                break;
            case OP_ABSTRACT_METHOD_ERROR:
                do_op_abstract_method_error();
                break;
            case OP_INLINE_REWRITER:
                do_op_inline_rewriter();
                break;
            case OP_PROFILE_REWRITER:
                do_op_profile_rewriter();
                break;
        }
    }

    private void do_op_nop() {
    }

    private void do_op_aconst_null() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.REF, null));
    }

    private void do_op_iconst_m1() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.INT, -1));
    }

    private void do_op_iconst_0() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.INT, 0));
    }

    private void do_op_iconst_1() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.INT, 1));
    }

    private void do_op_iconst_2() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.INT, 2));
    }

    private void do_op_iconst_3() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.INT, 3));
    }

    private void do_op_iconst_4() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.INT, 4));
    }

    private void do_op_iconst_5() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.INT, 5));
    }

    private void do_op_lconst_0() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.LONG, (long) 0));
    }

    private void do_op_lconst_1() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.LONG, (long) 1));
    }

    private void do_op_fconst_0() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.FLOAT, 0.0));
    }

    private void do_op_fconst_1() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.FLOAT, 1.0));
    }

    private void do_op_fconst_2() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.FLOAT, 2.0));
    }

    private void do_op_dconst_0() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.DOUBLE, 0.0));
    }

    private void do_op_dconst_1() {
        interpStack.push(new JVMValue(JVMPrimVsRefType.DOUBLE, 1.0));
    }

    private void do_op_bipush() {
        System.out.println("OP_BIPUSH not supported yet.");
    }

    private void do_op_sipush() {
        System.out.println("OP_SIPUSH not supported yet.");
    }

    private void do_op_ldc() {
        System.out.println("OP_LDC not supported yet.");
    }

    private void do_op_ldc_w() {
        System.out.println("OP_LDC_W not supported yet.");
    }

    private void do_op_ldc2_w() {
        System.out.println("OP_LDC2_W not supported yet.");
    }

    private void do_op_iload() {
        System.out.println("OP_ILOAD not supported yet.");
    }

    private void do_op_lload() {
        System.out.println("OP_LLOAD not supported yet.");
    }

    private void do_op_fload() {
        System.out.println("OP_FLOAD not supported yet.");
    }

    private void do_op_dload() {
        System.out.println("OP_DLOAD not supported yet.");
    }

    private void do_op_aload() {
        System.out.println("OP_ALOAD not supported yet.");
    }

    private void do_op_iload_0() {
        System.out.println("OP_ILOAD_0 not supported yet.");
    }

    private void do_op_iload_1() {
        System.out.println("OP_ILOAD_1 not supported yet.");
    }

    private void do_op_iload_2() {
        System.out.println("OP_ILOAD_2 not supported yet.");
    }

    private void do_op_iload_3() {
        System.out.println("OP_ILOAD_3 not supported yet.");
    }

    private void do_op_lload_0() {
        System.out.println("OP_LLOAD_0 not supported yet.");
    }

    private void do_op_lload_1() {
        System.out.println("OP_LLOAD_1 not supported yet.");
    }

    private void do_op_lload_2() {
        System.out.println("OP_LLOAD_2 not supported yet.");
    }

    private void do_op_lload_3() {
        System.out.println("OP_LLOAD_3 not supported yet.");
    }

    private void do_op_fload_0() {
        System.out.println("OP_FLOAD_0 not supported yet.");
    }

    private void do_op_fload_1() {
        System.out.println("OP_FLOAD_1 not supported yet.");
    }

    private void do_op_fload_2() {
        System.out.println("OP_FLOAD_2 not supported yet.");
    }

    private void do_op_fload_3() {
        System.out.println("OP_FLOAD_3 not supported yet.");
    }

    private void do_op_dload_0() {
        System.out.println("OP_DLOAD_0 not supported yet.");
    }

    private void do_op_dload_1() {
        System.out.println("OP_DLOAD_1 not supported yet.");
    }

    private void do_op_dload_2() {
        System.out.println("OP_DLOAD_2 not supported yet.");
    }

    private void do_op_dload_3() {
        System.out.println("OP_DLOAD_3 not supported yet.");
    }

    private void do_op_aload_0() {
        System.out.println("OP_ALOAD_0 not supported yet.");
    }

    private void do_op_aload_1() {
        System.out.println("OP_ALOAD_1 not supported yet.");
    }

    private void do_op_aload_2() {
        System.out.println("OP_ALOAD_2 not supported yet.");
    }

    private void do_op_aload_3() {
        System.out.println("OP_ALOAD_3 not supported yet.");
    }

    private void do_op_iaload() {
        System.out.println("OP_IALOAD not supported yet.");
    }

    private void do_op_laload() {
        System.out.println("OP_LALOAD not supported yet.");
    }

    private void do_op_faload() {
        System.out.println("OP_FALOAD not supported yet.");
    }

    private void do_op_daload() {
        System.out.println("OP_DALOAD not supported yet.");
    }

    private void do_op_aaload() {
        System.out.println("OP_AALOAD not supported yet.");
    }

    private void do_op_baload() {
        System.out.println("OP_BALOAD not supported yet.");
    }

    private void do_op_caload() {
        System.out.println("OP_CALOAD not supported yet.");
    }

    private void do_op_saload() {
        System.out.println("OP_SALOAD not supported yet.");
    }

    private void do_op_istore() {
        System.out.println("OP_ISTORE not supported yet.");
    }

    private void do_op_lstore() {
        System.out.println("OP_LSTORE not supported yet.");
    }

    private void do_op_fstore() {
        System.out.println("OP_FSTORE not supported yet.");
    }

    private void do_op_dstore() {
        System.out.println("OP_DSTORE not supported yet.");
    }

    private void do_op_astore() {
        System.out.println("OP_ASTORE not supported yet.");
    }

    private void do_op_istore_0() {
        System.out.println("OP_ISTORE_0 not supported yet.");
    }

    private void do_op_istore_1() {
        System.out.println("OP_ISTORE_1 not supported yet.");
    }

    private void do_op_istore_2() {
        System.out.println("OP_ISTORE_2 not supported yet.");
    }

    private void do_op_istore_3() {
        System.out.println("OP_ISTORE_3 not supported yet.");
    }

    private void do_op_lstore_0() {
        System.out.println("OP_LSTORE_0 not supported yet.");
    }

    private void do_op_lstore_1() {
        System.out.println("OP_LSTORE_1 not supported yet.");
    }

    private void do_op_lstore_2() {
        System.out.println("OP_LSTORE_2 not supported yet.");
    }

    private void do_op_lstore_3() {
        System.out.println("OP_LSTORE_3 not supported yet.");
    }

    private void do_op_fstore_0() {
        System.out.println("OP_FSTORE_0 not supported yet.");
    }

    private void do_op_fstore_1() {
        System.out.println("OP_FSTORE_1 not supported yet.");
    }

    private void do_op_fstore_2() {
        System.out.println("OP_FSTORE_2 not supported yet.");
    }

    private void do_op_fstore_3() {
        System.out.println("OP_FSTORE_3 not supported yet.");
    }

    private void do_op_dstore_0() {
        System.out.println("OP_DSTORE_0 not supported yet.");
    }

    private void do_op_dstore_1() {
        System.out.println("OP_DSTORE_1 not supported yet.");
    }

    private void do_op_dstore_2() {
        System.out.println("OP_DSTORE_2 not supported yet.");
    }

    private void do_op_dstore_3() {
        System.out.println("OP_DSTORE_3 not supported yet.");
    }

    private void do_op_astore_0() {
        System.out.println("OP_ASTORE_0 not supported yet.");
    }

    private void do_op_astore_1() {
        System.out.println("OP_ASTORE_1 not supported yet.");
    }

    private void do_op_astore_2() {
        System.out.println("OP_ASTORE_2 not supported yet.");
    }

    private void do_op_astore_3() {
        System.out.println("OP_ASTORE_3 not supported yet.");
    }

    private void do_op_iastore() {
        System.out.println("OP_IASTORE not supported yet.");
    }

    private void do_op_lastore() {
        System.out.println("OP_LASTORE not supported yet.");
    }

    private void do_op_fastore() {
        System.out.println("OP_FASTORE not supported yet.");
    }

    private void do_op_dastore() {
        System.out.println("OP_DASTORE not supported yet.");
    }

    private void do_op_aastore() {
        System.out.println("OP_AASTORE not supported yet.");
    }

    private void do_op_bastore() {
        System.out.println("OP_BASTORE not supported yet.");
    }

    private void do_op_castore() {
        System.out.println("OP_CASTORE not supported yet.");
    }

    private void do_op_sastore() {
        System.out.println("OP_SASTORE not supported yet.");
    }

    private void do_op_pop() {
        System.out.println("OP_POP not supported yet.");
    }

    private void do_op_pop2() {
        System.out.println("OP_POP2 not supported yet.");
    }

    private void do_op_dup() {
        System.out.println("OP_DUP not supported yet.");
    }

    private void do_op_dup_x1() {
        System.out.println("OP_DUP_X1 not supported yet.");
    }

    private void do_op_dup_x2() {
        System.out.println("OP_DUP_X2 not supported yet.");
    }

    private void do_op_dup2() {
        System.out.println("OP_DUP2 not supported yet.");
    }

    private void do_op_dup2_x1() {
        System.out.println("OP_DUP2_X1 not supported yet.");
    }

    private void do_op_dup2_x2() {
        System.out.println("OP_DUP2_X2 not supported yet.");
    }

    private void do_op_swap() {
        System.out.println("OP_SWAP not supported yet.");
    }

    private void do_op_iadd() {
        JVMValue st0 = interpStack.pop();
        JVMValue st1 = interpStack.pop();

        int i0 = (Integer) st0.getValue();
        int i1 = (Integer) st0.getValue();

        interpStack.push(new JVMValue(JVMPrimVsRefType.INT, i0 + i1));
    }

    private void do_op_ladd() {
        System.out.println("OP_LADD not supported yet.");
    }

    private void do_op_fadd() {
        System.out.println("OP_FADD not supported yet.");
    }

    private void do_op_dadd() {
        JVMValue st0 = interpStack.pop();
        JVMValue st1 = interpStack.pop();

        Double d0 = (Double) st0.getValue();
        Double d1 = (Double) st0.getValue();

        interpStack.push(new JVMValue(JVMPrimVsRefType.DOUBLE, d0 + d1));
    }

    private void do_op_isub() {
        System.out.println("OP_ISUB not supported yet.");
    }

    private void do_op_lsub() {
        System.out.println("OP_LSUB not supported yet.");
    }

    private void do_op_fsub() {
        System.out.println("OP_FSUB not supported yet.");
    }

    private void do_op_dsub() {
        System.out.println("OP_DSUB not supported yet.");
    }

    private void do_op_imul() {
        System.out.println("OP_IMUL not supported yet.");
    }

    private void do_op_lmul() {
        System.out.println("OP_LMUL not supported yet.");
    }

    private void do_op_fmul() {
        System.out.println("OP_FMUL not supported yet.");
    }

    private void do_op_dmul() {
        System.out.println("OP_DMUL not supported yet.");
    }

    private void do_op_idiv() {
        System.out.println("OP_IDIV not supported yet.");
    }

    private void do_op_ldiv() {
        System.out.println("OP_LDIV not supported yet.");
    }

    private void do_op_fdiv() {
        System.out.println("OP_FDIV not supported yet.");
    }

    private void do_op_ddiv() {
        System.out.println("OP_DDIV not supported yet.");
    }

    private void do_op_irem() {
        System.out.println("OP_IREM not supported yet.");
    }

    private void do_op_lrem() {
        System.out.println("OP_LREM not supported yet.");
    }

    private void do_op_frem() {
        System.out.println("OP_FREM not supported yet.");
    }

    private void do_op_drem() {
        System.out.println("OP_DREM not supported yet.");
    }

    private void do_op_ineg() {
        System.out.println("OP_INEG not supported yet.");
    }

    private void do_op_lneg() {
        System.out.println("OP_LNEG not supported yet.");
    }

    private void do_op_fneg() {
        System.out.println("OP_FNEG not supported yet.");
    }

    private void do_op_dneg() {
        System.out.println("OP_DNEG not supported yet.");
    }

    private void do_op_ishl() {
        System.out.println("OP_ISHL not supported yet.");
    }

    private void do_op_lshl() {
        System.out.println("OP_LSHL not supported yet.");
    }

    private void do_op_ishr() {
        System.out.println("OP_ISHR not supported yet.");
    }

    private void do_op_lshr() {
        System.out.println("OP_LSHR not supported yet.");
    }

    private void do_op_iushr() {
        System.out.println("OP_IUSHR not supported yet.");
    }

    private void do_op_lushr() {
        System.out.println("OP_LUSHR not supported yet.");
    }

    private void do_op_iand() {
        System.out.println("OP_IAND not supported yet.");
    }

    private void do_op_land() {
        System.out.println("OP_LAND not supported yet.");
    }

    private void do_op_ior() {
        System.out.println("OP_IOR not supported yet.");
    }

    private void do_op_lor() {
        System.out.println("OP_LOR not supported yet.");
    }

    private void do_op_ixor() {
        System.out.println("OP_IXOR not supported yet.");
    }

    private void do_op_lxor() {
        System.out.println("OP_LXOR not supported yet.");
    }

    private void do_op_iinc() {
        System.out.println("OP_IINC not supported yet.");
    }

    private void do_op_i2l() {
        System.out.println("OP_I2L not supported yet.");
    }

    private void do_op_i2f() {
        System.out.println("OP_I2F not supported yet.");
    }

    private void do_op_i2d() {
        System.out.println("OP_I2D not supported yet.");
    }

    private void do_op_l2i() {
        System.out.println("OP_L2I not supported yet.");
    }

    private void do_op_l2f() {
        System.out.println("OP_L2F not supported yet.");
    }

    private void do_op_l2d() {
        System.out.println("OP_L2D not supported yet.");
    }

    private void do_op_f2i() {
        System.out.println("OP_F2I not supported yet.");
    }

    private void do_op_f2l() {
        System.out.println("OP_F2L not supported yet.");
    }

    private void do_op_f2d() {
        JVMValue st0 = interpStack.pop();
        Float f = (Float) st0.getValue();
        interpStack.push(new JVMValue(DOUBLE, f.doubleValue()));
    }

    private void do_op_d2i() {
        JVMValue st0 = interpStack.pop();
        Double d = (Double) st0.getValue();
        interpStack.push(new JVMValue(INT, d.intValue()));
    }

    private void do_op_d2l() {
        JVMValue st0 = interpStack.pop();
        Double d = (Double) st0.getValue();
        interpStack.push(new JVMValue(LONG, d.longValue()));
    }

    private void do_op_d2f() {
        JVMValue st0 = interpStack.pop();
        Double d = (Double) st0.getValue();
        interpStack.push(new JVMValue(FLOAT, d.floatValue()));
    }

    private void do_op_i2b() {
        System.out.println("OP_I2B not supported yet.");
    }

    private void do_op_i2c() {
        System.out.println("OP_I2C not supported yet.");
    }

    private void do_op_i2s() {
        System.out.println("OP_I2S not supported yet.");
    }

    private void do_op_lcmp() {
        System.out.println("OP_LCMP not supported yet.");
    }

    private void do_op_fcmpl() {
        System.out.println("OP_FCMPL not supported yet.");
    }

    private void do_op_fcmpg() {
        System.out.println("OP_FCMPG not supported yet.");
    }

    private void do_op_dcmpl() {
        System.out.println("OP_DCMPL not supported yet.");
    }

    private void do_op_dcmpg() {
        System.out.println("OP_DCMPG not supported yet.");
    }

    private void do_op_ifeq() {
        System.out.println("OP_IFEQ not supported yet.");
    }

    private void do_op_ifne() {
        System.out.println("OP_IFNE not supported yet.");
    }

    private void do_op_iflt() {
        System.out.println("OP_IFLT not supported yet.");
    }

    private void do_op_ifge() {
        System.out.println("OP_IFGE not supported yet.");
    }

    private void do_op_ifgt() {
        System.out.println("OP_IFGT not supported yet.");
    }

    private void do_op_ifle() {
        System.out.println("OP_IFLE not supported yet.");
    }

    private void do_op_if_icmpeq(byte g0, byte g1) {
        JVMValue st0 = interpStack.pop();
        JVMValue st1 = interpStack.pop();

        int i0 = (Integer) st0.getValue();
        int i1 = (Integer) st1.getValue();
        if (i0 == i1) {
            bcOffset = (short) (((short) g0 << 8) + (short) g1);
        }
    }

    private void do_op_if_icmpne(byte g0, byte g1) {
        JVMValue st0 = interpStack.pop();
        JVMValue st1 = interpStack.pop();

        int i0 = (Integer) st0.getValue();
        int i1 = (Integer) st1.getValue();
        if (i0 != i1) {
            bcOffset = (short) (((short) g0 << 8) + (short) g1);
        }
    }

    private void do_op_if_icmplt(byte g0, byte g1) {
        JVMValue st0 = interpStack.pop();
        JVMValue st1 = interpStack.pop();

        int i0 = (Integer) st0.getValue();
        int i1 = (Integer) st1.getValue();
        if (i0 < i1) {
            bcOffset = (short) (((short) g0 << 8) + (short) g1);
        }
    }

    private void do_op_if_icmpge() {
        System.out.println("OP_IF_ICMPGE not supported yet.");
    }

    private void do_op_if_icmpgt() {
        System.out.println("OP_IF_ICMPGT not supported yet.");
    }

    private void do_op_if_icmple() {
        System.out.println("OP_IF_ICMPLE not supported yet.");
    }

    private void do_op_if_acmpeq(byte g0, byte g1) {
        System.out.println("OP_IF_ACMPEQ not supported yet.");
    }

    private void do_op_if_acmpne(byte g0, byte g1) {
        System.out.println("OP_IF_ACMPNE not supported yet.");
    }

    private void do_op_goto(byte g0, byte g1) {
        bcOffset = (short) (((short) g0 << 8) + (short) g1);
    }

    private void do_op_jsr() {
        System.out.println("OP_JSR not supported yet.");
    }

    private void do_op_ret() {
        System.out.println("OP_RET not supported yet.");
    }

    private void do_op_tableswitch() {
        System.out.println("OP_TABLESWITCH not supported yet.");
    }

    private void do_op_lookupswitch() {
        System.out.println("OP_LOOKUPSWITCH not supported yet.");
    }

    private void do_op_ireturn() {
        System.out.println("OP_IRETURN not supported yet.");
    }

    private void do_op_lreturn() {
        System.out.println("OP_LRETURN not supported yet.");
    }

    private void do_op_freturn() {
        System.out.println("OP_FRETURN not supported yet.");
    }

    private void do_op_dreturn() {
        System.out.println("OP_DRETURN not supported yet.");
    }

    private void do_op_areturn() {
        System.out.println("OP_ARETURN not supported yet.");
    }

    private void do_op_return() {
        System.out.println("OP_RETURN not supported yet.");
    }

    private void do_op_getstatic() {
        System.out.println("OP_GETSTATIC not supported yet.");
    }

    private void do_op_putstatic() {
        System.out.println("OP_PUTSTATIC not supported yet.");
    }

    private void do_op_getfield() {
        System.out.println("OP_GETFIELD not supported yet.");
    }

    private void do_op_putfield() {
        System.out.println("OP_PUTFIELD not supported yet.");
    }

    private void do_op_invokevirtual() {
        System.out.println("OP_INVOKEVIRTUAL not supported yet.");
    }

    private void do_op_invokespecial() {
        System.out.println("OP_INVOKESPECIAL not supported yet.");
    }

    private void do_op_invokestatic() {
        System.out.println("OP_INVOKESTATIC not supported yet.");
    }

    private void do_op_invokeinterface() {
        System.out.println("OP_INVOKEINTERFACE not supported yet.");
    }

    private void do_op_new(byte b0, byte b1) {
        short entry = (short) (((short) b0 << 8) + (short) b1);
        String klzStr = ClassRepository.lookupConstantPool(currentClass, entry);
        Object o = JVMHeap.newObj(klzStr);
        interpStack.push(new JVMValue(REF, o));
    }

    private void do_op_newarray() {
        System.out.println("OP_NEWARRAY not supported yet.");
    }

    private void do_op_anewarray() {
        System.out.println("OP_ANEWARRAY not supported yet.");
    }

    private void do_op_arraylength() {
        System.out.println("OP_ARRAYLENGTH not supported yet.");
    }

    private void do_op_athrow() {
        System.out.println("OP_ATHROW not supported yet.");
    }

    private void do_op_checkcast() {
        System.out.println("OP_CHECKCAST not supported yet.");
    }

    private void do_op_instanceof() {
        System.out.println("OP_INSTANCEOF not supported yet.");
    }

    private void do_op_monitorenter() {
        System.out.println("OP_MONITORENTER not supported yet.");
    }

    private void do_op_monitorexit() {
        System.out.println("OP_MONITOREXIT not supported yet.");
    }

    private void do_op_wide() {
        System.out.println("OP_WIDE not supported yet.");
    }

    private void do_op_multianewarray() {
        System.out.println("OP_MULTIANEWARRAY not supported yet.");
    }

    private void do_op_ifnull() {
        System.out.println("OP_IFNULL not supported yet.");
    }

    private void do_op_ifnonnull() {
        System.out.println("OP_IFNONNULL not supported yet.");
    }

    private void do_op_goto_w() {
        System.out.println("OP_GOTO_W not supported yet.");
    }

    private void do_op_jsr_w() {
        System.out.println("OP_JSR_W not supported yet.");
    }

    private void do_op_ldc_quick() {
        System.out.println("OP_LDC_QUICK not supported yet.");
    }

    private void do_op_ldc_w_quick() {
        System.out.println("OP_LDC_W_QUICK not supported yet.");
    }

    private void do_op_getfield_quick() {
        System.out.println("OP_GETFIELD_QUICK not supported yet.");
    }

    private void do_op_putfield_quick() {
        System.out.println("OP_PUTFIELD_QUICK not supported yet.");
    }

    private void do_op_getfield2_quick() {
        System.out.println("OP_GETFIELD2_QUICK not supported yet.");
    }

    private void do_op_putfield2_quick() {
        System.out.println("OP_PUTFIELD2_QUICK not supported yet.");
    }

    private void do_op_getstatic_quick() {
        System.out.println("OP_GETSTATIC_QUICK not supported yet.");
    }

    private void do_op_putstatic_quick() {
        System.out.println("OP_PUTSTATIC_QUICK not supported yet.");
    }

    private void do_op_getstatic2_quick() {
        System.out.println("OP_GETSTATIC2_QUICK not supported yet.");
    }

    private void do_op_putstatic2_quick() {
        System.out.println("OP_PUTSTATIC2_QUICK not supported yet.");
    }

    private void do_op_invokevirtual_quick() {
        System.out.println("OP_INVOKEVIRTUAL_QUICK not supported yet.");
    }

    private void do_op_invokenonvirtual_quick() {
        System.out.println("OP_INVOKENONVIRTUAL_QUICK not supported yet.");
    }

    private void do_op_invokesuper_quick() {
        System.out.println("OP_INVOKESUPER_QUICK not supported yet.");
    }

    private void do_op_getfield_quick_ref() {
        System.out.println("OP_GETFIELD_QUICK_REF not supported yet.");
    }

    private void do_op_putfield_quick_ref() {
        System.out.println("OP_PUTFIELD_QUICK_REF not supported yet.");
    }

    private void do_op_getstatic_quick_ref() {
        System.out.println("OP_GETSTATIC_QUICK_REF not supported yet.");
    }

    private void do_op_putstatic_quick_ref() {
        System.out.println("OP_PUTSTATIC_QUICK_REF not supported yet.");
    }

    private void do_op_getfield_this_ref() {
        System.out.println("OP_GETFIELD_THIS_REF not supported yet.");
    }

    private void do_op_invokevirtual_quick_w() {
        System.out.println("OP_INVOKEVIRTUAL_QUICK_W not supported yet.");
    }

    private void do_op_getfield_quick_w() {
        System.out.println("OP_GETFIELD_QUICK_W not supported yet.");
    }

    private void do_op_putfield_quick_w() {
        System.out.println("OP_PUTFIELD_QUICK_W not supported yet.");
    }

    private void do_op_getfield_this() {
        System.out.println("OP_GETFIELD_THIS not supported yet.");
    }

    private void do_op_lock() {
        System.out.println("OP_LOCK not supported yet.");
    }

    private void do_op_aload_this() {
        System.out.println("OP_ALOAD_THIS not supported yet.");
    }

    private void do_op_invokestatic_quick() {
        System.out.println("OP_INVOKESTATIC_QUICK not supported yet.");
    }

    private void do_op_new_quick() {
        System.out.println("OP_NEW_QUICK not supported yet.");
    }

    private void do_op_anewarray_quick() {
        System.out.println("OP_ANEWARRAY_QUICK not supported yet.");
    }

    private void do_op_checkcast_quick() {
        System.out.println("OP_CHECKCAST_QUICK not supported yet.");
    }

    private void do_op_instanceof_quick() {
        System.out.println("OP_INSTANCEOF_QUICK not supported yet.");
    }

    private void do_op_multianewarray_quick() {
        System.out.println("OP_MULTIANEWARRAY_QUICK not supported yet.");
    }

    private void do_op_invokeinterface_quick() {
        System.out.println("OP_INVOKEINTERFACE_QUICK not supported yet.");
    }

    private void do_op_abstract_method_error() {
        System.out.println("OP_ABSTRACT_METHOD_ERROR not supported yet.");
    }

    private void do_op_inline_rewriter() {
        System.out.println("OP_INLINE_REWRITER not supported yet.");
    }

    private void do_op_profile_rewriter() {
        System.out.println("OP_PROFILE_REWRITER not supported yet.");
    }

}
