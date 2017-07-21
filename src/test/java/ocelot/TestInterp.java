package ocelot;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ocelot.Opcode.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 *
 * @author ben
 */
public class TestInterp {

    private static InterpMain im = new InterpMain();

    // General form of a simple test case should be:
    //
    // 1.  Set up a byte array of the opcodes to test
    // 1.a Ensure that this ends with an opcode from the RETURN family
    // 2. Pass to an InterpMain instance
    // 3. Look at the return value
    
    @Test
    public void int_arithmetic_works() {
        byte[] buf = {ICONST_1.B(), ICONST_1.B(), IADD.B(), IRETURN.B()};
        JVMValue res = im.execMethod(buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);

        // Using the raw bytes instead of the enum mnemonics
        byte[] buf2 = {0x04, 0x02, 0x60, (byte) 0xac};
        res = im.execMethod(buf2);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 0", 0, (int) res.value);

        // Using the raw bytes instead of the enum mnemonics
        byte[] buf3 = {0x05, 0x02, 0x68, (byte) 0xac};
        res = im.execMethod(buf3);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be -2", -2, (int) res.value);
    }

    @Test
    public void jump_over_unimpld_opcodes() {
        byte[] buf = {ICONST_1.B(), ICONST_1.B(), IADD.B(), GOTO.B(), (byte) 0, (byte) 1, (byte) 0xff, IRETURN.B()};
        JVMValue res = im.execMethod(buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);
    }

    @Test
    public void iconst_store_iinc_load() {
        byte[] buf = {ICONST_1.B(), ISTORE.B(), (byte) 1, IINC.B(), (byte) 1, (byte) 1, ILOAD.B(), (byte) 1, IRETURN.B()};
        JVMValue res = im.execMethod(buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);
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
        JVMValue res = im.execMethod(buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);

        byte[] buf2 = {ICONST_2.B(), ISTORE_1.B(), ILOAD_1.B(), IFNE.B(), (byte) 0, (byte) 6,
            IINC.B(), (byte) 1, (byte) 1, ILOAD.B(), (byte) 1, IRETURN.B()};
        System.out.println(Arrays.toString(buf2));
        res = im.execMethod(buf2);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);

    }

    @Test
    public void iconst_dup() {
        byte[] buf = {ICONST_1.B(), DUP.B(), IADD.B(), IRETURN.B()};
        JVMValue res = im.execMethod(buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);

        byte[] buf2 = {ICONST_1.B(), DUP.B(), IADD.B(), DUP.B(), IADD.B(), IRETURN.B()};
        res = im.execMethod(buf2);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 4", 4, (int) res.value);
    }

    @Test
    public void iconst_dup_nop_pop() {
        byte[] buf = {ICONST_1.B(), DUP.B(), NOP.B(), POP.B(), IRETURN.B()};
        JVMValue res = im.execMethod(buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 1", 1, (int) res.value);

        byte[] buf2 = {ICONST_1.B(), DUP.B(), NOP.B(), POP.B(), POP.B(), RETURN.B()};
        res = im.execMethod(buf2);

        assertNull("Return should be null", res);
    }

    @Test
    public void iconst_dup_x1() {
        byte[] buf = {ICONST_1.B(), ICONST_2.B(), DUP_X1.B(), IADD.B(), IADD.B(), IRETURN.B()};
        JVMValue res = im.execMethod(buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 5, (int) res.value);

        byte[] buf2 = {ICONST_1.B(), ICONST_2.B(), DUP_X1.B(), IADD.B(), DUP_X1.B(), IADD.B(), IADD.B(), IRETURN.B()};
        res = im.execMethod(buf2);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 4", 8, (int) res.value);
    }

    //////////////////////////////////////
    // 
    // Simple file-based tests
    @Test
    public void hello_world_loaded_from_file_executes() throws IOException {
        byte[] buf = Utils.pullBytes("Println.class");
        int offset = 481; // Harcoded / hand-figured out
        byte[] tmp = {buf[offset], buf[offset + 1], buf[offset + 2], buf[offset + 3],
            buf[offset + 4], buf[offset + 5], buf[offset + 6], buf[offset + 7], buf[offset + 8]}; // new byte[9];
        System.out.println(Arrays.toString(tmp));

        assertNull("Hello World should execute", im.execMethod(tmp));
    }

    @Test
    public void simple_branching_executes() throws IOException {
        byte[] buf = Utils.pullBytes("optjava/bc/SimpleTests.class");
        int offset = 330; // Harcoded / hand-figured out
        byte[] tmp = {buf[offset], buf[offset + 1], buf[offset + 2], buf[offset + 3],
            buf[offset + 4], buf[offset + 5], buf[offset + 6], buf[offset + 7], buf[offset + 8], buf[offset + 9], buf[offset + 10]}; // new byte[11];
        System.out.println(Arrays.toString(tmp));

        JVMValue res = im.execMethod(tmp);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);
    }

}
