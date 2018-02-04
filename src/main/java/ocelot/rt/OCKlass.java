package ocelot.rt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import ocelot.InterpMain;
import ocelot.JVMValue;
import static ocelot.classfile.OCKlassParser.ACC_STATIC;

/**
 *
 * @author ben
 */
public class OCKlass {

    private final InterpMain interpreter;

    private final String name;
    private final String superClass;

    private final Map<Short, String> klassNamesByIndex = new HashMap<>();

    private final Map<String, OCMethod> methodsByName = new HashMap<>();
    private final Map<Short, String> methodNamesByIndex = new HashMap<>();

    private final Map<String, OCField> fieldsByName = new HashMap<>();
    private final Map<Short, String> fieldNamesByIndex = new HashMap<>();

    // The actual values of the static fields
    private final Map<String, Long> staticFieldsByName = new HashMap<>();

    public OCKlass(InterpMain i, String className) {
        interpreter = i;
        name = className;
        superClass = null;
    }

    public OCKlass(String className) {
        interpreter = null;
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

    public Collection<OCField> getFields() {
        return fieldsByName.values();
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

    public void callClInit() {
        final OCMethod meth = methodsByName.get("<clinit>:()V");
        
        if (meth != null) {
            interpreter.execMethod(meth);
        }
    }

    public void setStaticField(String name, JVMValue val) {
        staticFieldsByName.put(name, val.value);
    }

    public JVMValue getStaticField(OCField f) {
        long val = staticFieldsByName.get(f.getName());

        return new JVMValue(f.getType(), val);
    }

    public void addField(OCField field) {
        fieldsByName.put(field.getName(), field);
        if ((field.getFlags() & ACC_STATIC) > 0) {
            addCPStaticField(field.getName());
        }
    }

    void addCPStaticField(String name) {
        staticFieldsByName.put(name, 0L);
    }

    public void addCPFieldRef(short index, String name) {
        fieldNamesByIndex.put(index, name);
    }

    public String getFieldByCPIndex(short cpIndex) {
        return fieldNamesByIndex.get(cpIndex);
    }

    public OCField getFieldByName(String fieldName) {
        return fieldsByName.get(fieldName);
    }

    public void addDefinedField(OCField ocf) {
        fieldsByName.put(ocf.getName(), ocf);
    }

}
