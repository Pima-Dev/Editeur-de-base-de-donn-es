package vue;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
public class ModeleTable extends DefaultTableModel {
	
	private int hauteur;
	private int largeur;
	private boolean editable;
	private int ligneAEditer;
	private Fenetre fenetre;
	public ModeleTable(int hauteur, int largeur, Fenetre fenetre){
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
		if(editable){
			if(columnIndex > 0 && ligneAEditer == rowIndex){
				ret = true;
			}
			else{
				ret = false;
			}
		}
		else{
			if(columnIndex < this.largeur -2){
				ret = false;
			}
			else{
				ret = true;
			}
		}
		return ret;
    }
	
	public void setCellEditable(int row){
		editable = true;
		this.ligneAEditer = row;
	}

	/**
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}
	
	public void setCellNonEditable(){
		editable = false;
	}
}
