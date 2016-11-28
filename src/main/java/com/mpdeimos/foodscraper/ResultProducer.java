package com.mpdeimos.foodscraper;

@FunctionalInterface
public interface ResultProducer<R, E extends Exception>
{
	public R get() throws E;
}
