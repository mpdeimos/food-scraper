package com.mpdeimos.foodscraper.data.gate;

import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.foodscraper.data.IMenu;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.conversion.DateFormatConverter;
import com.mpdeimos.webscraper.conversion.DeepScrapeConverter;
import com.mpdeimos.webscraper.selection.RelativeElementSelector;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * The daily menu of the GATE Bistro.
 * 
 * @author mpdeimos
 */
public class Menu implements IMenu
{
	/** Date matching regex pattern excluding the year. */
	private static final String DATE_PATTERN = "\\d\\d\\.\\d\\d"; //$NON-NLS-1$

	/** Date matching regex pattern including the year. */
	private static final String DATE_PATTERN_WITH_YEAR = DATE_PATTERN
			+ "\\.\\d\\d\\d\\d"; //$NON-NLS-1$

	/** The weekday of the dish. */
	@Scrape(".accordion-title > span")
	public String weekDay;

	/** The end date of the menu card. */
	@Scrape(
			lenient = true,
			value = "h2",
			regex = ".* bis (" + DATE_PATTERN_WITH_YEAR + ").*",
			converter = DateFormatConverter.class,
			root = RelativeElementSelector.class)
	@DateFormatConverter.Option("dd.MM.yyyy")
	@RelativeElementSelector.Option(parent = 1)
	public Date endDate;

	/** The dishes. */
	@Scrape(
			value = ".accordion-body p > strong:matches(.+)",
			converter = DeepScrapeConverter.class)
	public Dish[] dishes;

	/** {@inheritDoc} */
	@Override
	public Date getDate()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, getWeekdayOffset());
		return calendar.getTime();
	}

	/** @returns The (negative) weekday offset from friday. */
	private int getWeekdayOffset()
	{
		switch (this.weekDay.toLowerCase().substring(0, 2))
		{
		case "mo": //$NON-NLS-1$
			return -4;
		case "di": //$NON-NLS-1$
			return -3;
		case "mi": //$NON-NLS-1$
			return -2;
		case "do": //$NON-NLS-1$
			return -1;
		}
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public Iterable<IDish> getDishes()
	{
		return Arrays.asList(this.dishes);
	}
}
