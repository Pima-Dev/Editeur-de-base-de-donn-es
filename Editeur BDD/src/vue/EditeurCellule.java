package vue;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
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
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            if(name.equals("modifier")){
            	dm.setCellEditable(fenetre.getVuePrincipale().getTable().getSelectedRow());
            }
            else if(name.equals("supprimer")){
            	
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