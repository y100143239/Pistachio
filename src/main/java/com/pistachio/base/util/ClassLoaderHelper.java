package com.pistachio.base.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 描述:	 类加载工具类
 */
public class ClassLoaderHelper
{
    private static Logger logger = Logger.getLogger(ClassLoaderHelper.class);
    /**
     * 读入相应的资源
     *
     * @param resourceName The name of the resource to load
     * @param callingClass The Class object of the calling object
     * @return
     */
    public static URL getResource(String resourceName, Class callingClass)
    {
        URL url = null;

        url = Thread.currentThread().getContextClassLoader().getResource(resourceName);

        if ( url == null )
        {
            url = ClassLoaderHelper.class.getClassLoader().getResource(resourceName);
        }

        if ( url == null )
        {
            url = callingClass.getClassLoader().getResource(resourceName);
        }

        return url;
    }

    /**
     * 读入指定的资源
     *
     * @param resourceName 资源名
     * @param callingClass 资源对应类
     * @return
     */
    public static InputStream getResourceAsStream(String resourceName, Class callingClass)
    {
        URL url = getResource(resourceName, callingClass);

        try
        {
            return (url != null) ? url.openStream() : null;
        }
        catch (IOException e)
        {
            logger.error("获取输入流失败，返回null", e);
            return null;
        }
    }

    /**
     * 读入Class
     *
     * @param className    The name of the class to load
     * @param callingClass The Class object of the calling object
     * @return
     * @throws ClassNotFoundException If the class cannot be found anywhere
     */
    public static Class loadClass(String className, Class callingClass) throws ClassNotFoundException
    {
        try
        {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        }
        catch (ClassNotFoundException e)
        {
            try
            {
                return Class.forName(className);
            }
            catch (ClassNotFoundException ex)
            {
                try
                {
                    return ClassLoaderHelper.class.getClassLoader().loadClass(className);
                }
                catch (ClassNotFoundException exc)
                {
                    return callingClass.getClassLoader().loadClass(className);
                }
            }
        }
    }

    /**
     * 打印当前的ClassLoader
     */
    public static void printClassLoader()
    {
        logger.info("ClassLoaderHelper.printClassLoader");
        printClassLoader(Thread.currentThread().getContextClassLoader());
    }

    /**
     * 描述：
     * 作者：
     * 时间：Oct 29, 2008 1:29:13 PM
     * @param cl ClassLoader
     */
    public static void printClassLoader(ClassLoader cl)
    {
        logger.info("ClassLoaderHelper.printClassLoader(cl = " + cl + ")");

        if ( cl != null )
        {
            printClassLoader(cl.getParent());
        }
    }

    /**
     * 从包package中获取所有的Class
     * @param pack
     * @return
     */
    public static Set getClasses(Package pack)
    {

        //第一个class类的集合
        Set classes = new LinkedHashSet();
        //是否循环迭代
        boolean recursive = true;
        //获取包的名字 并进行替换
        String packageName = pack.getName();
        String packageDirName = packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration dirs;
        try
        {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            //循环迭代下去
            while (dirs.hasMoreElements())
            {
                //获取下一个元素
                URL url = (URL) dirs.nextElement();
                //得到协议的名称
                String protocol = url.getProtocol();
                //如果是以文件的形式保存在服务器上
                if ( "file".equals(protocol) )
                {
                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                }
                else if ( "jar".equals(protocol) )
                {
                    //如果是jar包文件
                    //定义一个JarFile
                    JarFile jar;
                    try
                    {
                        //获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        //从此jar包 得到一个枚举类
                        Enumeration entries = jar.entries();
                        //同样的进行循环迭代
                        while (entries.hasMoreElements())
                        {
                            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = (JarEntry) entries.nextElement();
                            String name = entry.getName();
                            //如果是以/开头的
                            if ( name.charAt(0) == '/' )
                            {
                                //获取后面的字符串
                                name = name.substring(1);
                            }
                            //如果前半部分和定义的包名相同
                            if ( name.startsWith(packageDirName) )
                            {
                                int idx = name.lastIndexOf('/');
                                //如果以"/"结尾 是一个包
                                if ( idx != -1 )
                                {
                                    //获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                //如果可以迭代下去 并且是一个包
                                if ( (idx != -1) || recursive )
                                {
                                    //如果是一个.class文件 而且不是目录
                                    if ( name.endsWith(".class") && !entry.isDirectory() )
                                    {
                                        //去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try
                                        {
                                            //添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        }
                                        catch (ClassNotFoundException e)
                                        {
                                            logger.error("没有找到["+className+"]类", e);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    catch (IOException e)
                    {
                        logger.error("获取jar文件出错", e);
                    }
                }
            }
        }
        catch (IOException e)
        {
            logger.error("读取文件出错", e);
        }

        return classes;
    }

    /*
     * 取得某一类所在包的所有类名 不含迭代
     */
    private static String[] getPackageAllClassName(String classLocation, String packageName)
    {
        //将packageName分解
        String[] packagePathSplit = packageName.split("[.]");
        String realClassLocation = classLocation;
        int packageLength = packagePathSplit.length;
        for (int i = 0; i < packageLength; i++)
        {
            realClassLocation = realClassLocation + File.separator + packagePathSplit[i];
        }
        File packeageDir = new File(realClassLocation);
        if ( packeageDir.isDirectory() )
        {
            String[] allClassName = packeageDir.list();
            return allClassName;
        }
        return null;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath,
                                                         final boolean recursive, Set classes)
    {
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if ( !dir.exists() || !dir.isDirectory() )
        {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter()
        {
            //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file)
            {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        //循环所有文件
        for (int i = 0; i < dirfiles.length; i++)
        {
            File file = dirfiles[i];

            //如果是目录 则继续扫描
            if ( file.isDirectory() )
            {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive,
                        classes);
            }
            else
            {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try
                {
                    //添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                }
                catch (ClassNotFoundException e)
                {
                    logger.error("没有找到["+className+"]类", e);
                }
            }

        }
    }
}
