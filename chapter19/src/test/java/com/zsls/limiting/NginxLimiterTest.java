package com.zsls.limiting;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 */
public class NginxLimiterTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 6; i++) {
        	/**
			 * Future 的局限性
			 * 不能手动完成 当你写了一个函数，用于通过一个远程API获取一个电子商务产品最新价格。因为这个 API 太耗时，你把它允许在一个独立的线程中，并且从你的函数中返回一个 Future。现在假设这个API服务宕机了，这时你想通过该产品的最新缓存价格手工完成这个Future 。你会发现无法这样做。
			 * Future 的结果在非阻塞的情况下，不能执行更进一步的操作 Future 不会通知你它已经完成了，它提供了一个阻塞的 get() 方法通知你结果。你无法给 Future 植入一个回调函数，当 Future 结果可用的时候，用该回调函数自动的调用 Future 的结果。
			 * 多个 Future 不能串联在一起组成链式调用 有时候你需要执行一个长时间运行的计算任务，并且当计算任务完成的时候，你需要把它的计算结果发送给另外一个长时间运行的计算任务等等。你会发现你无法使用 Future 创建这样的一个工作流。
			 * 不能组合多个 Future 的结果 假设你有10个不同的Future，你想并行的运行，然后在它们运行未完成后运行一些函数。你会发现你也无法使用 Future 这样做。
			 * 没有异常处理 Future API 没有任务的异常处理结构居然有如此多的限制，幸好我们有CompletableFuture，你可以使用 CompletableFuture 达到以上所有目的。
			 * CompletableFuture 实现了 Future 和 CompletionStage接口，并且提供了许多关于创建，链式调用和组合多个 Future 的便利方法集，而且有广泛的异常处理支持
			 *
			 * 使用 supplyAsync() 运行一个异步任务并且返回结果 当任务不需要返回任何东西的时候， CompletableFuture.runAsync() 非常有用。但是如果你的后台任务需要返回一些结果应该要怎么样？
			 * CompletableFuture.supplyAsync() 就是你的选择
			 *
			 * CompletableFuture.get()方法是阻塞的。它会一直等到Future完成并且在完成后返回结果。 但是，这是我们想要的吗？对于构建异步系统，我们应该附上一个回调给CompletableFuture，当Future完成的时候，自动的获取结果。 如果我们不想等待结果返回，我们可以把需要等待Future完成执行的逻辑写入到回调函数中。
			 * 可以使用 thenApply(), thenAccept() 和thenRun()方法附上一个回调给CompletableFuture
        	 */
            CompletableFuture.supplyAsync(() -> {
                final ResponseEntity<String> entity = new RestTemplate().getForEntity("http://211.83.241.147:9088/index", String.class);
                return entity.getBody();
            }, service).thenAccept(System.out::println);
        }
        service.shutdown();
    }
}
