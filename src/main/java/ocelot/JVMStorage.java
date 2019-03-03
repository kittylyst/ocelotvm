package ocelot;

import ocelot.rt.OtObj;
import ocelot.rt.OtKlass;

/**
 *
 * @author ben
 */
public interface JVMStorage {

    public long allocateObj(OtKlass klass);

    public OtObj findObject(long id);

    public void runGC();

}
