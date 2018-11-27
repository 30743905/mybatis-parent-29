package org.simon.reflect.genericsuperclass;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author simon
 * @Copyright © 2018 tiger Inc. All rights reserved.
 * @create 2018-11-22 23:30
 * @Description:TODO
 */
public class BaseHibernateDao<T,ID extends Serializable> {

    private Class<T> clazz;

    public BaseHibernateDao(){
        //当前对象的直接超类的 Type
        Type genericSuperclass = getClass().getGenericSuperclass();
        if(genericSuperclass instanceof ParameterizedType){
            //参数化类型
            ParameterizedType parameterizedType= (ParameterizedType) genericSuperclass;

            System.out.println("rawType:"+parameterizedType.getRawType());
            System.out.println("ownerType:"+parameterizedType.getOwnerType());
            System.out.println("typeName:"+parameterizedType.getTypeName());

            //返回表示此类型实际类型参数的 Type 对象的数组
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            this.clazz= (Class<T>)actualTypeArguments[0];
        }else{
            this.clazz= (Class<T>)genericSuperclass;
        }
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
