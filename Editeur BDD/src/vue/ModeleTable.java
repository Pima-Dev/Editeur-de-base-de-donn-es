package vue;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
public class ModeleTable extends DefaultTableModel {
	
	private int hauteur;
	private int largeur;
	private boolean editable;
	private VuePrincipale vue;
	private int ligneSelectionne;
	public ModeleTable(int hauteur, int largeur, VuePrincipale vue){
		editable = false;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.vue = vue;
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
			if(columnIndex > 0 && rowIndex == ligneSelectionne || columnIndex < this.largeur -2){
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
	
	public void setCellEditable(int ligneSelectionne){
		editable = true;
		this.ligneSelectionne = ligneSelectionne;
	}
	
	

}
