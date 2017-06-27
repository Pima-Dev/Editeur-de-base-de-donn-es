package modele.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.channels.AsynchronousSocketChannel;
import java.sql.SQLException;
import java.util.ArrayList;

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

public class TestTable {

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
	public void testTable() {
		assertTrue(this.BDD.equals(this.table3.getBDD()));
		assertTrue(this.table3.getNom().equals("table3"));
	}

	@Test
	public void testAjouterAttribut() {
		try {
			Table table4 = new Table(this.BDD, "table4");
			Colonne<Integer> colonne7 = new Colonne<Integer>("colonne7", TypeDonnee.INTEGER);
			colonne7.ajouterValeur(1);
			colonne7.ajouterValeur(2);
			table4.ajouterAttribut(colonne7);
			assertTrue(table4.getListeColonnes().size() == 1);
		} catch (CustomException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testInsererTuple() {
		ArrayList<Object> tuple = new ArrayList<Object>();
		tuple.add("test");
		tuple.add("test2");
		try {
			this.table3.insererTuple(tuple, true);
		} catch (CustomException e) {
			assertTrue(e.getMessage().contains("est de type CHAR alors qu'il est attendu un type INTEGER"));
		}
		
		tuple = new ArrayList<Object>();
		tuple.add(6);
		try {
			this.table3.insererTuple(tuple, true);
		} catch (CustomException e) {
			assertTrue(e.getMessage().contains("Le tuple est incomplet ou contient trop d'attribut"));
		}
		
		tuple = new ArrayList<Object>();
		tuple.add(6);
		tuple.add(7);
		tuple.add(8);
		try {
			this.table3.insererTuple(tuple, true);
		} catch (CustomException e) {
			assertTrue(e.getMessage().contains("Le tuple est incomplet ou contient trop d'attribut"));
		}
		
		tuple = new ArrayList<Object>();
		tuple.add(6);
		tuple.add(7);
		try {
			this.table3.insererTuple(tuple, true);
			assertTrue(this.table3.getColonne("colonne6").getListeValeurs().size() == 3);
		} catch (CustomException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testSupprimerTupleById() {
		try {
			try {
				this.table3.supprimerTupleById(10);
				assertTrue(false);
			} catch (CustomException e) {
				assertTrue(e.getMessage().contains("Il n'existe pas de tuple avec"));
			}
		} catch (SQLException e1) {
			assertTrue(false);
		}
		
		try {
			try {
				this.table3.supprimerTupleById(1);
				assertTrue(this.table3.getListeColonnes().get(0).getListeValeurs().size() == 1);
			} catch (CustomException e) {
				assertTrue(false);
			}
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testEditerTuple() {
		try {
			try {
				this.table3.editerTuple(1, "existepas", 3);
				assertTrue(false);
			} catch (CustomException e) {
				assertTrue(e.getMessage().contains("n'existe pas"));
			}
		} catch (SQLException e1) {
			assertTrue(false);
		}
		
		try {
			try {
				this.table3.editerTuple(10, "colonne6", 3);
				assertTrue(false);
			} catch (CustomException e) {
				assertTrue(e.getMessage().contains("Il n'existe pas de tuple avec"));
			}
		} catch (SQLException e1) {
			assertTrue(false);
		}
		
		try {
			try {
				this.table3.editerTuple(1, "colonne6", "test");
				assertTrue(false);
			} catch (CustomException e) {
				assertTrue(e.getMessage().contains("ne peut pas être inséré dans la colonne"));
			}
		} catch (SQLException e1) {
			assertTrue(false);
		}
		
		try {
			try {
				this.table3.editerTuple(1, "colonne6", 3);
				assertTrue(this.table3.getColonne("colonne6").getListeValeurs().get(0).equals(3));
			} catch (CustomException e) {
				assertTrue(false);
			}
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testSupprimerTable(){
		try {
			this.table3.supprimerTable();
			assertTrue(this.BDD.getListeTable().size() == 2);
		} catch (CustomException e) {
			assertTrue(false);
		}
		
	}
	
	@Test
	public void testAjouterColonneATableDejaExistente() {
		try {
			try {
				Colonne col = new Colonne<>("uneColonne", TypeDonnee.INTEGER);
				col.ajouterContrainte(new Contrainte(TypeContrainte.NOTNULL, null));
				this.table3.ajouterColonneATableDejaExistente(col, 3);
				assertTrue(this.table3.getListeColonnes().size() == 3);
			} catch (CustomException e) {
				assertTrue(false);
			}
		} catch (SQLException e) {
			assertTrue(false);
		}
		
	}

	@Test
	public void testModifierContraintes() {

		try {
			ArrayList<Contrainte> contraintes = new ArrayList<Contrainte>();
			Contrainte c = new Contrainte(TypeContrainte.NOTNULL, null);
			contraintes.add(c);
			this.table3.modifierContraintes(contraintes, this.table3.getColonne("colonne6"));
			assertTrue(this.table3.getColonne("colonne6").getListeContraintes().size() == 1);
		} catch (CustomException e) {
			assertTrue(false);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testSupprimerColonne() {
		try {
			this.table3.supprimerColonne("colonne6");
			assertTrue(this.table3.getListeColonnes().size() == 1);
			assertTrue(this.table3.getListeColonnes().get(0).getNom().equals("colonne5"));
		} catch (SQLException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (CustomException e) {
			assertTrue(false);
		}
	}

}
