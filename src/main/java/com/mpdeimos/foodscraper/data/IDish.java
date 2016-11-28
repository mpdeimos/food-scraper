package com.mpdeimos.foodscraper.data;

/**
 * Object describing a single dish with a name and price.
 * 
 * @author mpdeimos
 */
public interface IDish
{
	/** @return The name of the dish. */
	public String getName();

	/** @return The price of the dish. */
	public double getPrice();
}
