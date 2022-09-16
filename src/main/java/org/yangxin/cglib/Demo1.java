package org.yangxin.cglib;

import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Method;

public class Demo1 {

    public String test(String s){
        return "hello world";
    }

    public String say(){
        return "i am dage";
    }

    public static String hello(){
        return "hello()";
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Demo1.class);
//        enhancer.setCallback(NoOp.INSTANCE);
//        Demo1 demo1 = new Demo1();
//        System.out.println(demo1.test("杨大哥"));
        CallbackHelper callbackHelper = new CallbackHelper(Demo1.class, new Class[0]){
            @Override
            protected Object getCallback(Method method) {
                if (method.getDeclaringClass() == Object.class) {
                    return NoOp.INSTANCE;
                } else {
                    return new FixedValue() {
                        @Override
                        public Object loadObject() throws Exception {
                            return "hello cglib";
                        }
                    };
                }
//                return null;
            }
        };
        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());
//
        Demo1 demo11 = new Demo1();
        System.out.println(demo11.test(""));
        System.out.println(demo11.say());
        System.out.println(Demo1.hello());
        System.out.println(demo11.hashCode());
        System.out.println(demo11.getClass());
        Demo1 demo1 = (Demo1)enhancer.create();
        System.out.println(demo1.test(""));
        System.out.println(demo1.say());
        System.out.println(Demo1.hello());
//        System.out.println(demo1.hashCode());
        System.out.println(demo1.getClass());
        System.out.println(demo1.toString());
    }
}
