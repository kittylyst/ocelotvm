/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adoptopenjdk.ocelotvm;

/**
 *
 * @author boxcat
 */
public final class JVMValue {
    private final JVMPrimVsRefType type;
    private final Object value;
    
    public JVMValue(JVMPrimVsRefType type_, Object value_) {
        type = type_;
        value = value_;
    }

    /**
     * @return the type
     */
    public JVMPrimVsRefType getType() {
        return type;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "JVMValue{" + "type=" + type + ", value=" + value + '}';
    }
    
    
}
