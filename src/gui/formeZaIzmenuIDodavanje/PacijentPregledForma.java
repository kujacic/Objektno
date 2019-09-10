package gui.formeZaIzmenuIDodavanje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import domZdravlja.DomZdravlja;
import entiteti.Pacijent;
import entiteti.Pregled;
import enumi.StatusPregleda;
import gui.GlavniProzorPacijent;
import net.miginfocom.swing.MigLayout;


public class PacijentPregledForma extends JFrame {
	
	private Pacijent pacijent;
	private DomZdravlja dom;
	
	private JLabel lblOpis = new JLabel("Opis");
	private JTextField txtOpis = new JTextField(50);
	private JButton btnDodaj;
	private JButton btnCancel;
	
	public PacijentPregledForma(Pacijent p, DomZdravlja dom) {
		this.pacijent = p;
		this.dom = dom;
		
		setTitle("Dodavanje pregleda - " + pacijent.getKorisnickoIme());
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		initGui();
		initActions();
	}
	
	private void initGui() {
		
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]20[][]20[]");
		setLayout(layout);
		
		this.lblOpis = new JLabel("Opis");
		this.txtOpis = new JTextField(50);
		this.btnDodaj = new JButton("Dodaj pregled");
		this.btnCancel = new JButton("Izadji");
		
		add(lblOpis);
		add(txtOpis);
		add(btnDodaj);
		add(btnCancel);
	}
	
	private void initActions() {
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String opis = txtOpis.getText();
				Pregled pregled = new Pregled();
				if (pacijent.getIzabraniLekar() == null) {
					JOptionPane.showMessageDialog(null, "Sacekajte da Vam dodele novog lekara, pa tada zakazite pregled", "Nemate izabranog lekara", JOptionPane.WARNING_MESSAGE);
					return;	
				}
				if (opis.equals("")) {
					JOptionPane.showMessageDialog(null, "Unesite opis", "Prazan opis", JOptionPane.WARNING_MESSAGE);
				}
				pregled.setLekar(pacijent.getIzabraniLekar());
				pregled.setPacijent(pacijent);
				pregled.setOpis(opis);
				pregled.setStatusPregleda(StatusPregleda.ZATRAZEN);
				dom.dodajPregled(pregled);
				dom.snimiPreglede("pregledi.txt");
				JOptionPane.showMessageDialog(null, "Uspesno ste zakazali pregled.");
				setVisible(false);
				PacijentPregledForma.this.dispose();
				GlavniProzorPacijent gp = new GlavniProzorPacijent(dom, pacijent);
				gp.setVisible(true);
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				PacijentPregledForma.this.dispose();
				GlavniProzorPacijent gp = new GlavniProzorPacijent(dom, pacijent);
				gp.setVisible(true);
			}
		});
	}

}
