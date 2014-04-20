package com.mpdeimos.foodscraper.data.fmi;

import com.mpdeimos.webscraper.ScraperContext;
import com.mpdeimos.webscraper.ScraperException;
import com.mpdeimos.webscraper.conversion.Converter;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

/**
 * Manually converts the weird HTML of the FMI Bistro plan.
 * 
 * @author mpdeimos
 */
public class FmiMenuConverter implements Converter
{

	/** {@inheritDoc} */
	@Override
	public List<Menu> convert(ScraperContext context) throws ScraperException
	{
		ArrayList<Menu> menus = new ArrayList<>();

		MenuIterator parser = new MenuIterator(
				context.getSourceElement().childNodes());
		while (parser.hasNext())
		{
			menus.add(parser.next());
		}

		return menus;
	}

	private static class MenuIterator implements Iterator<Menu>
	{
		private static final Pattern APPENDIX_SEPARATOR = Pattern.compile("B\\.n\\.W\\.\\=.*");

		private Menu next = null;
		private final Deque<Element> elements;

		public MenuIterator(List<Node> nodes)
		{
			this.elements = getNonEmptyElements(nodes);
			dequeToNextDay();
		}

		private Deque<Element> getNonEmptyElements(List<Node> nodes)
		{
			LinkedList<Element> elements = new LinkedList<>();
			for (Node node : nodes)
			{
				if (node instanceof Element)
				{
					Element element = (Element) node;
					if (!element.text().isEmpty())
					{
						elements.add(element);
					}
				}
			}
			return elements;
		}

		private Deque<Element> dequeToNextDay()
		{
			if (this.elements.size() < 2)
			{
				return null;
			}

			Deque<Element> dequed = new LinkedList<>();

			// removes the current date / headline
			dequed.add(this.elements.removeFirst());

			while (!elementMatchesAny(
					this.elements.getFirst(),
					Menu.DAY_SEPARATOR,
					this.APPENDIX_SEPARATOR))
			{
				dequed.add(this.elements.removeFirst());

				if (this.elements.isEmpty())
				{
					return null;
				}
			}
			return dequed;
		}

		private boolean elementMatchesAny(Element element, Pattern... patterns)
		{
			if (element == null)
			{
				return false;
			}

			String text = element.text();
			for (Pattern pattern : patterns)
			{
				if (pattern.matcher(text).matches())
				{
					return true;
				}
			}
			return false;
		}

		/** {@inheritDoc} */
		@Override
		public boolean hasNext()
		{
			parseNext();

			return this.next != null;
		}

		/** Parses the next Menu from the list of nodes. */
		private void parseNext()
		{
			if (this.next != null)
			{
				return;
			}

			Deque<Element> menuElements = dequeToNextDay();
			if (menuElements == null)
			{
				return;
			}

			next = new Menu(menuElements);
		}

		/** {@inheritDoc} */
		@Override
		public Menu next()
		{
			if (hasNext())
			{
				Menu menu = this.next;
				this.next = null;
				return menu;
			}
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}

	}

}
