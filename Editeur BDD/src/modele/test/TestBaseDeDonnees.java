package modele.test;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.BaseDeDonnees;
import modele.Colonne;
import modele.Contrainte;
import modele.CustomException;
import modele.ELFichier;
import modele.Serveur;
import modele.Session;
import modele.Table;
import modele.TypeContrainte;
import modele.TypeDonnee;

public class TestBaseDeDonnees {

	private Serveur serveur;
	private BaseDeDonnees BDD;
	private Table table1;
	private Table table2;
	private Table table3;
	
	@Before
	public void setUp() throws Exception {
		this.BDD = new BaseDeDonnees("bdddetest", "root", "azerty", null, "", 1);
		this.BDD.setSession(new Session("Pierre"));
		this.serveur = new Serveur(this.BDD);
		File file = new File(
				ELFichier.getRacine() + this.BDD.getSession().getNom() + "/listeBDD/" + this.BDD.getNomBDD());
		if (file.exists())
			file.delete();

		if (this.serveur.bddExiste()) {
			this.BDD.supprimerBDD();
		}
		this.table1 = new Table(this.BDD, "table1");
		this.table2 = new Table(this.BDD, "table2");
		this.table3 = new Table(this.BDD, "table3");

		Colonne<Integer> colonne1 = new Colonne<Integer>("colonne1", TypeDonnee.INTEGER);
		colonne1.ajouterContrainte(new Contrainte(TypeContrainte.PRIMARYKEY, null));
		colonne1.ajouterValeur(1);
		colonne1.ajouterValeur(2);

		Colonne<Integer> colonne2 = new Colonne<Integer>("colonne2", TypeDonnee.INTEGER);
		colonne2.ajouterValeur(1);
		colonne2.ajouterValeur(2);

		Colonne<Integer> colonne3 = new Colonne<Integer>("colonne3", TypeDonnee.INTEGER);
		colonne3.ajouterContrainte(new Contrainte(TypeContrainte.PRIMARYKEY, null));
		colonne3.ajouterValeur(1);
		colonne3.ajouterValeur(2);

		Colonne<Integer> colonne4 = new Colonne<Integer>("colonne4", TypeDonnee.INTEGER);
		colonne4.ajouterValeur(1);
		colonne4.ajouterValeur(2);

		Colonne<Integer> colonne5 = new Colonne<Integer>("colonne5", TypeDonnee.INTEGER);
		colonne5.ajouterContrainte(new Contrainte(TypeContrainte.PRIMARYKEY, null));
		colonne5.ajouterValeur(1);
		colonne5.ajouterValeur(2);

		Colonne<Integer> colonne6 = new Colonne<Integer>("colonne6", TypeDonnee.INTEGER);
		colonne6.ajouterValeur(1);
		colonne6.ajouterValeur(2);

		table1.ajouterAttribut(colonne1);
		table1.ajouterAttribut(colonne2);

		table2.ajouterAttribut(colonne3);
		table2.ajouterAttribut(colonne4);

		table3.ajouterAttribut(colonne5);
		table3.ajouterAttribut(colonne6);

		this.BDD.creerBDD();
		this.BDD.ajouterTable(table1);
		this.BDD.ajouterTable(table2);
		this.BDD.ajouterTable(table3);
	}

	@After
	public void tearDown() throws Exception {
		this.serveur.fermerConnexion();
		if (this.serveur.bddExiste()) {
			this.BDD.supprimerBDD();
		}
	}

	@Test
	public void testBaseDeDonnees() {
		assertTrue(this.BDD.getNomBDD().equals("bdddetest"));
		assertTrue(this.BDD.getNomUtilisateur().equals("root"));
		assertTrue(this.BDD.getMotDePasse().equals("azerty"));
		assertTrue(this.BDD.getUrlBDD().equals(""));
		assertTrue(this.BDD.getPort() == 1);
	}

	@Test
	public void testAjouterTable() {
		try {
			Table table4 = new Table(this.BDD, "table4");
			Colonne<Integer> colonne7 = new Colonne<Integer>("colonne7", TypeDonnee.INTEGER);
			colonne7.ajouterValeur(1);
			colonne7.ajouterValeur(2);
			table4.ajouterAttribut(colonne7);
			this.BDD.ajouterTable(table4);
			assertTrue(this.BDD.getListeTable().size() == 4);
		} catch (CustomException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetTable() {
		Table table = this.BDD.getTable(this.table3.getNom());
		assertTrue(this.table3.equals(table));
	}

	@Test
	public void testChargerBDD() {
		this.BDD.setListeTables(null);
		try {
			this.BDD.chargerBDD();
			assertTrue(this.BDD.getListeTable().size() == 3);
		} catch (CustomException e) {
			assertTrue(false);
		} catch (SQLException e) {
			assertTrue(false);
		}
		
	}

	@Test
	public void testCreerBDD() {
		try {
			this.BDD.creerBDD();
		} catch (CustomException e) {
			assertTrue(e.getMessage().contains("Cette base de données est déjà existente"));
		}
	}
	
	@Test
	public void testFormatTitres() {
		String[] res = this.BDD.formatTitres(this.table3.getNom());
		assertTrue(res[0].equals("colonne5(INTEGER)"));
		assertTrue(res[1].equals("colonne6(INTEGER)"));
	}

	@Test
	public void testFormatValeurs() {
		String[][] res = this.BDD.formatValeurs(this.table3.getNom());
		assertTrue(res.length == 2);
		assertTrue(res[0][0].equals("1"));
		assertTrue(res[1][0].equals("2"));
	}

}
