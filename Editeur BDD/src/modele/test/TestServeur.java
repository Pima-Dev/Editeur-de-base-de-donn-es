package modele.test;

import java.io.File;
import java.sql.SQLException;

import static org.junit.Assert.*;
import org.junit.Test;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import org.junit.After;
import org.junit.Assert;
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
import vue.Fenetre;

public class TestServeur {

	private Serveur serveur;
	private BaseDeDonnees BDD;

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
		Table table1 = new Table(this.BDD, "table1");
		Table table2 = new Table(this.BDD, "table2");
		Table table3 = new Table(this.BDD, "table3");

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
		if (this.serveur.bddExiste()) {
			this.BDD.supprimerBDD();
		}
	}

	@Test
	public void testServeur() {
		assertEquals(this.BDD, this.serveur.getBDD());
		assertEquals(this.BDD.getNomUtilisateur(), this.serveur.getNomUtilisateur());
		assertEquals(this.BDD.getMotDePasse(), this.serveur.getMotDePasse());
		assertEquals(this.BDD.getPort(), this.serveur.getPort());
		assertEquals(this.BDD.getUrlBDD(), this.serveur.getUrl());
	}

	@Test
	public void testFermerConnexion() {
		try {
			this.serveur.fermerConnexion();
			assertTrue(this.serveur.getConnect().isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testExecuterCode() {

		try {
			this.serveur.executerCode("INSERT INTO table1 VALUES(1,2)");
			assertTrue(false);
		} catch (MySQLSyntaxErrorException e) {
			assertTrue(true);
		} catch (SQLException e) {
			assertTrue(false);
		} catch (CustomException e) {
			assertTrue(e.getMessage().contains("Duplicate entry"));
		}

		try {
			this.serveur.executerCode("TEST CODE NON VALIDE");
			assertTrue(false);
		} catch (MySQLSyntaxErrorException e) {
			assertTrue(true);
		} catch (SQLException e) {
			assertTrue(false);
		} catch (CustomException e) {
			assertTrue(e.getMessage().contains("Le code SQL n'est pas correcte syntaxiquement"));
		}
		
		try {
			this.serveur.executerCode("CREATE TABLE table1 (colonne1 INTEGER PRIMARY KEY)");
			assertTrue(false);
		} 
		catch (MySQLSyntaxErrorException e) {
			assertTrue(true);
		}
		catch (SQLException e) {
			assertTrue(false);
		} catch (CustomException e) {
			System.out.println(e.getMessage());
			assertTrue(e.getMessage().contains("Ce nom de table est déjà utilisé"));
		}
		
		try {
			this.serveur.executerCode("INSERT INTO table1 VALUES(3,2)");
			assertTrue(true);
		} catch (SQLException e) {
			assertTrue(false);
		} catch (CustomException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testExecuteRequete() {
		
	}

	@Test
	public void testImporterDepuisBaseDeDonnees() {
	}

	@Test
	public void testCreerBaseDeDonnees() {
	}

	@Test
	public void testSupprimerBaseDeDonnes() {
	}

	@Test
	public void testCreerTable() {
	}

	@Test
	public void testSupprimerTable() {
	}

	@Test
	public void testInsererTuples() {
	}

	@Test
	public void testSupprimerTupleById() {
	}

	@Test
	public void testEditerTuple() {
	}

	@Test
	public void testGetListeTables() {
	}

	@Test
	public void testGetListeBDD() {
	}

	@Test
	public void testTableExiste() {
	}

	@Test
	public void testAjouterColonne() {
	}

	@Test
	public void testModifierContrainte() {
	}

	@Test
	public void testAjouterFKColExistente() {
	}

	@Test
	public void testSupprimerColonne() {
	}

	@Test
	public void testExecuterCodeConsole() {
	}

	@Test
	public void testExecuteRequeteConsole() {
	}

}
