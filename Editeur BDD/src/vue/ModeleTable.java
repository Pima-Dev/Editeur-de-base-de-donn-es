package vue;

import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ModeleTable extends DefaultTableModel {

	private int hauteur;
	private int largeur;
	private boolean editable;
	private int ligneAEditer;
	private Fenetre fenetre;

	public ModeleTable(int hauteur, int largeur, Fenetre fenetre) {
		editable = false;
		this.fenetre = fenetre;
		this.hauteur = hauteur;
		this.largeur = largeur;
	}

	public int getColumnCount() {
		return largeur;
	}

	public int getRowCount() {
		return hauteur;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return super.getValueAt(rowIndex, columnIndex);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		boolean ret = false;
		if (editable) {
			if (columnIndex > 0 && ligneAEditer == rowIndex) {
				ret = true;
			} else {
				ret = false;
			}
		} else {
			if (columnIndex < this.largeur - 2) {
				ret = false;
			} else {
				ret = true;
			}
		}
		return ret;
	}

	public void setCellEditable(int row) {
		editable = true;
		this.ligneAEditer = row;
	}

	/**
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}

	public void setCellNonEditable() {
		editable = false;
	}

	public void removeRow(int index){
		super.removeRow(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void rechercher(String texte) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				int colonnes = fenetre.getVuePrincipale().getTable().getColumnCount()-2;
				fenetre.setVueResultatRecherche(new VueResultatRecherche(fenetre));
				ArrayList<Integer> lignesValides = new ArrayList<Integer>();
				for (int i = 0; i < getRowCount(); i++) {
					for (int j = 0; j < getColumnCount() - 2; j++) {
						if (((String) getValueAt(i, j)).contains(texte) && !lignesValides.contains(i)) {
							lignesValides.add(i);
						}
					}
				}
				String[][] donnees = new String[colonnes][lignesValides.size()];
				String[] titres = new String[colonnes];
				for (Integer i : lignesValides) {
					for(int j = 0; i < colonnes ;j++){
						donnees[i][j] = (String)fenetre.getVuePrincipale().getTable().getValueAt(i, j);
						titres[i] = (String)fenetre.getVuePrincipale().getTable().getColumnName(i);
						fenetre.getVuePrincipale().insererValeursDansTab(donnees, titres);
					}
				}
			}
		});

	}
}
