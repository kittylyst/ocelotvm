package ocelot.rt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ben
 */
public class OCKlass {

    private final String name;
    private final String superClass;
    private final Map<String, OCMethod> methodsByName = new HashMap<>();
    private final Map<Short, String> klassNamesByIndex = new HashMap<>();
    private final Map<Short, String> methodNamesByIndex = new HashMap<>();

    public OCKlass(String className) {
        name = className;
        superClass = null;
    }

    public void addDefinedMethod(OCMethod m) {
        methodsByName.put(m.getNameAndType(), m);
    }

    public void addCPMethodRef(short index, String methodName) {
        methodNamesByIndex.put(index, methodName);
    }

    public void addCPKlassRef(short index, String methodName) {
        klassNamesByIndex.put(index, methodName);
    }

    public OCMethod getMethodByName(String nameAndType) {
        return methodsByName.get(nameAndType);
    }

    public Collection<OCMethod> getMethods() {
        return methodsByName.values();
    }

    public String getName() {
        return name;
    }

    public String getMethodNameByCPIndex(short cpIndex) {
        return methodNamesByIndex.get(cpIndex);
    }

    public String getKlassNameByCPIndex(short cpIndex) {
        return klassNamesByIndex.get(cpIndex);
    }

}
