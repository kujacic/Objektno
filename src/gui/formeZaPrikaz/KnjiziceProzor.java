package gui.formeZaPrikaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
import entiteti.ZdravstvenaKnjizica;
import gui.formeZaIzmenuIDodavanje.KnjiziceForma;

public class KnjiziceProzor extends JFrame {
	
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnAdd = new JButton();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	
	private DefaultTableModel tableModel;
	private JTable knjiziceTabela;
	
	private DomZdravlja domZdravlja;
	
	public KnjiziceProzor(DomZdravlja domZdravlja) {
		this.domZdravlja = domZdravlja;
		setTitle("Zdravstvene knjizice");
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
		
		String[] zaglavlje = new String[] {"Broj", "Datum isteka", "Kategorija osiguranja"};
		Object[ ][] podaci = new Object[this.domZdravlja.getKnjizice().size()][zaglavlje.length];
		
		for(int i=0; i<this.domZdravlja.getKnjizice().size(); i++) {
			ZdravstvenaKnjizica knjizica = this.domZdravlja.getKnjizice().get(i);
			podaci[i][0] = knjizica.getBroj();
			podaci[i][1] = knjizica.getDatumIsteka();
			podaci[i][2] = knjizica.getKategorijaOsiguranja();
		}

		tableModel = new DefaultTableModel(podaci, zaglavlje);
		knjiziceTabela = new JTable(tableModel);
		knjiziceTabela = new JTable(tableModel);
		knjiziceTabela.setRowSelectionAllowed(true);
		knjiziceTabela.setColumnSelectionAllowed(false);
		knjiziceTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		knjiziceTabela.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(knjiziceTabela);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	private void initActions() {
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KnjiziceForma kf = new KnjiziceForma(domZdravlja, null);
				kf.setVisible(true);
			}
		});
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = knjiziceTabela.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel)knjiziceTabela.getModel();
					int broj = (Integer)model.getValueAt(red, 0);
					ZdravstvenaKnjizica knjizica = domZdravlja.nadjiKnjizicu(broj);
					if(knjizica != null) {
						KnjiziceForma kf = new KnjiziceForma(domZdravlja, knjizica);
						kf.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranu zdravstvenu knjizicu!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = knjiziceTabela.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel)knjiziceTabela.getModel();
					int broj = (Integer)model.getValueAt(red, 0);
					ZdravstvenaKnjizica knjizica = domZdravlja.nadjiKnjizicu(broj);
					if(knjizica != null) {
						int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete knjizicu?", knjizica.getBroj() + " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							List<Pacijent> listaPacijenata = domZdravlja.getPacijenti();
							for (Pacijent pac: listaPacijenata) {
								if (pac.getKnjizica().getBroj() == broj) {
									domZdravlja.obrisiPacijenta(pac);
								}
							}
							domZdravlja.obrisiKnjizicu(knjizica);
							model.removeRow(red);
							domZdravlja.snimiKnjizice("zdravstveneKnjizice.txt");
							domZdravlja.snimiPacijente("pacijenti.txt");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranu knjizicu!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}

}