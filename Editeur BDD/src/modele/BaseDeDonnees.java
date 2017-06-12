package modele;

import java.util.ArrayList;

public class BaseDeDonnees {
	
	/**
	 * Le nom de la base de donnée
	 */
	private String nom;
	/**
	 * Le serveur sur lequel la base de donnée est hebergé
	 */
	private Serveur baseDeDonees;
	/**
	 * La liste des tables dans la base de données
	 */
	private ArrayList<Table> listeTable;
	
	/**
	 * Constructeur
	 * @param nom Nom de la base de données
	 * @param tables qui seront dans la base de donnée
	 */
	public BaseDeDonnees(String nom, ArrayList<Table> tables){
		this.nom = nom;
		this.listeTable = listeTable;
	}
		
	/**
	 * Méthode qui retourne la liste des tables de la base de données
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
	 * @param name Le nouveau nom de la base
	 */
	public void setName(String name){}
	
	/**
	 * Permet d'ajouter une table à la base
	 * @param table La table qui sera ajouté à la base de données
	 */
	public void ajouterTable(Table table){}
	
}
