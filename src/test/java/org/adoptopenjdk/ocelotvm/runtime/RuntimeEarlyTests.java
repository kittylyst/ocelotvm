/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adoptopenjdk.ocelotvm.runtime;

import org.adoptopenjdk.ocelotvm.JVMPrimVsRefType;
import org.adoptopenjdk.ocelotvm.JVMValue;
import org.adoptopenjdk.ocelotvm.Main;
import static org.adoptopenjdk.ocelotvm.Opcodes.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author boxcat
 */
public class RuntimeEarlyTests {

    @Test
    public void TestNewBasic() {
        Main vm = new Main();   
        byte[] buffy = {OP_NOP, OP_NEW, 0, 1};
        vm.interpret(buffy);
        JVMValue res = vm.topOfStack();
        vm.printStack();
        assertEquals(JVMPrimVsRefType.REF, res.getType());
        assertEquals(ClassRepository.newTypeMetadata(ClassRepository.STRSIG), ((JVMObj) res.getValue()).getTypeMetadata());
    }

}
