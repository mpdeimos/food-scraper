package com.mpdeimos.foodscraper.data.stwm;

import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.conversion.DefaultConverter.ScrapedEnum;

/**
 * A dish of Mensa Garching.
 * 
 * @author mpdeimos
 *
 */
public class Dish implements IDish
{
	/** @see #getName() */
	@Scrape(
			value = ".c-schedule__description > .js-schedule-dish-description",
			ownText = true)
	public String name;

	/** @see #getPrice() */
	@Scrape(".c-schedule__term > .stwm-artname")
	public EPrice price;

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
		return this.price.price;
	}

	/** Studentenwerk Price Matrix for dishes. */
	private static enum EPrice implements ScrapedEnum
	{
		/** Dish with price {@value} */
		TAGESGERICHT_1(1.00),

		/** Dish with price {@value} */
		TAGESGERICHT_2(1.55),

		/** Dish with price {@value} */
		TAGESGERICHT_3(1.90),

		/** Dish with price {@value} */
		TAGESGERICHT_4(2.40),

		/** Dish with price {@value} */
		BIOGERICHT_1(1.55),

		/** Dish with price {@value} */
		BIOGERICHT_2(1.90),

		/** Dish with price {@value} */
		BIOGERICHT_3(2.40),

		/** Dish with price {@value} */
		BIOGERICHT_4(2.60),

		/** Dish with price {@value} */
		BIOGERICHT_5(2.80),

		/** Dish with price {@value} */
		BIOGERICHT_6(3.00),

		/** Dish with price {@value} */
		BIOGERICHT_7(3.20),

		/** Dish with price {@value} */
		BIOGERICHT_8(3.50),

		/** Dish with price {@value} */
		BIOGERICHT_9(4.00),

		/** Dish with price {@value} */
		BIOGERICHT_10(4.50),

		/** Dish with price {@value} */
		AKTIONSESSEN_1(1.55),

		/** Dish with price {@value} */
		AKTIONSESSEN_2(1.90),

		/** Dish with price {@value} */
		AKTIONSESSEN_3(2.40),

		/** Dish with price {@value} */
		AKTIONSESSEN_4(2.60),

		/** Dish with price {@value} */
		AKTIONSESSEN_5(2.80),

		/** Dish with price {@value} */
		AKTIONSESSEN_6(3.00),

		/** Dish with price {@value} */
		AKTIONSESSEN_7(3.20),

		/** Dish with price {@value} */
		AKTIONSESSEN_8(3.50),

		/** Dish with price {@value} */
		AKTIONSESSEN_9(4.00),

		/** Dish with price {@value} */
		AKTIONSESSEN_10(4.50),

		/** Dish with unknown price */
		UNKNOWN(0);

		/** The price of the dish. */
		private final double price;

		/** Constructor. */
		private EPrice(double price)
		{
			this.price = price;
		}

		/** {@inheritDoc} */
		@Override
		public boolean equalsScrapedData(String data)
		{
			if (UNKNOWN == this)
			{
				return true; // prevents scraping errors.
			}

			data = data.toUpperCase().replace(' ', '_');
			return name().equals(data);
		}
	}
}
