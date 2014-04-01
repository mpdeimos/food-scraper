package com.mpdeimos.foodscraper.data;

import com.mpdeimos.foodscraper.data.IBistro;
import com.mpdeimos.webscraper.Scraper;
import com.mpdeimos.webscraper.ScraperException;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Base test case for {@link IBistro} parsing tests.
 * 
 * @author mpdeimos
 */
public abstract class BistroTestBase
{
	/** Glob to match HTML files. */
	private static final String HTML_FILE_GLOB = "*.html"; //$NON-NLS-1$

	/**
	 * Tests {@link IBistro#getMenu()} with HTML files provided in
	 * {@link #getTestDataDirectory()}.
	 */
	@Test
	public void testProvidedData() throws ScraperException
	{
		try (DirectoryStream<Path> paths = Files.newDirectoryStream(
				getTestDataDirectory(),
				HTML_FILE_GLOB))
		{
			for (Path path : paths)
			{
				assertScrapedData(path);
			}
		}
		catch (IOException e)
		{
			Assert.fail("Cannot read test data directory: " + e.getMessage()); //$NON-NLS-1$
		}
	}

	/**
	 * Asserts that the scraped data for the given html file matches the one
	 * stored in the json file with <code>filename + ".json"</code>.
	 */
	private void assertScrapedData(Path path) throws ScraperException
	{
		IBistro bistro = scrapeTestFile(path);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Assert.assertEquals(
				readTestFile(path.getFileName() + ".json"), //$NON-NLS-1$
				gson.toJson(bistro));
	}

	/** Reads a test file and fails of reading is not possible. */
	private String readTestFile(String filename)
	{
		try
		{
			return new String(
					Files.readAllBytes(getTestDataDirectory().resolve(
							filename)));
		}
		catch (IOException e)
		{
			Assert.fail("Could not read test file: " + e.getMessage()); //$NON-NLS-1$
			return null;
		}
	}

	/**
	 * @return The {@link Path} of the test data directory of the namespace of
	 *         this class.
	 */
	private Path getTestDataDirectory()
	{
		return Paths.get("test-data", //$NON-NLS-1$
				getClass().getPackage().getName());
	}

	/** Reads and scrapes a test file. */
	private IBistro scrapeTestFile(Path file) throws ScraperException
	{
		IBistro bistro = createBistro();

		Document doc = Jsoup.parse(readTestFile(file.getFileName().toString()));
		new Scraper.Builder(doc, bistro).build().scrape();

		return bistro;
	}

	/** Creates a fresh instance of {@link IBistro}. */
	protected abstract IBistro createBistro();

}