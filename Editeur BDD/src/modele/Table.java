package modele;

import java.util.ArrayList;

public class Table {

	/**
	 * Le nom de la table
	 */
	private String nom;
	
	/**
	 * La liste des colonnes de la table
	 */
	private ArrayList<Colonne> listeColonnes;
	
	/**
	 * La base de donnée ou est la table
	 */
	private BaseDeDonnees BDD;
	
	/** 
	 * Initialise le nom et la liste des colonnes
	 * @param nom Le nom de la table
	 */
	public Table(BaseDeDonnees BDD, String nom){
		this.BDD = BDD;
		this.nom = nom;
		this.listeColonnes = new ArrayList<Colonne>();
	}
	
	/**
	 * Ajouter une colonne
	 * @param colonne La colonne à ajouter
	 */
	public void ajouterAttribut(Colonne colonne){
		this.listeColonnes.add(colonne);
	}
	
	/**
	 * Inserer un tuple dans la table. Un seul tuples peut etre ajouté.
	 * @param attributs Liste des attributs
	 * @throws CustomException 
	 */
	private void insererUnTuple(ArrayList<Colonne> attributs) throws CustomException{
		
		if(attributs.size() != this.listeColonnes.size()){
			throw new CustomException("Erreur", "Le tuple est incomplet ou contient trop d'attribut");
		}
		
		for(Colonne colonne : attributs){
			if(colonne.getListeValeurs().size() != 1){
				throw new CustomException("Erreur", "Les tuples ne peuvent être inséré qu'un par un");
			}
		}
		
		try {
			this.BDD.getServeur().insererTuples(this.nom, attributs);
			for(int i = 0; i<attributs.size(); i++){
				this.listeColonnes.get(i).getListeValeurs().add(attributs.get(i).getValue(0));
			}
		} catch (CustomException e) {
			Util.logErreur(e.getMessage());
		}
	}
	
	public void insererTuple(ArrayList<Object> attributs) throws CustomException{
				
		ArrayList<Colonne> tuple = new ArrayList<Colonne>();
		
		try {
			for(Object obj : attributs){
				if(obj instanceof Integer){
					Colonne<Integer> col = new Colonne<Integer>("colonne", TypeDonnee.NOMBRE);
					col.ajouterValeur((int)obj);
					tuple.add(col);
				}
				else if (obj instanceof String){
					Colonne<String> col = new Colonne<String>("colonne", TypeDonnee.CHAR);
					col.ajouterValeur((String)obj);
					tuple.add(col);
				}
				else{
					throw new CustomException("Erreur", obj+" est de type incompatible pour être inséré dans un tuple");
				}
			}
			this.insererUnTuple(tuple);
		}
		catch (CustomException e) {
			Util.logErreur(e.getMessage());
		}
	}
	
	/**
	 * Supprimer un tuple avec son identifiant de clé primaire
	 * @param id La clé primaire
	 * @throws CustomException
	 */
	public void supprimerTupleById(Object id) throws CustomException{
		
		Util.log("Suppression de la ligne ayant comme id "+id+"...");
		
		if(id instanceof String && this.getClePrimaire().getTypeDonnees() == TypeDonnee.NOMBRE){
			throw new CustomException("Erreur de type", "La clé primaire est de type NOMBRE et non CHAR ou DATE");
		}
		else if(id instanceof Integer && this.getClePrimaire().getTypeDonnees() != TypeDonnee.NOMBRE){
			throw new CustomException("Erreur de type", "La clé primaire est de type "+this.getClePrimaire().getTypeDonnees()+" et non de type NOMBRE");
		}
		
		this.BDD.getServeur().supprimerTupleById(this, id);
		int index = -1;
		int i = 0;
		for (Object valeur : this.getClePrimaire().getListeValeurs()){
			if(valeur.equals(id)){
				index = i;
			}
			i++;
		}
		if(index == -1){
			throw new CustomException("Erreur", "Il n'existe pas de tuple avec '"+id+"' comme identifiant.");
		}
		else {
			for(Colonne colonne : this.getListeColonnes()){
				colonne.getListeValeurs().remove(index);
			}
		}
		Util.log("Suppression de la ligne ayant comme id "+id+" effectué.");
	}
	
	
	public void editerTuple(Object id, String nomColonne, Object newValeur) throws CustomException{
		Util.log("Modification de la valeur du tuple ayant pour identifiant '"+id+"' de la colonne '"+nomColonne+"' par la nouvelle valeur '"+newValeur+"'...");
		
		if(id instanceof String && this.getClePrimaire().getTypeDonnees() == TypeDonnee.NOMBRE){
			throw new CustomException("Erreur de type", "La clé primaire est de type NOMBRE et non CHAR ou DATE");
		}
		else if(id instanceof Integer && this.getClePrimaire().getTypeDonnees() != TypeDonnee.NOMBRE){
			throw new CustomException("Erreur de type", "La clé primaire est de type "+this.getClePrimaire().getTypeDonnees()+" et non de type NOMBRE");
		}
		
		if(this.getColonne(nomColonne) == null){
			throw new CustomException("Erreur de colonne", "La colonne ayant pour nom '"+nomColonne+"' n'existe pas");
		}
		
		if(this.getColonne(nomColonne).getTypeDonnees() == TypeDonnee.NOMBRE && !(newValeur instanceof Integer)){
			throw new CustomException("Erreur de type", "Le type de donnée de la colonne '"+nomColonne+"' est de type NOMBRE et non de type CHAR.");
		}
		
		if(this.getColonne(nomColonne).getTypeDonnees() != TypeDonnee.NOMBRE && (newValeur instanceof Integer)){
			throw new CustomException("Erreur de type", "Le type de donnée de la colonne '"+nomColonne+"' est de type "+this.getColonne(nomColonne).getTypeDonnees()+" et non de type NOMBRE.");
		}
		
		this.BDD.getServeur().editerTuple(this, id, nomColonne, newValeur);
		
		int index = -1;
		int i = 0;
		for (Object valeur : this.getClePrimaire().getListeValeurs()){
			if(valeur.equals(id)){
				index = i;
			}
			i++;
		}
		if(index == -1){
			throw new CustomException("Erreur", "Il n'existe pas de tuple avec '"+id+"' comme identifiant.");
		}
		else {
			for(Colonne colonne : this.getListeColonnes()){
				if(colonne.getNom().equals(nomColonne)){
					colonne.getListeValeurs().set(index, newValeur);
				}
			}
		}
		Util.log("Modification de la valeur du tuple ayant pour identifiant '"+id+"' de la colonne '"+nomColonne+"' par la nouvelle valeur '"+newValeur+"' effectué.");

	}
	
	/**
	 * Ajouter une contrainte a une colonne
	 * @param colonne La colonne concerné
	 * @param contrainte La contrainte
	 */
	public void AjouterContrainte(int colonne, TypeContrainte contrainte){
		
	}
	
	/**
	 * Récupérer une colonne à partir de son nom
	 * @param nom Le nom de la colonne à récupérer
	 * @return L'attribut de la colonne
	 */
	public Colonne getColonne(String nom){
		
		Colonne ret = null;
		
		for(Colonne colonne : this.listeColonnes){
			if(colonne.getNom().equals(nom)){
				ret = colonne;
			}
		}
		return ret;
	}
	
	/**
	 * Récupérer le nom de la table
	 * @return Le nom de la table
	 */
	public String getNom(){
		return this.nom;
	}
	
	/**
	 * Récupérer la liste des colonnes
	 * @return la liste des colonnes
	 */
	public ArrayList<Colonne> getListeColonnes(){
		return this.listeColonnes;
	}
	
	public Colonne getClePrimaire(){
		Colonne colonne = null;
		
		for(Colonne col : this.listeColonnes){
			ArrayList<Contrainte> listeContraintes = col.getListeContraintes();
			for(Contrainte contrainte : listeContraintes){
				if(contrainte.getContrainteType() == TypeContrainte.PRIMARYKEY){
					colonne = col;
				}
			}
		}
		
		return colonne;
	}
}
