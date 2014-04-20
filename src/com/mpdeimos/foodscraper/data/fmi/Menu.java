package com.mpdeimos.foodscraper.data.fmi;

import com.mpdeimos.foodscraper.data.IDish;
import com.mpdeimos.foodscraper.data.IMenu;
import com.mpdeimos.webscraper.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

/**
 * A daily menu of the FMI Bistro.
 * 
 * @author mpdeimos
 */
public class Menu implements IMenu
{
	public static final Pattern DAY_SEPARATOR = Pattern.compile("\\w+,\\s*den\\s*(\\d+\\.\\d+.\\d+)");

	/** @see #getDate() */
	public Date date;

	/** @see #getDishes() */
	public List<IDish> dishes = new ArrayList<>();

	public Menu(Deque<Element> menuElements)
	{
		String dateText = menuElements.removeFirst().text();
		Matcher matcher = DAY_SEPARATOR.matcher(dateText);
		if (matcher.matches())
		{
			try
			{
				this.date = new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(matcher.group(1));
			}
			catch (ParseException e)
			{
				Assert.notCaught(e, "Date must be in valid format (pattern)");
			}
		}

		Deque<String> dishElements = new LinkedList<>();
		for (Element element : menuElements)
		{
			if (element.text().matches("^\\d\\.(.*)"))
			{
				if (!dishElements.isEmpty())
				{
					this.dishes.add(new Dish(dishElements));
				}
				dishElements.clear();
			}
			dishElements.add(element.text());
		}
		if (!dishElements.isEmpty())
		{
			this.dishes.add(new Dish(dishElements));
		}
	}

	/** {@inheritDoc} */
	@Override
	public Date getDate()
	{
		return this.date;
	}

	/** {@inheritDoc} */
	@Override
	public Iterable<IDish> getDishes()
	{
		return this.dishes;
	}

}
