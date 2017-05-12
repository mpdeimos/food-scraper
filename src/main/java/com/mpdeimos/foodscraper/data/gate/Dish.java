package com.mpdeimos.foodscraper.data.gate;

import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.conversion.NumberFormatConverter;

import org.jsoup.helper.StringUtil;

/**
 * Describes a dish at GATE Garching.
 * 
 * @author mpdeimos
 */
public class Dish implements IDish
{
	/**
	 * @see #getName()
	 */
	@Scrape(value = "strong:first-child")
	public String name;

	/**
	 * @see #getName()
	 */
	@Scrape(
			value = ":root",
			ownText = true)
	public String nameAddition;

	/**
	 * @see #getPrice()
	 */
	@Scrape(
			value = "strong:last-child",
			lenient = true,
			regex = ".*(\\d),(\\d\\d).*",
			replace = "$1.$2",
			converter = NumberFormatConverter.class)
	public double price = 0;

	/** {@inheritDoc} */
	@Override
	public String getName()
	{
		if (!StringUtil.isBlank(this.nameAddition))
		{
			return this.name + " " + this.nameAddition; //$NON-NLS-1$
		}

		return this.name;
	}

	/** {@inheritDoc} */
	@Override
	public double getPrice()
	{
		return this.price;
	}
}
