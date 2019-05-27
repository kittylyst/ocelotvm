package ocelot;

import org.junit.BeforeClass;
import org.junit.Test;


import static ocelot.Opcode.*;
import static org.junit.Assert.*;

import org.junit.Ignore;

/**
 *
 * @author ben
 */
public class TestIntArithmetic {

    private static InterpMain im;
    
    @BeforeClass
    public static void setup() {
        im = new InterpMain();
    }

    // General form of a simple test case should be:
    //
    // 1.  Set up a byte array of the opcodes to test
    // 1.a Ensure that this ends with an opcode from the RETURN family
    // 2. Pass to an InterpMain instance
    // 3. Look at the return value
    @Test
    public void int_divide_works() {
        byte[] buf = {ICONST_5.B(), ICONST_3.B(), IDIV.B(), IRETURN.B()};
        JVMValue res = im.execMethod("", "main:()V", buf, new InterpLocalVars());
        assertEquals("Return type is int", JVMType.I, res.type);
        assertEquals("Return value should be 1", 1, (int) res.value);

        byte[] buf1 = {ICONST_2.B(), ICONST_2.B(), IDIV.B(), IRETURN.B()};
        res = im.execMethod("", "main:()V", buf1, new InterpLocalVars());
        assertEquals("Return type is int", JVMType.I, res.type);
        assertEquals("Return value should be 1", 1, (int) res.value);

        byte[] buf2 = {BIPUSH.B(), (byte)17, BIPUSH.B(), (byte)5, IREM.B(), IRETURN.B()};
        res = im.execMethod("", "main:()V", buf2, new InterpLocalVars());
        assertEquals("Return type is int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);
    }

    @Test
    public void int_arithmetic_works() {

        byte[] buf = {ICONST_1.B(), ICONST_1.B(), IADD.B(), IRETURN.B()};
        JVMValue res = im.execMethod("", "main:()V", buf, new InterpLocalVars());

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);

        byte[] buf2 = {ICONST_1.B(), ICONST_M1.B(), IADD.B(), IRETURN.B()};
        res = im.execMethod("", "main:()V", buf2, new InterpLocalVars());
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 0", 0, (int) res.value);

        byte[] buf3 = {ICONST_2.B(), ICONST_M1.B(), IMUL.B(), IRETURN.B()};
        res = im.execMethod("", "main:()V", buf3, new InterpLocalVars());
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be -2", -2, (int) res.value);
    }

    @Test
    public void iconst_store_iinc_load() {
        byte[] buf = {ICONST_1.B(), ISTORE.B(), (byte) 1, IINC.B(), (byte) 1, (byte) 1, ILOAD.B(), (byte) 1, IRETURN.B()};
        JVMValue res = im.execMethod("", "main:()V", buf, new InterpLocalVars());

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);
    }

    @Test
    public void iconst_dup() {
        byte[] buf = {ICONST_1.B(), DUP.B(), IADD.B(), IRETURN.B()};
        JVMValue res = im.execMethod("", "main:()V", buf, new InterpLocalVars());

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);

        byte[] buf2 = {ICONST_1.B(), DUP.B(), IADD.B(), DUP.B(), IADD.B(), IRETURN.B()};
        res = im.execMethod("", "main:()V", buf2, new InterpLocalVars());

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 4", 4, (int) res.value);
    }

    @Test
    public void iconst_dup_nop_pop() {
        byte[] buf = {ICONST_1.B(), DUP.B(), NOP.B(), POP.B(), IRETURN.B()};
        JVMValue res = im.execMethod("", "main:()V", buf, new InterpLocalVars());

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 1", 1, (int) res.value);

        byte[] buf2 = {ICONST_1.B(), DUP.B(), NOP.B(), POP.B(), POP.B(), RETURN.B()};
        res = im.execMethod("", "main:()V", buf2, new InterpLocalVars());

        assertNull("Return should be null", res);
    }

    @Test
    public void iconst_dup_x1() {
        byte[] buf = {ICONST_1.B(), ICONST_2.B(), DUP_X1.B(), IADD.B(), IADD.B(), IRETURN.B()};
        JVMValue res = im.execMethod("", "main:()V", buf, new InterpLocalVars());

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 5", 5, (int) res.value);

        byte[] buf2 = {ICONST_1.B(), ICONST_2.B(), DUP_X1.B(), IADD.B(), DUP_X1.B(), IADD.B(), IADD.B(), IRETURN.B()};
        res = im.execMethod("", "main:()V", buf2, new InterpLocalVars());

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 8", 8, (int) res.value);
    }

}
