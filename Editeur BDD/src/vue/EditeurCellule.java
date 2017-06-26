package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

class EditeurCellule extends DefaultCellEditor {

    protected String name;
    private JButton button;
    private String label;
    private boolean isPushed;
    private ModeleTable dm;
    private Fenetre fenetre;
    private int ligne; 
    private int colonne;

    public EditeurCellule(JCheckBox checkBox,String name,ModeleTable dm,Fenetre fenetre) {
        super(checkBox);
        this.name = name;
        this.dm = dm;
        this.fenetre = fenetre;
        button = new JButton();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        ligne = row;
        colonne = column;
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            if(name.equals("modifier")){
            	if(fenetre.getVuePrincipale().getDm().isEditable()){
            		dm.setCellNonEditable();
            		((RenduCellule)fenetre.getVuePrincipale().getTable().getCellRenderer(ligne, colonne)).setIcon(new ImageIcon("src/ressources/modifier.png"));
            	}
            	else{
            		dm.setCellEditable(ligne);
            		((RenduCellule)fenetre.getVuePrincipale().getTable().getCellRenderer(ligne, colonne)).setIcon(new ImageIcon("src/ressources/valider.png"));
            	}
            }
            else if(name.equals("supprimer")){
            	//supprimer tuple
            	//id : fenetre.getVuePrincipale().getTable().getValueAt(ligne, 0);
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}