package com.mpdeimos.foodscraper.data.gate;

import com.mpdeimos.foodscraper.data.IBistro;
import com.mpdeimos.foodscraper.data.IMenu;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.ScraperSource;
import com.mpdeimos.webscraper.conversion.DeepScrapeConverter;

import java.util.Arrays;

/**
 * Defines menus of the GATE Bistro in Garching.
 * 
 * @author mpdeimos
 */
public class Bistro implements IBistro
{
	/**
	 * @see #getUrl()
	 */
	public static final String URL = "http://www.gategarching.com/standort/bistro/wochenkarte/"; //$NON-NLS-1$

	/**
	 * @see #getName()
	 */
	public static String NAME = "Gate"; //$NON-NLS-1$

	/** The available menus. */
	@Scrape(
			value = "table>tbody>tr:has(td:first-child>strong):has(td:nth-child(2):not(:empty))",
			converter = DeepScrapeConverter.class)
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
		return ScraperSource.fromUrl(this.getUrl());
	}
}
