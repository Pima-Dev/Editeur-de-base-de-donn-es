package modele;

import java.util.ArrayList;

public class Table {

	/**
	 * Le nom de la table
	 */
	private String nom;
	
	/** 
	 * Initialise le nom
	 * @param nom Le nom de la table
	 */
	public Table(String nom){
		this.nom = nom;
	}
	
	/**
	 * Ajouter une colonne
	 * @param nomColonne Nom de la colonne
	 * @param type Type de la données
	 */
	protected void ajouterColonne(String nomColonne, TypeDonnee type){
		
	}
	
	/**
	 * Inserer un tuple dans la table
	 * @param attributs Liste des attributs
	 */
	public void insererTuple(ArrayList<Colonne> attributs){
		
	}
	
	/**
	 * Editer une valeur dans la table. 
	 * @param index l'index 
	 * @param colonne La colonne concerné
	 * @param strValeur La nouvelle valeur qui sera ensuite tranformé 
	 */
	public void editerValeur(int index, int colonne, String strValeur){
		
	}
	
	/**
	 * Ajouter une contrainte
	 * @param colonne La colonne concerné
	 * @param contrainte La contrainte
	 */
	public void AjouterContrainte(int colonne, Contrainte contrainte){
		
	}
	
	/**
	 * Récupérer l'attribut de la colonne
	 * @param colonne La colonne
	 * @return L'attribut de la colonne
	 */
	public Colonne getColonne(int colonne){
		return null;
	}
	
	/**
	 * Récupérer l'index de la colonne avec son nom
	 * @param nomColonne Nom de la colonne
	 * @return L'index de la colonne ayant comme nom le nom passé en paramètre
	 */
	public int getNbColonne(String nomColonne){
		return 0;
	}
}
