package ocelot.rt;

/**
 *
 * @author boxcat
 */
public class RuntimeEarlyTests {

    /*
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

    @Test
    @Ignore
    public void TestIntIfEqRef() {
        Main vm = new Main();

        // FIXME: Check & fix loop offsets
        byte[] buffy = {OP_NEW, 0, 1, OP_ASTORE_1, OP_ALOAD_1, OP_DUP, OP_IF_ACMPEQ, 0, 11, OP_ICONST_4, OP_GOTO, 0, 12, OP_ICONST_3, OP_NOP};
        vm.interpret(buffy);
        JVMValue res = vm.topOfStack();
        assertEquals(JVMPrimVsRefType.INT, res.getType());
        assertEquals(2, ((Integer) res.getValue()).intValue());
    }
*/
    
}
