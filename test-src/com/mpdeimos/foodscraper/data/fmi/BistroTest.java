/**
 * 
 */
package com.mpdeimos.foodscraper.data.fmi;

import com.mpdeimos.foodscraper.data.BistroTestBase;

/**
 * Test case for {@link Bistro}.
 * 
 * @author mpdeimos
 */
public class BistroTest extends BistroTestBase
{
	/** {@inheritDoc} */
	@Override
	protected Bistro createBistro()
	{
		return new Bistro();
	}
}
