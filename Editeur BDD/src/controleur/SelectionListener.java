package controleur;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
				if (list.getName().equals("jlist des tables")) {
					if (this.fenetre.getVuePrincipale().getListModel().size() < 2) {
						this.fenetre.getVuePrincipale().getJList().setSelectedIndex(-1);
					} else {
						this.fenetre.getVuePrincipale().getJList().setSelectedIndex(e.getFirstIndex());
					}
				}
			}
		}
	}

}
