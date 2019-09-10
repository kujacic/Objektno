package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import domZdravlja.DomZdravlja;
import entiteti.Korisnik;
import entiteti.Lekar;
import entiteti.Pacijent;
import net.miginfocom.swing.MigLayout;

public class LoginProzor extends JFrame {

	private JLabel lblPoruka;
	private JLabel lblKorisnickoIme;
	private JTextField txtKorisnickoIme;
	private JLabel lblSifra;
	private JPasswordField pfSifra;
	private JButton btnOK;
	private JButton btnCancel;
	
	private DomZdravlja domZdravlja;
	
	public LoginProzor(DomZdravlja domZdravlja) {
		this.domZdravlja = domZdravlja;
		setTitle("Prijava");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		initGUI();
		initActions();
		pack();
		
	}
	
	private void initGUI() {
		
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]20[][]20[]");
		setLayout(layout);
		
		this.lblPoruka = new JLabel("Dobrodošli. Molimo da se prijavite.");
		this.lblKorisnickoIme = new JLabel("Korisnicko ime");
		this.txtKorisnickoIme = new JTextField(20);
		this.lblSifra = new JLabel("Šifra");
		this.pfSifra = new JPasswordField(20);
		this.btnOK = new JButton("OK");
		this.btnCancel = new JButton("Cancel");
		this.getRootPane().setDefaultButton(btnOK);
		
		add(lblPoruka, "span 2");
		add(lblKorisnickoIme);
		add(txtKorisnickoIme);
		add(lblSifra);
		add(pfSifra);
		add(new JLabel());
		add(btnOK, "split 2");
		add(btnCancel);
	}
	
	private void initActions() {
		
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String korisnickoIme = txtKorisnickoIme.getText().trim();
				String sifra = new String(pfSifra.getPassword()).trim();
				if(korisnickoIme.equals("") || sifra.equals("")) {
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke.");
				}else {
					Korisnik korisnik = domZdravlja.login(korisnickoIme, sifra);
					if(korisnik != null) {
						LoginProzor.this.setVisible(false);
						LoginProzor.this.dispose();
						if (korisnik instanceof Lekar) {
							GlavniProzorLekar glavni = new GlavniProzorLekar(domZdravlja, korisnik);
							glavni.setVisible(true);
						}
						else if (korisnik instanceof Pacijent) {
							GlavniProzorPacijent glavni = new GlavniProzorPacijent(domZdravlja, korisnik);
							glavni.setVisible(true);
						}	
						else {
							GlavniProzorSestra glavni = new GlavniProzorSestra(domZdravlja, korisnik);
							glavni.setVisible(true);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Pogrešni login podaci!");
					}
				}
			}
		});
			
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginProzor.this.setVisible(false);
				LoginProzor.this.dispose();
			}
		});
		
		}
}
