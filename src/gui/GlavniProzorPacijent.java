package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import domZdravlja.DomZdravlja;
import entiteti.Korisnik;
import entiteti.Pacijent;
import entiteti.Pregled;
import gui.formeZaIzmenuIDodavanje.PacijentPregledForma;

public class GlavniProzorPacijent extends JFrame {

	private DomZdravlja dom;
	private Pacijent pacijent;
	
	private DefaultTableModel tableModel;
	private JTable preglediTabela;
	
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnDodaj = new JButton("Zakazi pregled");
	private JButton btnOtkazi = new JButton();
	
	public GlavniProzorPacijent(DomZdravlja dom, Korisnik pacijent) {
		this.dom = dom;
		this.pacijent = (Pacijent) pacijent;
		
		setTitle("Pregledi");
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initGui();
		initActions();
	}
	
	private void initGui() {
		ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/slike/remove.gif"));
		btnOtkazi.setIcon(deleteIcon);
		mainToolbar.add(btnOtkazi);
		add(mainToolbar, BorderLayout.NORTH);
		
		ArrayList<Pregled> sviPregledi = dom.getPregledi();
		ArrayList<Pregled> preglediPacijenta = new ArrayList<>();
		
		for(Pregled p : sviPregledi) {
			if (p.getPacijent().getKorisnickoIme().contentEquals(pacijent.getKorisnickoIme())) {
				preglediPacijenta.add(p);
			}
		}
		int brojPregleda = preglediPacijenta.size();
		String[] zaglavlje = new String[] {"Lekar", "Termin", "Soba", "Opis", "Status"};
		Object[][] podaci = new Object[brojPregleda][zaglavlje.length];
		
		for (int i = 0; i < brojPregleda; i++) {
			Pregled pregled = preglediPacijenta.get(i);
			podaci[i][0] = pregled.getLekar().getKorisnickoIme();
			podaci[i][1] = pregled.getTerminDatum();
			if (pregled.getSoba() == 0) {
				podaci[i][2] = "";
			}
			else {
				podaci[i][2] = pregled.getSoba();
			}
			podaci[i][3] = pregled.getOpis();
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
		add(btnDodaj, BorderLayout.SOUTH);
		
	}
	
	private void initActions() {
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacijentPregledForma prozor = new PacijentPregledForma(pacijent, dom);
				prozor.setVisible(true);
				GlavniProzorPacijent.this.setVisible(false);
				GlavniProzorPacijent.this.dispose();
			}
		});
		
		btnOtkazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = preglediTabela.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel)preglediTabela.getModel();
					String p = pacijent.getKorisnickoIme();
					Pacijent pac = dom.nadjiPacijenta(p);
					
					String terminDatum = model.getValueAt(red, 1).toString();
					int brojSobe = ((Integer)model.getValueAt(red, 2));
					Pregled pregled = dom.nadjiPregled(pac, terminDatum , brojSobe );
					if(pregled != null) {
						int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete pregled?", dom.nadjiPregled(pac, terminDatum, brojSobe) + " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							dom.obrisiPregled(pregled);
							model.removeRow(red);
							dom.snimiPreglede("pregledi.txt");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani pregled!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	
	
}
