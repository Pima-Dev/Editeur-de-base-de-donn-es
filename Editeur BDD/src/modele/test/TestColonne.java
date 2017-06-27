package modele.test;

import static org.junit.Assert.*;

import java.io.File;

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

public class TestColonne {

	private Serveur serveur;
	private BaseDeDonnees BDD;
	private Table table1;
	private Table table2;
	private Table table3;
	private Colonne<Integer> colonne1;
	private Colonne<Integer> colonne2;
	private Colonne<Integer> colonne3;
	private Colonne<Integer> colonne4;
	private Colonne<Integer> colonne5;
	private Colonne<Integer> colonne6;
	
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

		this.colonne1 = new Colonne<Integer>("colonne1", TypeDonnee.INTEGER);
		this.colonne1.ajouterContrainte(new Contrainte(TypeContrainte.PRIMARYKEY, null));
		this.colonne1.ajouterValeur(1);
		this.colonne1.ajouterValeur(2);

		this.colonne2 = new Colonne<Integer>("colonne2", TypeDonnee.INTEGER);
		this.colonne2.ajouterValeur(1);
		this.colonne2.ajouterValeur(2);

		this.colonne3 = new Colonne<Integer>("colonne3", TypeDonnee.INTEGER);
		this.colonne3.ajouterContrainte(new Contrainte(TypeContrainte.PRIMARYKEY, null));
		this.colonne3.ajouterValeur(1);
		this.colonne3.ajouterValeur(2);

		this.colonne4 = new Colonne<Integer>("colonne4", TypeDonnee.INTEGER);
		this.colonne4.ajouterValeur(1);
		this.colonne4.ajouterValeur(2);

		this.colonne5 = new Colonne<Integer>("colonne5", TypeDonnee.INTEGER);
		this.colonne5.ajouterContrainte(new Contrainte(TypeContrainte.PRIMARYKEY, null));
		this.colonne5.ajouterValeur(1);
		this.colonne5.ajouterValeur(2);

		this.colonne6 = new Colonne<Integer>("colonne6", TypeDonnee.INTEGER);
		this.colonne6.ajouterValeur(1);
		this.colonne6.ajouterValeur(2);

		this.table1.ajouterAttribut(colonne1);
		this.table1.ajouterAttribut(colonne2);

		this.table2.ajouterAttribut(colonne3);
		this.table2.ajouterAttribut(colonne4);

		this.table3.ajouterAttribut(colonne5);
		this.table3.ajouterAttribut(colonne6);

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
	public void testColonne() {
		assertTrue(this.colonne5.getNom().equals("colonne5"));
		assertTrue(this.colonne5.getTypeDonnees().equals(TypeDonnee.INTEGER));
	}

	@Test
	public void testAjouterContrainte() {
		try {
			this.colonne5.ajouterContrainte(new Contrainte(TypeContrainte.PRIMARYKEY, null));
			assertTrue(false);
		} catch (CustomException e) {
			assertTrue(e.getMessage().contains("est déjà présente pour cette attribut"));
		}
		
		try {
			this.colonne5.ajouterContrainte(new Contrainte(TypeContrainte.REFERENCEKEY, null));
			assertTrue(false);
		} catch (CustomException e) {
			assertTrue(e.getMessage().contains("La Table que l'attribut référence ne peut pas être null"));
		}
		
		try {
			this.colonne5.ajouterContrainte(new Contrainte(TypeContrainte.NOTNULL, null));
			assertTrue(this.colonne5.getListeContraintes().size() == 2);
		} catch (CustomException e) {
			assertTrue(false);
		}
	}

}
