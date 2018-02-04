package ocelot.rt;

import ocelot.*;
import static ocelot.JVMValue.entry;
import ocelot.classfile.OCKlassParser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author ben
 */
public class TestInterp {

    private InterpMain im;
    private ClassRepository repo;

    @Before
    public void setup() {
        repo = ClassRepository.of();
    }

    private byte[] buf;

    @Test
    @Ignore
    public void hello_world_loaded_from_file_executes() throws Exception {
        String fName = "Println.class";
        buf = Utils.pullBytes(fName);
        OCKlass klass = OCKlassParser.of(null, buf, fName);

        repo.add(klass);
        im = new InterpMain(repo);

        OCMethod meth = klass.getMethodByName("main:([Ljava/lang/String;)V");

        assertNull("Hello World should execute", im.execMethod(meth));
    }

    @Test
    public void simple_branching_executes() throws Exception {
        String fName = "optjava/bc/SimpleTests.class";
        buf = Utils.pullBytes(fName);
        OCKlass klass = OCKlassParser.of(null, buf, fName);

        repo.add(klass);
        im = new InterpMain(repo);

        OCMethod meth = klass.getMethodByName("if_bc:()I");
        JVMValue res = im.execMethod(meth);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);
    }

    @Test
    public void simple_static_calls_executes() throws Exception {
        String fName = "octest/StaticCalls.class";
        buf = Utils.pullBytes(fName);
        OCKlass klass = OCKlassParser.of(null, buf, fName);

        repo.add(klass);
        im = new InterpMain(repo);

        OCMethod meth = klass.getMethodByName("call1:()I"); // "main:([Ljava/lang/String;)V");
        JVMValue res = im.execMethod(meth);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 23", 23, (int) res.value);

        meth = klass.getMethodByName("call4:()I");
        res = im.execMethod(meth);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 47", 47, (int) res.value);

        meth = klass.getMethodByName("adder:(II)I");
        LocalVars lv = new LocalVars();
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
        OCKlass klass = OCKlassParser.of(null, buf, fName);

        repo.add(klass);
        im = new InterpMain(repo);

        OCMethod meth = klass.getMethodByName("setJ:(I)V");
        LocalVars lvt = new LocalVars();
        JVMValue[] vs = new JVMValue[1];
        vs[0] = new JVMValue(JVMType.I, 13L);
        lvt.setup(vs);
        JVMValue res = im.execMethod(meth, lvt);

        assertNull("Call to setter should be return null", res);

        meth = klass.getMethodByName("getJ:()I");
        res = im.execMethod(meth);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 13", 13, (int) res.value);

        meth = klass.getMethodByName("incJ:()V");
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
        OCKlass klass = OCKlassParser.of(null, buf, fName);

        repo.add(klass);
        im = new InterpMain(repo);

        OCMethod meth = klass.getMethodByName("simple:()I");
        JVMValue res = im.execMethod(meth);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 23", 23, (int) res.value);
    }

    @Test
    public void simple_invoke() throws Exception {
        String fName = "SampleInvoke.class";
        buf = Utils.pullBytes(fName);
        OCKlass klass = OCKlassParser.of(null, buf, fName);

        repo.add(klass);
        im = new InterpMain(repo);

        OCMethod meth = klass.getMethodByName("foo:()I");
        JVMValue res = im.execMethod(meth);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 9", 9, (int) res.value);
    }

}
