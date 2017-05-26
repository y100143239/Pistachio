package com.pistachio.base.socket;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


public final class SocketPool
{
    private static Logger logger = Logger.getLogger(SocketPool.class);
    //连接网络主机
    private String host;
    //连接端口
    private int port;
    //池数量
    private int poolSize;
    //socket队列
    private LinkedBlockingQueue socketQueue = new LinkedBlockingQueue();
    //保存所有的socket
    private ArrayList socketList = new ArrayList();
    //池连接检测线程
    private Thread checkThread = null;
    //连接池当前是否可用
    boolean isUseable = false;

    /**
     * 创建连接池
     *
     * @param host
     * @param port
     */
    public SocketPool(String host, int port)
    {
        this(host, port, 10);
    }

    /**
     * 创建线程池
     *
     * @param host
     * @param port
     * @param poolSize
     */
    public SocketPool(String host, int port, int poolSize)
    {
        this.host = host;
        this.port = port;
        this.poolSize = poolSize;
        initPool();

        //启动池检测线程
        checkThread = new PoolCheckThread();
        checkThread.start();
    }


    /**
     * 判断socket池是否可用
     *
     * @return
     */
    public boolean isUseable()
    {
        return isUseable;
    }

    /**
     * 获得此池连接的服务器的IP地址
     *
     * @return
     */
    public String getHost()
    {
        return host;
    }

    /**
     * 获得此服务器连接的服务器的端口号
     *
     * @return
     */
    public int getPort()
    {
        return port;
    }


    /**
     * 初始化连接池
     */
    private void initPool()
    {
        try
        {
            for (int i = 0; i < poolSize; i++)
            {
                PoolSocket socket = new PoolSocket(host, port);
                socketQueue.put(socket);
                socketList.add(socket);
            }
            isUseable = true;
        }
        catch (Exception ex)
        {
            logger.error("初始化socket池失败[" + host + ":" + port + "]", ex);
        }
    }

    /**
     * 获得Socket连接
     *
     * @return 返回的socket连接，若不可用，则返回null
     */
    public Socket getSocket()
    {
        if (!isUseable) //若不可用，则返回空的socket对象
        {
            return null;
        }

        try
        {
            return (PoolSocket) socketQueue.poll(5, TimeUnit.SECONDS); //若取不到，则等待5秒钟再取
        }
        catch (Exception ex)
        {
            logger.error("获取socket失败", ex);
        }
        return null;
    }


    /**
     * socket池检测线程
     */
    class PoolCheckThread extends Thread
    {
        public void run()
        {
            while (true)
            {
                try
                {
                    sleep(1000);    //每1秒钟检查一次
                    if (isServerCanConnect())  //若服务器可以连接，则判断是否池中数量足够
                    {
                        if (socketList.size() < poolSize) //有部分Socket出现问题无法还回socket池
                        {
                            PoolSocket socket = new PoolSocket(host, port);
                            socketList.add(socket);
                            socketQueue.put(socket);
                        }
                    }
                    else  //网络已经断开，则池中所有的连接已经不可用，需要重新建立连接
                    {
                        isUseable = false;
                        logger.info("服务器[" + host + ":" + port + "]无法连接");

                        for (int i = 0; i < socketList.size(); i++)  //服务器连接不上，关闭所有无效socket
                        {
                            try
                            {
                                PoolSocket socket = (PoolSocket) socketList.get(i);
                                socket.setIsUsable(false);
                                socket.close();
                            }
                            catch (Exception ex)
                            {
                                logger.error("关闭socket失败", ex);
                            }
                        }

                        socketList.clear();
                        socketQueue.clear();

                        //每隔5秒检查一次服务器是否已经可以连接
                        while (!isServerCanConnect())
                        {
                            sleep(1000);
                        }

                        //服务器已经可以连接，则重新初始化池
                        initPool();
                        logger.info("服务器[" + host + ":" + port + "]池重新初始化完成");
                    }
                }
                catch (Exception ex)
                {
                    logger.error("检查socket池出错", ex);
                }
            }
        }

        /**
         * 判断服务器是否可以连接
         *
         * @return
         */
        private boolean isServerCanConnect()
        {
            Socket socket = null;
            try
            {
                socket = new Socket(host, port);
                return true;
            }
            catch (Exception ex)
            {
                logger.error("连接服务器["+host+":"+port+"]失败，请检查服务器是否启动", ex);
            }
            finally
            {
                if (socket != null)
                {
                    try
                    {
                        socket.close();
                    }
                    catch (Exception ex)
                    {
                        logger.error("关闭连接失败", ex);
                    }
                }
            }
            return false;
        }
    }

    /**
     * PoolSocket封装类
     */
    class PoolSocket extends Socket
    {
        //标示该socket是否可用
        private boolean isUsable = true;

        public void setIsUsable(boolean isUsable)
        {
            this.isUsable = isUsable;
        }

        public PoolSocket(String host, int port) throws UnknownHostException, IOException
        {
            super();
            InetSocketAddress socketAddress = new InetSocketAddress(host, port);
            super.connect(socketAddress);
        }

        public void bind(SocketAddress bindpoint) throws IOException
        {
            super.bind(bindpoint);
        }

        public void connect(SocketAddress endpoint) throws IOException
        {
            super.connect(endpoint);
        }

        public void connect(SocketAddress endpoint, int timeout) throws IOException
        {
            super.connect(endpoint,timeout);
        }

        public InputStream getInputStream() throws IOException
        {
            try
            {
                return super.getInputStream();
            }
            catch (IOException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public boolean getKeepAlive() throws SocketException
        {
            try
            {
                return super.getKeepAlive();
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public boolean getOOBInline() throws SocketException
        {
            try
            {
                return super.getOOBInline();
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public OutputStream getOutputStream() throws IOException
        {
            try
            {
                return super.getOutputStream();
            }
            catch (IOException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public int getReceiveBufferSize() throws SocketException
        {
            try
            {
                return super.getReceiveBufferSize();
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }


        public boolean getReuseAddress() throws SocketException
        {
            try
            {
                return super.getReuseAddress();
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }


        public int getSendBufferSize() throws SocketException
        {
            try
            {
                return super.getSendBufferSize();
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public int getSoLinger() throws SocketException
        {
            try
            {
                return super.getSoLinger();
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public int getSoTimeout() throws SocketException
        {
            try
            {
                return super.getSoTimeout();
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public boolean getTcpNoDelay() throws SocketException
        {
            try
            {
                return super.getTcpNoDelay();
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public int getTrafficClass() throws SocketException
        {
            try
            {
                return super.getTrafficClass();
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }


        public void sendUrgentData(int data) throws IOException
        {
            try
            {
                super.sendUrgentData(data);
            }
            catch (IOException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public void setKeepAlive(boolean on) throws SocketException
        {
            try
            {
                super.setKeepAlive(on);
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public void setOOBInline(boolean on) throws SocketException
        {
            try
            {
                super.setOOBInline(on);
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public void setReceiveBufferSize(int size) throws SocketException
        {
            try
            {
                super.setReceiveBufferSize(size);
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public void setReuseAddress(boolean on) throws SocketException
        {
            try
            {
                super.setReuseAddress(on);
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public void setSendBufferSize(int size) throws SocketException
        {
            try
            {
                super.setSendBufferSize(size);
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public void setSoLinger(boolean on, int linger) throws SocketException
        {
            try
            {
                super.setSoLinger(on, linger);
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public void setSoTimeout(int timeout) throws SocketException
        {
            try
            {
                super.setSoTimeout(timeout);
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public void setTcpNoDelay(boolean on) throws SocketException
        {
            try
            {
                super.setTcpNoDelay(on);
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public void setTrafficClass(int tc) throws SocketException
        {
            try
            {
                super.setTrafficClass(tc);
            }
            catch (SocketException ex)
            {
                isUsable = false;
                throw ex;
            }
        }

        public void shutdownInput() throws IOException
        {
            super.shutdownInput();
        }

        public void shutdownOutput() throws IOException
        {
            super.shutdownOutput();
        }

        public void close() throws IOException
        {
            if (isUsable)
            {
                socketQueue.offer(this); //还回池中
            }
            else
            {
                super.close();
            }
        }
    }
}
