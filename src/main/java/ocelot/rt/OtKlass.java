package ocelot.rt;

import java.util.*;

import ocelot.InterpMain;
import ocelot.JVMValue;
import static ocelot.classfile.OtKlassParser.ACC_STATIC;

/**
 *
 * @author ben
 */
public final class OtKlass {
    private final String name;
    private final String superClass;

    private final Map<Short, String> klassNamesByIndex = new HashMap<>();

    private final Map<String, OtMethod> methodsByName = new HashMap<>();
    private final Map<Short, String> methodNamesByIndex = new HashMap<>();

    // The ordered fields, needed for object layout
    private final List<OtField> orderedFields = new ArrayList<>();
    private final Map<String, OtField> fieldsByName = new HashMap<>();
    private final Map<Short, String> fieldNamesByIndex = new HashMap<>();

    // The actual values of the static fields
    private final Map<String, JVMValue> staticFieldsByName = new HashMap<>();

    public OtKlass(String className, String superClassName) {
        name = className;
        superClass = superClassName;
    }

    public String getParent() {
        return superClass;
    }

    public void addDefinedMethod(OtMethod m) {
        methodsByName.put(m.getNameAndType(), m);
    }

    public void addCPMethodRef(short index, String methodName) {
        methodNamesByIndex.put(index, methodName);
    }

    public void addCPKlassRef(short index, String methodName) {
        klassNamesByIndex.put(index, methodName);
    }

    public OtMethod getMethodByName(String nameAndType) {
        return methodsByName.get(nameAndType);
    }

    public Collection<OtMethod> getMethods() {
        return methodsByName.values();
    }

    public Collection<OtField> getFields() {
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

    public void callClInit(InterpMain interpreter) {
        final OtMethod meth = methodsByName.get("<clinit>:()V");
        
        if (meth != null) {
            interpreter.execMethod(meth);
        }
    }

    public void setStaticField(String name, JVMValue val) {
        staticFieldsByName.put(name, val);
    }

    public JVMValue getStaticField(OtField f) {
        return staticFieldsByName.get(f.getName());
    }

    public void addField(OtField field) {
        fieldsByName.put(field.getName(), field);
        if ((field.getFlags() & ACC_STATIC) > 0) {
            addCPStaticField(field.getName());
        } else {
            orderedFields.add(field);
        }
    }

    void addCPStaticField(String name) {
        staticFieldsByName.put(name, null);
    }

    public void addCPFieldRef(short index, String name) {
        fieldNamesByIndex.put(index, name);
    }

    public String getFieldByCPIndex(short cpIndex) {
        return fieldNamesByIndex.get(cpIndex);
    }

    public OtField getFieldByName(String fieldName) {
        return fieldsByName.get(fieldName);
    }

    public void addDefinedField(OtField ocf) {
        fieldsByName.put(ocf.getName(), ocf);
    }

    public int numFields() {
        return orderedFields.size();
    }

    public int getFieldOffset(OtField f) {
        // FIXME
        return 0;
    }
}
