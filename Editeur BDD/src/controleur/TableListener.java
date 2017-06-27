package controleur;

import java.sql.SQLException;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import modele.CustomException;
import modele.TypeDonnee;
import modele.Util;
import vue.Fenetre;
/**
 * Cette classe est appelée lors de la modification des données d'une table
 */
public class TableListener implements TableModelListener{

	/**
	 * la racine de référence qui permet d'accéder à toutes les vues
	 */
	private Fenetre fenetre;
	
	/**
	 * construit le listener
	 * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	 */
	public TableListener(Fenetre fenetre){
		this.fenetre = fenetre;
	}
	
	/**
	 * redéfinition de la méthode tableChanged qui s'éxécute lors de la modification de données dans un tableau
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		if(e.getColumn() >= this.fenetre.getVuePrincipale().getTable().getColumnCount()-2) return;
		String nomColonne = this.fenetre.getVuePrincipale().getTable().getColumnName(e.getColumn());
		nomColonne = nomColonne.replaceAll("[()]", "");
		nomColonne = nomColonne.replaceAll(TypeDonnee.CHAR.getSQLType(), "");
		nomColonne = nomColonne.replaceAll(TypeDonnee.DOUBLE.getSQLType(), "");
		nomColonne = nomColonne.replaceAll(TypeDonnee.INTEGER.getSQLType(), "");
		nomColonne = nomColonne.replaceAll(TypeDonnee.DATE.getSQLType(), "");
		int id = Integer.parseInt((String)this.fenetre.getVuePrincipale().getTable().getValueAt(e.getLastRow(), 0));
		Object obj = this.fenetre.getVuePrincipale().getTable().getModel().getValueAt(e.getLastRow(), e.getColumn());
		try {
			try {
				fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable()).editerTuple(id, nomColonne, obj);
				fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable()).refreshTable();
			} catch (CustomException e1) {
				try {
					Object object = this.fenetre.getBDD().getServeur().getValeurAt(fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable()), id, nomColonne);
					System.out.println(object);
					this.fenetre.getVuePrincipale().getTable().getModel().setValueAt(object, e.getLastRow(), e.getColumn());
					fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable()).refreshTable();
				} catch (CustomException e2) {
					Util.logErreur(e2.getMessage());
				} catch (SQLException e2) {
					Util.logErreur(e2.getMessage());
				}
				Util.logErreur(e1.getMessage());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
