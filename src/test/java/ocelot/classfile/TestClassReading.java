package ocelot.classfile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import ocelot.*;
import static org.junit.Assert.*;
import org.junit.Test;
import static ocelot.classfile.CPType.*;
import ocelot.rt.ClassRepository;

/**
 *
 * @author ben
 */
public class TestClassReading {

    private static InterpMain im = new InterpMain(new ClassRepository());
    private ClassRepository repo = new ClassRepository();

    private OCKlass ce;
    private byte[] buf;

    @Test
    public void check_cp_for_hello_world() throws IOException, ClassNotFoundException {
        String fName = "Println.class";
        buf = Utils.pullBytes(fName);
        ce = new OCKlass(buf, fName);
        ce.parseHeader();
        assertEquals("Major version should be 52", 52, ce.getMajor());
        assertEquals("Minor version should be 0", 0, ce.getMinor());
        assertEquals("Constant Pool should contain 34 items", 34, ce.getPoolItemCount());
        ce.parseConstantPool();
        CPEntry cp = ce.getCPEntry(1);
        assertEquals("Entry 1 should be a METHODREF", METHODREF, cp.getType());
        int other = cp.getRef().getOther();
        assertEquals("Entry 1 should point at Entry 6", 6, other);
        CPEntry cp2 = ce.getCPEntry(other);
        assertEquals("Entry 6 should be a CLASS", CLASS, cp2.getType());
        other = cp2.getRef().getOther();
        assertEquals("Entry 6 should point at Entry 27", 27, other);
        cp2 = ce.getCPEntry(other);
        assertEquals("Entry 27 should be a UTF8", UTF8, cp2.getType());
        assertEquals("Entry 27 should be java/lang/Object", "java/lang/Object", cp2.getStr());

//        for (int i =1; i < ce.getPoolItemCount(); i++) {
//            System.out.println(ce.resolveAsString(i));
//        }
        assertEquals("Entry 1 should be Object's ctor", "java/lang/Object.<init>:()V", ce.resolveAsString(1));
        ce.parseBasicTypeInfo();
        assertTrue(fName + " should be public", ce.isPublic());
        assertFalse(fName + " should not be abstract", ce.isAbstract());
        assertFalse(fName + " should not be annotation", ce.isAnnotation());

        CPEntry self = ce.getThisClass();
        String toStr = ce.resolveAsString(self.getIndex());
        assertEquals("Self class should resolve to kathik/Println", "kathik/Println", toStr);
        CPEntry superClz = ce.getSuperClass();
        toStr = ce.resolveAsString(superClz.getIndex());
        assertEquals("Superclass should resolve to java/lang/Object", "java/lang/Object", toStr);
    }

    @Test
    public void check_simple_fields_methods() throws IOException, ClassNotFoundException {
        String fName = "octest/SimpleFieldsAndMethods.class";
        buf = Utils.pullBytes(fName);
        ce = new OCKlass(buf, fName);
        ce.parseHeader();
        assertEquals("Major version should be 52", 52, ce.getMajor());
        assertEquals("Minor version should be 0", 0, ce.getMinor());
        assertEquals("Constant Pool should contain 24 items", 24, ce.getPoolItemCount());
        ce.parseConstantPool();

        ce.parseBasicTypeInfo();
        assertTrue(fName + " should be public", ce.isPublic());
        assertFalse(fName + " should not be abstract", ce.isAbstract());
        assertFalse(fName + " should not be annotation", ce.isAnnotation());

        ce.parseFields();
        List<OCKlass.CPField> fields = ce.getFields();
        assertEquals(fName + " should have 1 field", 1, fields.size());
        int idx = fields.get(0).getNameIndex();
        assertEquals(fName + " should have a field called a", "a", ce.getCPEntry(idx).getStr());
        idx = fields.get(0).getDescIndex();
        assertEquals(fName + " should have a field called a of type I", "I", ce.getCPEntry(idx).getStr());

        ce.parseMethods();
        List<OCKlass.CPMethod> methods = ce.getMethods();
        assertEquals(fName + " should have 2 methods", 2, methods.size());
        OCKlass.CPMethod init = methods.get(0);
        idx = init.getNameIndex();
        assertEquals(fName + " should have a method called <init>", "<init>", ce.getCPEntry(idx).getStr());
        idx = init.getDescIndex();
        assertEquals(fName + " should have a method of type ()V", "()V", ce.getCPEntry(idx).getStr());
        byte[] b = init.getBuf();
        System.out.println(Arrays.toString(b));
//        InterpMain im = new InterpMain();
//        im.execMethod(b);
    }

    @Test
    public void check_names() throws IOException, ClassNotFoundException {
        String fName = "Println.class";
        buf = Utils.pullBytes(fName);
        ce = new OCKlass(buf, fName);
        ce.parse();
        String clzName = ce.className();
        assertEquals("kathik/Println", clzName);

        fName = "octest/SimpleFieldsAndMethods.class";
        buf = Utils.pullBytes(fName);
        ce = new OCKlass(buf, fName);
        ce.parse();
        clzName = ce.className();
        assertEquals("octest/SimpleFieldsAndMethods", clzName);
    }

    @Test
    public void simple_invoke() throws Exception {
        String fName = "SampleInvoke.class";
        buf = Utils.pullBytes(fName);
        ce = new OCKlass(buf, fName);
        ce.parse();
        ClassRepository repo = new ClassRepository();
        repo.add(ce);
        InterpMain im = new InterpMain(repo);

        OCKlass.CPMethod meth = ce.getMethodByName("foo:()I"); // "main:([Ljava/lang/String;)V"
        JVMValue res = im.execMethod(meth);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 9", 9, (int) res.value);

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
        buf = Utils.pullBytes(fName);
        ce = OCKlass.of(buf, fName);

        repo.add(ce);
        im = new InterpMain(repo);

        OCKlass.CPMethod meth = ce.getMethodByName("main:([Ljava/lang/String;)V");

        assertNull("Hello World should execute", im.execMethod(meth));
    }

    @Test
    public void simple_branching_executes() throws Exception {
        String fName = "optjava/bc/SimpleTests.class";
        buf = Utils.pullBytes(fName);
        ce = OCKlass.of(buf, fName);

        repo.add(ce);
        im = new InterpMain(repo);

        OCKlass.CPMethod meth = ce.getMethodByName("if_bc:()I");
        JVMValue res = im.execMethod(meth);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);
//        int offset = 330; // Harcoded / hand-figured out
//        byte[] tmp = {buf[offset], buf[offset + 1], buf[offset + 2], buf[offset + 3],
//            buf[offset + 4], buf[offset + 5], buf[offset + 6], buf[offset + 7], buf[offset + 8], buf[offset + 9], buf[offset + 10]}; // new byte[11];
//        System.out.println(Arrays.toString(tmp));
    }

    @Test
    public void simple_static_calls_executes() throws Exception {
        String fName = "octest/StaticCalls.class";
        buf = Utils.pullBytes(fName);
        ce = OCKlass.of(buf, fName);

        repo.add(ce);
        im = new InterpMain(repo);

        OCKlass.CPMethod meth = ce.getMethodByName("call1:()I"); // "main:([Ljava/lang/String;)V");
        JVMValue res = im.execMethod(meth);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 23", 23, (int) res.value);

        meth = ce.getMethodByName("call4:()I");
        res = im.execMethod(meth);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 47", 47, (int) res.value);

        meth = ce.getMethodByName("adder:(II)I");
        LocalVars lv = new LocalVars();
        JVMValue[] vars = new JVMValue[2];
        vars[0] = new JVMValue(JVMType.I, 5L);
        vars[1] = new JVMValue(JVMType.I, 7L);
        lv.setup(vars);
        
        res = im.execMethod(meth, lv);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 12", 12, (int) res.value);

    }

}
