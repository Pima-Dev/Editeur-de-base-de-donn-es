package modele.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

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

public class TestServeur {

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
		} catch (MySQLSyntaxErrorException e) {
			assertTrue(true);
		} catch (SQLException e) {
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

		ResultSet rs;
		try {
			rs = this.serveur.executeRequete("PAS CORRECTE");
			assertTrue(false);

		} catch (MySQLSyntaxErrorException e1) {
			assertTrue(true);
		} catch (CustomException e1) {
			assertTrue(e1.getMessage().contains("Le code SQL n'est pas correcte syntaxiquement"));
		} catch (SQLException e1) {
			assertTrue(false);
		}

		try {
			rs = this.serveur.executeRequete("SELECT * FROM table1");
			int i = 0;
			while (rs.next()) {
				i++;
			}
			assertEquals(i, 2);
		} catch (CustomException e) {
			assertTrue(false);
		} catch (SQLException e) {
			assertTrue(false);
		}

	}

	@Test
	public void testCreerBaseDeDonnees() {
		try {
			this.BDD.supprimerBDD();
			if (this.serveur.bddExiste()) {
				assertTrue(false);
			}
			this.BDD.creerBDD();
			if (this.serveur.bddExiste()) {
				assertTrue(true);
			}
		} catch (CustomException e) {
			assertTrue(false);
		} catch (SQLException e) {
			assertTrue(false);
			;
		}

	}

	@Test
	public void testSupprimerBaseDeDonnes() {
		try {
			this.BDD.supprimerBDD();
			if (!this.serveur.bddExiste()) {
				assertTrue(true);
			}
		} catch (CustomException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			assertTrue(false);
			;
		}
	}

	@Test
	public void testCreerTable() {
		Table table4 = new Table(this.BDD, "table4");
		Colonne<Integer> colonne7 = new Colonne<Integer>("colonne7", TypeDonnee.INTEGER);
		try {
			try {
				colonne7.ajouterValeur(1);
				colonne7.ajouterValeur(2);
				table4.ajouterAttribut(colonne7);
				this.BDD.ajouterTable(table4);
				assertTrue(this.BDD.tableExiste(table4.getNom()));
			} catch (SQLException e) {
				assertTrue(false);
			}
		} catch (CustomException e) {
			assertTrue(false);
		}

		Table table5 = new Table(this.BDD, "table5");
		colonne7 = new Colonne<Integer>("colonne7", TypeDonnee.INTEGER);

		try {
			colonne7.ajouterValeur(1);
			colonne7.ajouterValeur(2);
			colonne7.ajouterContrainte(new Contrainte(TypeContrainte.NOTNULL, null));
			table5.ajouterAttribut(colonne7);
			this.BDD.ajouterTable(table5);
			ArrayList<Object> tuple = new ArrayList<Object>();
			tuple.add(null);
			table5.insererTuple(tuple, false);
			assertTrue(false);
		} catch (CustomException e) {
			assertTrue(e.getMessage().contains("1 tuple inséré ne respecte pas les contraintes de la table"));

		}
	}

	@Test
	public void testSupprimerTable() {
		try {
			try {
				assertTrue(this.serveur.bddExiste());
			} catch (SQLException e1) {
				assertTrue(false);
			}
			this.serveur.supprimerBaseDeDonnes();
			try {
				assertTrue(this.serveur.bddExiste() == false);
			} catch (SQLException e) {
				assertTrue(false);
			}
		} catch (CustomException e) {
			assertTrue(false);
		}

	}

	@Test
	public void testInsererTuples() {
		try {
			this.serveur.insererTuples(table3.getNom(), new ArrayList<Colonne>());
			assertTrue(false);
		} catch (CustomException e) {
			assertTrue(e.getMessage().contains("Il n'y a aucune valeur à ajouter"));
		}

		ArrayList<Colonne> tuple = new ArrayList<Colonne>();
		Colonne<Integer> col1 = new Colonne<Integer>("col1", TypeDonnee.INTEGER);
		Colonne<Integer> col2 = new Colonne<Integer>("col1", TypeDonnee.INTEGER);
		try {
			col1.ajouterValeur(1);
			col1.ajouterValeur(2);
			col2.ajouterValeur(1);
			tuple.add(col1);
			tuple.add(col2);
			this.serveur.insererTuples(table3.getNom(), tuple);
			assertTrue(false);
		} catch (CustomException e) {
			assertTrue(e.getMessage()
					.contains("Toutes les colonnes doivent contenir un même nombre de valeurs à ajouter"));
		}

		tuple = new ArrayList<Colonne>();
		col1 = new Colonne<Integer>("col1", TypeDonnee.INTEGER);
		col2 = new Colonne<Integer>("col1", TypeDonnee.INTEGER);
		try {
			col1.ajouterValeur(3);
			col2.ajouterValeur(4);
			tuple.add(col1);
			tuple.add(col2);
			this.serveur.insererTuples(table3.getNom(), tuple);
			try {
				ResultSet rs = this.serveur.executeRequete("SELECT * FROM " + table3.getNom());
				int i = 0;
				while (rs.next())
					i++;
				assertTrue(i == 3);
			} catch (SQLException e) {
				assertTrue(false);
			}

		} catch (CustomException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testSupprimerTupleById() {
		try {
			try {
				this.serveur.supprimerTupleById(this.table3, 1);
				ResultSet rs = this.serveur
						.executeRequete("SELECT * FROM " + this.table3.getNom() + " WHERE colonne5 = 1");
				assertTrue(!rs.next());
			} catch (SQLException e) {
				assertTrue(false);
			}
		} catch (CustomException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testEditerTuple() {
		try {
			try {
				this.serveur.editerTuple(this.table3, 1, "colonne6", 10);
				ResultSet rs = this.serveur
						.executeRequete("SELECT colonne6 FROM " + this.table3.getNom() + " WHERE colonne5 = 1");
				if (!rs.next()) {
					assertTrue(false);
				}
				assertTrue(rs.getInt("colonne6") == 10);
			} catch (SQLException e) {
				e.printStackTrace();
				assertTrue(false);
			}
		} catch (CustomException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetListeTables() {
		try {
			ArrayList<Table> res = this.serveur.getListeTables();
			assertTrue(res.size() == 3);
			for (Table table : res) {
				assertTrue(table.getListeColonnes().size() == 2);
			}
		} catch (CustomException e) {
			assertTrue(false);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetListeColonnes() {
		try {
			ArrayList<Colonne> colonnes = this.serveur.getListeColonnes(this.table3.getNom());
			boolean pk = false;
			for (Colonne col : colonnes) {
				assertTrue(col.getListeValeurs().size() == 2);
				for (Contrainte c : (ArrayList<Contrainte>) col.getListeContraintes()) {
					if (c.getContrainteType() == TypeContrainte.PRIMARYKEY) {
						pk = true;
					}
				}
			}
			assertTrue(pk);
		} catch (CustomException e) {
			assertTrue(false);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetListeBDD() {
		try {
			ArrayList<String> res = this.serveur.getListeBDD();
			assertTrue(res.contains("bdddetest"));
		} catch (CustomException e) {
			assertTrue(false);
		}

	}

	@Test
	public void testTableExiste() {
		try {
			assertTrue(!this.serveur.tableExiste("existepas"));
			assertTrue(this.serveur.tableExiste(this.table3.getNom()));
		} catch (CustomException e) {
			assertTrue(false);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testBddExiste() {
		try {
			assertTrue(this.serveur.bddExiste());
			this.BDD.supprimerBDD();
			assertTrue(!this.serveur.bddExiste());
		} catch (CustomException e) {
			assertTrue(false);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testAjouterColonne() {
		try {
			try {
				Colonne col = new Colonne<>("uneColonne", TypeDonnee.INTEGER);
				col.ajouterContrainte(new Contrainte(TypeContrainte.NOTNULL, null));
				this.serveur.ajouterColonne(this.table3.getNom(), 1, col);
				ResultSet rs = this.serveur.executeRequete("SHOW COLUMNS from " + this.table3.getNom());
				int i = 0;
				while (rs.next()) {
					i++;
				}
				assertTrue(i == 3);
			} catch (CustomException e) {
				assertTrue(false);
			}
		} catch (SQLException e) {
			assertTrue(false);
		}

	}

	@Test
	public void testModifierContrainte() {
		try {
			Colonne<Integer> col = new Colonne<Integer>("colonne6", TypeDonnee.INTEGER);
			try {
				col.ajouterContrainte(new Contrainte(TypeContrainte.NOTNULL, null));
				this.serveur.modifierContrainte(this.table3.getNom(), col);
				ArrayList<Colonne> tuple = new ArrayList<Colonne>();
				Colonne<Integer> col1 = new Colonne<Integer>("col1", TypeDonnee.INTEGER);
				Colonne<Integer> col2 = new Colonne<Integer>("col1", TypeDonnee.INTEGER);

				col1.ajouterValeur(null);
				col1.ajouterValeur(5);
				col2.ajouterValeur(10);
				col2.ajouterValeur(11);
				tuple.add(col1);
				tuple.add(col2);
				this.serveur.insererTuples(table3.getNom(), tuple);
				assertTrue(false);
			} catch (CustomException e) {
				assertTrue(e.getMessage().contains("1 tuple inséré ne respecte pas les contraintes de la table"));
			}
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testAjouterFKColExistente() {
		try {
			this.serveur.ajouterFKColExistente(this.table3.getNom(), "colonne6", new Contrainte(TypeContrainte.NOTNULL, null));
			assertTrue(false);
		} catch (SQLException e) {
			assertTrue(false);
		} catch (CustomException e) {
			assertTrue(e.getMessage().contains("La contrainte à ajouter est null ou n'est pas une clé étrangère"));
		}
		
		try {
			this.serveur.ajouterFKColExistente(this.table3.getNom(), "colonne6", new Contrainte(TypeContrainte.REFERENCEKEY, null));
			assertTrue(false);
		} catch (SQLException e) {
			assertTrue(false);
		} catch (CustomException e) {
			assertTrue(e.getMessage().contains("La table à référencer n'existe pas"));
		}
		
		try {
			this.serveur.ajouterFKColExistente(this.table3.getNom(), "colonne6", new Contrainte(TypeContrainte.REFERENCEKEY, this.table1));
			ArrayList<Colonne> tuple = new ArrayList<Colonne>();
			Colonne<Integer> col1 = new Colonne<Integer>("col1", TypeDonnee.INTEGER);
			Colonne<Integer> col2 = new Colonne<Integer>("col1", TypeDonnee.INTEGER);

			col1.ajouterValeur(6);
			col1.ajouterValeur(5);
			col2.ajouterValeur(10);
			col2.ajouterValeur(11);
			tuple.add(col1);
			tuple.add(col2);
			this.serveur.insererTuples(table3.getNom(), tuple);
			assertTrue(false);
		} catch (SQLException e) {
			assertTrue(false);
		} catch (CustomException e) {
			assertTrue(e.getMessage().contains("1 tuple inséré ne respecte pas les contraintes de la table"));
		}
	}

	@Test
	public void testSupprimerColonne() {
		try {
			this.serveur.supprimerColonne(this.table3.getNom(), "colonne6");
			ResultSet rs = this.serveur.executeRequete("SHOW COLUMNS from " + this.table3.getNom());
			int i = 0;
			while (rs.next()) {
				i++;
			}
			assertTrue(i == 1);
		} catch (SQLException e) {
			assertTrue(false);
		} catch (CustomException e) {
			assertTrue(false);
		}
	}

}
