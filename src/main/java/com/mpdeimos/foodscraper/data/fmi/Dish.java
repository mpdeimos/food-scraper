package com.mpdeimos.foodscraper.data.fmi;

import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.conversion.NumberFormatConverter;

/**
 * A dish of the FMI Bistro.
 * 
 * @author mpdeimos
 */
public class Dish implements IDish
{
	/** @see #getName() */
	@Scrape(value = "description")
	public String name;

	/** @see #getPrice() */
	@Scrape(value = "price", converter = NumberFormatConverter.class)
	public double price;

	/** {@inheritDoc} */
	@Override
	public String getName()
	{
		return this.name;
	}

	/** {@inheritDoc} */
	@Override
	public double getPrice()
	{
		return this.price;
	}
}
