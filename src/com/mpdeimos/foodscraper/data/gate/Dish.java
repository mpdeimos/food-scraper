package com.mpdeimos.foodscraper.data.gate;

import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.conversion.NumberFormatConverter;

/**
 * Describes a dish at GATE Garching.
 * 
 * @author mpdeimos
 */
public class Dish implements IDish
{
	/** @see #getName() */
	@Scrape(
			value = "td:nth-child(2)",
			regex = "Gericht \\d (.*?)( Guten Appetit.*)?$")
	public String name;

	/** @see #getPrice() */
	@Scrape(
			value = "td:nth-child(3):matches(\\d,\\d\\d)",
			lenient = true,
			regex = ".*(\\d),(\\d\\d)",
			replace = "$1.$2",
			converter = NumberFormatConverter.class)
	public double price = 0;

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
