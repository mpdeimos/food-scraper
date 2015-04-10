package com.mpdeimos.foodscraper.data.utum;

import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.conversion.NumberFormatConverter;

/**
 * A dish of UnternehmerTUM / Herr Lichtenberg.
 * 
 * @author mpdeimos
 *
 */
public class Dish implements IDish
{
	/** The title of the dish. */
	@Scrape(".menu-item-title")
	public String title;

	/** Description of the dish. */
	@Scrape(".menu-item-description")
	public String description;

	/** @see #getPrice() */
	@Scrape(
			value = ".menu-item-price-bottom",
			lenient = true,
			regex = ".*(\\d),(\\d\\d)",
			replace = "$1.$2",
			converter = NumberFormatConverter.class)
	public double price = 0;

	/** {@inheritDoc} */
	@Override
	public String getName()
	{
		return this.title + " " + this.description; //$NON-NLS-1$
	}

	/** {@inheritDoc} */
	@Override
	public double getPrice()
	{
		return this.price;
	}
}
