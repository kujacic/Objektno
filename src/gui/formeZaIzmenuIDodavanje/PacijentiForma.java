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
import entiteti.Pacijent;
import entiteti.ZdravstvenaKnjizica;
import enumi.Pol;
import net.miginfocom.swing.MigLayout;

public class PacijentiForma extends JFrame {
	
	private DomZdravlja dom;
	private Pacijent pacijent;
	
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
	private JLabel lblPol = new JLabel("Pol");
	private JLabel lblIzabraniLekar = new JLabel("Izabrani lekar");
	private JComboBox<String> cbIzabraniLekar;
	private JLabel lblZdravstvenaKnjizica = new JLabel("Zdravstvena knjizica");
	private JTextField txtZdravstvenaKnjizica = new JTextField(20);
	private JComboBox<Pol> cbPol = new JComboBox<Pol>(Pol.values());
	private JButton btnOk = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");
	
	
	
	public PacijentiForma(DomZdravlja dom, Pacijent pacijent) {
		this.dom = dom;
		this.pacijent = pacijent;
		String[] lekariNiz = new String[dom.getLekari().size()];
		for (int i = 0; i < lekariNiz.length; i++) {
			lekariNiz[i] = dom.getLekari().get(i).getKorisnickoIme();
		}
		this.cbIzabraniLekar = new JComboBox<String>(lekariNiz);
		if(this.pacijent == null) {
			setTitle("Dodavanje pacijenta");
		}else {
			setTitle("Izmena podataka - " + this.pacijent.getKorisnickoIme());
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
		
		if(this.pacijent != null) {
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
		add(lblIzabraniLekar);
		add(cbIzabraniLekar);
		add(lblZdravstvenaKnjizica);
		add(txtZdravstvenaKnjizica);
		add(lblPol);
		add(cbPol);
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
					String izabraniLekarStr = cbIzabraniLekar.getSelectedItem().toString();
					Lekar izabraniLekar = dom.nadjiLekara(izabraniLekarStr);
					if (txtZdravstvenaKnjizica.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Niste uneli knjizicu za pacijenta", "Nije uneta knjizica", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int brZdravstveneKnjizice = Integer.parseInt(txtZdravstvenaKnjizica.getText());
					ZdravstvenaKnjizica zdravstvenaKnjizica = dom.nadjiKnjizicu(brZdravstveneKnjizice);
					if (zdravstvenaKnjizica == null) {
						JOptionPane.showMessageDialog(null, "Niste uneli postojecu knjizicu", "Neispravna knjizica", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					Pol pol = (Pol) cbPol.getSelectedItem();
					if(pacijent == null) {
						for (Pacijent pac: dom.getPacijenti()) {
							if (pac.getKnjizica().getBroj() == brZdravstveneKnjizice) {
								JOptionPane.showMessageDialog(null, "Ova knjizica je zauzeta!", "Zauzeta knjizica", JOptionPane.WARNING_MESSAGE);
								return;
							}
						}	
						pacijent = new Pacijent(ime, prezime, jmbg, adresa, brojTelefona, korisnickoIme, lozinka, izabraniLekar, zdravstvenaKnjizica, pol);
						dom.dodajPacijenta(pacijent);
					}else {
						pacijent.setIme(ime);
						pacijent.setPrezime(prezime);
						pacijent.setJmbg(jmbg);
						pacijent.setAdresa(adresa);
						pacijent.setBrojTelefona(brojTelefona);
						pacijent.setKorisnickoIme(korisnickoIme);
						pacijent.setLozinka(lozinka);
						pacijent.setIzabraniLekar(izabraniLekar);
						pacijent.setKnjizica(zdravstvenaKnjizica);
						pacijent.setPol(pol);
					}
					dom.snimiPacijente("pacijenti.txt");
					PacijentiForma.this.dispose();
					PacijentiForma.this.setVisible(false);
				}
			}
		});
		this.btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				PacijentiForma.this.dispose();
			}
		});
	}
	
	private void popuniPolja() {
		txtIme.setText(this.pacijent.getIme());
		txtPrezime.setText(this.pacijent.getPrezime());
		txtJmbg.setText(this.pacijent.getJmbg());
		txtAdresa.setText(this.pacijent.getAdresa());
		txtBrojTelefona.setText(this.pacijent.getBrojTelefona());
		txtKorisnickoIme.setText(this.pacijent.getKorisnickoIme());
		pfLozinka.setText(this.pacijent.getLozinka());
		if (this.pacijent.getIzabraniLekar() != null) {
			for (int i = 0; i < this.dom.getLekari().size(); i++) {
				if (this.dom.getLekari().get(i).getKorisnickoIme().equals(this.pacijent.getIzabraniLekar().getKorisnickoIme())) {
					cbIzabraniLekar.setSelectedIndex(i);
				}
			}
		}
		txtZdravstvenaKnjizica.setText(((Integer)(this.pacijent.getKnjizica().getBroj())).toString());
		cbPol.setSelectedItem(this.pacijent.getPol());
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
		if(txtZdravstvenaKnjizica.getText().trim().equals("")) {
			poruka += "- Unesite broj zdravstvene knjizice\n";
			ok = false;
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}
	
}
