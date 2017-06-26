package vue;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

class RenduCellule extends JButton implements TableCellRenderer {

	private String type;
	private Fenetre fenetre;
	public RenduCellule(String type,Fenetre fenetre) {
		this.type = type;
		if(type.equals("modifier")){
			this.setIcon(new ImageIcon("src/ressources/modifier.png"));
		}else if(type.equals("supprimer")){
			this.setIcon(new ImageIcon("src/ressources/supprimer.png"));
		}
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        if(EditeurCellule.rowEditable.contains(row)){
        	if(type.equals("modifier")){	
        		this.setIcon(new ImageIcon("src/ressources/valider.png"));
        	}
        }
        else{
        	if(type.equals("modifier")){
        		this.setIcon(new ImageIcon("src/ressources/modifier.png"));
        	}
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
    
    
}