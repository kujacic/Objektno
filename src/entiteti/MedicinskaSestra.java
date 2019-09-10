package entiteti;

import enumi.Pol;
import enumi.Sluzba;
import enumi.Uloga;

public class MedicinskaSestra extends Korisnik {
	private int plata;
	private Sluzba sluzba; 
	
	public MedicinskaSestra() {
		super();
	}

	public MedicinskaSestra(String ime, String prezime, String jmbg, String adresa, String brojTelefona,
			String korisnickoIme, String lozinka, int plata, Pol pol, Sluzba sluzba) {
		super(ime, prezime, jmbg, adresa, brojTelefona, korisnickoIme, lozinka, pol, Uloga.MEDICINSKA_SESTRA);
		this.plata = plata;
		this.sluzba = sluzba;
	}

	public int getPlata() {
		return plata;
	}

	public void setPlata(int plata) {
		this.plata = plata;
	}

	public Sluzba getSluzba() {
		return sluzba;
	}

	public void setSluzba(Sluzba sluzba) {
		this.sluzba = sluzba;
	}
	
	public void update(MedicinskaSestra ms) {
		if (plata != 0) {
			setPlata(ms.getPlata());
		}
	}

}
