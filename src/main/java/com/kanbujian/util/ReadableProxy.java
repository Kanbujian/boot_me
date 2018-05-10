package com.kanbujian.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReadableProxy implements InvocationHandler {
    private Object target;

    public ReadableProxy(Object book) {
        this.target = book;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Object proxy is  " + proxy.getClass());
        System.out.println("Object target is  " + target.getClass());
        Object result = null;
        try {
            if (method.getName() == "read"){
                result = method.invoke(target, args);
            }else{
                System.out.println("method name is " + method.getName());
            }
        } catch (InvocationTargetException e) {
            throw e.getCause();
        } catch(Exception ex){
            ex.printStackTrace();
        }finally {
            System.out.println("I have read you");
        }
        return result;
    }
}
