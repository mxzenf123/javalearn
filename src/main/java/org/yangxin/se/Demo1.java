package org.yangxin.se;

import com.sun.beans.editors.BooleanEditor;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import sun.plugin.cache.JarCacheUtil;

import javax.annotation.PostConstruct;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarFile;

public class Demo1 {

    static boolean runned = false;
    static Object lock = new Object();

    public void sayHello(String s){
        System.out.println("hello " + s);
    }
    public static void main(String[] args) throws Exception {
        Demo1 demo1 = new Demo1();
        demo1.sayHello("杨欣");
//        new Thread(()->{
//            while (!runned) {
//                //输出runned的值后可以读取到runned修改后的值
////                System.out.println(runned);
//                //锁对象也可以导致runned变为可见
//                synchronized (lock) {
//
//                }
//            }
//        },"thread1").start();
//        Thread.sleep(1000);
//        System.out.println("终止thread1运行");
//        runned = true;

//        System.out.println(ClassUtils.convertClassNameToResourcePath("org.yangxin.se.Demo1"));
//        final Object lock = new Object();
//        Thread thread2 = new Thread(()->{
//            try {
//                System.out.println("开始执行："+Thread.currentThread().getName());
//                synchronized (lock) {
//                    Thread.sleep(60000);
//                    System.out.println("开始wait...");
//                    lock.wait();
//                    System.out.println("执行完毕");
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },"thread2");
//        thread2.start();
//
//        Thread thread = new Thread(()->{
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("开始执行："+Thread.currentThread().getName());
//            synchronized (lock){
//
//
//                try {
//                    Thread.sleep(60000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        },"thread1");
//        thread.start();
    }

    @Test
    public void test_jarfile() throws Exception{
        JarFile jarFile = new JarFile("D:\\ja-netfilter\\ja-netfilter.jar");

        jarFile.stream().forEach(jarEntry -> {
            System.out.println(jarEntry.getName());
        });
        jarFile.close();
    }

    @Test
    public void test_PathMatchingResourcePatternResolver(){
        System.out.println(File.separator);
        PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();
        try {
            //net.sf.cglib.proxy
            Resource[] resources = pmrpr.getResources("classpath*:net/sf/cglib/proxy/**/*.class");
            Arrays.asList(resources).stream().forEach( resource -> {
                try {
                    System.out.println(resource.getFile().getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_annotation_context(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.yangxin");
        Demo4 bean4 = context.getBean("demo4", Demo4.class);
        Assert.notNull(bean4, "不应该为空");
    }

    @Test
    public void test_enviroment(){
        ConfigurableEnvironment environment = new StandardEnvironment();
        String javaHome = environment.getProperty("JAVA_HOME");
        System.out.println(javaHome);
    }

    @Test
    public void test_system_properties(){
        System.out.println(System.getProperties());
        System.out.println(System.getProperty("com.yangxin.name"));
    }

    @Test
    public void test_read_class_file() throws Exception{
        GenericApplicationContext context = new GenericApplicationContext();
        MetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
        String className = "org.yangxin.se.*";
        String filepath = ClassUtils.convertClassNameToResourcePath(className)+".class";
        ClassLoader cl = getClass().getClassLoader();
        Resource[] resources = context.getResources(filepath);
        Arrays.asList(resources).forEach(resource -> {
            try {
                MetadataReader metadataReader = cachingMetadataReaderFactory.getMetadataReader(resource);
                AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
                annotationMetadata.getAnnotationTypes().stream().forEach(s->{
                    if (annotationMetadata.isAnnotated(Component.class.getName())) {
                        String clazz = metadataReader.getClassMetadata().getClassName();
                        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(clazz).getBeanDefinition();
                        context.getDefaultListableBeanFactory().registerBeanDefinition(clazz, beanDefinition);
                    }

                    System.out.println(resource.getFilename() + "是否含有"+ Component.class.getName() +"注解" + annotationMetadata.isAnnotated(Component.class.getName()));
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//        className = "org.yangxin.se.Demo1";
//        filepath = ClassUtils.convertClassNameToResourcePath(className)+".class";
//        try (InputStream is = cl.getResourceAsStream(filepath)) {
//
//            System.out.println( is == null);
//        }

        System.out.println("------------输出所有beanDefinition--------------");
        Arrays.asList(context.getBeanDefinitionNames()).stream().forEach(System.out::println);
        context.refresh();
        System.out.println(null == context.getBean("org.yangxin.se.Demo5"));

//        ClassPathResource classPathResource = new ClassPathResource(filepath, cl);
//        System.out.println(null == classPathResource.getInputStream());
    }

    @Test
    public void test_disable_prototype(){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);

        Demo4 demo41 = context.getBean("demo4", Demo4.class);
        Demo4 demo42 = context.getBean("demo4", Demo4.class);

        System.out.println(demo41);
        System.out.println(demo42);
//        Demo5 bean51 = context.getBean("demo5", Demo5.class);
//        Demo5 bean52 = context.getBean("demo5", Demo5.class);
//        System.out.println(bean51.getDemo4().getClass());
//        System.out.println(bean51.getDemo4());
//        System.out.println(bean52.getDemo4());
    }

    @Test
    public void test_configurationCLassPostProcessor() throws Exception{
        GenericApplicationContext context = new GenericApplicationContext();

        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        context.registerBean("configurationClassPostProcessor", ConfigurationClassPostProcessor.class);
        context.registerBean("config", Config.class);
        context.refresh();
//        Bean4 bean41 = context.getBean("bean4", Bean4.class);
//        Bean4 bean42 = context.getBean("bean4", Bean4.class);
//        System.out.println(bean41 == bean42);
        Demo5 bean51 = context.getBean("demo5", Demo5.class);
        Demo5 bean52 = context.getBean("demo5", Demo5.class);
        System.out.println(bean51 == bean52);
        System.out.println(bean51.getDemo4());
        System.out.println(bean52.getDemo4());
//        System.out.println(bean51 == bean52);
//        System.out.println(bean51.getBean4() == bean52.getBean4());

//        Demo4 demo4 = context.getBean("demo4", Demo4.class);
        /*
          1，如何扫描到Demo4 查找classpath下的一个和多个class文件
          2，扫描到Demo4后如何解析
          3，解析后如何注册到BeanFactory
         */
//        Assert.notNull(demo4, "不应该为空");
//        Bean4 bean4 = context.getBean("bean4", Bean4.class);
//        Assert.notNull(bean4, "不应该为空");
    }

    @Test
    public void test_bean_pd() throws Exception{
        Bean2 bean2 = new Bean2();
        BeanInfo beanInfo = Introspector.getBeanInfo(Bean2.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
            if (propertyDescriptor.getName().equals("name")) {
                propertyDescriptor.getWriteMethod().invoke(bean2, "杨大哥");
            }
        }

        System.out.println(bean2.getName());
    }

    @Test
    public void test_beanwrapper(){
        Bean2 bean2 = new Bean2();
        BeanWrapper bw = new BeanWrapperImpl(bean2);
        bw.registerCustomEditor(Boolean.class, new BooleanEditor());
        bw.setPropertyValue("name", "杨大哥");
        bw.setPropertyValue("boy", "true");
        System.out.println(bean2.getBoy());


    }

    @Test
    public void test_beanfactory_dependecy() throws Exception{
        GenericApplicationContext context = new GenericApplicationContext();
        context.refresh();

        Field field = Bean2.class.getDeclaredField("beanFactory");
        DependencyDescriptor dependencyDescriptor = new DependencyDescriptor(field, true);
        dependencyDescriptor.setContainingClass(Bean2.class);

        Object o = context.getDefaultListableBeanFactory().resolveDependency(dependencyDescriptor,null);
        System.out.println(o.getClass());
    }

    @Test
    public void test_autowiredpostprocessor() throws Exception{
        GenericApplicationContext context = new GenericApplicationContext();
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());

        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        context.registerBean(Bean1.class);
        context.registerBean(Bean2.class);

        context.refresh();

        Bean1 bean1 = context.getBean(Bean1.class);
        Bean2 bean2 = context.getBean(Bean2.class);
        System.out.println(bean1.getBean2());

        context.close();
//        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
//        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);
//
//        beanFactory.registerSingleton("bean1", new Bean1());
//        beanFactory.registerSingleton("bean2", new Bean2());
//
//        Bean1 bean1 = beanFactory.getBean("bean1",Bean1.class);
//        Bean2 bean2 = beanFactory.getBean("bean2",Bean2.class);

//        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
//        processor.setBeanFactory(beanFactory);

//        Bean2 bean2 = new Bean2();
//        processor.postProcessProperties(null,bean2, "bean3");
//        Method m1 = Bean2.class.getDeclaredMethod("setHome", String.class);
//        MethodParameter methodParam = new MethodParameter(m1, 0);
//        DependencyDescriptor dd1 = new DependencyDescriptor(methodParam, true);
//        Object o = beanFactory.resolveDependency(dd1, "bean3", null, null);
//        System.out.println(o);
//        processor.postProcessProperties(null,bean1, "bean1");


    }

    @Test
    public void test_beanpostprocessor(){
        GenericApplicationContext context = new GenericApplicationContext();

        System.out.println(context.getEnvironment().getProperty("JAVA_HOME"));

        context.registerBean("bean1", Bean1.class);
        context.registerBean("bean2", Bean2.class);
        context.registerBean("bean3", Bean3.class);

        //解析autowire和value
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());

        //解析resource 和bean生命周期注解
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        context.refresh();

        Bean1 bean1 = context.getBean("bean1", Bean1.class);
        Bean2 bean2 = bean1.getBean2();
        System.out.println(bean2 == null);

        context.close();
    }
}

@Configurable
class Config2 {
    @Autowired
    public void bean5(Bean4 bean4){
        System.out.println("Config2中获得自动注入bean4"+bean4);
    }
}

@Configurable
//@Import(value = {Config2.class})
@ComponentScan("org.yangxin.se")
class Config {

//    @Autowired
//    public void bean5(Bean4 bean4){
//        System.out.println("Config中获得自动注入bean4"+bean4);
//    }

    @Bean
    public Bean4 bean4(){
        return new Bean4();
    }

//    @Autowired
//    public void setContext(ApplicationContext context){
//        System.out.println("获取到的上下文"+context);
//    }

//    @PostConstruct
//    public void init(){
//        System.out.println("配置类初始化："+this.getClass());
//    }

//    @Bean
//    public BeanFactoryPostProcessor myBeanFactoryProcessor(){
//        return beanFactory -> {
//            System.out.println("自定义BeanFactoryPostProcessor");
//        };
//    }
}
@Order
class Foo {
    public static void main(String[] args) throws Exception {


//        System.out.println("我是 foo");
    }



}

class Bean1{
    @Autowired
    Bean2 bean2;
    public Bean1(){}

    public Bean2 getBean2(){
        return bean2;
    }


}
class Bean2{

    private String home;
    private String name;
    private Boolean boy;
    @Value("${JAVA_HOME}")
    private String java_home;
    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    public void setHome(@Value("${JAVA_HOME}") String home){
        System.out.println(home);
        this.home = home;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHome() {
        return home;
    }

    public String getName() {
        return name;
    }

    public Boolean getBoy() {
        return boy;
    }

    public void setBoy(Boolean boy) {
        this.boy = boy;
    }
}

class Bean3{}

class Bean4{}

class Student{
    private int id;
    private String name;

    public Student(int id, String name){
        this.id = id;
        this.name = name;
    }

//    @Override
//    public int hashCode() {
//        return id+name.hashCode();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        Student stu = (Student)obj;
//
//        if (this.id == stu.id && this.name.equals(stu.name)) {
//            return true;
//        }
//
//        return false;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


