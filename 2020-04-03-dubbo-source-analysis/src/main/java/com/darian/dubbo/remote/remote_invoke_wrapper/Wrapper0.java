//package com.darian.dubbo.remote.remote_invoke_wrapper;
//
//public class Wrapper0 {
//
//    public Object invokeMethod(Object o, String n, Class[] p, Object[] v)
//            throws java.lang.reflect.InvocationTargetException {
//        com.darian.dubbo.DemoService w;
//        try {
//            w = ((com.darian.dubbo.DemoService) $1);
//        } catch (Throwable e) {
//            throw new IllegalArgumentException(e);
//        }
//        try {
//            if ("sayHello".equals($2) && $3.length == 1) {
//                return ($w) w.sayHello((java.lang.String) $4[0]);
//            }
//        } catch (Throwable e) {
//
//            throw new java.lang.reflect.InvocationTargetException(e);
//        }
//        throw new org.apache.dubbo.common.bytecode.NoSuchMethodException(
//                "Not found method \"" + $2 + "\" in class com.darian.dubbo.DemoService.");
//    }
//}