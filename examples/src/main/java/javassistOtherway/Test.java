package javassistOtherway;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.bytecode.*;

import java.util.List;

public class Test {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, BadBytecode {

        ClassPool classPool = ClassPool.getDefault();
        ClassFile classFile = classPool.get(Point.class.getName()).getClassFile();

        Bytecode bytecode = new Bytecode(classFile.getConstPool());
        bytecode.addAload(0);
        bytecode.addInvokespecial("java/lang/Object", MethodInfo.nameClinit, "()V");
        bytecode.addReturn(null);

        MethodInfo methodInfo = new MethodInfo(classFile.getConstPool(), MethodInfo.nameClinit, "()V");
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());

        classFile.addMethod(methodInfo);

        List<MethodInfo> test = classFile.getMethods();
        for (MethodInfo m:test) {
            System.out.println(m.getName());
            CodeAttribute ca = m.getCodeAttribute();
            CodeIterator ci =ca.iterator();

            while (ci.hasNext()) {
                int index = ci.next();
                int op = ci.byteAt(index);
                System.out.println((Mnemonic.OPCODE[op]));

            }
        }
    }
}

class Point {
    private int x;
    private int y;

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
}