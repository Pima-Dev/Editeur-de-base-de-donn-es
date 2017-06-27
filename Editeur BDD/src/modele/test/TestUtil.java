package modele.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.Util;

public class TestUtil {

	@Test
	public void testIsValidDate() {
		String date = "04-20-2016";
		assertTrue(!Util.isValidDate(date));
		
		date = "04/02/2016";
		assertTrue(!Util.isValidDate(date));
		
		date = "04-02-2016";
		assertTrue(Util.isValidDate(date));
	}

	@Test
	public void testIsInteger() {
		String nombre = "test";
		assertTrue(!Util.isInteger(nombre));
		
		nombre = "1.2";
		assertTrue(!Util.isInteger(nombre));
		
		nombre = "1";
		assertTrue(Util.isInteger(nombre));
	}

	@Test
	public void testIsDouble() {
		String nombre = "test";
		assertTrue(!Util.isDouble(nombre));
		
		nombre = "2";
		assertTrue(Util.isDouble(nombre));
		
		nombre = "1.3";
		assertTrue(Util.isDouble(nombre));
	}

}
