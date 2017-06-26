package controleur;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import modele.CustomException;
import modele.TypeDonnee;
import modele.Util;
import vue.Fenetre;

public class TableListener implements TableModelListener{

	private Fenetre fenetre;
	
	public TableListener(Fenetre fenetre){
		this.fenetre = fenetre;
	}
	
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
			fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable()).editerTuple(id, nomColonne, obj);
			fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable()).refreshTable();
		} catch (CustomException e1) {
			Util.logErreur(e1.getMessage());
		}
	}

}
