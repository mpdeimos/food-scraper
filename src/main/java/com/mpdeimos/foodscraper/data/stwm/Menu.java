package com.mpdeimos.foodscraper.data.stwm;

import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.foodscraper.data.IMenu;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.conversion.DateFormatConverter;
import com.mpdeimos.webscraper.conversion.DeepScrapeConverter;

import java.util.Arrays;
import java.util.Date;

/**
 * The daily menu of Mensa Garching.
 * 
 * @author mpdeimos
 */
public class Menu implements IMenu
{
	/** @see #getDate() */
	@Scrape(
			value = ".c-schedule__header > span > strong",
			converter = DateFormatConverter.class)
	@DateFormatConverter.Option(value = "dd.MM.yyyy")
	public Date date;

	/** @see #getDishes() */
	@Scrape(
			value = ".c-schedule__list > .c-schedule__list-item",
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
