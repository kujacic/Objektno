package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import domZdravlja.DomZdravlja;
import entiteti.Korisnik;
import entiteti.MedicinskaSestra;
import gui.formeZaPrikaz.KnjiziceProzor;
import gui.formeZaPrikaz.LekariProzor;
import gui.formeZaPrikaz.MedSesProzor;
import gui.formeZaPrikaz.PacijentiProzor;
import gui.formeZaPrikaz.PreglediProzor;

public class GlavniProzorSestra extends JFrame {

	private DomZdravlja dom;
	private MedicinskaSestra sestra;
	
	private JMenuBar mainMenu;
	private JMenu preglediMenu;
	private JMenuItem preglediItem;
	private JMenu pacijentiMenu;
	private JMenuItem pacijentiItem;
	private JMenu lekariMenu;
	private JMenuItem lekariItem;
	private JMenu medicinskeSestreMenu;
	private JMenuItem medicinskeSestreItem;
	private JMenu zdravstveneKnjiziceMenu;
	private JMenuItem zdravstveneKnjiziceItem;
	
	public GlavniProzorSestra(DomZdravlja dom, Korisnik sestra) {
		this.dom = dom;
		this.sestra = (MedicinskaSestra) sestra;
		
		setTitle("Medicinska sestra");
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initMenu();
		initActions();
	}
	
	private void initMenu() {
		this.mainMenu = new JMenuBar();
		this.preglediMenu = new JMenu("Pregledi");
		this.preglediItem = new JMenuItem("Prikazi preglede");
		this.pacijentiMenu = new JMenu("Pacijenti");
		this.pacijentiItem = new JMenuItem("Prikazi pacijente");
		this.lekariMenu = new JMenu("Lekari");
		this.lekariItem = new JMenuItem("Prikazi lekare");
		this.medicinskeSestreMenu = new JMenu("Medicinske sestre");
		this.medicinskeSestreItem = new JMenuItem("Prikazi m.sestre");
		this.zdravstveneKnjiziceMenu = new JMenu("Zdravstvene knjizice");
		this.zdravstveneKnjiziceItem = new JMenuItem("Prikazi z.knjizice");
		
		this.preglediMenu.add(preglediItem);
		this.pacijentiMenu.add(pacijentiItem);
		this.lekariMenu.add(lekariItem);
		this.medicinskeSestreMenu.add(medicinskeSestreItem);
		this.zdravstveneKnjiziceMenu.add(zdravstveneKnjiziceItem);
		
		this.mainMenu.add(preglediMenu);
		this.mainMenu.add(pacijentiMenu);
		this.mainMenu.add(lekariMenu);
		this.mainMenu.add(medicinskeSestreMenu);
		this.mainMenu.add(zdravstveneKnjiziceMenu);
		
		setJMenuBar(this.mainMenu);

	}
	
	private void initActions() {
		
		preglediItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PreglediProzor pp = new PreglediProzor(dom);
				pp.setVisible(true);
			}
		});
		
		pacijentiItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacijentiProzor pp = new PacijentiProzor(dom);
				pp.setVisible(true);
			}
		});
		
		lekariItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LekariProzor lp = new LekariProzor(dom);
				lp.setVisible(true);
			}
		});
		
		medicinskeSestreItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MedSesProzor mp = new MedSesProzor(dom);
				mp.setVisible(true);
			}
		});
		
		zdravstveneKnjiziceItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KnjiziceProzor kp = new KnjiziceProzor(dom);
				kp.setVisible(true);
			}
		});
	}
}
