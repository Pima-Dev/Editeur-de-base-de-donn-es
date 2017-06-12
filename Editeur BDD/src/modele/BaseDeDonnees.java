package modele;

import java.util.ArrayList;

public class BaseDeDonnees {
<<<<<<< HEAD
=======
	
>>>>>>> 018a7b06348f999563cd1f6ed3afe8b794d021c2
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
	public BaseDeDonnees(String nom, ArrayList<Table> tables){
		this.nom = nom;
		this.listeTable = listeTable;
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
		
	}
	
	public Serveur getServeur(){
		return this.serveur;
	}
	
}
