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
import entiteti.Lekar;
import enumi.Pol;
import enumi.Sluzba;
import net.miginfocom.swing.MigLayout;

public class LekariForma extends JFrame {

	private DomZdravlja dom;	
	private Lekar lekar;
	
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
	private JLabel lblSpecijalizacija = new JLabel("Specijalizacija");
	private JTextField txtSpecijalizacija = new JTextField(20);
	private JLabel lblPol = new JLabel("Pol");
	private JLabel lblSluzba = new JLabel("Sluzba");
	private JComboBox<Pol> cbPol = new JComboBox<Pol>(Pol.values());
	private JComboBox<Sluzba> cbSluzba;
	private JButton btnOk = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");
	
	public LekariForma(DomZdravlja dom, Lekar lekar) {
		
		Sluzba[] nizSluzbi = new Sluzba[Sluzba.values().length - 2];
		int i = 0;
		for (Sluzba sl: Sluzba.values()) {
			if (!(sl.name().equals("SLUZBA_ZA_PRAVNE_I_EKONOMSKE_POSLOVE")) && !(sl.name().equals("SLUZBA_ZA_TEHNICKE_POSLOVE"))) {
				nizSluzbi[i] = sl;
				i++;
			}
		}
		cbSluzba = new JComboBox<Sluzba>(nizSluzbi);
		
		this.dom = dom;
		this.lekar = lekar;
		if(this.lekar == null) {
			setTitle("Dodavanje lekara");
		}else {
			setTitle("Izmena podataka - " + this.lekar.getKorisnickoIme());
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
		
		if(this.lekar != null) {
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
		add(lblSpecijalizacija);
		add(txtSpecijalizacija);
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
					String specijalizacija = txtSpecijalizacija.getText().trim();
					Pol pol = (Pol) cbPol.getSelectedItem();
					Sluzba sluzba = (Sluzba) cbSluzba.getSelectedItem();
					if(lekar == null) {
						lekar = new Lekar(ime, prezime, jmbg, adresa, brojTelefona, korisnickoIme, lozinka, plata, specijalizacija, pol, sluzba);
						dom.dodajLekara(lekar);
					}else {
						lekar.setIme(ime);
						lekar.setPrezime(prezime);
						lekar.setJmbg(jmbg);
						lekar.setAdresa(adresa);
						lekar.setBrojTelefona(brojTelefona);
						lekar.setKorisnickoIme(korisnickoIme);
						lekar.setLozinka(lozinka);
						lekar.setPlata(plata);
						lekar.setSpecijalizacija(specijalizacija);
						lekar.setPol(pol);
						lekar.setSluzba(sluzba);
					}
					dom.snimiLekare("lekari.txt");
					LekariForma.this.dispose();
					LekariForma.this.setVisible(false);
				}
			}
		});
		this.btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				LekariForma.this.dispose();
			}
		});
	}
	
	private void popuniPolja() {
		txtIme.setText(this.lekar.getIme());
		txtPrezime.setText(this.lekar.getPrezime());
		txtJmbg.setText(this.lekar.getJmbg());
		txtAdresa.setText(this.lekar.getAdresa());
		txtBrojTelefona.setText(this.lekar.getBrojTelefona());
		txtKorisnickoIme.setText(this.lekar.getKorisnickoIme());
		pfLozinka.setText(this.lekar.getLozinka());
		txtPlata.setText(((Integer)(this.lekar.getPlata())).toString());
		txtSpecijalizacija.setText(this.lekar.getSpecijalizacija());
		cbPol.setSelectedItem(this.lekar.getPol());
		cbSluzba.setSelectedItem(this.lekar.getSluzba());
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
		if(txtSpecijalizacija.getText().trim().equals("")) {
			poruka += "- Unesite specijalizaciju\n";
			ok = false;
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}
}
	
	