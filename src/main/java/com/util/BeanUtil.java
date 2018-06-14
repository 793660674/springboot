package com.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BeanUtil {
	/** 
     * 利用反射实现对象之间属性复制 
     *  
     * @param from 
     * @param to 
     */  
    public static void copyProperties(Object from, Object to) throws Exception {  
        copyPropertiesExclude(from, to, null);  
    }  
    
    
    /** 
     * 复制对象属性 
     *  
     * @param from 
     * @param to 
     * @param excludsArray 
     *            排除属性列表 
     * @throws Exception 
     */  
    public static void copyPropertiesExclude(Object from, Object to,  
            String[] excludsArray) throws Exception {  
        List<String> excludesList = null;  
        if (excludsArray != null && excludsArray.length > 0) {  
            excludesList = Arrays.asList(excludsArray); // 构造列表对象  
        }  
        Method[] fromMethods = from.getClass().getDeclaredMethods();  
        Method[] toMethods = to.getClass().getDeclaredMethods();  
        Method fromMethod = null, toMethod = null;  
        String fromMethodName = null, toMethodName = null;  
        for (int i = 0; i < fromMethods.length; i++) {  
            fromMethod = fromMethods[i];  
            fromMethodName = fromMethod.getName();  
            if (!fromMethodName.contains("get"))  
                continue;  
            // 排除列表检测  
            if (excludesList != null  
                    && excludesList.contains(fromMethodName.substring(3)  
                            .toLowerCase())) {  
                continue;  
            }  
            toMethodName = "set" + fromMethodName.substring(3);  
            toMethod = findMethodByName(toMethods, toMethodName);  
            if (toMethod == null)  
                continue;  
            Object value = fromMethod.invoke(from, new Object[0]);  
            if (value == null)  
                continue;  
            // 集合类判空处理  
            if (value instanceof Collection) {  
                Collection<?> newValue = (Collection<?>) value;  
                if (newValue.size() <= 0)  
                    continue;  
            }  
            toMethod.invoke(to, new Object[] { value });  
        }  
    }  
  
    /** 
     * 对象属性值复制，仅复制指定名称的属性值 
     *  
     * @param from 
     * @param to 
     * @param includsArray 
     * @throws Exception 
     */  
    public static void copyPropertiesInclude(Object from, Object to,  
            String[] includsArray) throws Exception {  
        List<String> includesList = null;  
        if (includsArray != null && includsArray.length > 0) {  
            includesList = Arrays.asList(includsArray); // 构造列表对象  
        } else {  
            return;  
        }  
        Method[] fromMethods = from.getClass().getDeclaredMethods();  
        Method[] toMethods = to.getClass().getDeclaredMethods();  
        Method fromMethod = null, toMethod = null;  
        String fromMethodName = null, toMethodName = null;  
        for (int i = 0; i < fromMethods.length; i++) {  
            fromMethod = fromMethods[i];  
            fromMethodName = fromMethod.getName();  
            if (!fromMethodName.contains("get"))  
                continue;  
            // 排除列表检测  
            String str = fromMethodName.substring(3);  
            if (!includesList.contains(str.substring(0, 1).toLowerCase()  
                    + str.substring(1))) {  
                continue;  
            }  
            toMethodName = "set" + fromMethodName.substring(3);  
            toMethod = findMethodByName(toMethods, toMethodName);  
            if (toMethod == null)  
                continue;  
            Object value = fromMethod.invoke(from, new Object[0]);  
            if (value == null)  
                continue;  
            // 集合类判空处理  
            if (value instanceof Collection) {  
                Collection<?> newValue = (Collection<?>) value;  
                if (newValue.size() <= 0)  
                    continue;  
            }  
            toMethod.invoke(to, new Object[] { value });  
        }  
    }  
  
    /** 
     * 从方法数组中获取指定名称的方法 
     *  
     * @param methods 
     * @param name 
     * @return 
     */  
    public static Method findMethodByName(Method[] methods, String name) {  
        for (int j = 0; j < methods.length; j++) {  
            if (methods[j].getName().equals(name))  
                return methods[j];  
        }  
        return null;  
    }  
  
    /** 
     * map 转 Bean 
     *  
     * @param map 
     * @param cls 
     * @return 
     */  
    public static Object map2Bean(Map<?,?> map, Class<?> cls) {  
        Object obj = null;  
        try {  
            obj = cls.newInstance();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        // 取出bean里的所有方法  
        Method[] methods = cls.getMethods();  
        for (int i = 0; i < methods.length; i++) {  
            // 取方法名  
            String method = methods[i].getName();  
            // 取出方法的类型  
            Class<?>[] cc = methods[i].getParameterTypes();  
            if (cc.length != 1)  
                continue;  
  
            // 如果方法名没有以set开头的则退出本次for  
            if (method.indexOf("set") < 0)  
                continue;  
            // 类型  
            String type = cc[0].getSimpleName();  
  
            try {  
                // 转成小写  
                // Object value = method.substring(3).toLowerCase();  
                Object value = method.substring(3, 4).toLowerCase()  
                        + method.substring(4);  
                // 如果map里有该key  
                if (map.containsKey(value) && map.get(value) != null) {  
                    // 调用其底层方法  
                    setValue(type, map.get(value), i, methods, obj);  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return obj;  
    }  
    
    /** 
     * 调用底层方法设置值 
     */  
    private static void setValue(String type, Object value, int i,  
            Method[] method, Object bean) {  
        if (value != null && !value.equals("")) {  
            try {  
                if (type.equals("String")) {  
                    // 第一个参数:从中调用基础方法的对象 第二个参数:用于方法调用的参数  
                    method[i].invoke(bean, new Object[] { value });  
                } else if (type.equals("int") || type.equals("Integer")) {  
                    method[i].invoke(bean, new Object[] { new Integer(""  
                            + value) });  
                } else if (type.equals("double") || type.equals("Double")) {  
                    method[i].invoke(bean,  
                            new Object[] { new Double("" + value) });  
                } else if (type.equals("float") || type.equals("Float")) {  
                    method[i].invoke(bean,  
                            new Object[] { new Float("" + value) });  
                } else if (type.equals("long") || type.equals("Long")) {  
                    method[i].invoke(bean,  
                            new Object[] { new Long("" + value) });  
                } else if (type.equals("boolean") || type.equals("Boolean")) {  
                    method[i].invoke(bean,  
                            new Object[] { Boolean.valueOf("" + value) });  
                } else if (type.equals("BigDecimal")) {  
                    method[i].invoke(bean, new Object[] { new BigDecimal(""  
                            + value) });  
                } else if (type.equals("Date")) {  
                    Date date = null;  
                    if (value.getClass().getName().equals("java.util.Date")) {  
                        date = (Date) value;  
                    } else {  
                        String format = ((String) value).indexOf(":") > 0 ? "yyyy-MM-dd hh:mm:ss"  
                                : "yyyy-MM-dd";  
                        SimpleDateFormat sf = new SimpleDateFormat();  
                        sf.applyPattern(format);  
                        date = sf.parse((String) (value));  
                    }  
                    if (date != null) {  
                        method[i].invoke(bean, new Object[] { date });  
                    }  
                } else if (type.equals("byte[]")) {  
                    method[i].invoke(bean,  
                            new Object[] { new String(value + "").getBytes() });  
                }  
            } catch (Exception e) {  
                System.out  
                        .println("将linkHashMap 或 HashTable 里的值填充到javabean时出错,请检查!");  
                e.printStackTrace();  
            }  
        }  
    }  
}
