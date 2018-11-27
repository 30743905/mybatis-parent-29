package org.simon.reflect.parameterizedType;

import com.alibaba.fastjson.JSON;

import org.junit.Test;


import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Type;

/**
 * @author simon
 * @Copyright © 2018 tiger Inc. All rights reserved.
 * @create 2018-11-22 23:09
 * @Description:TODO
 */
public class TestMain {

    @Test
    public void fun1(){
        ResponseDTO<Message> dto = new ResponseDTO<Message>();
        dto.setCode("code");
        dto.setMessage("message");

        Message message = new Message();
        message.setCode(111);
        message.setResult("result message");

        dto.setData(message);

        String msg1 = JSON.toJSONString(dto, true);
        System.out.println("json字符串:"+msg1);

        ResponseDTO obj1 = JSON.parseObject(msg1, ResponseDTO.class);
        System.out.println("反序列化类型:"+obj1);
        System.out.println("反序列化result的类型:"+obj1.getData().getClass());


        System.out.println("===================");

        Type responseTypeClass = ParameterizedTypeImpl.make(ResponseDTO.class, new Type[]{Message.class}, ResponseDTO.class);
        ResponseDTO obj2 = JSON.parseObject(msg1, responseTypeClass);
        System.out.println("带泛型反序列化类型:"+obj2);
        System.out.println("带泛型反序列化result的类型:"+obj2.getData().getClass());
    }

}
