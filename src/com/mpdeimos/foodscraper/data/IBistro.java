package com.mpdeimos.foodscraper.data;

/**
 * Object describing a website containing daily menus.
 * 
 * @author mpdeimos
 */
public interface IBistro
{
	/** @return The daily menus of the website. */
	public Iterable<IMenu> getMenu();

	/** @return The url of the webiste. */
	public String getUrl();

	/** @return The name of the bistro. */
	public String getName();
}
