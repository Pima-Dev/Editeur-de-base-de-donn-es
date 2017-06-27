package vue;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
/**
 * Cet objet définie l'apparence du tableau, il est utilisé pour le tableau principal
 */
class RenduCellule extends JButton implements TableCellRenderer {

	/**
	 * le type de bouton contenu dans la cellule
	 */
	private String type;
	/**
	 * la racine de référence qui permet d'accéder à toutes les vues
	 */
	private Fenetre fenetre;
	/**
	 * construction du rendu de la céllule
	 * @param type le type de bouton contenu dans la cellule
	 * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	 */
	public RenduCellule(String type,Fenetre fenetre) {
		this.type = type;
		if(type.equals("modifier")){
			this.setIcon(new ImageIcon("src/ressources/modifier.png"));
		}else if(type.equals("supprimer")){
			this.setIcon(new ImageIcon("src/ressources/croix.png"));
		}
    }

	/**
	 * redéfinition de la méthode getTableCellRendererComponent qui permet d'accéder au rendu d'une case du tableau
	 */
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
        		this.setIcon(new ImageIcon("src/ressources/check.png"));
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