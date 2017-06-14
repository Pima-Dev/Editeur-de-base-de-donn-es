package vue;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ModeleTable extends AbstractTableModel {

	private final ArrayList<String> entetes = new ArrayList<String>();
	
	void ModeleTable(){
		
	}
	
	public int getColumnCount() {
		return entetes.size();
	}

	public String getColumnName(int columnIndex) {
		return entetes.get(columnIndex);
	}
	
	public int getRowCount() {
		return 0;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return null;
	}

}
