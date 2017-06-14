package modele;

public enum TypeContrainte {
	
	NOTNULL("NOT NULL"), PRIMARYKEY("PRIMARY KEY"), REFERENCEKEY("REFERENCES"), UNIQUE("UNIQUE"), AUCUNE("");

	  private final String symbole;
	  
	  private TypeContrainte(String symbole) {
		this.symbole = symbole;
	  }

	  public String getSQLContrainte() {
		  return symbole;
	  }
}
