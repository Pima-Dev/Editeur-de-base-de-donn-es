package vue;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import modele.CustomException;
import modele.TypeDonnee;
import modele.Util;

class EditeurCellule extends DefaultCellEditor {

    protected String name;
    private JButton button;
    private String label;
    private boolean isPushed;
    private ModeleTable dm;
    private Fenetre fenetre;
    private int ligne; 
    private int colonne;
    public static ArrayList<Integer> rowEditable;
    
    public EditeurCellule(JCheckBox checkBox,String name,ModeleTable dm,Fenetre fenetre) {
        super(checkBox);
        this.name = name;
        this.dm = dm;
        this.fenetre = fenetre;
        button = new JButton();
        rowEditable = new ArrayList<>();
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
            		rowEditable.remove((Integer)ligne);
            	}
            	else{
            		dm.setCellEditable(ligne);
            		rowEditable.add(ligne);
            	}
            }
            else if(name.equals("supprimer")){
            	int rep = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette ligne ?", "Supprimer la ligne ?", JOptionPane.CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            	if(rep == JOptionPane.OK_OPTION){
            		int id = Integer.parseInt((String)fenetre.getVuePrincipale().getTable().getValueAt(ligne, 0));
            		try {
						this.fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable()).supprimerTupleById(id);
						this.fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable()).refreshTable();
            		} catch (CustomException e) {
						Util.logErreur(e.getMessage());
					}
            	}
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