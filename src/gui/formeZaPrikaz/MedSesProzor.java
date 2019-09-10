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
import entiteti.MedicinskaSestra;
import gui.formeZaIzmenuIDodavanje.MedSesForma;

public class MedSesProzor extends JFrame {

	private JToolBar mainToolbar = new JToolBar();
	private JButton btnAdd = new JButton();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	
	private DefaultTableModel tableModel;
	private JTable sestreTabela;
	
	private DomZdravlja domZdravlja;
	
	public MedSesProzor(DomZdravlja domZdravlja) {
		this.domZdravlja = domZdravlja;
		setTitle("Medicinske sestre");
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
		
		String[] zaglavlje = new String[] {"Ime", "Prezime", "Jmbg", "Adresa", "Broj telefona", "Korisnicko ime", "Lozinka", "Plata", "Pol", "Sluzba"};
		Object[ ][] podaci = new Object[this.domZdravlja.getMedicinskeSestre().size()][zaglavlje.length];
		
		for(int i=0; i<this.domZdravlja.getMedicinskeSestre().size(); i++) {
			MedicinskaSestra sestra = this.domZdravlja.getMedicinskeSestre().get(i);
			podaci[i][0] = sestra.getIme();
			podaci[i][1] = sestra.getPrezime();
			podaci[i][2] = sestra.getJmbg();
			podaci[i][3] = sestra.getAdresa();
			podaci[i][4] = sestra.getBrojTelefona();
			podaci[i][5] = sestra.getKorisnickoIme();
			podaci[i][6] = sestra.getLozinka();
			podaci[i][7] = sestra.getPlata();
			podaci[i][8] = sestra.getPol();
			podaci[i][9] = sestra.getSluzba();

		}
		
		tableModel = new DefaultTableModel(podaci, zaglavlje);
		sestreTabela = new JTable(tableModel);
		sestreTabela = new JTable(tableModel);
		sestreTabela.setRowSelectionAllowed(true);
		sestreTabela.setColumnSelectionAllowed(false);
		sestreTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sestreTabela.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(sestreTabela);
		add(scrollPane, BorderLayout.CENTER);
	}	
	
	private void initActions() {
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MedSesForma mf = new MedSesForma(domZdravlja, null);
				mf.setVisible(true);
			}
		});
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = sestreTabela.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel)sestreTabela.getModel();
					String korisnickoIme = model.getValueAt(red, 5).toString();
					MedicinskaSestra sestra = domZdravlja.nadjiMedicinskuSestru(korisnickoIme);
					if(sestra != null) {
						MedSesForma mf = new MedSesForma(domZdravlja, sestra);
						mf.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranu medicinsku sestru!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = sestreTabela.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel)sestreTabela.getModel();
					String korisnickoIme = model.getValueAt(red, 5).toString();
					MedicinskaSestra sestra = domZdravlja.nadjiMedicinskuSestru(korisnickoIme);
					if(sestra != null) {
						int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete medicinsku sestru", sestra.getKorisnickoIme() + " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							domZdravlja.obrisiMedicinskuSestru(sestra);
							model.removeRow(red);
							domZdravlja.snimiMedicinskeSestre("medicinskeSestre.txt");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranu medicinsku sestru!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
}
