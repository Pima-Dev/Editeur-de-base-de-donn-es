package vue;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
/**
 * Cet objet définie l'action lorsque l'utilisateur intéragit avec le tableau, il est utilisé pour le tableau principal
 */
class EditeurCellule extends DefaultCellEditor {

	/**
	 * détermine l'origine du bouton appelant
	 */
    protected String name;
    /**
     * le bouton créé pour chaque case
     */
    private JButton button;
    /**
     * contenu des boutons
     */
    private String label;
    /**
     * vérifie si le bouton est appuyé
     */
    private boolean isPushed;
    /**
     * la racine de référence qui permet d'accéder à toutes les vues
     */
    private Fenetre fenetre;
    /**
     * la ligne sélectionnnée par l'utilisateur
     */
    private int ligne;
    /**
     * la liste des valeurs de la ligne modifiable avant modification
     */
    public static ArrayList<Integer> rowEditable;
    
    /**
     * construit les cellules à chaque création de tableau
     * @param checkBox objet permettant l'appel du constructeur de la super classe
     * @param name détermine l'origine du bouton appelant
     * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
     */
    public EditeurCellule(JCheckBox checkBox,String name,Fenetre fenetre) {
        super(checkBox);
        this.name = name;
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

    /**
     * agit à chaque séléction des cases concernées
     */
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
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    /**
     * renvoie la valeur de la case concernée
     */
    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            if(name.equals("modifier")){
            	if(fenetre.getVuePrincipale().getDm().isEditable()){
            		fenetre.getVuePrincipale().getDm().setCellNonEditable();
            		rowEditable.remove((Integer)ligne);
            	}
            	else{
            		fenetre.getVuePrincipale().getDm().setCellEditable(ligne);
            		rowEditable.add(ligne);
            	}
            }
            else if(name.equals("supprimer")){
            	int rep = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette ligne ?", "Supprimer la ligne ?", JOptionPane.CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            	if(rep == JOptionPane.OK_OPTION){
            		int id = Integer.parseInt((String)fenetre.getVuePrincipale().getTable().getValueAt(ligne, 0));
            		try {
						try {
							this.fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable()).supprimerTupleById(id);
							this.fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable()).refreshTable();
						} catch (CustomException e) {
							Util.logErreur(e.getMessage());
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
            	}
            }
        }
        isPushed = false;
        return label;
    }

    /**
     * stoppe l'édition de la case concernée
     */
    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}