package com.mpdeimos.foodscraper.data.utum;

import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.foodscraper.data.IMenu;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.conversion.DateFormatConverter;
import com.mpdeimos.webscraper.conversion.DeepScrapeConverter;

import java.util.Arrays;
import java.util.Date;

/**
 * The daily menu of UnternehmerTUM / Herr Lichtenberg.
 * 
 * @author mpdeimos
 */
public class Menu implements IMenu
{
	/**
	 * @see #getDate()
	 */
	@Scrape(
			value = ".menu-section-title,.menu-item-title",
			resultIndex = 0,
			converter = DateFormatConverter.class)
	@DateFormatConverter.Option(value = "dd.MM.yyyy")
	public Date date;

	/**
	 * @see #getDishes()
	 */
	@Scrape(
			value = ".menu-items .menu-item:has(.menu-item-title:matches(.*\\p{IsAlphabetic}+.*):not(:matches(\\*Pichler Bio-Fleisch)))",
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
