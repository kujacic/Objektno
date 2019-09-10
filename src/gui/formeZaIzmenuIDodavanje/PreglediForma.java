package gui.formeZaIzmenuIDodavanje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import domZdravlja.DomZdravlja;
import entiteti.Lekar;
import entiteti.Pacijent;
import entiteti.Pregled;
import enumi.StatusPregleda;
import net.miginfocom.swing.MigLayout;

public class PreglediForma extends JFrame {
	private DomZdravlja dom;
	private Pregled pregled;
	
	private JComboBox<String> cbPacijent;
	private JLabel lblPacijent = new JLabel("Pacijent");
	private JLabel lblTermin = new JLabel("Termin");
	private JTextField txtTermin = new JTextField(20);
	private JLabel lblSoba = new JLabel("Soba");
	private JTextField txtSoba = new JTextField(20);
	private JLabel lblOpis = new JLabel("Opis");
	private JTextField txtOpis = new JTextField(20);
	private JLabel lblStatus = new JLabel("Status");
	private JComboBox<StatusPregleda> cbStatus = new JComboBox<StatusPregleda>(StatusPregleda.values());
	private JButton btnOk = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");
	
	public PreglediForma(DomZdravlja dom, Pregled pregled) {
		this.dom = dom;
		this.pregled = pregled;
		if(pregled != null) {
			setTitle(pregled.getPacijent().getKorisnickoIme() + " - Izmena podataka");
		}else {
			setTitle("Dodavanje pregleda");
		}
		
		String[] korisnickaImenaPacijenata = new String[dom.getPacijenti().size()];
		
		int i = 0;
		for (Pacijent p : dom.getPacijenti()) {
			korisnickaImenaPacijenata[i] = p.getKorisnickoIme();
			i++;
		}
		cbPacijent = new JComboBox<String>(korisnickaImenaPacijenata);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		initActions();
		pack();
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		
		if(this.pregled != null) {
			popuniPolja();
		}
		add(lblPacijent);
		add(cbPacijent);
		add(lblTermin);
		add(txtTermin);
		add(lblSoba);
		add(txtSoba);
		add(lblOpis);
		add(txtOpis);
		if (pregled != null) {
			add(lblStatus);
			add(cbStatus);
		}
		add(btnOk, "split 2");
		add(btnCancel);
		
	}
	
	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validacija() == true) {
					String pacijent = cbPacijent.getSelectedItem().toString();
					Pacijent pac = dom.nadjiPacijenta(pacijent);
					Lekar lek = pac.getIzabraniLekar();
					System.out.println(lek + ", " + pac);
					String termin = txtTermin.getText().trim();
					int soba = Integer.parseInt(txtSoba.getText());
					String opis = txtOpis.getText().trim();
					StatusPregleda status = (StatusPregleda) cbStatus.getSelectedItem();
					if(pregled == null) {
						pregled = new Pregled(pac, lek, termin, soba, opis, StatusPregleda.ZAKAZAN);
						dom.dodajPregled(pregled);
					}else {
						pregled.setPacijent(pac);
						pregled.setLekar(lek);
						pregled.setTerminDatum(termin);
						pregled.setSoba(soba);
						pregled.setOpis(opis);
						pregled.setStatusPregleda(status);
						
					}
					dom.snimiPreglede("pregledi.txt");
					PreglediForma.this.dispose();
					PreglediForma.this.setVisible(false);
				}
			}
		});
		this.btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				PreglediForma.this.dispose();
			}
		});
	}
	
	private void popuniPolja() {
		cbPacijent.setSelectedItem(this.pregled.getPacijent().getKorisnickoIme());
		txtTermin.setText(this.pregled.getTerminDatum());
		txtSoba.setText(((Integer)(this.pregled.getSoba())).toString());
		txtOpis.setText(this.pregled.getOpis());
		cbStatus.setSelectedItem(this.pregled.getStatusPregleda());
	}
	
	private boolean validacija() {
		boolean ok = true;
		String poruka = "Molimo popravite sledece greske u unosu:\n";
		if(txtTermin.getText().trim().equals("")) {
			poruka += "- Unesite datum termina\n";
			ok = false;
		}
		if(txtSoba.getText().trim().equals("")) {
			poruka += "- Unesite broj sobe\n";
			ok = false;
		}
		if(txtOpis.getText().trim().equals("")) {
			poruka += "- Unesite opis\n";
			ok = false;
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}
}
