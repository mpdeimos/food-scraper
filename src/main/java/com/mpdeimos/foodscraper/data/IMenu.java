package com.mpdeimos.foodscraper.data;

import java.util.Date;

/**
 * Object describing a daily menus with various dishes.
 * 
 * @author mpdeimos
 */
public interface IMenu
{
	/** The date the menu is offered. */
	public Date getDate();

	/** @return The dishes of the menu. */
	public Iterable<IDish> getDishes();
}
