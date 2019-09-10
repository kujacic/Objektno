package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import domZdravlja.DomZdravlja;
import entiteti.Korisnik;
import entiteti.Lekar;
import entiteti.Pregled;

public class GlavniProzorLekar extends JFrame {
	
	private DomZdravlja dom;
	private Lekar lekar;
	
	private DefaultTableModel tableModel;
	private JTable preglediTabela;
	
	public GlavniProzorLekar (DomZdravlja dom, Korisnik korisnik) {
		this.dom = dom;
		this.lekar = (Lekar) korisnik;
		
		setTitle("Pregledi");
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		initGui();
	}
	
	private void initGui() {
		ArrayList<Pregled> sviPregledi = dom.getPregledi();
		
		ArrayList<Pregled> preglediLekara = new ArrayList<>();
		for(Pregled p : sviPregledi) {
			if (p.getLekar().getKorisnickoIme().equals(lekar.getKorisnickoIme())) {
				preglediLekara.add(p);
			}
		}
		int brojPregleda = preglediLekara.size();
		String[] zaglavlje = new String[] {"Pacijent", "Termin", "Soba", "Opis", "Status"};
		Object[][] podaci = new Object[brojPregleda][zaglavlje.length];
		
		for (int i = 0; i < brojPregleda; i++) {
			Pregled pregled = preglediLekara.get(i);
			podaci[i][0] = pregled.getPacijent().getKorisnickoIme();
			podaci[i][1] = pregled.getTerminDatum();
			podaci[i][2] = pregled.getSoba();
			podaci[i][3] = pregled.getOpis();
			System.out.println(pregled.getStatusPregleda());
			podaci[i][4] = pregled.getStatusPregleda().toString();
		}
		tableModel = new DefaultTableModel(podaci, zaglavlje);
		preglediTabela = new JTable(tableModel);
		preglediTabela.setRowSelectionAllowed(true);
		preglediTabela.setColumnSelectionAllowed(false);
		preglediTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		preglediTabela.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(preglediTabela);
		add(scrollPane, BorderLayout.CENTER);
		
	}
	
}
