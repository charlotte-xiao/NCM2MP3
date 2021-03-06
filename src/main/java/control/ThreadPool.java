package control;

import java.util.LinkedList;

/**
 * @author charlottexiao
 */
public class ThreadPool {
    //线程池的大小
    int threadPoolsize;
    //任务容器
    LinkedList<Runnable> tasks=new LinkedList<Runnable>();

    /** 初始化线程池
     *  功能:按照提供的线程池大小初始化线程池
     * @param threadPoolsize 线程池大小
     */
    public ThreadPool(int threadPoolsize){
        this.threadPoolsize=threadPoolsize;
        synchronized (tasks){
            for(int i=0;i<threadPoolsize;i++){
                new TaskConsumeThread().start();
            }
        }
    }

    /** 添加任务
     * 功能:添加执行转换NCM文件的任务的(生产者)
     * @param task
     */
    public void addTask(Runnable task){
        synchronized (tasks){
            tasks.add(task);
            tasks.notifyAll();
        }
    }

    /** 等待任务,执行
     * 功能:消费者线程
     */
    class TaskConsumeThread extends Thread{
        Runnable task;
        @Override
        public void run() {
          while(true){
              synchronized (tasks){
                  while(tasks.isEmpty()){
                      try {
                          tasks.wait();
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }
                  task=tasks.removeLast();
                  tasks.notifyAll();
              }
              task.run();
          }
        }
    }
}
