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
				// System.out.println(getValueAt(0,0));
				ArrayList<Integer> lignesValides = new ArrayList<Integer>();
				// System.out.println(getRowCount());
				for (int i = 0; i < getRowCount(); i++) {
					for (int j = 0; j < getColumnCount() - 2; j++) {
						// System.out.println(getValueAt(i,j));
						System.out.println(getValueAt(i, j));
						if (((String) getValueAt(i, j)).contains(texte)) {
							lignesValides.add(i);
						}
					}
				}
				System.out.println("nombre row : " + getRowCount());
				for (Integer i : lignesValides) {
					System.out.println("ligne a remove: " + i);
					int index = fenetre.getVuePrincipale().getTable().convertRowIndexToModel(i);
					removeRow(index);
				}
			}
		});

	}
}
