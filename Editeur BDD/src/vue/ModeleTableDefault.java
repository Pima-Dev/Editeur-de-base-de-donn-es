package vue;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class ModeleTableDefault extends DefaultTableModel{

	public boolean isCellEditable(int row, int column) {
        return true;
    }
}
