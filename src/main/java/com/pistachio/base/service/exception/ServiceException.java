package com.pistachio.base.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;


public class ServiceException extends RuntimeException
{
    public ServiceException()
    {
        super();
    }


    public ServiceException(String message)
    {
        super(message);
    }


    public ServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }


    public ServiceException(Throwable cause)
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
