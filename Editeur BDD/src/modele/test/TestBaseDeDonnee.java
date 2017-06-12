package modele.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import modele.BaseDeDonnees;
import modele.Table;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

public class TestBaseDeDonnee {

        private String nom;
        private ArrayList<Table> tables;
        private BaseDeDonnees bdd1;
        private BaseDeDonnees bdd2;
        private Table e;

        @Before
        public void setUp() {
                this.tables = new ArrayList<Table>();
                e = new Table("");
                tables.add(e);
                this.nom = "nom";
                this.bdd1 = new BaseDeDonnees(nom, tables);
                this.bdd2 = new BaseDeDonnees(nom, tables);
                System.out.println("debug");

        }
        
        
        @Test
        public void testConstructeur() {
                assertTrue(!bdd1.equals(null));
                assertEquals(bdd1,bdd2);
        }
        
        @Test
        public void testGetterSetter() {
                bdd1.setName("LeNom");
                bdd1.ajouterTable(e);
                assertTrue(bdd1.getName().equals(this.nom));
                assertTrue(bdd1.getListeTable().equals(tables));
        }
        
        @After()                            
        public void tearDown() {           
          this.bdd1 = null;                       
          this.bdd2 = null;
          this.e = null;
          this.tables = null;
          this.nom = null;
        } 
}