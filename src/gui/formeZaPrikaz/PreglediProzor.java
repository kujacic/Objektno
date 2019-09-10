package gui.formeZaPrikaz;

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
import entiteti.Pacijent;
import entiteti.Pregled;
import gui.formeZaIzmenuIDodavanje.PreglediForma;

public class PreglediProzor extends JFrame {
	
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnAdd = new JButton();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	
	private DefaultTableModel tableModelPregledi;
	private JTable preglediTabela;

	private DomZdravlja domZdravlja;
	
	public PreglediProzor(DomZdravlja domZdravlja) {
		this.domZdravlja = domZdravlja;
		setTitle("Pregledi");
		setSize(800, 300);
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
		
		ArrayList<Pregled> sviPregledi = domZdravlja.getPregledi();
		String[] zaglavljePregled = new String[] {"Pacijent", "Lekar", "Termin", "Soba", "Opis", "Status"};
		Object[][] podaciPregled = new Object[sviPregledi.size()][zaglavljePregled.length];
		
		for (int i = 0; i < sviPregledi.size(); i++) {
			Pregled pr = sviPregledi.get(i);
			podaciPregled[i][0] = pr.getPacijent().getKorisnickoIme();
			podaciPregled[i][1] = pr.getLekar().getKorisnickoIme();
			podaciPregled[i][2] = pr.getTerminDatum();
			podaciPregled[i][3] = pr.getSoba();
			podaciPregled[i][4] = pr.getOpis();
			podaciPregled[i][5] = pr.getStatusPregleda().toString();
		}
		tableModelPregledi = new DefaultTableModel(podaciPregled, zaglavljePregled);
		preglediTabela = new JTable(tableModelPregledi);
		preglediTabela = new JTable(tableModelPregledi);
		preglediTabela.setRowSelectionAllowed(true);
		preglediTabela.setColumnSelectionAllowed(false);
		preglediTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		preglediTabela.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(preglediTabela);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	private void initActions() {
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PreglediForma pf = new PreglediForma(domZdravlja, null);
				pf.setVisible(true);
			}
		});
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = preglediTabela.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel)preglediTabela.getModel();
					String pacijent = model.getValueAt(red, 0).toString();
					Pacijent pac = domZdravlja.nadjiPacijenta(pacijent);
					String terminDatum = model.getValueAt(red, 2).toString();
					int brojSobe = ((Integer)model.getValueAt(red, 3));
					Pregled pregled = domZdravlja.nadjiPregled(pac, terminDatum, brojSobe);
					if(pregled != null) {
						PreglediForma pf = new PreglediForma(domZdravlja, pregled);
						pf.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog pregled!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = preglediTabela.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel)preglediTabela.getModel();
					String pacijent = model.getValueAt(red, 0).toString();
					Pacijent pac = domZdravlja.nadjiPacijenta(pacijent);
					String terminDatum = model.getValueAt(red, 2).toString();
					int brojSobe = ((Integer)model.getValueAt(red, 3));
					Pregled pregled = domZdravlja.nadjiPregled(pac, terminDatum, brojSobe);
					if(pregled != null) {
						int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete pregled?", domZdravlja.nadjiPregled(pac, terminDatum, brojSobe) + " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							domZdravlja.obrisiPregled(pregled);
							model.removeRow(red);
							domZdravlja.snimiPreglede("pregledi.txt");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani pregled!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	
}
