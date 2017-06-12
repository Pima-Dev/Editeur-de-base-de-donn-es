package modele;

import java.util.ArrayList;

public class BaseDeDonnees {
	test
	/**
	 * Le nom de la base de données
	 */
	private String nom;
	/**
	 * Le serveur sur lequel la base de donné est hebergé
	 */
	private Serveur baseDeDonees;
	/**
	 * La liste des tables dans la base de donnés
	 */
	private ArrayList<Table> listeTable;
	
	/**
	 * Constructeur
	 * @param nom Nom de la base de donnés
	 * @param tables qui seront dans la base de donné
	 */
	public BaseDeDonnees(String nom, ArrayList<Table> tables){
		this.nom = nom;
		this.listeTable = listeTable;
	}
		
	/**
	 * Méthode qui retourne la liste des tables de la base de donnés
	 * @return Liste des tables de la base de donnés
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
	 * @param name Le nouveau nom de la base
	 */
	public void setName(String name){}
	
	/**
	 * Permet d'ajouter une table Ã  la base
	 * @param table La table qui sera ajouté Ã  la base de donnés
	 */
	public void ajouterTable(Table table){}
	
}
