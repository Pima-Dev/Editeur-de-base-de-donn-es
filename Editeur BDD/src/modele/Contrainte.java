package modele;

public class Contrainte {

	/**
	 * Le type de la contrainte
	 */
	private TypeContrainte type;
	
	/**
	 * La table référencé si le type de la contrainte est REFENCEKEY. La valeur est null sinon.
	 */
	private Table referenceTable;
	
	/**
	 * Créer une contrainte, initialise les attributs
	 * @param type Le type de la contrainte
	 * @param referenceTable La table référencé par l'attribut ou null sinon.
	 */
	public Contrainte(TypeContrainte type, Table referenceTable){
		
		this.type = type;
		if(type == TypeContrainte.REFERENCEKEY){
			this.referenceTable = referenceTable;
		}
		else{
			this.referenceTable = null;
		}
	}
	
	/**
	 * Récupérer le type de la contrainte
	 * @return Le type de la contrainte
	 */
	public TypeContrainte getContrainteType(){
		return this.type;
	}
	
	/**
	 * Récupérer la table référencé par la clé étrangère
	 * @return La table reférencé par la clé etrangère ou null si la contrainte n'est pas une contrainte de clé étrangère
	 */
	public Table getReferenceTable(){
		return this.referenceTable;
	}
}
