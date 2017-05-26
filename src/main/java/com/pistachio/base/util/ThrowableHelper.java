package com.pistachio.base.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 描述:	 异常工具类
 */
@Deprecated
public class ThrowableHelper
{
    /**
     * 把异常和错误的信息转化为字串
     *
     * @param throwObj Throwable类或接口
     * @return
     */
    public static String toString(Throwable throwObj)
    {
        StringWriter writer = new StringWriter();
        PrintWriter pWriter = new PrintWriter(writer);
        throwObj.printStackTrace(pWriter);
        return writer.getBuffer().toString();
    }

    /**
     * 显示调用者
     */
    public static void getCaller()
    {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        for (int i = 0; i < stack.length; i++)
        {
            StackTraceElement ste = stack[i];
            System.out.println(ste.getClassName() + "." + ste.getMethodName() + "(...)");
            System.out.println(i + "--" + ste.getMethodName());
            System.out.println(i + "--" + ste.getFileName());
            System.out.println(i + "--" + ste.getLineNumber());
        }
    }

}
