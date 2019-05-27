package ocelot;

import java.util.Arrays;
import static ocelot.Opcode.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author ben
 */
public class TestJumpBranch {

    private InterpMain im;

    @Before
    public void setup() {
        im = new InterpMain();
    }

    @Test
    public void jump_over_unimpld_opcodes() {
        byte[] buf = {ICONST_1.B(), ICONST_1.B(), IADD.B(), GOTO.B(), (byte) 0, (byte) 1, (byte) 0xff, IRETURN.B()};
        JVMValue res = im.execMethod("", "main:()V", buf, new InterpLocalVars());

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
//        System.out.println(Arrays.toString(buf));
        JVMValue res = im.execMethod("", "main:()V", buf, new InterpLocalVars());

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);

        byte[] buf2 = {ICONST_2.B(), ISTORE_1.B(), ILOAD_1.B(), IFNE.B(), (byte) 0, (byte) 6,
            IINC.B(), (byte) 1, (byte) 1, ILOAD.B(), (byte) 1, IRETURN.B()};
//        System.out.println(Arrays.toString(buf2));
        res = im.execMethod("", "main:()V", buf2, new InterpLocalVars());

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);

    }

    @Test
    @Ignore
    public void TestIntIfEqPrim() {
        byte[] buffy = {ICONST_1.B(), ICONST_1.B(), IADD.B(), ICONST_2.B(), IF_ICMPEQ.B(), (byte) 0, (byte) 11, ICONST_4.B(), GOTO.B(), (byte) 0, (byte) 12, ICONST_3.B(), IRETURN.B()};

        JVMValue res = im.execMethod("", "main:()V", buffy, new InterpLocalVars());

        assertEquals(JVMType.I, res.type);
        assertEquals(2, ((int) res.value));

        byte[] buffy2 = {ICONST_1.B(), ICONST_1.B(), IADD.B(), ICONST_3.B(), IF_ICMPEQ.B(), (byte) 0, (byte) 11, ICONST_4.B(), GOTO.B(), (byte) 0, (byte) 12, ICONST_3.B(), IRETURN.B()};
        res = im.execMethod("", "main:()V", buffy2, new InterpLocalVars());

        assertEquals(JVMType.I, res.type);
        assertEquals(2, ((int) res.value));
    }

}
