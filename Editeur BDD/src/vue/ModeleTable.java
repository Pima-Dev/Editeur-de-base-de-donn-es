package vue;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
public class ModeleTable extends DefaultTableModel {
	
	private int hauteur;
	private int largeur;
	private boolean editable;
	public ModeleTable(int hauteur, int largeur){
		editable = false;
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
			if(columnIndex > 0){
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
	
	public void setCellEditable(){
		editable = true;
	}
	
	

}
