package entiteti;

import enumi.Pol;
import enumi.Sluzba;
import enumi.Uloga;

public class Lekar extends Korisnik {

	private int plata;
	private String specijalizacija;
	private Sluzba sluzba;
	
	public Lekar() {
		super();
	}
	
	public Lekar(String ime, String prezime, String jmbg, String adresa, String brojTelefona,
			String korisnickoIme, String lozinka, int plata, String specijalizacija, Pol pol, Sluzba sluzba) {
		super(ime, prezime, jmbg, adresa, brojTelefona,
				korisnickoIme, lozinka , pol, Uloga.LEKAR);
		this.plata = plata;
		this.specijalizacija = specijalizacija;
		this.sluzba = sluzba;
	}



	public int getPlata() {
		return plata;
	}

	public void setPlata(int plata) {
		this.plata = plata;
	}

	public String getSpecijalizacija() {
		return specijalizacija;
	}

	public void setSpecijalizacija(String specijalizacija) {
		this.specijalizacija = specijalizacija;
	}

	public Sluzba getSluzba() {
		return sluzba;
	}

	public void setSluzba(Sluzba sluzba) {
		this.sluzba = sluzba;
	}
	
	public void update(Lekar l) {
		if (l.plata != 0) {
			setPlata(l.getPlata());
		}
		if (!l.getSpecijalizacija().isEmpty()) {
			setSpecijalizacija(l.getSpecijalizacija());
		}
	}
	
}

