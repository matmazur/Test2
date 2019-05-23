package com.javacodegeeks.advanced.agent;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;


public class SimpleClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(
            final ClassLoader loader,
            final String className,
            final Class<?> classBeingRedefined,
            final ProtectionDomain protectionDomain,
            final byte[] classfileBuffer) {


        if (className.endsWith("SampleClass")) {
            try {
                final ClassPool classPool = ClassPool.getDefault();
                final CtClass clazz = classPool.get("SampleClass");

                classPool.importPackage("java.util.concurrent.TimeUnit");
                for (final CtConstructor constructor : clazz.getConstructors()) {
                    constructor.insertAfter("System.out.println(\"SAMPLE Constructor called\");");
                }

                CtMethod method = clazz.getDeclaredMethod("fetch");

                method.addLocalVariable("start", CtClass.longType);
                method.addLocalVariable("finish", CtClass.longType);
                method.addLocalVariable("time", CtClass.longType);

                method.insertBefore("start = System.nanoTime();");
                method.insertAfter("finish = System.nanoTime();");

                method.insertAfter("time = TimeUnit.MILLISECONDS.convert(finish-start, TimeUnit.NANOSECONDS);");
                method.insertAfter("System.out.println(\"Time passed in milliseconds : \"+ time);");

                byte[] byteCode = clazz.toBytecode();
                clazz.detach();

                return byteCode;
            } catch (final NotFoundException | CannotCompileException | IOException ex) {
                ex.printStackTrace();
            }
        }


        if (className.endsWith("java/net/HttpURLConnection")) {
            try {
                final ClassPool classPool = ClassPool.getDefault();
                classPool.insertClassPath(new ClassClassPath(SimpleClassTransformer.class));
                final CtClass clazz = classPool.get("java.net.HttpURLConnection");

                for (final CtConstructor constructor : clazz.getConstructors()) {
                    constructor.insertAfter("System.out.println(this.getURL());");
                }

                byte[] byteCode = clazz.toBytecode();
                clazz.detach();

                return byteCode;
            } catch (final NotFoundException | CannotCompileException | IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
