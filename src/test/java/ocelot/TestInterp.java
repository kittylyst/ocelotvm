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

}
