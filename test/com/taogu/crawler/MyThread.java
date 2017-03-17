package com.taogu.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThread implements Runnable {

	public static void main(String[] args) {
		ExecutorService ctp = Executors.newCachedThreadPool(new ThreadFactory() {
			private AtomicInteger count = new AtomicInteger();

			public Thread newThread(Runnable r) {
				int c = count.incrementAndGet();
				System.out.println("create no " + c + " Threads");
				return new WorkThread(r, count);

			}
		});

		ctp.execute(new MyThread());
		ctp.execute(new MyThread());
		ctp.execute(new MyThread());
		ctp.execute(new MyThread());
		ctp.execute(new MyThread());
		ctp.execute(new MyThread());

		ctp.shutdown();
		try {
			ctp.awaitTermination(1200, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.println("complete a task!!!");
	}

	static class WorkThread extends Thread {

		private Runnable target;
		private AtomicInteger counter;

		public WorkThread(Runnable target, AtomicInteger counter) {
			this.target = target;
			this.counter = counter;
		}

		@Override
		public void run() {
			try {
				target.run();
			} finally {
				int c = counter.getAndDecrement();
				System.out.println("terminate no " + c + " Threads");
			}
		}
	}
}