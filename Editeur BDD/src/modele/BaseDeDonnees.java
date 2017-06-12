package modele;

import java.util.ArrayList;

public class BaseDeDonnees {
	
	/**
	 * Le nom de la base de donn�es
	 */
	private String nom;
	/**
	 * Le serveur sur lequel la base de donn� est heberg�
	 */
	private Serveur baseDeDonees;
	/**
	 * La liste des tables dans la base de donn�s
	 */
	private ArrayList<Table> listeTable;
	
	/**
	 * Constructeur
	 * @param nom Nom de la base de donn�s
	 * @param tables qui seront dans la base de donn�
	 */
	public BaseDeDonnees(String nom, ArrayList<Table> tables){
		this.nom = nom;
		this.listeTable = listeTable;
	}
		
	/**
	 * M�thode qui retourne la liste des tables de la base de donn�s
	 * @return Liste des tables de la base de donn�s
	 */
	public ArrayList<Table> getListeTable(){
		return this.listeTable;
	}
	
	/**
	 * Permet d'acc�der au nom de la base
	 * @return Le nom de la base
	 */
	public String getName(){
		return this.nom;
	}
	
	/**
	 * D�finis le nom de la base	
	 * @param name Le nouveau nom de la base
	 */
	public void setName(String name){}
	
	/**
	 * Permet d'ajouter une table à la base
	 * @param table La table qui sera ajout� à la base de donn�s
	 */
	public void ajouterTable(Table table){}
	
}
