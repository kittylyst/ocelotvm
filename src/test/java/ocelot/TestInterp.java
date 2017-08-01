package ocelot;

import org.junit.Test;

import java.util.Arrays;

import static ocelot.Opcode.*;
import ocelot.classfile.OcelotClass;
import ocelot.rt.ClassRepository;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;

/**
 *
 * @author ben
 */
public class TestInterp {

    public static final String MAIN_METHOD_DESC = "main:()V";
    
    private static InterpMain im = new InterpMain(new ClassRepository());

    ClassRepository repo = new ClassRepository();

    @Before
    public void init() {
        repo = new ClassRepository();
    }

    // General form of a simple test case should be:
    //
    // 1.  Set up a byte array of the opcodes to test
    // 1.a Ensure that this ends with an opcode from the RETURN family
    // 2. Pass to an InterpMain instance
    // 3. Look at the return value
    @Test
    public void int_divide_works() {
        byte[] buf = {ICONST_2.B(), ICONST_2.B(), IDIV.B(), IRETURN.B()};
        JVMValue res = im.execMethod("", MAIN_METHOD_DESC, buf);
        assertEquals("Return type is int", JVMType.I, res.type);
        assertEquals("Return value should be 1", 1, (int) res.value);

        byte[] buf1 = {0x05, 0x05, 0x6c, (byte) 0xac};
        res = im.execMethod("", MAIN_METHOD_DESC, buf1);
        assertEquals("Return type is int", JVMType.I, res.type);
        assertEquals("Return value should be 1", 1, (int) res.value);

    }

    @Test
    public void int_arithmetic_works() {
        byte[] buf = {ICONST_1.B(), ICONST_1.B(), IADD.B(), IRETURN.B()};
        JVMValue res = im.execMethod("", MAIN_METHOD_DESC, buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);

        // Using the raw bytes instead of the enum mnemonics
        byte[] buf2 = {0x04, 0x02, 0x60, (byte) 0xac};
        res = im.execMethod("", MAIN_METHOD_DESC, buf2);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 0", 0, (int) res.value);

        // Using the raw bytes instead of the enum mnemonics
        byte[] buf3 = {0x05, 0x02, 0x68, (byte) 0xac};
        res = im.execMethod("", MAIN_METHOD_DESC, buf3);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be -2", -2, (int) res.value);
    }

    @Test
    public void jump_over_unimpld_opcodes() {
        byte[] buf = {ICONST_1.B(), ICONST_1.B(), IADD.B(), GOTO.B(), (byte) 0, (byte) 1, (byte) 0xff, IRETURN.B()};
        JVMValue res = im.execMethod("", MAIN_METHOD_DESC, buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);
    }

    @Test
    public void iconst_store_iinc_load() {
        byte[] buf = {ICONST_1.B(), ISTORE.B(), (byte) 1, IINC.B(), (byte) 1, (byte) 1, ILOAD.B(), (byte) 1, IRETURN.B()};
        JVMValue res = im.execMethod("", MAIN_METHOD_DESC, buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);
    }

    @Test
    public void double_add_works() {
        byte[] buf = {DCONST_1.B(), DCONST_1.B(), DADD.B(), DRETURN.B()};
        JVMValue res = im.execMethod("", MAIN_METHOD_DESC, buf);
        assertEquals("Return type is double", JVMType.D, res.type);
        assertEquals("Return value is 2.0", 2.0d, Double.longBitsToDouble(res.value), 0.0);
    }

    @Test
    public void double_multiply_works() {
        byte[] buf = {DCONST_1.B(), DCONST_1.B(), DMUL.B(), DRETURN.B()};
        JVMValue res = im.execMethod("", MAIN_METHOD_DESC, buf);
        assertEquals("Return type is double", JVMType.D, res.type);
        assertEquals("Return value is 1.0", 1.0d, Double.longBitsToDouble(res.value), 0.0);
    }

    @Test
    public void double_divide_works() {
        byte[] buf = {DCONST_1.B(), DCONST_1.B(), DDIV.B(), DRETURN.B()};
        JVMValue res = im.execMethod("", MAIN_METHOD_DESC, buf);
        assertEquals("Return type is double", JVMType.D, res.type);
        assertEquals("Return value is 1.0", 1.0d, Double.longBitsToDouble(res.value), 0.0);
    }

    @Test
    public void double_negative_works() {
        byte[] buf = {DCONST_1.B(), DNEG.B(), DRETURN.B()};
        JVMValue res = im.execMethod("", MAIN_METHOD_DESC, buf);
        assertEquals("Return type is double", JVMType.D, res.type);
        assertEquals("Return value is -1.0", -1.0d, Double.longBitsToDouble(res.value), 0.0);

        byte[] buf2 = {DCONST_1.B(), DNEG.B(), DNEG.B(), DRETURN.B()};
        JVMValue res2 = im.execMethod("", MAIN_METHOD_DESC, buf2);
        assertEquals("Return type is double", JVMType.D, res2.type);
        assertEquals("Return value is 1.0", 1.0d, Double.longBitsToDouble(res2.value), 0.0);
    }

    @Test
    public void double_store_load() {
        byte[] buf = {DCONST_1.B(), DCONST_1.B(), DSTORE.B(), (byte) 5, DSTORE_0.B(), DLOAD.B(), (byte) 5, DLOAD_0.B(), DADD.B(), DRETURN.B()};

        JVMValue res = im.execMethod("", MAIN_METHOD_DESC, buf);

        assertEquals("Return type is double", JVMType.D, res.type);
        assertEquals("Return value is 2.0", 2.0d, Double.longBitsToDouble(res.value), 0.0);
    }

    // Simple if tests
//       0: iconst_2
//       1: istore_1
//       2: iload_1
//       3: ifne          9
//       6: iinc          1, 1
//       9: iload_1
//      10: ireturn
    @Test
    public void test_ifne() {
        byte[] buf = {ICONST_2.B(), ISTORE.B(), (byte) 1, ILOAD.B(), (byte) 1, IFNE.B(), (byte) 0, (byte) 6,
            IINC.B(), (byte) 1, (byte) 1, ILOAD.B(), (byte) 1, IRETURN.B()};
        System.out.println(Arrays.toString(buf));
        JVMValue res = im.execMethod("", MAIN_METHOD_DESC, buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);

        byte[] buf2 = {ICONST_2.B(), ISTORE_1.B(), ILOAD_1.B(), IFNE.B(), (byte) 0, (byte) 6,
            IINC.B(), (byte) 1, (byte) 1, ILOAD.B(), (byte) 1, IRETURN.B()};
        System.out.println(Arrays.toString(buf2));
        res = im.execMethod("", MAIN_METHOD_DESC, buf2);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);

    }

    @Test
    public void iconst_dup() {
        byte[] buf = {ICONST_1.B(), DUP.B(), IADD.B(), IRETURN.B()};
        JVMValue res = im.execMethod("", MAIN_METHOD_DESC, buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);

        byte[] buf2 = {ICONST_1.B(), DUP.B(), IADD.B(), DUP.B(), IADD.B(), IRETURN.B()};
        res = im.execMethod("", MAIN_METHOD_DESC, buf2);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 4", 4, (int) res.value);
    }

    @Test
    public void iconst_dup_nop_pop() {
        byte[] buf = {ICONST_1.B(), DUP.B(), NOP.B(), POP.B(), IRETURN.B()};
        JVMValue res = im.execMethod("", MAIN_METHOD_DESC, buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 1", 1, (int) res.value);

        byte[] buf2 = {ICONST_1.B(), DUP.B(), NOP.B(), POP.B(), POP.B(), RETURN.B()};
        res = im.execMethod("", MAIN_METHOD_DESC, buf2);

        assertNull("Return should be null", res);
    }

    @Test
    public void iconst_dup_x1() {
        byte[] buf = {ICONST_1.B(), ICONST_2.B(), DUP_X1.B(), IADD.B(), IADD.B(), IRETURN.B()};
        JVMValue res = im.execMethod("", MAIN_METHOD_DESC, buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 5, (int) res.value);

        byte[] buf2 = {ICONST_1.B(), ICONST_2.B(), DUP_X1.B(), IADD.B(), DUP_X1.B(), IADD.B(), IADD.B(), IRETURN.B()};
        res = im.execMethod("", MAIN_METHOD_DESC, buf2);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 4", 8, (int) res.value);
    }

    @Test
    @Ignore
    public void TestIntIfEqPrim() {
        InterpMain vm = new InterpMain(new ClassRepository());
        byte[] buffy = {ICONST_1.B(), ICONST_1.B(), IADD.B(), ICONST_2.B(), IF_ICMPEQ.B(), (byte) 0, (byte) 11, ICONST_4.B(), GOTO.B(), (byte) 0, (byte) 12, ICONST_3.B(), IRETURN.B()};

        JVMValue res = vm.execMethod("", MAIN_METHOD_DESC, buffy);

        assertEquals(JVMType.I, res.type);
        assertEquals(2, ((int) res.value));

        byte[] buffy2 = {ICONST_1.B(), ICONST_1.B(), IADD.B(), ICONST_3.B(), IF_ICMPEQ.B(), (byte) 0, (byte) 11, ICONST_4.B(), GOTO.B(), (byte) 0, (byte) 12, ICONST_3.B(), IRETURN.B()};
        res = vm.execMethod("", MAIN_METHOD_DESC, buffy2);

        assertEquals(JVMType.I, res.type);
        assertEquals(2, ((int) res.value));
    }

    //////////////////////////////////////
    //
    // Simple file-based tests
    @Test
    public void hello_world_loaded_from_file_executes() throws Exception {
//        byte[] buf = Utils.pullBytes("Println.class");
//        int offset = 481; // Harcoded / hand-figured out
//        byte[] tmp = {buf[offset], buf[offset + 1], buf[offset + 2], buf[offset + 3],
//            buf[offset + 4], buf[offset + 5], buf[offset + 6], buf[offset + 7], buf[offset + 8]}; // new byte[9];
//        System.out.println(Arrays.toString(tmp));

        String fName = "Println.class";
        byte[] buf = Utils.pullBytes(fName);
        OcelotClass ce = OcelotClass.of(buf, fName);

        repo.add(ce);
        im = new InterpMain(repo);

        OcelotClass.CPMethod meth = ce.getMethodByName("main:([Ljava/lang/String;)V");

        assertNull("Hello World should execute", im.execMethod(meth));
    }

    @Test
    public void simple_branching_executes() throws Exception {
        String fName = "optjava/bc/SimpleTests.class";
        byte[] buf = Utils.pullBytes(fName);
        OcelotClass ce = OcelotClass.of(buf, fName);

        repo.add(ce);
        im = new InterpMain(repo);

        OcelotClass.CPMethod meth = ce.getMethodByName("if_bc:()I");
        JVMValue res = im.execMethod(meth);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);
//        int offset = 330; // Harcoded / hand-figured out
//        byte[] tmp = {buf[offset], buf[offset + 1], buf[offset + 2], buf[offset + 3],
//            buf[offset + 4], buf[offset + 5], buf[offset + 6], buf[offset + 7], buf[offset + 8], buf[offset + 9], buf[offset + 10]}; // new byte[11];
//        System.out.println(Arrays.toString(tmp));
    }

}
