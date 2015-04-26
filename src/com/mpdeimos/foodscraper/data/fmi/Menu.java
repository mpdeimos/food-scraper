package com.mpdeimos.foodscraper.data.fmi;

import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.foodscraper.data.IMenu;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.conversion.DateFormatConverter;
import com.mpdeimos.webscraper.conversion.DeepScrapeConverter;

import java.util.Arrays;
import java.util.Date;

/**
 * A daily menu of the FMI Bistro.
 * 
 * @author mpdeimos
 */
public class Menu implements IMenu
{
	/** @see #getDate() */
	@Scrape(value = ":root", attribute = "date", regex = ".*,(.*)",
			converter = DateFormatConverter.class)
	@DateFormatConverter.Option(value = "dd.MM.yyyy")
	public Date date;

	/** @see #getDishes() */
	@Scrape(
			value = "meal:not(:contains(Tagessuppe)):not(:has(price:matches(^$)))",
			converter = DeepScrapeConverter.class)
	public Dish[] dishes;

	/** {@inheritDoc} */
	@Override
	public Date getDate()
	{
		return this.date;
	}

	/** {@inheritDoc} */
	@Override
	public Iterable<IDish> getDishes()
	{
		return Arrays.asList((IDish[]) this.dishes);
	}

}
