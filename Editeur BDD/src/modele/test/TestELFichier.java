package modele.test;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import modele.ELFichier;

public class TestELFichier {

	@After
	public void tearDown() throws Exception {
		File file = new File(ELFichier.getRacine()+"test.txt");
		if(file.exists())
			file.delete();
	}
	
	@Test
	public void testChargerValeurSetCle() {
		ELFichier.setCle("test.txt", "test", "1");
		assertTrue(ELFichier.chargerValeur("test.txt", "test").equals("1"));
	}

}
