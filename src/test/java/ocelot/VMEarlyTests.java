package ocelot;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author boxcat
 */
public class VMEarlyTests {

    private static InterpMain vm = new InterpMain();

    @BeforeClass
    public static void init() {
        vm.init();
    }

    /*
    @Test
    public void TestDoublesBasic() {
        byte[] buffy = {}; // {DCONST_1.B(), DCONST_1.B(), DADD.B()};
        
        JVMValue res = vm.execMethod(buffy);
        
        assertEquals(JVMPrimVsRefType.DOUBLE, res.getType());
        assertEquals(2.0, (Double) res.getValue(), 0.0001);
    }

    @Test
    public void TestIntIfEqPrim() {
        Main vm = new Main();
        byte[] buffy = {OP_ICONST_1, OP_ICONST_1, OP_IADD, OP_ICONST_2, OP_IF_ICMPEQ, 0, 11, OP_ICONST_4, OP_GOTO, 0, 12, OP_ICONST_3, 0};
        vm.loadBytecodes(buffy);
        vm.doSingleOpCode();
        vm.doSingleOpCode();
        vm.doSingleOpCode();
        JVMValue res = vm.topOfStack();
        assertEquals(JVMPrimVsRefType.INT, res.getType());
        assertEquals(2, ((Integer) res.getValue()).intValue());
        vm.interpret();
        res = vm.topOfStack();
        assertEquals(JVMPrimVsRefType.INT, res.getType());
        assertEquals(3, ((Integer) res.getValue()).intValue());

        byte[] buffy2 = {OP_ICONST_1, OP_ICONST_1, OP_IADD, OP_ICONST_3, OP_IF_ICMPEQ, 0, 11, OP_ICONST_4, OP_GOTO, 0, 12, OP_ICONST_3, 0};
        vm.loadBytecodes(buffy2);
        vm.doSingleOpCode();
        vm.doSingleOpCode();
        vm.doSingleOpCode();
        res = vm.topOfStack();
        assertEquals(JVMPrimVsRefType.INT, res.getType());
        assertEquals(2, ((Integer) res.getValue()).intValue());
        vm.interpret();
        res = vm.topOfStack();
        assertEquals(JVMPrimVsRefType.INT, res.getType());
        assertEquals(4, ((Integer) res.getValue()).intValue());
    }

    @Test
    public void TestIntIfNePrim() {
        Main vm = new Main();
        byte[] buffy = {OP_ICONST_1, OP_ICONST_1, OP_IADD, OP_ICONST_2, OP_IF_ICMPNE, 0, 11, OP_ICONST_4, OP_GOTO, 0, 12, OP_ICONST_3, 0};
        vm.loadBytecodes(buffy);
        vm.doSingleOpCode();
        vm.doSingleOpCode();
        vm.doSingleOpCode();
        JVMValue res = vm.topOfStack();
        assertEquals(JVMPrimVsRefType.INT, res.getType());
        assertEquals(2, ((Integer) res.getValue()).intValue());
        vm.interpret();
        res = vm.topOfStack();
        assertEquals(JVMPrimVsRefType.INT, res.getType());
        assertEquals(4, ((Integer) res.getValue()).intValue());

        byte[] buffy2 = {OP_ICONST_1, OP_ICONST_1, OP_IADD, OP_ICONST_3, OP_IF_ICMPNE, 0, 11, OP_ICONST_4, OP_GOTO, 0, 12, OP_ICONST_3, 0};
        vm.loadBytecodes(buffy2);
        vm.doSingleOpCode();
        vm.doSingleOpCode();
        vm.doSingleOpCode();
        res = vm.topOfStack();
        assertEquals(JVMPrimVsRefType.INT, res.getType());
        assertEquals(2, ((Integer) res.getValue()).intValue());
        vm.interpret();
        res = vm.topOfStack();
        assertEquals(JVMPrimVsRefType.INT, res.getType());
        assertEquals(3, ((Integer) res.getValue()).intValue());
    }
*/
    
    // FIXME Add tests for IF_ICMPLT
}
