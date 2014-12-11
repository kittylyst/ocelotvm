/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adoptopenjdk.ocelotvm;

import static org.adoptopenjdk.ocelotvm.Opcodes.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author boxcat
 */
public class VMEarlyTests {

    @Test
    public void TestDoublesBasic() {
        Main vm = new Main();
        byte[] buffy = {OP_DCONST_1, OP_DCONST_1, OP_DADD};
        vm.interpret(buffy);
        JVMValue res = vm.topOfStack();
        assertEquals(JVMPrimVsRefType.DOUBLE, res.getType());
        assertEquals(2.0, (Double) res.getValue(), 0.0001);
    }

    @Test
    public void TestIntIfEqBasic() {
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
    public void TestIntIfNeBasic() {
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
    
    // FIXME Add tests for IF_ICMPLT
}
