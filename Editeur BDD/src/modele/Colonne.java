package modele;

import java.util.ArrayList;

public class Colonne<V> {

	/**
	 * Nom de la colonne
	 */
	private String nom;
	/**
	 * Les valeurs contenue dans la colonne
	 */
	
	private ArrayList<V> valeurs;
	/**
	 * La liste des contrainte
	 */
	
	private ArrayList<Contrainte> contrainte;
	
	/**
	 * Initialise le nom de la colonne
	 * @param nom Le nom de la colonne
	 */
	public Colonne(String nom){
		this.nom = nom;
	}
	
	/**
	 * Accéder au nom de la colonne
	 * @return Le nom de la colonne
	 */
	public String getNom(){
		return this.nom;
	}
	
	/**
	 * Définir le nom de la colonne
	 * @param nom Le nom de la colonne
	 */
	public void setNom(String nom){
		this.nom = nom;
	}
	
	/**
	 * Permet d'accéder à une valeur avec 
	 * @param index
	 * @return
	 */
	public V getValue(int index){
		return null;
	}
	
	/**
	 * Ajouter une valeur à la colonne
	 * @param valeur La valeur à ajouter
	 */
	public void ajouterValeur(V valeur){
		
	}
	
	/**
	 * Ajouter une contrainte à la colonne. Verifie qu'elle n'existe pas déjà 
	 * @param contrainte La contrainte à ajouter
	 */
	public void ajouterContrainte(Contrainte contrainte){
		
	}
	
	/**
	 * Ajouter une contrainte check sur la colonne
	 * @param operateur L'operateur peut etre: <, >, <=, >=, ==, != 
	 * @param valeur La valeur à mettre à droite de l'opérateur
	 */
	public void ajouterCheck(String operateur, V valeur){
		
	}
	
	/**
	 * Verifier les valeurs
	 */
	private void verifValues(){
		
	}
}

