package com.mpdeimos.foodscraper.data;

import com.mpdeimos.foodscraper.BistroRegistry;
import com.mpdeimos.webscraper.Scraper;
import com.mpdeimos.webscraper.ScraperException;
import com.mpdeimos.webscraper.ScraperSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

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
	private static final Pattern HTML_FILE_PATTERN = Pattern.compile(
			".*\\.html"); //$NON-NLS-1$

	/** Gathers the test data. */
	@Parameters(name = "{1}")
	public static Iterable<Object[]> data()
	{
		List<Object[]> data = new ArrayList<Object[]>();

		for (Class<? extends IBistro> bistro : BistroRegistry.getBistros())
		{

			Set<String> testFiles = getTestFiles(bistro);
			Assert.assertTrue(
					"No test-cases found for " + bistro.getName(), //$NON-NLS-1$
					testFiles.size() > 0);
			for (String testFile : testFiles)
			{
				data.add(new Object[] { BistroRegistry.create(bistro),
						testFile });
			}
		}

		return data;
	}

	/** The bistro object to test. */
	@Parameter(0)
	public IBistro bistro;

	/** The test file to scrape. */
	@Parameter(1)
	public String testFile;

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

		String referenceFile = readTestFile(this.testFile + ".json"); //$NON-NLS-1$
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Assert.assertEquals(
				referenceFile,
				gson.toJson(this.bistro));
	}

	/** Reads a test file and fails of reading is not possible. */
	private String readTestFile(String path)
	{
		try (InputStream stream = getClass().getResourceAsStream("/" + path)) //$NON-NLS-1$
		{
			return new BufferedReader(
					new InputStreamReader(stream)).lines().collect(
							Collectors.joining("\n")); //$NON-NLS-1$
		}
		catch (IOException e)
		{
			Assert.fail("Could not read test file: " + e.getMessage()); //$NON-NLS-1$
			return null;
		}
	}

	/**
	 * @return The test resources for the given bistro.
	 */
	private static Set<String> getTestFiles(
			Class<? extends IBistro> bistro)
	{
		return new Reflections(
				bistro.getPackage().getName(),
				new ResourcesScanner()).getResources(
						HTML_FILE_PATTERN);
	}
}