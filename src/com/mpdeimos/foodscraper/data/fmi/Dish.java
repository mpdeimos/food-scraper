package com.mpdeimos.foodscraper.data.fmi;

import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.webscraper.Scrape;
import com.mpdeimos.webscraper.conversion.NumberFormatConverter;
import com.mpdeimos.webscraper.util.Assert;
import com.mpdeimos.webscraper.util.Strings;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A dish of the FMI Bistro.
 * 
 * @author mpdeimos
 */
public class Dish implements IDish
{
	public static Pattern DISH_START = Pattern.compile("^\\d\\.(.*)");
	public static Pattern DISH_END = Pattern.compile("(.*)\\w*(\\d+.\\d+)");

	/** @see #getName() */
	@Scrape(value = "description")
	public String name = Strings.EMPTY;

	/** @see #getPrice() */
	@Scrape(value = "price", converter = NumberFormatConverter.class)
	public double price;

	public Dish(Deque<String> dishLines)
	{
		Matcher matcher = DISH_START.matcher(dishLines.getFirst());
		if (matcher.matches())
		{
			dishLines.removeFirst();
			dishLines.addFirst(matcher.group(1));
		}
		matcher = DISH_END.matcher(dishLines.getLast());
		if (matcher.matches())
		{
			dishLines.removeLast();
			dishLines.addLast(matcher.group(1));
			try
			{
				price = NumberFormat.getInstance().parse(matcher.group(2)).doubleValue();
			}
			catch (ParseException e)
			{
				Assert.notCaught(e, "Is checked by pattern");
			}
		}

		for (String string : dishLines)
		{
			name += string.trim();
		}

	}

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
