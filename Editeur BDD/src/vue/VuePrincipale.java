package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import controleur.ChampsListener;
import controleur.PresserBoutonListener;
import controleur.TouchePresseListener;

public class VuePrincipale extends JPanel{
	
	private JPanel panneauListeBoutonTable;
	private JPanel panneauBoutonTable;
	//menu
	private JPanel panneauMenu;
	private JMenuBar menu;
	
	private JMenu fichier;
	private JMenuItem nouveau;
	private JMenuItem supprimerBDD;
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
	private JLabel lTitreBDD;
	private JPanel panneauListeTable;
	private JScrollPane panneauTableau;
	private JList<String> tableauListeTable;
	private DefaultListModel<String> modeleListe;
	
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
	 
	 private Fenetre fenetre;
	 
	 //table
	 private JTable table;
	 private JScrollPane scrollTable;
	 private JPanel panneauTable;
	 private ModeleTable dm;
	 
	public VuePrincipale(Fenetre fenetre){
		this.fenetre = fenetre;
		this.fenetre.setVuePrincipale(this);
		this.panneauTable = new JPanel();
		menu();
		listeTable();
		boutons();
		//String[][] tab = new String[][]{ {"1", "1", "1", "1"}, {"2", "2", "2", "2"} };
		//table(tab, new String[]{"test1", "test2", "test3", "test4"});
		panneauBoutonTable = new JPanel();
		panneauBoutonTable.setLayout(new BorderLayout());
		panneauBoutonTable.add(marge,BorderLayout.SOUTH);
		panneauBoutonTable.add(panneauTable,BorderLayout.CENTER);
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
		nouveau.setName("MenuNouveau");
		nouveau.addActionListener(new PresserBoutonListener(this.fenetre));
		supprimerBDD = new JMenuItem("SupprimerBDD");
		supprimerBDD.setName("MenuSupprimerBDD");
		supprimerBDD.addActionListener(new PresserBoutonListener(this.fenetre));
		ouvrir = new JMenuItem("Ouvrir");
		ouvrir.setName("MenuOuvrir");
		ouvrir.addActionListener(new PresserBoutonListener(this.fenetre));
		enregistrer = new JMenuItem("Enregistrer");
		enregistrer.setName("MenuEnregistrer");
		enregistrer.addActionListener(new PresserBoutonListener(this.fenetre));
		enregistrerSous = new JMenuItem("Enregistrer sous");
		enregistrerSous.setName("MenuEnregistrerSous");
		enregistrerSous.addActionListener(new PresserBoutonListener(this.fenetre));
		exporterEnPDF = new JMenuItem("Exporter en PDF");
		exporterEnPDF.setName("MenuExporterEnPDF");
		exporterEnPDF.addActionListener(new PresserBoutonListener(this.fenetre));
		
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
		fichier.add(supprimerBDD);
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
		
		lTitreBDD = new JLabel("nouvelle BDD");
		lTitreBDD.setHorizontalAlignment(SwingConstants.CENTER);
		panneauListeTable = new JPanel();
		modeleListe = new DefaultListModel<String>();
		modeleListe.addElement("+ NouvelleTable");
		tableauListeTable = new JList<String>(modeleListe);
		tableauListeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableauListeTable.setLayoutOrientation(JList.VERTICAL);
		tableauListeTable.setVisibleRowCount(5);
		panneauTableau = new JScrollPane(tableauListeTable);
		panneauTableau.setPreferredSize(new Dimension(250, 80));
		panneauListeTable.setLayout(new BorderLayout());
		panneauListeTable.add(panneauTableau, BorderLayout.CENTER);
		panneauListeTable.add(lTitreBDD, BorderLayout.NORTH);
		
		//modeleListe.addElement("nouvelle BDD");
		
		/*panneauListeTable = new JPanel();
		//tableauListeTable = new JTable(new String[][]{ {"0","2","4","6","8"} }, new String[]{"Nouvelle BDD"});
		DefaultTableModel dm = new DefaultTableModel();
        dm.setDataVector(new Object[][]{{"button 1"},{"button 2"}}, new Object[]{"Nouvelle BDD"});
        tableauListeTable = new JTable(dm);
		tableauListeTable.getColumn("Nouvelle BDD").setCellRenderer(new RenduCellule());
		tableauListeTable.getColumn("Nouvelle BDD").setCellEditor(new EditeurCellule(new JCheckBox()));
		tableauListeTable.getTableHeader().setReorderingAllowed(false);
		panneauTableau = new JScrollPane(tableauListeTable);
		panneauListeTable.add(panneauTableau);
        CellEditor tce = new CellEditor("Nouvelle table");
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
		 ajouterAttribut.setName("ajouter attribut");
		 ajouterAttribut.addActionListener(new PresserBoutonListener(this.fenetre));
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
		 fChercher.setName("BarreRecherche");
		 fChercher.addFocusListener(new ChampsListener(this.fenetre));
		 fChercher.addKeyListener(new TouchePresseListener(this.fenetre));
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
	
	public void insererValeursDansTab(String[][] tab, String[] titre){
		dm = new ModeleTable(tab.length, tab[0].length+2,fenetre);
		String[] lesTitres = new String[titre.length+2];

		for(int i = 0; i<titre.length; i++){
			lesTitres[i]=titre[i];
		}
		lesTitres[lesTitres.length-2] = "Modifier";
		lesTitres[lesTitres.length-1] = "Supprimer";
		
        dm.setDataVector(tab, lesTitres);
        table = new JTable(dm);
        table.getColumn("Modifier").setCellRenderer(new RenduCellule());
        table.getColumn("Modifier").setCellEditor(new EditeurCellule(new JCheckBox(),"modifier",dm,fenetre));
        table.getColumn("Supprimer").setCellRenderer(new RenduCellule());
        table.getColumn("Supprimer").setCellEditor(new EditeurCellule(new JCheckBox(),"supprimer",dm,fenetre));
        table.setRowHeight(30);
		scrollTable = new JScrollPane(table);
		scrollTable.createVerticalScrollBar();
		panneauTable.setLayout(new BorderLayout());
		panneauTable.add(scrollTable, BorderLayout.CENTER);
		
	}
	
	/*
	public static void main(String[] args){
		JFrame fenetre = new JFrame("Connexion");
		fenetre.setContentPane(new VuePrincipale(new Fenetre()));
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.pack();
	*/
	
	

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
	
	public void ajouterTable(String table){
		modeleListe.set(tableauListeTable.getModel().getSize(),table);
		modeleListe.addElement("+ Nouvelle table");
	}


	/**
	 * @return the dm
	 */
	public ModeleTable getDm() {
		return dm;
	}
}
