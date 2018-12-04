package org.simon.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author simon
 * @Copyright © 2018 tiger Inc. All rights reserved.
 * @create 2018-12-04 23:18
 * @Description:TODO
 */

public class Test {

    public void createUser(String name, int age, int version) {
    }

    public static void main(String[] args) throws Exception {
        for (Method m : Test.class.getMethods()) {
            System.out.println("--------------------");
            System.out.println(" method: " + m.getName());
            System.out.println(" return: " + m.getReturnType().getName());
            for (Parameter p : m.getParameters()) {
                System.out.println("parameter:" + p.getType().getName() + ", " + p.getName());
            }
        }
    }
}