package ocelot;

import static ocelot.JVMValue.entry;

import ocelot.classfile.OtKlassParser;

import static ocelot.classfile.OtKlassParser.ACC_PRIVATE;
import static ocelot.classfile.OtKlassParser.ACC_PUBLIC;
import static ocelot.classfile.OtKlassParser.ACC_STATIC;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import ocelot.rt.SharedKlassRepo;
import ocelot.rt.OtKlass;
import ocelot.rt.OtMethod;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author ben
 */
public class TestInterp {

    private InterpMain im;
    private SharedKlassRepo repo;

    @Before
    public void setup() {
        repo = SharedKlassRepo.of();
    }

    private byte[] buf;

    @Test
    @Ignore
    public void hello_world_loaded_from_file_executes() throws Exception {
        String fName = "Println.class";
        buf = Utils.pullBytes(fName);
        OtKlass klass = OtKlassParser.of(null, buf, fName);

        repo.add(klass);
        im = new InterpMain(repo);

        OtMethod meth = klass.getMethodByName("main:([Ljava/lang/String;)V");
        assertEquals("Flags should be public, static", meth.getFlags(), ACC_PUBLIC | ACC_STATIC);

        assertNull("Hello World should execute", im.execMethod(meth));
    }

    @Test
    public void simple_branching_executes() throws Exception {
        String fName = "optjava/bc/SimpleTests.class";
        buf = Utils.pullBytes(fName);
        OtKlass klass = OtKlassParser.of(null, buf, fName);

        repo.add(klass);
        im = new InterpMain(repo);

        OtMethod meth = klass.getMethodByName("if_bc:()I");
        assertEquals("Flags should be public", ACC_PUBLIC, meth.getFlags());

        JVMValue res = im.execMethod(meth);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);
    }

    @Test
    public void simple_static_calls_executes() throws Exception {
        String fName = "octest/StaticCalls.class";
        buf = Utils.pullBytes(fName);
        OtKlass klass = OtKlassParser.of(null, buf, fName);

        repo.add(klass);
        im = new InterpMain(repo);

        OtMethod meth = klass.getMethodByName("call1:()I"); // "main:([Ljava/lang/String;)V");
        assertEquals("Flags should be public, static", ACC_PUBLIC | ACC_STATIC, meth.getFlags());
        JVMValue res = im.execMethod(meth);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 23", 23, (int) res.value);

        meth = klass.getMethodByName("call4:()I");
        assertEquals("Flags should be public, static", ACC_PUBLIC | ACC_STATIC, meth.getFlags());
        res = im.execMethod(meth);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 47", 47, (int) res.value);

        meth = klass.getMethodByName("adder:(II)I");
        assertEquals("Flags should be public, static", ACC_PUBLIC | ACC_STATIC, meth.getFlags());
        InterpLocalVars lv = new InterpLocalVars();
        JVMValue[] vars = new JVMValue[2];
        vars[0] = entry(5);
        vars[1] = entry(7);
        lv.setup(vars);

        res = im.execMethod(meth, lv);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 12", 12, (int) res.value);

    }

    @Test
    public void simple_calls_modify_static_fields() throws Exception {
        String fName = "octest/StaticCalls.class";
        buf = Utils.pullBytes(fName);
        OtKlass klass = OtKlassParser.of(null, buf, fName);

        repo.add(klass);
        im = new InterpMain(repo);

        OtMethod meth = klass.getMethodByName("setJ:(I)V");
        assertEquals("Flags should be public, static", ACC_PUBLIC | ACC_STATIC, meth.getFlags());
        InterpLocalVars lvt = new InterpLocalVars();
        JVMValue[] vs = new JVMValue[1];
        vs[0] = new JVMValue(JVMType.I, 13L);
        lvt.setup(vs);
        JVMValue res = im.execMethod(meth, lvt);

        assertNull("Call to setter should be return null", res);

        meth = klass.getMethodByName("getJ:()I");
        assertEquals("Flags should be public, static", ACC_PUBLIC | ACC_STATIC, meth.getFlags());
        res = im.execMethod(meth);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 13", 13, (int) res.value);

        meth = klass.getMethodByName("incJ:()V");
        assertEquals("Flags should be public, static", ACC_PUBLIC | ACC_STATIC, meth.getFlags());
        res = im.execMethod(meth);
        assertNull("Call to incJ() should be return null", res);

        meth = klass.getMethodByName("getJ:()I");
        res = im.execMethod(meth);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 14", 14, (int) res.value);

    }

    @Test
    public void simple_executes() throws Exception {
        String fName = "octest/Simple.class";
        buf = Utils.pullBytes(fName);
        OtKlass klass = OtKlassParser.of(null, buf, fName);

        repo.add(klass);
        im = new InterpMain(repo);

        OtMethod meth = klass.getMethodByName("simple:()I");
        assertEquals("Flags should be private, static", ACC_PRIVATE | ACC_STATIC, meth.getFlags());
        JVMValue res = im.execMethod(meth);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 23", 23, (int) res.value);
    }

    @Test
    public void simple_invoke() throws Exception {
        String fName = "SampleInvoke.class";
        buf = Utils.pullBytes(fName);
        OtKlass klass = OtKlassParser.of(null, buf, fName);

        repo.add(klass);
        im = new InterpMain(repo);

        OtMethod meth = klass.getMethodByName("foo:()I");
        assertEquals("Flags should be public, static", ACC_PUBLIC | ACC_STATIC, meth.getFlags());
        JVMValue res = im.execMethod(meth);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 9", 9, (int) res.value);
    }


    @Test
    public void simple_new_field_executes() throws Exception {
        String fName = "octest/MyInteger.class";
        buf = Utils.pullBytes(fName);
        OtKlass klass = OtKlassParser.of(null, buf, fName);
        repo.add(klass);
        im = new InterpMain(repo);

        fName = "octest/IndirectMyI.class";
        buf = Utils.pullBytes(fName);
        klass = OtKlassParser.of(im, buf, fName);
        repo.add(klass);

        fName = "octest/UseMyI.class";
        buf = Utils.pullBytes(fName);
        klass = OtKlassParser.of(im, buf, fName);
        repo.add(klass);

        OtMethod meth = klass.getMethodByName("run:()I");
        JVMValue res = im.execMethod(meth);
        assertEquals("Try to exec the ctor", 42, res.value);

        meth = klass.getMethodByName("run2:()I");
        res = im.execMethod(meth);
        assertEquals("Use an extra level of indirection", 1337, res.value);

        meth = klass.getMethodByName("runC:()I");
        res = im.execMethod(meth);
        assertEquals("Try to exec the invokevirtual", 42, res.value);

        meth = klass.getMethodByName("run2C:()I");
        res = im.execMethod(meth);
        assertEquals("Try to exec the invokevirtual", 1337, res.value);

    }

    @Test
    @Ignore
    public void simple_invokevirtual() throws Exception {
        String fName = "octest/SonOfMyInteger.class";
        buf = Utils.pullBytes(fName);
        OtKlass klass = OtKlassParser.of(null, buf, fName);
        repo.add(klass);
        im = new InterpMain(repo);

        OtMethod meth = klass.getMethodByName("getValue2:()I");
        assertEquals("Flags should be public", ACC_PUBLIC, meth.getFlags());
        JVMValue res = im.execMethod(meth);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 9", 9, (int) res.value);
    }

}
