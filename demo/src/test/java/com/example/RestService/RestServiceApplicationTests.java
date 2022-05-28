package com.example.RestService;

import com.example.RestService.process.InputParams;
import com.example.RestService.services.Counter;
import com.example.RestService.services.Solution;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RestServiceApplicationTests {
	public static AnnotationConfigApplicationContext context
			= new AnnotationConfigApplicationContext(SpringConfig.class);
	public static Solution solution = context.getBean("solution",Solution.class);
	public static Counter counter = context.getBean("counter",Counter.class);

	@Test
	void isRootEqualToOneHundred() {
		solution.calculateResult(new InputParams(200,(float)50,1));

		assertThat(solution.getResult()).isEqualTo(100);
	}

	@Test
	void areTwoSameObjectsEqual() {
		var s1 = new InputParams(100, (float)20, 1);
		var s2 = new InputParams(100, (float)20, 1);

		assertThat(s1.equals(s2)).isTrue();
	}

	@Test
	void areTwoSameObjectsHaveSameHash() {
		var s1 = new InputParams(100,(float)50,1);
		var s2 = new InputParams(100,(float)50,1);

		assert s1.hashCode() == s2.hashCode();
	}
	@Test
	void testIncrement() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		IntStream.range(0, 10000).forEach(count -> executorService.execute(counter::increment));

		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow();
		}

		assertEquals(10000, Counter.getCount(), "Synchronization check");
	}
}
