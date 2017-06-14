package modele.test;

import java.util.ArrayList;

import modele.BaseDeDonnees;
import modele.Colonne;
import modele.Contrainte;
import modele.CustomException;
import modele.Serveur;
import modele.Table;
import modele.TypeContrainte;
import modele.TypeDonnee;
import modele.Util;

public class TestServeur {

	public static void main(String[] args){
		BaseDeDonnees bdd = null;
		try{
			bdd = new BaseDeDonnees("UneBDD", "root", "azerty", new ArrayList<Table>());
			
			Table table1 = new Table(bdd, "table1");
			Table table2 = new Table(bdd, "table2");
			
			Colonne<Integer> colonne1 = new Colonne<Integer>("colonne1", TypeDonnee.NOMBRE);
			colonne1.ajouterContrainte(new Contrainte(TypeContrainte.PRIMARYKEY, null));
			colonne1.ajouterContrainte(new Contrainte(TypeContrainte.NOTNULL, null));

			
			Colonne<String> colonne2 = new Colonne<String>("colonne2", TypeDonnee.CHAR);
			colonne2.ajouterContrainte(new Contrainte(TypeContrainte.PRIMARYKEY, null));
			//colonne2.ajouterContrainte(new Contrainte(TypeContrainte.REFERENCEKEY, table1));
			
			Colonne<String> colonne3 = new Colonne<String>("colonne3", TypeDonnee.CHAR);
			colonne3.ajouterContrainte(new Contrainte(TypeContrainte.NOTNULL, null));
			colonne3.ajouterContrainte(new Contrainte(TypeContrainte.UNIQUE, null));
			
			table1.ajouterAttribut(colonne1);
			table2.ajouterAttribut(colonne2);
			table2.ajouterAttribut(colonne3);
			
			bdd.ajouterTable(table1);
			bdd.ajouterTable(table2);
			
			ArrayList<Object> tuple1 = new ArrayList<Object>();
			tuple1.add(2);
			
			ArrayList<Object> tuple2 = new ArrayList<Object>();
			tuple2.add(1);
			tuple2.add("test");
			
			ArrayList<Object> tuple3 = new ArrayList<Object>();
			tuple3.add(2);
			tuple3.add("test2");
			
			ArrayList<Object> tuple4 = new ArrayList<Object>();
			tuple4.add(3);
			tuple4.add("test3");
			
			table1.insererTuple(tuple1);
			table2.insererTuple(tuple2);
			table2.insererTuple(tuple3);
			table2.insererTuple(tuple4);
			
			table2.editerTuple("1", "colonne3", 3);

			//table2.supprimerTupleById("test");
			
			
		}
		catch(CustomException e){}
		
		finally{
			bdd.supprimerBDD();
		}
	}
	
}
