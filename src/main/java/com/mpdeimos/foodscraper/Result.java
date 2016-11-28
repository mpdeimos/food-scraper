package com.mpdeimos.foodscraper;

public class Result<R, E extends Exception> implements ResultProducer<R, E>
{
	private R result;

	private E exception;

	/** Private constructor. */
	private Result()
	{

	}

	@Override
	public R get() throws E
	{
		if (exception != null)
		{
			throw exception;
		}

		return result;
	}

	public static <R, E extends Exception> Result<R, E> from(
			ResultProducer<R, E> producer)
	{
		Result<R, E> result = new Result<>();
		try
		{
			result.result = producer.get();
		}
		catch (Exception e)
		{
			result.exception = (E) e;
		}

		return result;
	}

}
