package modele;

import java.sql.SQLException;
import java.util.ArrayList;

public class BaseDeDonnees {

	/**
	 * Le nom de la base de données
	 */
	private String nom;
	/**
	 * Le serveur sur lequel la base de données est hebergé
	 */
	private Serveur serveur;
	/**
	 * La liste des tables dans la base de donnés
	 */
	private ArrayList<Table> listeTable;
	
	/**
	 * Constructeur
	 * @param nom Nom de la base de données
	 * @param tables qui seront dans la base de données
	 */
	public BaseDeDonnees(String nom, String nomUtilisateur, String motDePasse, ArrayList<Table> tables){
		this.nom = nom;
		this.listeTable = listeTable;
		this.serveur = new Serveur(this.nom, nomUtilisateur, motDePasse, this);
		this.listeTable = new ArrayList<Table>();
		/**
		try {
			this.serveur.creerBaseDeDonnees();
		} catch (CustomException e) {
			Util.log(e.getMessage());
		}
		*/
	}
		
	/**
	 * Méthode qui retourne la liste des tables de la base de donnés
	 * @return Liste des tables de la base de données
	 */
	public ArrayList<Table> getListeTable(){
		return this.listeTable;
	}
	
	/**
	 * Permet d'accéder au nom de la base
	 * @return Le nom de la base
	 */
	public String getName(){
		return this.nom;
	}
	
	/**
	 * Définis le nom de la base	
	 * @param nom Le nouveau nom de la base
	 */
	public void setName(String nom){
		this.nom = nom;
	}
	
	/**
	 * Permet d'ajouter une table à la base
	 * @param table La table qui sera ajouté à la base de donnés
	 */
	public void ajouterTable(Table table){
		try {
			this.serveur.creerTable(table.getNom(), table.getListeColonnes());
			
			this.listeTable.add(table);
		} catch (CustomException e) {
			Util.logErreur(e.getMessage());
		}
	}
	
	/**
	 * Accéder au serveur de la base de données
	 * @return
	 */
	public Serveur getServeur(){
		return this.serveur;
	}
	
	/**
	 * Supprimer la base de données courante
	 */
	public void supprimerBDD(){
		try {
			this.serveur.supprimerBaseDeDonnes();
		} catch (CustomException e) {
			Util.log(e.getMessage());
		}
	}
	
	public Table getTable(String nom){
		Table table = null;
		for(Table t : this.listeTable){
			if(t.getNom().equals(nom)){
				table=t;
			}
		}
		return table;
	}
	
	public void chargerBDD() throws CustomException, SQLException{
		this.listeTable = this.serveur.getListeTables();
	}
	
	public void setListeTables(ArrayList<Table> tables){
		this.listeTable = tables;
	}
}
