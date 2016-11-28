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

	/** @see #getDate() */
	@Scrape(
			lenient = true,
			value = "td:first-child:matches(" + DATE_PATTERN + ")",
			regex = ".*,.*(" + DATE_PATTERN + ").*",
			converter = DateFormatConverter.class)
	@DateFormatConverter.Option("dd.MM")
	public Date dateWithoutYear;

	/** @see #getDate() */
	@Scrape(
			lenient = true,
			value = "td:first-child:matches(" + DATE_PATTERN_WITH_YEAR
					+ ")",
			regex = ".*,.*(" + DATE_PATTERN_WITH_YEAR + ").*",
			converter = DateFormatConverter.class)
	@DateFormatConverter.Option("dd.MM.yyyy")
	public Date date;

	/** The year the page has been rendered. */
	@Scrape(
			lenient = true,
			value = ".footer-upper>p:first-child",
			regex = "© (\\d{4}).*",
			root = RelativeElementSelector.class)
	@RelativeElementSelector.Option(parent = Integer.MAX_VALUE) // TODO (MP)
																// Write root
																// element
																// selector
	public int year;

	/** The first dish of the menu. */
	@Scrape(value = ":root", converter = DeepScrapeConverter.class)
	public Dish firstDish;

	/** The second dish of the menu. */
	@Scrape(
			value = ":root",
			converter = DeepScrapeConverter.class,
			root = RelativeElementSelector.class)
	@RelativeElementSelector.Option(sibling = 1)
	public Dish secondDish;

	/** {@inheritDoc} */
	@Override
	public Date getDate()
	{
		if (this.date != null)
		{
			return this.date;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.dateWithoutYear);
		calendar.set(Calendar.YEAR, this.year);

		return calendar.getTime();
	}

	/** {@inheritDoc} */
	@Override
	public Iterable<IDish> getDishes()
	{
		return Arrays.asList((IDish) this.firstDish, this.secondDish);
	}
}
