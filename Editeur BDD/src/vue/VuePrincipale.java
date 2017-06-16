package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import modele.Colonne;
import modele.Table;

import java.awt.*;

public class VuePrincipale extends JPanel{
	
	private JPanel panneauListeBoutonTable;
	private JPanel panneauBoutonTable;
	//menu
	private JPanel panneauMenu;
	private JMenuBar menu;
	
	private JMenu fichier;
	private JMenuItem nouveau;
	private JMenuItem ouvrir;
	private JMenuItem enregistrer;
	private JMenuItem enregistrerSous;
	private JMenuItem exporterEnPDF;
	
	private JMenu editer;
	private JMenuItem retourArriere;
	private JMenuItem couper;
	private JMenuItem copier;
	private JMenuItem coller;
	private JMenuItem supprimer;
	private JMenuItem toutSelectionner;
	
	
	private JMenu outils;
	private JMenuItem accederALaConsole;
	private JMenuItem rechercher;
	private JMenuItem ajouterUnTuple;
	private JMenuItem modifierUnTuple;
	private JMenuItem supprimerUnTuple;
	private JMenuItem gererDesAttributs;
	
	private JMenu aide;
	private JMenuItem aideEnLigne;
	
	//liste table
	private JPanel panneauListeTable;
	private JScrollPane panneauTableau;
	private JTable tableauListeTable;
	private JLabel titreTableau;
	private JButton nouvelleTable;
	
	//boutons
	 private JPanel panneauBoutons;
	 
	 private JPanel ajouter;
	 private JButton ajouterTuple;
	 private JButton ajouterAttribut;
	 
	 private JPanel margeRecherche;
	 private JPanel chercher;
	 private JPanel barreRecherche;
	 private JLabel lChercher;
	 private JTextField fChercher;
	 private JButton optionRecherche;
	 
	 private JPanel marge;
	 private JPanel contrainte;
	 private JLabel lContrainte;
	 private JButton ajouterContrainte;
	 private JButton supprimerContrainte;
	 
	 //table
	 private JTable table;
	 private JScrollPane scrollTable;
	 private JPanel panneauTable;
	 
	public VuePrincipale(){
		menu();
		listeTable();
		boutons();
		table();
		
		panneauBoutonTable = new JPanel();
		panneauBoutonTable.setLayout(new BorderLayout());
		panneauBoutonTable.add(marge,BorderLayout.SOUTH);
		panneauBoutonTable.add(scrollTable,BorderLayout.NORTH);
		panneauListeBoutonTable = new JPanel();
		panneauListeBoutonTable.setLayout(new BorderLayout());
		panneauListeBoutonTable.add(panneauListeTable,BorderLayout.WEST);
		panneauListeBoutonTable.add(panneauBoutonTable,BorderLayout.CENTER);
		this.setLayout(new BorderLayout());
		this.add(panneauListeBoutonTable, BorderLayout.CENTER);
		this.add(panneauMenu, BorderLayout.NORTH);
	}
	
	
	public void menu(){
		panneauMenu = new JPanel();
		
		menu = new JMenuBar();
		panneauMenu.setLayout(new FlowLayout(FlowLayout.LEFT));

		
		fichier = new JMenu("    Fichier    ");
		nouveau = new JMenuItem("Nouveau");
		ouvrir = new JMenuItem("Ouvrir");
		enregistrer = new JMenuItem("Enregistrer");
		enregistrerSous = new JMenuItem("Enregistrer sous");
		exporterEnPDF = new JMenuItem("Exporter en PDF");
		
		editer = new JMenu("    Editer    ");
		retourArriere = new JMenuItem("Retour arrière");
		couper = new JMenuItem("Couper");
		copier = new JMenuItem("Copier");
		coller = new JMenuItem("Coller");
		supprimer = new JMenuItem("Supprimer");
		toutSelectionner = new JMenuItem("Tout supprimer");
		
		outils = new JMenu("    Outils    ");
		accederALaConsole = new JMenuItem("Accéder à la console");
		rechercher = new JMenuItem("Rechercher");
		ajouterUnTuple = new JMenuItem("Ajouter un tupe");
		modifierUnTuple = new JMenuItem("Modifier un tuple");
		supprimerUnTuple = new JMenuItem("Supprimer un tuple");
		gererDesAttributs = new JMenuItem("Gerer des attributs");
		
		aide = new JMenu("    Aide    ");
		aideEnLigne = new JMenuItem("Aide en ligne");
		
		menu.add(fichier);
		menu.add(editer);
		menu.add(outils);
		menu.add(aide);
		
		fichier.add(nouveau);
		fichier.add(ouvrir);
		fichier.add(enregistrer);
		fichier.add(enregistrerSous);
		fichier.add(exporterEnPDF);
		
		editer.add(retourArriere);
		editer.add(couper);
		editer.add(copier);
		editer.add(coller);
		editer.add(supprimer);
		editer.add(toutSelectionner);
		editer.add(retourArriere);
		
		outils.add(accederALaConsole);
		outils.add(rechercher);
		outils.add(ajouterUnTuple);
		outils.add(modifierUnTuple);
		outils.add(supprimerUnTuple);
		outils.add(gererDesAttributs);
		
		aide.add(aideEnLigne);
		
		panneauMenu.add(menu);
	}
	
	
	public void listeTable(){ 
		
		panneauListeTable = new JPanel();
		//tableauListeTable = new JTable(new String[][]{ {"0","2","4","6","8"} }, new String[]{"Nouvelle BDD"});
		DefaultTableModel dm = new DefaultTableModel();
        dm.setDataVector(new Object[][]{{"button 1"},{"button 2"}}, new Object[]{"Nouvelle BDD"});
        tableauListeTable = new JTable(dm);
		tableauListeTable.getColumn("Nouvelle BDD").setCellRenderer(new RenduCellule());
		tableauListeTable.getColumn("Nouvelle BDD").setCellEditor(new EditeurCellule(new JCheckBox()));
		panneauTableau = new JScrollPane(tableauListeTable);
		panneauListeTable.add(panneauTableau);
        /*CellEditor tce = new CellEditor("Nouvelle table");
		tce.setBorderPainted(false);
		tce.setContentAreaFilled(false);
		tce.setForeground(new Color(142,162,198));
		
		// TableColumn maColonne = tableauListeTable.getColumnModel().getColumn(0);
		tableauListeTable.setCellEditor(new ButtonColumn);
		panneauTableau = new JScrollPane(tableauListeTable);
		panneauListeTable = new JPanel();
		panneauListeTable.setLayout(new BorderLayout());
		panneauListeTable.add(tableauListeTable);
		
		
		tableauListeTable = new JPanel();
		tableauListeTable.setLayout(new GridLayout(0,1));
		titreTableau = new JLabel("Nouvelle BDD");
		titreTableau.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		tableauListeTable.add(nouvelleTable);
		panneauListeTable.add(titreTableau,BorderLayout.NORTH);
		panneauListeTable.add(panneauTableau,BorderLayout.CENTER);*/
		
		
	}
	
	 public void boutons(){
		 panneauBoutons = new JPanel();
		 panneauBoutons.setLayout(new GridLayout(1,3,20,10));
		 Font font = new Font("Arial",Font.BOLD,16);
		 
		 ajouter = new JPanel();
		 ajouter.setLayout(new GridLayout(2,1,10,5));
		 ajouterTuple = new JButton("Ajouter un tuple");
		 ajouter.add(ajouterTuple);
		 ajouterAttribut = new JButton("Ajouter un attribut");
		 ajouter.add(ajouterAttribut);
		 panneauBoutons.add(ajouter);
		 
		 
		 chercher = new JPanel();
		 chercher.setLayout(new BorderLayout());
		 barreRecherche = new JPanel();
		 barreRecherche.setLayout(new BorderLayout());
		 lChercher = new JLabel("Chercher");
		 lChercher.setFont(font);
		 lChercher.setHorizontalAlignment(SwingConstants.CENTER);
		 fChercher = new JTextField("Chercher les occurences");
		 optionRecherche = new JButton(new ImageIcon("src/ressources/parametre.png"));
		 optionRecherche.setBorderPainted(false);
		 optionRecherche.setContentAreaFilled(false);
		 barreRecherche.add(fChercher, BorderLayout.CENTER);
		 barreRecherche.add(optionRecherche, BorderLayout.EAST);
		 margeRecherche = new JPanel();
		 margeRecherche.setLayout(new BorderLayout());
		 margeRecherche.add(new JLabel("        "), BorderLayout.NORTH);
		 margeRecherche.add(new JLabel("        "), BorderLayout.SOUTH);
		 margeRecherche.add(barreRecherche, BorderLayout.CENTER);
		 chercher.add(lChercher, BorderLayout.NORTH);
		 chercher.add(margeRecherche, BorderLayout.CENTER);
		 panneauBoutons.add(chercher);
		 
		 contrainte = new JPanel();
		 contrainte.setLayout(new GridLayout(3,1,5,5));
		 lContrainte = new JLabel("Contraintes");
		 lContrainte.setFont(font);
		 lContrainte.setHorizontalAlignment(SwingConstants.CENTER);
		 contrainte.add(lContrainte);
		 ajouterContrainte = new JButton("Ajouter une contrainte");
		 contrainte.add(ajouterContrainte);
		 supprimerContrainte = new JButton("Supprimer une contrainte");
		 contrainte.add(supprimerContrainte);
		 panneauBoutons.add(contrainte);
		 
		 
		 marge = new JPanel();
		 marge.setLayout(new BorderLayout());
		 marge.add(new JLabel("        "), BorderLayout.WEST);
		 marge.add(new JLabel("        "), BorderLayout.EAST);
		 marge.add(new JLabel("        "), BorderLayout.SOUTH);
		 marge.add(new JLabel("        "), BorderLayout.NORTH);
		 marge.add(panneauBoutons, BorderLayout.CENTER);
	}
	
	public void table(){
		table = new JTable(new String[][]{ {"0","2","4","6","8"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9"},{"1","3","5","7","9",} }, new String[]{"0","1","2","3","4",});
		scrollTable = new JScrollPane(table);
		scrollTable.createVerticalScrollBar();
	}
	
	public static void main(String[] args){
		JFrame fenetre = new JFrame("Connexion");
		fenetre.setContentPane(new VuePrincipale());
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.pack();
	}


	/**
	 * @return the accederALaConsole
	 */
	public JMenuItem getAccederALaConsole() {
		return accederALaConsole;
	}


	/**
	 * @return the aide
	 */
	public JMenu getAide() {
		return aide;
	}


	/**
	 * @return the aideEnLigne
	 */
	public JMenuItem getAideEnLigne() {
		return aideEnLigne;
	}


	/**
	 * @return the nouveau
	 */
	public JMenuItem getNouveau() {
		return nouveau;
	}


	/**
	 * @return the ouvrir
	 */
	public JMenuItem getOuvrir() {
		return ouvrir;
	}


	/**
	 * @return the enregistrer
	 */
	public JMenuItem getEnregistrer() {
		return enregistrer;
	}


	/**
	 * @return the enregistrerSous
	 */
	public JMenuItem getEnregistrerSous() {
		return enregistrerSous;
	}


	/**
	 * @return the exporterEnPDF
	 */
	public JMenuItem getExporterEnPDF() {
		return exporterEnPDF;
	}


	/**
	 * @return the retourArriere
	 */
	public JMenuItem getRetourArriere() {
		return retourArriere;
	}


	/**
	 * @return the couper
	 */
	public JMenuItem getCouper() {
		return couper;
	}


	/**
	 * @return the copier
	 */
	public JMenuItem getCopier() {
		return copier;
	}


	/**
	 * @return the coller
	 */
	public JMenuItem getColler() {
		return coller;
	}


	/**
	 * @return the supprimer
	 */
	public JMenuItem getSupprimer() {
		return supprimer;
	}


	/**
	 * @return the toutSelectionner
	 */
	public JMenuItem getToutSelectionner() {
		return toutSelectionner;
	}


	/**
	 * @return the ajouterUnTuple
	 */
	public JMenuItem getAjouterUnTuple() {
		return ajouterUnTuple;
	}


	/**
	 * @return the modifierUnTuple
	 */
	public JMenuItem getModifierUnTuple() {
		return modifierUnTuple;
	}


	/**
	 * @return the supprimerUnTuple
	 */
	public JMenuItem getSupprimerUnTuple() {
		return supprimerUnTuple;
	}


	/**
	 * @return the gererDesAttributs
	 */
	public JMenuItem getGererDesAttributs() {
		return gererDesAttributs;
	}


	/**
	 * @return the nouvelleTable
	 */
	public JButton getNouvelleTable() {
		return nouvelleTable;
	}


	/**
	 * @return the ajouterTuple
	 */
	public JButton getAjouterTuple() {
		return ajouterTuple;
	}


	/**
	 * @return the ajouterAttribut
	 */
	public JButton getAjouterAttribut() {
		return ajouterAttribut;
	}


	/**
	 * @return the barreRecherche
	 */
	public JPanel getBarreRecherche() {
		return barreRecherche;
	}


	/**
	 * @return the fChercher
	 */
	public JTextField getfChercher() {
		return fChercher;
	}


	/**
	 * @return the optionRecherche
	 */
	public JButton getOptionRecherche() {
		return optionRecherche;
	}


	/**
	 * @return the ajouterContrainte
	 */
	public JButton getAjouterContrainte() {
		return ajouterContrainte;
	}


	/**
	 * @return the supprimerContrainte
	 */
	public JButton getSupprimerContrainte() {
		return supprimerContrainte;
	}


	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}
	
	
}
