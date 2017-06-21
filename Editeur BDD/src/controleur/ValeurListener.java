package controleur;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import vue.Fenetre;

public class ValeurListener implements VetoableChangeListener{

	Fenetre fenetre;
	public ValeurListener(Fenetre fenetre) {
		this.fenetre = fenetre;
	}

	@Override
	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
		fenetre.getVueRechercheAvance().getLigneMinEditeur().getModel().setMaximum((int)fenetre.getVueRechercheAvance().getLigneMaxEditeur().getModel().getNumber());
		
	}

	

}
