package controleur;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vue.Fenetre;
/**
 * Cette classe est appelée lors de la modification de valeur d'un JSpinner
 */
public class ValeurListener implements ChangeListener{

	/**
	 * la racine de référence qui permet d'accéder à toutes les vues
	 */
	Fenetre fenetre;
	/**
	 * construit le listener
	 * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	 */
	public ValeurListener(Fenetre fenetre) {
		this.fenetre = fenetre;
	}

	/**
	 * redéfinition de la méthode stateChanged qui s'éxécute lors de la modification d'état d'un JSpinner
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		fenetre.getVueRechercheAvance().getLigneMinEditeur().getModel().setMaximum((int)fenetre.getVueRechercheAvance().getLigneMaxEditeur().getModel().getNumber());
		fenetre.getVueRechercheAvance().getLigneMaxEditeur().getModel().setMinimum((int)fenetre.getVueRechercheAvance().getLigneMinEditeur().getModel().getNumber());
	}
}
