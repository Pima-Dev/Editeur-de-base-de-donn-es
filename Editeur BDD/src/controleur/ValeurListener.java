package controleur;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vue.Fenetre;

public class ValeurListener implements ChangeListener{

	Fenetre fenetre;
	public ValeurListener(Fenetre fenetre) {
		this.fenetre = fenetre;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		fenetre.getVueRechercheAvance().getLigneMinEditeur().getModel().setMaximum((int)fenetre.getVueRechercheAvance().getLigneMaxEditeur().getModel().getNumber());
		fenetre.getVueRechercheAvance().getLigneMaxEditeur().getModel().setMinimum((int)fenetre.getVueRechercheAvance().getLigneMinEditeur().getModel().getNumber());
	}

	

}
