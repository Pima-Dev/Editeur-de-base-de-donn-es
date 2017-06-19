package modele.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import modele.BaseDeDonnees;
import modele.Colonne;
import modele.Contrainte;
import modele.CustomException;
import modele.ELFichier;
import modele.Table;
import modele.TypeContrainte;
import modele.TypeDonnee;

public class TestServeur {

	public static void main(String[] args){
		BaseDeDonnees bdd = null;
		try{
			//bdd = new BaseDeDonnees("UneBDD", "root", "azerty", new ArrayList<Table>());
			
			Table table1 = new Table(bdd, "table1");
			Table table2 = new Table(bdd, "table2");
			
			Colonne<Integer> colonne1 = new Colonne<Integer>("colonne1", TypeDonnee.INTEGER);
			colonne1.ajouterContrainte(new Contrainte(TypeContrainte.PRIMARYKEY, null));
			colonne1.ajouterContrainte(new Contrainte(TypeContrainte.NOTNULL, null));

			
			Colonne<Double> colonne2 = new Colonne<Double>("colonne2", TypeDonnee.DOUBLE);
			colonne2.ajouterContrainte(new Contrainte(TypeContrainte.PRIMARYKEY, null));
			//colonne2.ajouterContrainte(new Contrainte(TypeContrainte.REFERENCEKEY, table1));
			
			Colonne<Integer> colonne3 = new Colonne<Integer>("colonne3", TypeDonnee.INTEGER);
			//colonne3.ajouterContrainte(new Contrainte(TypeContrainte.NOTNULL, null));
			//colonne3.ajouterContrainte(new Contrainte(TypeContrainte.UNIQUE, null));
			colonne3.ajouterContrainte(new Contrainte(TypeContrainte.REFERENCEKEY, table1));
			
			table1.ajouterAttribut(colonne1);
			table2.ajouterAttribut(colonne2);
			table2.ajouterAttribut(colonne3);
			
			bdd.ajouterTable(table1);
			bdd.ajouterTable(table2);
			
			ArrayList<Object> tuple1 = new ArrayList<Object>();
			tuple1.add(2);
			
			ArrayList<Object> tuple2 = new ArrayList<Object>();
			tuple2.add(1.369);
			tuple2.add(2);
			
			ArrayList<Object> tuple3 = new ArrayList<Object>();
			tuple3.add(5.698);
			tuple3.add(2);
			
			ArrayList<Object> tuple4 = new ArrayList<Object>();
			tuple4.add(9.582);
			tuple4.add(2);
			
			table1.insererTuple(tuple1);
			table2.insererTuple(tuple2);
			table2.insererTuple(tuple3);
			table2.insererTuple(tuple4);
			
			//table2.editerTuple("1", "colonne3", "3");

			//table2.supprimerTupleById("test");
			/*
			bdd = new BaseDeDonnees("UneBDD", "root", "azerty", new ArrayList<Table>());
			bdd.chargerBDD();
			for(Table t : bdd.getListeTable()){
				System.out.println(t);
				for(Colonne col : t.getListeColonnes()){
					System.out.println(col);
				}
			}
			*/
		}
		catch(CustomException e){}
		
		//catch(SQLException e){}
		finally{
			//bdd.supprimerBDD();
		}
	}
	
}
