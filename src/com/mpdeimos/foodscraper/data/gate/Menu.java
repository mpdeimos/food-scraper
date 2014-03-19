package com.mpdeimos.foodscraper.data.gate;

import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.foodscraper.data.IMenu;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.conversion.DateFormatConverter;
import com.mpdeimos.webscraper.conversion.DeepScrapeConverter;
import com.mpdeimos.webscraper.selection.RelativeElementSelector;

import java.util.Arrays;
import java.util.Date;

/**
 * The daily menu of the GATE Bistro.
 * 
 * @author mpdeimos
 */
public class Menu implements IMenu
{
	/** @see #getDate() */
	@Scrape(value = "td:first-child>strong", regex = ".*,(.*)",
			converter = DateFormatConverter.class)
	@DateFormatConverter.Option(value = "dd.MM.yyyy")
	public Date date;

	/** The first dish of the menu. */
	@Scrape(value = ":root", converter = DeepScrapeConverter.class)
	public Dish firstDish;

	/** The second dish of the menu. */
	@Scrape(value = ":root", converter = DeepScrapeConverter.class,
			root = RelativeElementSelector.class)
	@RelativeElementSelector.Option(sibling = 1)
	public Dish secondDish;

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
		return Arrays.asList((IDish) this.firstDish, this.secondDish);
	}
}
