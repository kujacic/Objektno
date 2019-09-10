package gui.formeZaIzmenuIDodavanje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import domZdravlja.DomZdravlja;
import entiteti.MedicinskaSestra;
import enumi.Pol;
import enumi.Sluzba;
import net.miginfocom.swing.MigLayout;

public class MedSesForma extends JFrame {
	
	private DomZdravlja dom;
	private MedicinskaSestra sestra;
	
	private JLabel lblIme = new JLabel("Ime");
	private JTextField txtIme = new JTextField(20);
	private JLabel lblPrezime = new JLabel("Prezime");
	private JTextField txtPrezime = new JTextField(20);
	private JLabel lblJmbg = new JLabel("Jmbg");
	private JTextField txtJmbg = new JTextField(20);
	private JLabel lblAdresa = new JLabel("Adresa");
	private JTextField txtAdresa = new JTextField(20);
	private JLabel lblBrojTelefona = new JLabel("Broj telefona");
	private JTextField txtBrojTelefona = new JTextField(20);
	private JLabel lblKorisnickoIme = new JLabel("Korisnicko ime");
	private JTextField txtKorisnickoIme = new JTextField(20);
	private JLabel lblLozinka = new JLabel("Lozinka");
	private JPasswordField pfLozinka = new JPasswordField(20);
	private JLabel lblPlata = new JLabel("Plata");
	private JTextField txtPlata = new JTextField(20);
	private JLabel lblPol = new JLabel("Pol");
	private JLabel lblSluzba = new JLabel("Sluzba");
	private JComboBox<Pol> cbPol = new JComboBox<Pol>(Pol.values());
	private JComboBox<Sluzba> cbSluzba = new JComboBox<Sluzba>(Sluzba.values());
	private JButton btnOk = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");
	
	public MedSesForma(DomZdravlja dom, MedicinskaSestra sestra) {
		this.dom = dom;
		this.sestra = sestra;
		if(this.sestra == null) {
			setTitle("Dodavanje medicinske sestre");
		}else {
			setTitle("Izmena podataka - " + this.sestra.getKorisnickoIme());
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
		
		if(this.sestra != null) {
		popuniPolja();
		}
		
		add(lblIme);
		add(txtIme);
		add(lblPrezime);
		add(txtPrezime);
		add(lblJmbg);
		add(txtJmbg);
		add(lblAdresa);
		add(txtAdresa);
		add(lblBrojTelefona);
		add(txtBrojTelefona);
		add(lblKorisnickoIme);
		add(txtKorisnickoIme);
		add(lblLozinka);
		add(pfLozinka);
		add(lblPlata);
		add(txtPlata);
		add(lblPol);
		add(cbPol);
		add(lblSluzba);
		add(cbSluzba);
		add(new JLabel());
		add(btnOk, "split 2");
		add(btnCancel);
	
	}
	
	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validacija() == true) {
					String ime = txtIme.getText().trim();
					String prezime = txtPrezime.getText().trim();
					String jmbg = txtJmbg.getText().trim();
					String adresa = txtAdresa.getText().trim();
					String brojTelefona = txtBrojTelefona.getText().trim();
					String korisnickoIme = txtKorisnickoIme.getText().trim();
					String lozinka = new String(pfLozinka.getPassword()).trim();
					int plata = Integer.parseInt(txtPlata.getText());
					Pol pol = (Pol) cbPol.getSelectedItem();
					Sluzba sluzba = (Sluzba) cbSluzba.getSelectedItem();
					if(sestra == null) {
						sestra = new MedicinskaSestra(ime, prezime, jmbg, adresa, brojTelefona, korisnickoIme, lozinka, plata, pol, sluzba);
						dom.dodajMedicinskuSestru(sestra);
					}else {
						sestra.setIme(ime);
						sestra.setPrezime(prezime);
						sestra.setJmbg(jmbg);
						sestra.setAdresa(adresa);
						sestra.setBrojTelefona(brojTelefona);
						sestra.setKorisnickoIme(korisnickoIme);
						sestra.setLozinka(lozinka);
						sestra.setPlata(plata);
						sestra.setPol(pol);
						sestra.setSluzba(sluzba);
					}
					dom.snimiMedicinskeSestre("medicinskeSestre.txt");
					MedSesForma.this.dispose();
					MedSesForma.this.setVisible(false);
				}
			}
		});
		this.btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				MedSesForma.this.dispose();
			}
		});
	}
	
	private void popuniPolja() {
		txtIme.setText(this.sestra.getIme());
		txtPrezime.setText(this.sestra.getPrezime());
		txtJmbg.setText(this.sestra.getJmbg());
		txtAdresa.setText(this.sestra.getAdresa());
		txtBrojTelefona.setText(this.sestra.getBrojTelefona());
		txtKorisnickoIme.setText(this.sestra.getKorisnickoIme());
		pfLozinka.setText(this.sestra.getLozinka());
		txtPlata.setText(((Integer)(this.sestra.getPlata())).toString());
		cbPol.setSelectedItem(this.sestra.getPol());
		cbSluzba.setSelectedItem(this.sestra.getSluzba());
	}
	
	private boolean validacija() {
		boolean ok = true;
		String poruka = "Molimo popravite sledece greske u unosu:\n";
		if(txtIme.getText().trim().equals("")) {
			poruka += "- Unesite ime\n";
			ok = false;
		}
		if(txtPrezime.getText().trim().equals("")) {
			poruka += "- Unesite prezime\n";
			ok = false;
		}
		if(txtJmbg.getText().trim().equals("")) {
			poruka += "- Unesite jmbg\n";
			ok = false;
		}
		if(txtAdresa.getText().trim().equals("")) {
			poruka += "- Unesite adresu\n";
			ok = false;
		}
		if(txtBrojTelefona.getText().trim().equals("")) {
			poruka += "- Unesite broj telefona\n";
			ok = false;
		}
		if(txtKorisnickoIme.getText().trim().equals("")) {
			poruka += "- Unesite korisnicko ime\n";
			ok = false;
		}
		String lozinka = new String(pfLozinka.getPassword()).trim();
		if(lozinka.trim().equals("")) {
			poruka += "- Unesite lozinku\n";
			ok = false;
		}
		if(txtPlata.getText().trim().equals("")) {
			poruka += "- Unesite platu\n";
			ok = false;
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}

}
