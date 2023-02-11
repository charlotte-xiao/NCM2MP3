package executor;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author charlottexiao
 */
public class AsyncTaskExecutor {

    /**
     * 异步线程执行器
     */
    private static volatile ThreadPoolExecutor executor = null;

    /**
     * 核心线程数
     */
    private static final int corePoolSize = 10;

    /**
     * 最大线程数
     */
    private static final int maxPoolSize = 20;

    /**
     * 队列容量
     */
    private static final int queueCapacity = 100;

    /**
     * 空余线程存活最长时间
     */
    private static final long keepAliveTime = 1L;

    /**
     * 创建异步线程执行器
     *
     * @return 异步线程执行器
     */
    private static ThreadPoolExecutor createExecutor() {
        return new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueCapacity),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 执行异步任务
     *
     * @param task 异步任务
     */
    public static void execute(Runnable task) {
        try {
            if (executor == null) {
                synchronized (AsyncTaskExecutor.class) {
                    ThreadPoolExecutor temp = executor;
                    if (temp == null) {
                        temp = createExecutor();
                        executor = temp;
                    }
                }
            }
            executor.execute(task);
        } catch (Exception e) {
            System.out.format("异步任务启动失败: task %s ,异常： %s", JSON.toJSONString(task), JSON.toJSONString(e));
        }
    }
}
