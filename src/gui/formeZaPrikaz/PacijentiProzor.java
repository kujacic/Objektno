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
import entiteti.Pacijent;
import gui.formeZaIzmenuIDodavanje.PacijentiForma;

public class PacijentiProzor extends JFrame {
	
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnAdd = new JButton();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	
	private DefaultTableModel tableModel;
	private JTable pacijentiTabela;
	
	private DomZdravlja domZdravlja;
	
	public PacijentiProzor(DomZdravlja domZdravlja) {
		this.domZdravlja = domZdravlja;
		setTitle("Pacijenti");
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
		
		String[] zaglavlje = new String[] {"Ime", "Prezime", "Jmbg", "Adresa", "Broj telefona", "Korisnicko ime", "Lozinka", "Izabrani lekar", "Knjizica", "Pol"};
		Object[][] podaci = new Object[this.domZdravlja.getPacijenti().size()][zaglavlje.length];
		
		for(int i=0; i<this.domZdravlja.getPacijenti().size(); i++) {
			Pacijent pacijent = this.domZdravlja.getPacijenti().get(i);
			podaci[i][0] = pacijent.getIme();
			podaci[i][1] = pacijent.getPrezime();
			podaci[i][2] = pacijent.getJmbg();
			podaci[i][3] = pacijent.getAdresa();
			podaci[i][4] = pacijent.getBrojTelefona();
			podaci[i][5] = pacijent.getKorisnickoIme();
			podaci[i][6] = pacijent.getLozinka();
			podaci[i][7] = pacijent.getIzabraniLekar();
			podaci[i][8] = pacijent.getKnjizica().getBroj();
			podaci[i][9] = pacijent.getPol();

		}
		
		tableModel = new DefaultTableModel(podaci, zaglavlje);
		pacijentiTabela = new JTable(tableModel);
		pacijentiTabela = new JTable(tableModel);
		pacijentiTabela.setRowSelectionAllowed(true);
		pacijentiTabela.setColumnSelectionAllowed(false);
		pacijentiTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pacijentiTabela.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(pacijentiTabela);
		add(scrollPane, BorderLayout.CENTER);
	}	
	
	private void initActions() {
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacijentiForma pf = new PacijentiForma(domZdravlja, null);
				pf.setVisible(true);
			}
		});
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = pacijentiTabela.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel)pacijentiTabela.getModel();
					String korisnickoIme = model.getValueAt(red, 5).toString();
					Pacijent pacijent = domZdravlja.nadjiPacijenta(korisnickoIme);
					if(pacijent != null) {
						PacijentiForma pf = new PacijentiForma(domZdravlja, pacijent);
						pf.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog pacijenta!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = pacijentiTabela.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel)pacijentiTabela.getModel();
					String korisnickoIme = model.getValueAt(red, 5).toString();
					Pacijent pacijent = domZdravlja.nadjiPacijenta(korisnickoIme);
					if(pacijent != null) {
						int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete pacijenta?", pacijent.getKorisnickoIme() + " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							domZdravlja.obrisiPacijenta(pacijent);
							model.removeRow(red);
							domZdravlja.snimiPacijente("pacijenti.txt");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog pacijenta!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
}