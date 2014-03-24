package ro.endava.bestmarathon.webserver;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by cosmin on 3/20/14.
 */
public class WebServerTest {

    @Test
    public void test() throws InterruptedException {
        ExecutorService executor = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 5; i++){
            //executor.submit(new TestThread());
        }

        Thread.sleep(100000);

    }
}
