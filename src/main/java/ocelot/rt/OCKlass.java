package ocelot.rt;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ben
 */
public class OCKlass {

    private final String name;
    private final Map<String, OCMethod> methodsByName = new HashMap<>();
    private final Map<Integer, OCMethod> methodsByIndex = new HashMap<>();

    public OCKlass(String className) {
        name = className;
    }

    public void addDefinedMethod(OCMethod m) {
        methodsByName.put(m.getNameAndType(), m);
//        methodsByIndex.put(index, m);
    }

    public OCMethod getMethodByName(String nameAndType) {
        return methodsByName.get(nameAndType);
    }

    public long allocateObj() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Collection<OCMethod> getMethods() {
        return methodsByName.values();
    }

    public String getName() {
        return name;
    }

    public OCMethod getMethodByCPIndex(short cpIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public OCKlass getKlassByCPIndex(short cpIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
