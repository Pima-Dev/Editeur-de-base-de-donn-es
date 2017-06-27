package codeInutilise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.Fenetre;

public class MotDePasseOublieBoutonCode implements ActionListener {

	private Fenetre fenetre;
	private VueMotDePasseOublieCode vue;
	private String code;
	private String entre;
	
	public MotDePasseOublieBoutonCode(VueMotDePasseOublieCode vue, Fenetre fenetre) {
		this.vue = vue;
		this.fenetre = fenetre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		code = vue.getCode();
		entre = vue.getfCode().getText();
		if(!entre.equals("")){
			if(entre.equals(code)){
				fenetre.getFenetre().setContentPane(new VueMotDePasseOublieNouveau(fenetre));
				fenetre.getFenetre().setVisible(true);
				fenetre.getFenetre().pack();
			}
			else{
				vue.getlInfo().setText("Code invalide");
			}
		}
		else{
			vue.getlInfo().setText("Veuillez entrer le code reçu par mail");
		}
	}
}