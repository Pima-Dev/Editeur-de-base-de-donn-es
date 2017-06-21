package vue;

import java.util.ArrayList;

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
		System.out.println(rowIndex);
		System.out.println(columnIndex);
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
	
	public void rechercher(String texte){
		//System.out.println(getValueAt(0,0));
		ArrayList<Integer> lignesValides = new ArrayList<Integer>();
		//System.out.println(getRowCount());
		for(int i = 0; i<getRowCount()-1;i++){
			for(int j=0; j<getColumnCount()-2; j++){
				//System.out.println(getValueAt(i,j));
				if(((String)getValueAt(i,j)).contains(texte)){
					lignesValides.add(i);
				}
			}
		}
		//System.out.println(lignesValides.toString());
		int l = getRowCount();
		for(int k = 0; k<l; k++){
			//System.out.println(lignesValides.contains(k));
			//System.out.println(k);
			if(!lignesValides.contains(k)){
				removeRow(k);
				l--;
			}
		}
		fenetre.getFenetre().setVisible(true);
	}
}
