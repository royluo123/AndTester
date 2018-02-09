package com.roy.tester.web.hack;

import android.os.Build;
import android.os.Looper;
import android.os.MessageQueue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/1/12.
 */

public class HackManger {
    public static long fakeThreadId(long fakeId) {
        Thread t = Thread.currentThread();
        long id = t.getId();
        if(fakeId != id) {
            if(Build.VERSION.SDK_INT <= 23) {
                setFieldValue(t, "id", Long.valueOf(fakeId));
            } else {
                setFieldValue(t, "tid", Long.valueOf(fakeId));
            }
        }

        return id;
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static boolean prepareLooperWithMainThreadQueue(boolean reset) {
        if(isMainThread()) {
            return true;
        } else {
            ThreadLocal threadLocal = (ThreadLocal)getStaticFieldValue(Looper.class, "sThreadLocal");
            if(threadLocal == null) {
                return false;
            } else {
                Looper looper = null;
                if(!reset) {
                    Looper.prepare();
                    looper = Looper.myLooper();
                    Object queue = invokeMethod(Looper.getMainLooper(), "getQueue", new Class[0], new Object[0]);
                    if(!(queue instanceof MessageQueue)) {
                        return false;
                    }

                    setFieldValue(looper, "mQueue", queue);
                }

                invokeMethod(threadLocal, "set", new Class[]{Object.class}, new Object[]{looper});
                return true;
            }
        }
    }


    public static void setFieldValue(Object obj, String field, Object value) {
        try {
            Field e;
            try {
                e = obj.getClass().getDeclaredField(field);
            } catch (Exception var5) {
                e = obj.getClass().getField(field);
            }

            e.setAccessible(true);
            e.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    public static Object getStaticFieldValue(Class cls, String fieldName) {
        Object fieldValue = null;

        try {
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            fieldValue = field.get((Object)null);
        } catch (IllegalAccessException e) {
           e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return fieldValue;
    }

    public static Object invokeMethod(Object obj, String method) {
        try {
            Method e;
            try {
                e = obj.getClass().getDeclaredMethod(method, new Class[0]);
            } catch (Exception var4) {
                e = obj.getClass().getMethod(method, new Class[0]);
            }

            e.setAccessible(true);
            return e.invoke(obj, new Object[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object invokeMethod(Object o, String methodName, Class[] argsClass, Object[] args) {
        Object returnValue = null;

        try {
            Class e = o.getClass();
            Method method = e.getMethod(methodName, argsClass);
            method.setAccessible(true);
            returnValue = method.invoke(o, args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return returnValue;
    }
}
