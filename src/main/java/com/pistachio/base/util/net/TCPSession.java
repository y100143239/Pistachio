/*
 * Copyright (c) 2016 Your Corporation. All Rights Reserved.
 */

package com.pistachio.base.util.net;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 描述:
 */
public class TCPSession
{
    private static Logger logger = Logger.getLogger(TCPSession.class);
    private Socket socket = new Socket();

    public TCPSession()
    {
    }

    public Socket socket()
    {
        return socket;
    }

    /**
     * 关闭Session连接
     */
    public void close()
    {
        try
        {
            socket.close();
        }
        catch (IOException ex)
        {
            logger.error("关闭socket失败", ex);
        }
    }

    /**
     * 连接到服务器,打开Session
     *
     * @param host    服务器名称或IP
     * @param port    服务器端口号
     * @param timeout 连接的超时时间(单位毫秒),0表示无限超时
     * @return
     */
    public boolean connect(String host, int port, int timeout)
    {
        try
        {
            if (socket.isConnected())
            {
                close();
            }
            InetSocketAddress address = new InetSocketAddress(host, port);
            socket.connect(address, timeout);
            return true;
        }
        catch (IOException ex)
        {
            logger.error("连接服务器失败", ex);
        }
        return false;
    }

    /**
     * 连接到服务器
     *
     * @param host 服务器名称或IP
     * @param port 服务器端口号
     * @return
     */
    public boolean connect(String host, int port)
    {
        try
        {
            if (socket.isConnected())
            {
                close();
            }
            InetSocketAddress address = new InetSocketAddress(host, port);
            socket.connect(address);
            return true;
        }
        catch (IOException ex)
        {
            logger.error("连接服务器失败", ex);
        }
        return false;
    }

    /**
     * 发送字串数据
     *
     * @param content 需要发送的内容
     * @return
     */
    public boolean send(String content)
    {
        try
        {
            OutputStream outs = socket.getOutputStream();
            outs.write(content.getBytes("GBK"));
            return true;
        }
        catch (Exception ex)
        {
            logger.error("发送数据失败", ex);
        }
        return false;
    }

    /**
     * 发送字节数据
     *
     * @param bytes 需要发送的内容
     * @return
     */
    public boolean send(byte[] bytes)
    {
        try
        {
            OutputStream outs = socket.getOutputStream();
            outs.write(bytes);
            return true;
        }
        catch (Exception ex)
        {
            logger.error("发送数据失败", ex);
        }
        return false;
    }

    /**
     * 接收数据,接收缓冲区大小为4K
     *
     * @param timeout 指定超时的时间，以毫秒为单位
     * @return
     */
    public String receive(int timeout)
    {
        return receive(4096, timeout);
    }

    /**
     * 接收数据
     *
     * @param bufSize 接收数据的缓冲区大小
     * @param timeout 指定超时的时间，以毫秒为单位
     * @return
     */
    public String receive(int bufSize, int timeout)
    {
        try
        {
            socket.setSoTimeout(timeout);
            byte[] buffer = new byte[bufSize];
            int length = socket.getInputStream().read(buffer);
            if (length > 0)
            {
                return new String(buffer, 0, length, "GBK");
            }
        }
        catch (Exception ex)
        {
            logger.error("接收数据失败", ex);
        }
        return "";
    }
}
