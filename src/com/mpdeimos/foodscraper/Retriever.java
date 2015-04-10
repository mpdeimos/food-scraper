package com.mpdeimos.foodscraper;

import com.mpdeimos.foodscraper.data.IBistro;
import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.foodscraper.data.IMenu;
import com.mpdeimos.webscraper.Scraper;
import com.mpdeimos.webscraper.Scraper.ScraperBuilder;
import com.mpdeimos.webscraper.ScraperException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Retrieves menus from all configured bistros.
 * 
 * @author mpdeimos
 */
public class Retriever
{
	/** List of available bistros. */
	private final IBistro[] bistros = new IBistro[]
	{
			new com.mpdeimos.foodscraper.data.gate.Bistro(),
			new com.mpdeimos.foodscraper.data.stwm.Bistro(),
			new com.mpdeimos.foodscraper.data.utum.Bistro(),
			new com.mpdeimos.foodscraper.data.fmi.Bistro()
	};

	/** Indicates whether the bistro websites have been scraped. */
	private boolean retrieved = false;

	/** @return A list of bistros including their daily menus. */
	public Iterable<IBistro> getBistros() throws ScraperException
	{
		this.retrieve();

		return Arrays.asList(this.bistros);
	}

	/** @return A list of bistros including todays menus. */
	public Iterable<Entry<IBistro, IMenu>> getTodaysMenu()
			throws ScraperException
	{
		Map<IBistro, IMenu> today = new LinkedHashMap<>();

		for (IBistro bistro : this.getBistros())
		{
			for (IMenu menu : bistro.getMenu())
			{
				if (isToday(menu.getDate()))
				{
					today.put(bistro, menu);
				}
			}
			if (!today.containsKey(bistro))
			{
				today.put(bistro, new IMenu()
				{

					@Override
					public Iterable<IDish> getDishes()
					{
						return new ArrayList<IDish>(0);
					}

					@Override
					public Date getDate()
					{
						return new Date(); // today
					}
				});
			}
		}

		return today.entrySet();
	}

	/** Retrieves the daily meals from the bistro websites. */
	public void retrieve() throws ScraperException
	{
		if (this.retrieved)
		{
			return;
		}

		ScraperBuilder builder = Scraper.builder();
		for (IBistro bistro : this.bistros)
		{
			builder.add(bistro);
		}
		builder.build().scrape();

		this.retrieved = true;
	}

	/** @return <code>true</code> if the passed date is today. */
	private static boolean isToday(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar now = Calendar.getInstance();
		return cal.get(Calendar.YEAR) == now.get(Calendar.YEAR)
				&& cal.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR);
	}
}
