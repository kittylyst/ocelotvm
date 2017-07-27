/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocelot.rt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import ocelot.classfile.CPEntry;
import ocelot.classfile.OcelotClass;

/**
 * Holds all the loaded classes to date. Operations should be concurrent safe so
 * any data races are benign.
 *
 * @author ben
 */
public class ClassRepository {

    public static final String OBJSIG = "Ljava/lang/Object;";
    public static final String STRSIG = "Ljava/lang/String;";

    private static final ConcurrentMap<String, JVMTypeMetadata> loadedClasses = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, OcelotClass> loadedOcClz = new ConcurrentHashMap<>();
    private ConcurrentMap<String, OcelotClass.CPMethod> methodCache = new ConcurrentHashMap<>();
    
    public ClassRepository() {
    }

    // TEST: Put Object & String in the cache
    static {
        JVMTypeMetadata.Bldr b = new JVMTypeMetadata.Bldr();
        JVMTypeMetadata obj = b.name(OBJSIG).parent(null).storage(0).build();
        loadedClasses.put(OBJSIG, obj);
        // Now String
        loadedClasses.put(STRSIG, b.name(STRSIG).parent(obj).storage(3).build());
    }

    public static JVMTypeMetadata newTypeMetadata(String toCreate) {
        return loadedClasses.get(toCreate);
    }

    public OcelotClass.CPMethod lookupInCP(final String clzName, short entry) {
        OcelotClass clz = loadedOcClz.get(clzName);
        CPEntry cpe = clz.getCPEntry(entry);
        String methName = clz.resolveAsString(cpe.getIndex());
        return methodCache.get(methName);
        // FIXME Fully qualified name... 
//        return clz.getMethodByName(methName);
    }

    public void add(OcelotClass ce) {
        loadedOcClz.put(ce.className(), ce);
        for (OcelotClass.CPMethod m : ce.getMethods()) {
            methodCache.put(m.getClassName() +"."+ m.getNameAndType(), m);
        }
    }

}
