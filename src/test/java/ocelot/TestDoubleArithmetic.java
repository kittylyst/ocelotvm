package ocelot;

import static ocelot.Opcode.*;
import ocelot.rt.ClassRepository;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ben
 */
public class TestDoubleArithmetic {

    private InterpMain im;

    private static final double EPSILON = 0.000001;

    @Before
    public void setup() {
        im = new InterpMain(ClassRepository.of());
    }

    @Test
    public void double_add_works() {
        byte[] buf = {DCONST_1.B(), DCONST_1.B(), DADD.B(), DRETURN.B()};
        JVMValue res = im.execMethod("", "main:()V", buf, new LocalVars());
        assertEquals("Return type is double", JVMType.D, res.type);
        assertEquals("Return value should be 2.0", 2.0, Double.longBitsToDouble(res.value), EPSILON);
    }

    @Test
    public void i2d_double_add_works() {
        byte[] buf = {DCONST_1.B(), ICONST_1.B(), I2D.B(), DADD.B(), DRETURN.B()};
        JVMValue res = im.execMethod("", "main:()V", buf, new LocalVars());
        assertEquals("Return type is double", JVMType.D, res.type);
        assertEquals("Return value should be 2.0", 2.0, Double.longBitsToDouble(res.value), EPSILON);
    }

}
