/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocelot.rt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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

    public byte[] lookupInCP(final String currentClass, short entry) {
        return new byte[0];
    }

}
