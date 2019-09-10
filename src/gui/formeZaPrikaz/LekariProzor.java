package gui.formeZaPrikaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import entiteti.Lekar;
import gui.formeZaIzmenuIDodavanje.LekariForma;


public class LekariProzor extends JFrame {

	private JToolBar mainToolbar = new JToolBar();
	private JButton btnAdd = new JButton();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	
	private DefaultTableModel tableModel;
	private JTable lekariTabela;
	
	private DomZdravlja domZdravlja;
	
	public LekariProzor(DomZdravlja domZdravlja) {
		this.domZdravlja = domZdravlja;
		setTitle("Lekari");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initGUI();
		initActions();
	}
	
	private void initGUI() {
		ImageIcon addIcon = new ImageIcon(getClass().getResource("/slike/add.gif"));
		btnAdd.setIcon(addIcon);
		mainToolbar.add(btnAdd);
		ImageIcon editIcon = new ImageIcon(getClass().getResource("/slike/edit.gif"));
		btnEdit.setIcon(editIcon);
		mainToolbar.add(btnEdit);
		ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/slike/remove.gif"));
		btnDelete.setIcon(deleteIcon);
		mainToolbar.add(btnDelete);
		add(mainToolbar, BorderLayout.NORTH);
		
		String[] zaglavlje = new String[] {"Ime", "Prezime", "Jmbg", "Adresa", "Broj telefona", "Korisnicko ime", "Lozinka", "Plata", "Specijalizacija", "Pol", "Sluzba"};
		Object[ ][] podaci = new Object[this.domZdravlja.getLekari().size()][zaglavlje.length];
		
		for(int i=0; i<this.domZdravlja.getLekari().size(); i++) {
			Lekar lekar = this.domZdravlja.getLekari().get(i);
			podaci[i][0] = lekar.getIme();
			podaci[i][1] = lekar.getPrezime();
			podaci[i][2] = lekar.getJmbg();
			podaci[i][3] = lekar.getAdresa();
			podaci[i][4] = lekar.getBrojTelefona();
			podaci[i][5] = lekar.getKorisnickoIme();
			podaci[i][6] = lekar.getLozinka();
			podaci[i][7] = lekar.getPlata();
			podaci[i][8] = lekar.getSpecijalizacija();
			podaci[i][9] = lekar.getPol();
			podaci[i][10] = lekar.getSluzba();

		}
		
		tableModel = new DefaultTableModel(podaci, zaglavlje);
		lekariTabela = new JTable(tableModel);
		lekariTabela = new JTable(tableModel);
		lekariTabela.setRowSelectionAllowed(true);
		lekariTabela.setColumnSelectionAllowed(false);
		lekariTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lekariTabela.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(lekariTabela);
		add(scrollPane, BorderLayout.CENTER);
	}	
	
	private void initActions() {
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LekariForma lf = new LekariForma(domZdravlja, null);
				lf.setVisible(true);
			}
		});
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = lekariTabela.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel)lekariTabela.getModel();
					String korisnickoIme = model.getValueAt(red, 5).toString();
					Lekar lekar = domZdravlja.nadjiLekara(korisnickoIme);
					if(lekar != null) {
						LekariForma lf = new LekariForma(domZdravlja, lekar);
						lf.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog lekara!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = lekariTabela.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel)lekariTabela.getModel();
					String korisnickoIme = model.getValueAt(red, 5).toString();
					Lekar lekar = domZdravlja.nadjiLekara(korisnickoIme);
					if(lekar != null) {
						int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete lekara?", lekar.getKorisnickoIme() + " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							domZdravlja.obrisiLekara(lekar);
							model.removeRow(red);
							domZdravlja.snimiPreglede("pregledi.txt");
							domZdravlja.snimiLekare("lekari.txt");
							domZdravlja.snimiPacijente("pacijenti.txt");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog lekara!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}

}
