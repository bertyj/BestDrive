package com.conti.share.utils.http.httpParam;


import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class HttpParamsResolver {

    public static boolean isBasicType(Class<?> clz) {
        if (clz.isPrimitive() || String.class.isAssignableFrom(clz)) {
            return true;
        }
        return isWrapType(clz);
    }

    public static boolean isWrapType(Class<?> clz) {
        try {
            if (((Class<?>) clz.getField("TYPE").get(null)).isPrimitive()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static String toString(Object src) throws Exception {
        return toString(src, src.getClass());
    }

    public static String toString(Object src, Class<?> classOfSrc)
            throws Exception {
        if (isBasicType(classOfSrc)) {
            throw new IllegalArgumentException(
                    "src could not supply basic type..");
        } else {// 对象
            return object2String(src);
        }
    }

    private static String object2String(Object entity) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        List<Field> fields = getAllFields(entity.getClass());
        for (Field field : fields) {
            if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
            }
            HttpParam jsonNode = field.getAnnotation(HttpParam.class);
            if (jsonNode != null) {
                Object value = field.get(entity);
                if (value != null) {
                    String key = jsonNode.key();
                    //判断是否需要url编码
                    boolean needEncode = jsonNode.encode();
                    Class<?> fieldClazz = field.getType();
                    if (isBasicType(fieldClazz)) {// 基础类型
                        if (!params.containsKey(key)) {
                            params.put(key, needEncode ? Uri.encode(String.valueOf(value)) : String.valueOf(value));
                        } else {
                            Log.w("HTTP_PARAMS::",
                                    "class["
                                            + field.getDeclaringClass()
                                            .getSimpleName()
                                            + "] declares multiple HTTP_PARAM fields named name["
                                            + key + "]..");
                        }
                    } else {
                        Log.w("HTTP_PARAMS::", "class["
                                + field.getDeclaringClass().getSimpleName()
                                + "] is not a basic type named name[" + key
                                + "]..");
                    }
                }
            }
        }
        String result = "";
        for (Entry<String, String> param : params.entrySet()) {
            if (!TextUtils.isEmpty(result)) {
                result += "&";
            }

            result += (param.getKey() + "=" + param.getValue());
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromString(String paramsStr, Type typeOfT)
            throws Exception {
        if (typeOfT instanceof Class) {// 对象
            return (T) fromString(paramsStr, TypeToken.getRawType(typeOfT));
        } else {
            String className = typeOfT == null ? "null" : typeOfT.getClass()
                    .getName();
            throw new IllegalArgumentException(
                    "Expected a Class, ParameterizedType, or "
                            + "GenericArrayType, but <" + typeOfT
                            + "> is of type " + className);
        }
    }

    private static <T> T fromString(String paramsStr, Class<T> classOfT)
            throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        String[] paramsSplit;
        if (paramsStr.contains("&")) {
            paramsSplit = paramsStr.split("&");
        } else {
            paramsSplit = new String[1];
            paramsSplit[0] = paramsStr;
        }
        for (String param : paramsSplit) {
            try {
                if (!TextUtils.isEmpty(param)) {
                    String[] entity = param.split("=");
                    params.put(entity[0], entity[1]);
                }
            } catch (Exception ex) {
                Log.e("HTTP_PARAMS", "Can't split String: " + param, ex);
            }
        }

        Constructor<T> cons = classOfT.getDeclaredConstructor();
        cons.setAccessible(true);
        T entity = cons.newInstance();
        List<Field> fields = getAllFields(classOfT);
        for (Field field : fields) {
            if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
            }
            try {
                fillField(entity, field, params);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        return entity;
    }

    private static void fillField(Object entity, Field field,
                                  HashMap<String, String> params) throws Exception {
        HttpParam jsonNode = field.getAnnotation(HttpParam.class);
        if (jsonNode != null) {
            String key = jsonNode.key();
            if (params.containsKey(key)) {
                Class<?> fieldClazz = field.getType();
                if (isBasicType(fieldClazz)) {// 基础类型
                    Object value = params.get(key);
                    params.remove(key);
                    try {
                        if (isWrapType(fieldClazz)
                                || String.class.isAssignableFrom(fieldClazz)) {
                            Constructor<?> cons = fieldClazz
                                    .getConstructor(String.class);// 包装类，均有带String的构造
                            Object attrValue = cons.newInstance(value
                                    .toString());
                            field.set(entity, attrValue);
                        } else {
                            field.set(entity, value);
                        }
                    } catch (Exception e) {
                        String error = "HTTP_PARAMS invalid value[" + value
                                + "] for field[" + field.getName()
                                + "]; valueClass[" + value.getClass()
                                + "]; annotationName[" + key + "]";
                        throw new Exception(error);
                    }
                } else {
                    String objValue = params.get(key);
                    Log.w("HTTP_PARAMS::", "class["
                            + field.getDeclaringClass().getSimpleName()
                            + "] is not a basic type named name[" + key
                            + "] valued[" + objValue + "]..");
                }
            }
        }
    }

    private static List<Field> getAllFields(Class<?> objClass) {
        List<Field> fields = new ArrayList<Field>();
        Field[] declaredFields = objClass.getDeclaredFields();
        Collections.addAll(fields, declaredFields);
        fillSuperFields(fields, objClass);
        return fields;
    }

    private static void fillSuperFields(List<Field> fields, Class<?> subClass) {
        Class<?> superClass = subClass.getSuperclass();
        if (superClass != null) {
            Field[] superFields = superClass.getDeclaredFields();
            if (superFields.length > 0) {
                Collections.addAll(fields, superFields);
                fillSuperFields(fields, superClass);
            }
        }
    }

    public static abstract class TypeToken<T> {
        final Class<? super T> rawType;
        final Type type;

        @SuppressWarnings("unchecked")
        protected TypeToken() {
            this.type = getSuperclassTypeParameter(getClass());
            this.rawType = (Class<? super T>) getRawType(type);
        }

        public Type getType() {
            return type;
        }

        public Class<?> getRawType() {
            return rawType;
        }

        public static Type getInterfacesTypeParameter(Class<?> subclass,
                                                      Class<?> interfClass) {
            Type resultType = null;
            Type[] types = subclass.getGenericInterfaces();
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    if (interfClass.isAssignableFrom(getRawType(type))) {
                        ParameterizedType parameterized = (ParameterizedType) type;
                        return parameterized.getActualTypeArguments()[0];
                    }
                }
            }
            return resultType;
        }

        public static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return parameterized.getActualTypeArguments()[0];
        }

        public static Class<?> getRawType(Type type) {
            if (type instanceof Class<?>) {
                return (Class<?>) type;
            } else if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type rawType = parameterizedType.getRawType();
                if (!(rawType instanceof Class)) {
                    throw new IllegalArgumentException();
                }
                return (Class<?>) rawType;
            } else if (type instanceof GenericArrayType) {
                Type componentType = ((GenericArrayType) type)
                        .getGenericComponentType();
                return Array.newInstance(getRawType(componentType), 0)
                        .getClass();
            } else if (type instanceof TypeVariable) {
                return Object.class;
            } else if (type instanceof WildcardType) {
                return getRawType(((WildcardType) type).getUpperBounds()[0]);
            } else {
                String className = type == null ? "null" : type.getClass()
                        .getName();
                throw new IllegalArgumentException(
                        "Expected a Class, ParameterizedType, or "
                                + "GenericArrayType, but <" + type
                                + "> is of type " + className);
            }
        }
    }

}
