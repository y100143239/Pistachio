package com.pistachio.timerengine;

/**
 * 具体的任务的接口，所有的任务只需要实现该接口，然后
 * 在配置文件中配置即可
 */

public interface Task
{
    public void execute();
}
