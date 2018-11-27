package org.simon.reflect.genericsuperclass;

/**
 * @author simon
 * @Copyright © 2018 tiger Inc. All rights reserved.
 * @create 2018-11-22 23:30
 * @Description:TODO
 */
public class TestUserService {
    public static void main(String[] args) {
        UserService userService = new UserService();
        System.out.println(userService.getClazz());


        System.out.println(User.class.getSuperclass());
        System.out.println(UserService.class.getSuperclass());

        System.out.println(User.class.getGenericSuperclass());
        System.out.println(UserService.class.getGenericSuperclass());
    }
}
