/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adoptopenjdk.ocelotvm.runtime;

/**
 * This class should be public to allow the new opcode to create it
 * 
 * @author boxcat
 */
public class JVMObj {
    private final JVMObjMetadata mark = new JVMObjMetadata();
    private final JVMTypeMetadata meta;
    private final long id; 
    
    public JVMObj(String toCreate, long id_) {
        meta = ClassRepository.newTypeMetadata(toCreate);
        id = id_;
    }

    @Override
    public String toString() {
        return "JVMObj{" + "mark=" + mark + ", meta=" + meta + ", id=" + id + '}';
    }

    JVMTypeMetadata getTypeMetadata() {
        return meta;
    }
    
}
