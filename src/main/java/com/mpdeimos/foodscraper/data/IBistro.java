package com.mpdeimos.foodscraper.data;

import com.mpdeimos.webscraper.ScraperSource.ScraperSourceProvider;

/**
 * Object describing a website containing daily menus.
 * 
 * @author mpdeimos
 */
public interface IBistro extends ScraperSourceProvider
{
	/** @return The daily menus of the website. */
	public Iterable<IMenu> getMenu();

	/** @return The url of the webiste. */
	public String getUrl();

	/** @return The name of the bistro. */
	public String getName();
}
