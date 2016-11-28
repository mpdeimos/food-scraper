package com.mpdeimos.foodscraper.data.utum;

import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.conversion.NumberFormatConverter;
import com.mpdeimos.webscraper.selection.RelativeElementSelector;

/**
 * A dish of UnternehmerTUM / Herr Lichtenberg.
 * 
 * @author mpdeimos
 *
 */
public class Dish implements IDish
{
	/** Regular expression for selecting the price. */
	private static final String REGEX_PRICE = ".*(\\d),(\\d\\d)?.*?"; //$NON-NLS-1$

	/** Regex replacement for the price. */
	private static final String REGEX_PREPLACE_PRICE = "$1.$2"; //$NON-NLS-1$

	/** CSS selector for the price. */
	private static final String SELECTOR_PRICE = ".menu-item-price-bottom:matches(" //$NON-NLS-1$
			+ REGEX_PRICE + "),.menu-item-title:matches(" //$NON-NLS-1$
			+ REGEX_PRICE + ")"; //$NON-NLS-1$

	/** The title of the dish. */
	@Scrape(".menu-item-title")
	public String title;

	/** Description of the dish. */
	@Scrape(value = ".menu-item-description", lenient = true)
	public String description;

	/**
	 * @see #getPrice()
	 */
	@Scrape(
			value = SELECTOR_PRICE,
			lenient = true,
			regex = REGEX_PRICE,
			replace = REGEX_PREPLACE_PRICE,
			converter = NumberFormatConverter.class)
	public double price = 0;

	/**
	 * Sometimes the above reported price is zero (no element exists), then we
	 * try to parse the next one.
	 */
	@Scrape(
			value = SELECTOR_PRICE,
			lenient = true,
			regex = REGEX_PRICE,
			replace = REGEX_PREPLACE_PRICE,
			root = RelativeElementSelector.class,
			converter = NumberFormatConverter.class)
	@RelativeElementSelector.Option(sibling = 1)
	public double nextPrice = 0;

	/** {@inheritDoc} */
	@Override
	public String getName()
	{
		if (this.description != null)
		{
			return this.title + " " + this.description; //$NON-NLS-1$
		}

		return this.title;
	}

	/** {@inheritDoc} */
	@Override
	public double getPrice()
	{
		if (this.price > 0)
		{
			return this.price;
		}

		return this.nextPrice;
	}
}
