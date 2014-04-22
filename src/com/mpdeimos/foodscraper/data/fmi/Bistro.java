package com.mpdeimos.foodscraper.data.fmi;

import com.mpdeimos.foodscraper.data.IBistro;
import com.mpdeimos.foodscraper.data.IMenu;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.ScraperSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the menus at the FMI Bistro
 * 
 * @author mpdeimos
 */
public class Bistro implements IBistro
{
	/** @see #getUrl() */
	private static final String URL = "http://wwwrbgalt.in.tum.de/kontakt/FMI_Bistro/aktuell.html"; //$NON-NLS-1$

	/** @see #getName() */
	private static final String NAME = "FMI"; //$NON-NLS-1$

	/** @see #getMenu() */
	@Scrape(value = "td.maincontent", converter = FmiMenuConverter.class)
	public List<Menu> menus;

	/** {@inheritDoc} */
	@Override
	public Iterable<IMenu> getMenu()
	{
		return new ArrayList<IMenu>(this.menus);
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
