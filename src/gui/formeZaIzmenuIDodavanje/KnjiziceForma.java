package gui.formeZaIzmenuIDodavanje;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import domZdravlja.DomZdravlja;
import entiteti.ZdravstvenaKnjizica;
import enumi.KategorijaOsiguranja;
import net.miginfocom.swing.MigLayout;

public class KnjiziceForma extends JFrame {
	
	private DomZdravlja dom;
	private ZdravstvenaKnjizica knjizica;
	
	private JLabel lblBroj = new JLabel("Broj");
	private JTextField txtBroj = new JTextField(20);
	private JLabel lblDatumIsteka = new JLabel("Datum isteka");
	private JTextField txtDatumIsteka = new JTextField(20);
	private JLabel lblKategorijaOsiguranja = new JLabel("Kategorija osiguranja");
	private JComboBox<KategorijaOsiguranja> cbKategorijaOsiguranja = new JComboBox<KategorijaOsiguranja>(KategorijaOsiguranja.values());
	private JButton btnOk = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");
	
	public KnjiziceForma(DomZdravlja dom, ZdravstvenaKnjizica knjizica) {
		this.dom = dom;
		this.knjizica = knjizica;
		if(this.knjizica == null) {
			setTitle("Dodavanje zdravstvene knjizice"); 
		}else {
			setTitle("Izmena podataka - " + this.knjizica.getBroj());
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initGUI();
		initActions();
		setResizable(false);
		pack();
	}

	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		
		if(this.knjizica != null) {
		popuniPolja();
		}
		
		add(lblBroj);
		add(txtBroj);
		add(lblDatumIsteka);
		add(txtDatumIsteka);
		add(lblKategorijaOsiguranja);
		add(cbKategorijaOsiguranja);
		add(new JLabel());
		add(btnOk, "split 2");
		add(btnCancel);
	
	}
	
	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validacija() == true) {
					int broj = Integer.parseInt(txtBroj.getText().trim());
					String datumIsteka = txtDatumIsteka.getText().trim();
					KategorijaOsiguranja kategorijaOsiguranja = (KategorijaOsiguranja) cbKategorijaOsiguranja.getSelectedItem();
					if(knjizica == null) {
						knjizica = new ZdravstvenaKnjizica(broj, datumIsteka, kategorijaOsiguranja);
						dom.dodajKnjizicu(knjizica);
					}else {
						knjizica.setBroj(broj);
						knjizica.setDatumIsteka(datumIsteka);
						knjizica.setKategorijaOsiguranja(kategorijaOsiguranja);
					}
					dom.snimiKnjizice("zdravstveneKnjizice.txt");
					dom.snimiPacijente("pacijenti.txt");
					KnjiziceForma.this.dispose();
					KnjiziceForma.this.setVisible(false);
				}
			}
		});
		this.btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				KnjiziceForma.this.dispose();
			}
		});
	}
	
	private void popuniPolja() {
		txtBroj.setText(((Integer)(this.knjizica.getBroj())).toString());
		txtDatumIsteka.setText(this.knjizica.getDatumIsteka());
		cbKategorijaOsiguranja.setSelectedItem(this.knjizica.getKategorijaOsiguranja());
	}

	private boolean validacija() {
		boolean ok = true;
		String poruka = "Molimo popravite sledece greske u unosu:\n";
		if(txtBroj.getText().trim().equals("")) {
			poruka += "- Unesite broj\n";
			ok = false;
		}
		if(txtDatumIsteka.getText().trim().equals("")) {
			poruka += "- Unesite datum isteka\n";
			ok = false;
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}
}
