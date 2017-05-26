package com.pistachio.base.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;


public class NoRightException extends RuntimeException
{
    public NoRightException()
    {
        super();
    }


    public NoRightException(String message)
    {
        super(message);
    }


    public NoRightException(String message, Throwable cause)
    {
        super(message, cause);
    }


    public NoRightException(Throwable cause)
    {
        super(cause);
    }

    public void printStackTrace()
    {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream outStream)
    {
        printStackTrace(new PrintWriter(outStream));
    }

    public void printStackTrace(PrintWriter writer)
    {
        super.printStackTrace(writer);

        if (getCause() != null)
        {
            getCause().printStackTrace(writer);
        }
        writer.flush();
    }
}