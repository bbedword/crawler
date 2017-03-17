package com.lshb.crawler.util;

import java.util.Random;

public class RandomUtil {

	public static int oneInRange(int min, int max) {
		return new Random().nextInt(max - min) + min;
	}
}
