package entiteti;

import enumi.Pol;
import enumi.Uloga;

public class Pacijent extends Korisnik {
	
	private Lekar izabraniLekar;
	private ZdravstvenaKnjizica knjizica;
	
	public Pacijent() {
		super();
	}
	
	public Pacijent(String ime, String prezime, String jmbg, String adresa, String brojTelefona,
			String korisnickoIme, String lozinka, Lekar izabraniLekar, ZdravstvenaKnjizica knjizica, Pol pol) {
		super(ime, prezime, jmbg, adresa, brojTelefona,
				korisnickoIme, lozinka, pol, Uloga.PACIJENT);
		this.izabraniLekar = izabraniLekar;
		this.knjizica = knjizica;
	}

	public Lekar getIzabraniLekar() {
		return izabraniLekar;
	}

	public void setIzabraniLekar(Lekar izabraniLekar) {
		this.izabraniLekar = izabraniLekar;
	}

	public ZdravstvenaKnjizica getKnjizica() {
		return knjizica;
	}

	public void setKnjizica(ZdravstvenaKnjizica knjizica) {
		this.knjizica = knjizica;
	}
	
	public void update(Pacijent p) {
		if (p.getIzabraniLekar() != null) {
			setIzabraniLekar(p.getIzabraniLekar());
		}
		if (p.getKnjizica() != null) {
			setKnjizica(p.getKnjizica());
		}
	}
	
}
