package com.mpdeimos.foodscraper;

import com.mpdeimos.foodscraper.data.IBistro;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Registry of bistros. */
public class BistroRegistry
{
	/** Available bistros. */
	private static final Iterable<Class<? extends IBistro>> BISTROS = Arrays.asList(
			com.mpdeimos.foodscraper.data.gate.Bistro.class,
			com.mpdeimos.foodscraper.data.utum.Bistro.class,
			com.mpdeimos.foodscraper.data.stwm.Bistro.class,
			com.mpdeimos.foodscraper.data.fmi.Bistro.class);

	/**
	 * @return The registered bistro types.
	 */
	public static Iterable<Class<? extends IBistro>> getBistros()
	{
		return BISTROS;
	}

	/**
	 * @return a new instance of the bistro class or <code>null</code> if the
	 *         bistro cannot be instantiated.
	 */
	public static IBistro create(Class<? extends IBistro> clazz)
	{
		try
		{
			return clazz.getConstructor().newInstance();
		}
		catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e)
		{
			return null;
		}
	}

	/**
	 * @return New instances of all registered bistros.
	 */
	public static Iterable<IBistro> createBistros()
	{
		List<IBistro> bistros = new ArrayList<>();
		for (Class<? extends IBistro> clazz : BISTROS)
		{
			bistros.add(create(clazz));
		}
		return bistros;
	}
}
