/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adoptopenjdk.ocelotvm.runtime;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author boxcat
 */
public class JVMHeap {

    private static final AtomicLong objCounter = new AtomicLong(0);
    
    public static Object newObj(String klzStr) {
        return new JVMObj(klzStr, objCounter.getAndIncrement());
    }
    
}
