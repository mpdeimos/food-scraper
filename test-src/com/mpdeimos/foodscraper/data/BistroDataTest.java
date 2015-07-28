package com.mpdeimos.foodscraper.data;

import com.mpdeimos.foodscraper.BistroRegistry;
import com.mpdeimos.webscraper.Scraper;
import com.mpdeimos.webscraper.ScraperException;
import com.mpdeimos.webscraper.ScraperSource;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Base test case for {@link IBistro} parsing tests.
 * 
 * @author mpdeimos
 */
@RunWith(Parameterized.class)
public class BistroDataTest
{
	/** Glob to match HTML files. */
	private static final String HTML_FILE_GLOB = "*.html"; //$NON-NLS-1$

	/** Gathers the test data. */
	@Parameters(name = "{1}")
	public static Iterable<Object[]> data()
	{
		List<Object[]> data = new ArrayList<Object[]>();

		for (Class<? extends IBistro> bistro : BistroRegistry.getBistros())
		{
			try (DirectoryStream<Path> paths = Files.newDirectoryStream(
					getTestDataDirectory(bistro),
					HTML_FILE_GLOB))
			{
				for (Path path : paths)
				{
					data.add(new Object[] { BistroRegistry.create(bistro),
							path });
				}
			}
			catch (IOException e)
			{
				Assert.fail(
						"Cannot read test data directory: " + e.getMessage()); //$NON-NLS-1$
			}
		}

		return data;
	}

	/** The bistro object to test. */
	@Parameter(0)
	public IBistro bistro;

	/** The test file to scrape. */
	@Parameter(1)
	public Path testFile;

	/**
	 * Asserts that the scraped data for the given html file matches the one
	 * stored in the json file with <code>filename + ".json"</code>.
	 */
	@Test
	public void testBistro() throws ScraperException
	{
		ScraperSource source = ScraperSource.fromHtml(
				readTestFile(this.testFile));
		Scraper.builder().add(source, this.bistro).build().scrape();

		Path referenceFile = this.testFile.resolveSibling(
				this.testFile.getFileName() + ".json"); //$NON-NLS-1$
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		Assert.assertEquals(
				readTestFile(referenceFile),
				gson.toJson(this.bistro));
	}

	/** Reads a test file and fails of reading is not possible. */
	private String readTestFile(Path path)
	{
		try
		{
			return new String(Files.readAllBytes(path));
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
	private static Path getTestDataDirectory(Class<? extends IBistro> bistro)
	{
		return Paths.get(
				"test-data", //$NON-NLS-1$
				bistro.getPackage().getName());
	}
}