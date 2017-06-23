package controleur;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.Colonne;
import modele.Contrainte;
import modele.CustomException;
import modele.Table;
import modele.TypeContrainte;
import modele.TypeDonnee;
import modele.Util;
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
				if (list.getName().equals("jlist des tables") && list.getSelectedValue() != null
						&& list.getSelectedValue().equals("+ Nouvelle Table")) {
					if (this.fenetre.getVuePrincipale().getListModel().size() < 2) {
						this.fenetre.getVuePrincipale().getJList().clearSelection();
					} else {
						this.fenetre.getVuePrincipale().getJList().setSelectedIndex(e.getFirstIndex());
					}
					if (this.fenetre.getBDD() == null) {
						new CustomException("Erreur", "Vous devez d'abord ouvrir ou créer une base de données.");
						return;
					}
					String s = JOptionPane.showInputDialog(null, "Saisir le nom de la nouvelle table", "Nouvelle table",
							JOptionPane.QUESTION_MESSAGE);

					String s2 = null;
					if (s != null)
						s2 = JOptionPane.showInputDialog(null, "Saisir le nom de votre clé primaire de type INTEGER",
								"Clé primaire", JOptionPane.QUESTION_MESSAGE);
					if (s != null && s2 != null && s.length() > 0 && s2.length() > 0) {
						try {
							Table table = new Table(this.fenetre.getBDD(), s);
							Colonne<Integer> col = new Colonne<Integer>(s2, TypeDonnee.INTEGER);
							col.ajouterContrainte(new Contrainte(TypeContrainte.PRIMARYKEY, null));
							table.ajouterAttribut(col);
							this.fenetre.getBDD().ajouterTable(table);
							this.fenetre.getVuePrincipale().ajouterTable(s);
							this.fenetre.getVuePrincipale().getJList().setSelectedIndex(this.fenetre.getVuePrincipale().getListModel().size()-2);
							table.refreshTable();
						} catch (CustomException e1) {
							Util.logErreur(e1.getMessage());
						}
					}
				}
				else if (list.getName().equals("jlist des tables") && list.getSelectedValue() != null){
					this.fenetre.getBDD().getTable(list.getSelectedValue().toString()).refreshTable();
				}
			}
		}
	}

}
