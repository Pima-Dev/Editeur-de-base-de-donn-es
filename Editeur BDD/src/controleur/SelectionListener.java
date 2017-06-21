package controleur;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.Table;
import vue.Fenetre;

public class SelectionListener implements ListSelectionListener {

	private Fenetre fenetre;

	public SelectionListener(Fenetre fenetre) {
		this.fenetre = fenetre;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() instanceof JList) {
			JList list = (JList) e.getSource();
			if (!e.getValueIsAdjusting()) {
				if (list.getName().equals("jlist des tables") && list.getSelectedValue() != null && list.getSelectedValue().equals("+ NouvelleTable")) {
					String s = JOptionPane.showInputDialog(null, "Saisir le nom de la nouvelle table", "Nouvelle table", JOptionPane.QUESTION_MESSAGE);
					//this.fenetre.getBDD().ajouterTable(new Table(this.fenetre.getBDD(), nom));
					if (this.fenetre.getVuePrincipale().getListModel().size() < 2) {
						this.fenetre.getVuePrincipale().getJList().clearSelection();
					} else {
						this.fenetre.getVuePrincipale().getJList().setSelectedIndex(e.getFirstIndex());
					}
				}
			}
		}
	}

}
