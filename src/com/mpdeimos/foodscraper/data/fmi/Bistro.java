package com.mpdeimos.foodscraper.data.fmi;

import com.mpdeimos.foodscraper.data.IBistro;
import com.mpdeimos.foodscraper.data.IMenu;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.ScraperSource;
import com.mpdeimos.webscraper.conversion.DeepScrapeConverter;

import java.util.Arrays;

/**
 * Defines the menus at the FMI Bistro
 * 
 * @author mpdeimos
 */
public class Bistro implements IBistro
{
	/** @see #getUrl() */
	private static final String URL = "http://www.interface-ag.com/open/wp-content/uploads/2010/07/fmi.xml"; //$NON-NLS-1$

	/** @see #getName() */
	private static final String NAME = "FMI"; //$NON-NLS-1$

	/** @see #getMenu() */
	@Scrape(value = "mealPlan", converter = DeepScrapeConverter.class)
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

	/** {@inheritDoc} */
	@Override
	public ScraperSource getSource()
	{
		return ScraperSource.fromUrl(URL);
	}
}
