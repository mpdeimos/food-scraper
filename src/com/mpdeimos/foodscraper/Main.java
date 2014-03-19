package com.mpdeimos.foodscraper;

import com.mpdeimos.foodscraper.data.IBistro;
import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.foodscraper.data.IMenu;
import com.mpdeimos.webscraper.ScraperException;
import com.mpdeimos.webscraper.util.Strings;

import java.text.DecimalFormat;
import java.util.Map.Entry;

/**
 * Main executable.
 * 
 * @author mpdeimos
 */
public class Main
{
	/** Format string for prices. */
	private static final DecimalFormat FORMAT = new DecimalFormat("#.00€"); //$NON-NLS-1$

	/** Program entry point. */
	public static void main(String[] args) throws ScraperException
	{
		Retriever retriever = new Retriever();

		for (Entry<IBistro, IMenu> entry : retriever.getTodaysMenu())
		{
			System.out.println("## " + entry.getKey().getName() + " ##"); //$NON-NLS-1$ //$NON-NLS-2$
			System.out.println(Strings.EMPTY);
			for (IDish dish : entry.getValue().getDishes())
			{
				System.out.println(" " + dish.getName()); //$NON-NLS-1$
				System.out.println(" " + FORMAT.format(dish.getPrice())); //$NON-NLS-1$
				System.out.println(Strings.EMPTY);
			}
		}
	}
}
