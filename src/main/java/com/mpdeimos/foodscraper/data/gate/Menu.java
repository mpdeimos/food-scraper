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
	/** Date matching regex pattern excluding the year. */
	public static final String DATE_PATTERN = ".*(\\d\\d\\.\\d\\d.\\d\\d\\d\\d)$"; //$NON-NLS-1$

	/** The weekday of the dish. */
	@Scrape(
			value = ":root",
			regex = DATE_PATTERN,
			converter = DateFormatConverter.class)
	@DateFormatConverter.Option(value = "dd.MM.yyyy")
	public Date date;

	/** The dishes. */
	@Scrape(
			value = ":root",
			converter = DeepScrapeConverter.class,
			root = RelativeElementSelector.class)
	@RelativeElementSelector.Option(sibling = 1)
	public Dish dish1;

	/** The dishes. */
	@Scrape(
			value = ":root",
			converter = DeepScrapeConverter.class,
			root = RelativeElementSelector.class)
	@RelativeElementSelector.Option(sibling = 2)
	public Dish dish2;

	/** The dishes. */
	@Scrape(
			value = ":root",
			converter = DeepScrapeConverter.class,
			root = RelativeElementSelector.class)
	@RelativeElementSelector.Option(sibling = 3)
	public Dish dish3;

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
		return Arrays.asList(this.dish1, this.dish2, this.dish3);
	}
}
