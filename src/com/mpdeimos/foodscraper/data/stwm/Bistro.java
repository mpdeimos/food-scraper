package com.mpdeimos.foodscraper.data.stwm;

import com.mpdeimos.foodscraper.data.IBistro;
import com.mpdeimos.foodscraper.data.IMenu;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.conversion.DeepScrapeConverter;

import java.util.Arrays;

/**
 * Defines menus at Mensa Garching.
 * 
 * @author mpdeimos
 */
public class Bistro implements IBistro
{
	/** @see #getUrl() */
	public static final String URL = "http://www.studentenwerk-muenchen.de/mensa/speiseplan/speiseplan_422_-de.html"; //$NON-NLS-1$

	/** @see #getName() */
	public static String NAME = "Mensa"; //$NON-NLS-1$

	/** @see #getMenu() */
	@Scrape(value = "table.menu", converter = DeepScrapeConverter.class)
	public Menu[] menus;

	/** {@inheritDoc} */
	@Override
	public Iterable<IMenu> getMenu()
	{
		return Arrays.asList((IMenu[]) this.menus);
	}

	/** {@inheritDoc} */
	@Override
	public String getUrl()
	{
		return URL;
	}

	/** {@inheritDoc} */
	@Override
	public String getName()
	{
		return NAME;
	}
}
