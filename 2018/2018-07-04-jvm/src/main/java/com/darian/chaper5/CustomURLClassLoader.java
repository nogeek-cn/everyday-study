package com.darian.chaper5;

import java.io.FilePermission;
import java.lang.reflect.ReflectPermission;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.*;
import java.util.PropertyPermission;

/**
 * 自定义ClassLoader
 */
public class CustomURLClassLoader extends URLClassLoader {

    public static void main(String[] args) {

        new CustomURLClassLoader(null,null);
    }
    public CustomURLClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
        
        //特权代码 设置验证管理器
        AccessController.doPrivileged(new PrivilegedAction() {
            @Override
            public Object run() {
                //设置权限验证器
                System.setSecurityManager(CustomSecurityManager.INSTANCE);
                return null;
            }
        });
    }
 
 
    @Override
    protected PermissionCollection getPermissions(CodeSource codesource) {
        Permissions p = new Permissions();
        p.add(new AllPermission());
        return super.getPermissions(codesource);
    }
 
 
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }
    /**
     * 自定义权限管理
     */
    static class CustomSecurityManager extends SecurityManager {
 
 
        public static CustomSecurityManager INSTANCE = new CustomSecurityManager() {
        };
 
 
        private CustomSecurityManager() {
        }
 
 
        /**
         * 策略权限查看 当执行操作时调用,如果操作允许则返回,操作不允许抛出SecurityException
         */
        private void sandboxCheck(Permission perm) throws SecurityException {
            // 设置只读属性 
            //以下操作不做权限验证  Property属性读取，文件读写，运行时访问，反射权限
            if (perm instanceof SecurityPermission && perm.getName().startsWith("getProperty")) {
                return;
            } else if (perm instanceof PropertyPermission) {
                return;
            } else if (perm instanceof FilePermission) {
                return;
            } else if (perm instanceof RuntimePermission || perm instanceof ReflectPermission) {
                return;
            }
            super.checkPermission(perm);
        }
 
 
        @Override
        public void checkPermission(Permission perm) {
            this.sandboxCheck(perm);
        }
 
 
        @Override
        public void checkPermission(Permission perm, Object context) {
            this.sandboxCheck(perm);
        }
    }
 
 
}
