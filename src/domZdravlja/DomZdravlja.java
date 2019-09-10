package domZdravlja;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entiteti.Korisnik;
import entiteti.Lekar;
import entiteti.MedicinskaSestra;
import entiteti.Pacijent;
import entiteti.Pregled;
import entiteti.ZdravstvenaKnjizica;
import enumi.KategorijaOsiguranja;
import enumi.Pol;
import enumi.Sluzba;
import enumi.StatusPregleda;
import enumi.Uloga;

public class DomZdravlja {

	private ArrayList<Lekar> lekari;
	private ArrayList<MedicinskaSestra> medicinskeSestre;
	private ArrayList<Pacijent> pacijenti;
	private ArrayList<Pregled> pregledi;
	private ArrayList<ZdravstvenaKnjizica> knjizice;
	
	public DomZdravlja() {
		lekari = new ArrayList<Lekar>();
		medicinskeSestre = new ArrayList<MedicinskaSestra>();
		pacijenti = new ArrayList<Pacijent>();
		pregledi = new ArrayList<Pregled>();
		knjizice = new ArrayList<ZdravstvenaKnjizica>();
	}
	
	
	public ArrayList<Lekar> getLekari() {
		return lekari;
	}
	
	public void dodajLekara(Lekar l) {
		if (nadjiLekara(l.getKorisnickoIme()) == null) {
			lekari.add(l);
		}
	}

	public void obrisiLekara(Lekar l) {
		lekari.remove(l);
		
		for (Pacijent p : pacijenti) {
			if (p.getIzabraniLekar().getJmbg().equals(l.getJmbg())) {
				p.setIzabraniLekar(null);
			}
		}

		List<Pregled> preglediZaBrisanje = new ArrayList<>();
		for (Pregled p : pregledi) {
			if (p.getLekar().getJmbg().equals(l.getJmbg())) {
				preglediZaBrisanje.add(p);
			}
		}
		pregledi.removeAll(preglediZaBrisanje);
	}
	
	public void updateLekar(Lekar l) {
		for (Lekar lek : lekari) {
			if (lek.getJmbg().equals(l.getJmbg())) {
				lek.update(l);
			}
		}
	}
	
	public Korisnik login(String korisnickoIme, String sifra) {
		for (Lekar l : lekari) {
			if(l.getKorisnickoIme().equals(korisnickoIme) && l.getLozinka().equals(sifra)) {
				return l;
			}
		}
		for (Pacijent p : pacijenti) {
			if(p.getKorisnickoIme().equals(korisnickoIme) && p.getLozinka().equals(sifra)) {
				return p;	
			}
		}
		for (MedicinskaSestra ms : medicinskeSestre) {
			if(ms.getKorisnickoIme().equals(korisnickoIme) && ms.getLozinka().equals(sifra)) {
				return ms;
			}	
		} 
		return null;
	}
	
	public ArrayList<MedicinskaSestra> getMedicinskeSestre() {
		return medicinskeSestre;
	}
	
	public void dodajMedicinskuSestru(MedicinskaSestra ms) {
		if (nadjiMedicinskuSestru(ms.getKorisnickoIme()) == null) {
			medicinskeSestre.add(ms);
		}
	}

	public void obrisiMedicinskuSestru(MedicinskaSestra ms) {
		medicinskeSestre.remove(ms);
	}
	
	public void updateMedicinskaSestra(MedicinskaSestra medS) {
		for (MedicinskaSestra ms : medicinskeSestre) {
			if (ms.getJmbg().equals(medS.getJmbg())) {
				ms.update(medS);
			}
		}
	}
	
	public ArrayList<Pacijent> getPacijenti() {
		return pacijenti;
	}
	
	public void dodajPacijenta(Pacijent p) {
		if (nadjiPacijenta(p.getKorisnickoIme()) == null) {
			pacijenti.add(p);
		}
	}
	
	public void obrisiPacijenta(Pacijent p) {
		pacijenti.remove(p);
		for(Pregled pr : pregledi) {
			if (pr.getPacijent().getJmbg().equals(p.getJmbg())) {
				pregledi.remove(pr);
			}
		}
		for(ZdravstvenaKnjizica zk : knjizice) {
			if (p.getKnjizica().getBroj() == zk.getBroj()) {
				knjizice.remove(zk);
			}
		}
	}
	
	public void updatePacijent(Pacijent p) {
		for (Pacijent pa : pacijenti) {
			if (pa.getJmbg().equals(p.getJmbg())) {
				pa.update(p);
			}
		}
	}
	
	public ArrayList<Pregled> getPregledi() {
		return pregledi;
	}
	
	public void dodajPregled(Pregled p) {
		pregledi.add(p);
	}
	
	public void obrisiPregled(Pregled p) {
		pregledi.remove(p);
	}
	
	public ArrayList<ZdravstvenaKnjizica> getKnjizice() {
		return knjizice;
	}
	
	public void dodajKnjizicu(ZdravstvenaKnjizica k) {
		if (nadjiKnjizicu(k.getBroj()) == null) {
			knjizice.add(k);
		}
	}
	
	public void obrisiKnjizicu(ZdravstvenaKnjizica k) {
		knjizice.remove(k);
		for (Pacijent p : pacijenti) {
			if (k.getBroj() == p.getKnjizica().getBroj()) {
				obrisiPacijenta(p);
			}
		}
	}
	
	public void updateKnjizica(ZdravstvenaKnjizica zk) {
		for (ZdravstvenaKnjizica zdk : knjizice) {
			if (zdk.getBroj() == zk.getBroj()) {
				zdk.update(zk);
			}
		}
	}
	
 	public Lekar nadjiLekara(String korisnickoIme) {
	 	for(Lekar l : lekari) {
	 		if (l.getKorisnickoIme().equals(korisnickoIme))
	 			return l;
	 	}
	 	return null;
 	}
 	
 	public MedicinskaSestra nadjiMedicinskuSestru(String korisnickoIme) {
 		for (MedicinskaSestra ms : medicinskeSestre) {
 			if (ms.getKorisnickoIme().equals(korisnickoIme)) {
 				return ms;
 			}
 		}
 		return null;
 	}
 
	public Pacijent nadjiPacijenta(String korisnickoIme) {
		for (Pacijent p : pacijenti) {
			System.out.println(p.getKorisnickoIme());
			if (p.getKorisnickoIme().equals(korisnickoIme)) {
				return p;
			}
		}
		return null;
	}
	
	public Pregled nadjiPregled(Pacijent pacijent, String datum, int brojSobe) {
		for (Pregled p : pregledi) {
			if (p.getPacijent().getKorisnickoIme().equals(pacijent.getKorisnickoIme()) && p.getTerminDatum().equals(datum) && p.getSoba() == brojSobe) {
				return p;
			}
		}
		return null;
	}
	
	public ZdravstvenaKnjizica nadjiKnjizicu(int broj) {
		for (ZdravstvenaKnjizica zk : knjizice) {
			if (zk.getBroj() == broj) {
				return zk;
			}
		}
		return null;
	}
	
	public void snimiLekare(String imeFajla) {
		try {
			File file = new File("src/fajlovi/" + imeFajla);
			String sadrzaj = "";
			for (Lekar lekar : lekari) {
				sadrzaj += lekar.getUloga().toString() + "|" + lekar.getIme() + "|" + lekar.getPrezime() + "|" + lekar.getJmbg() + "|" + lekar.getAdresa() + "|" 
						+ lekar.getBrojTelefona() + "|" + lekar.getKorisnickoIme() + "|" 
						+ lekar.getLozinka() + "|" + lekar.getPlata() + "|" 
						+ lekar.getSpecijalizacija() + "|"
								+ lekar.getPol() + "|" + lekar.getSluzba().toString() + "\n"; 
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(sadrzaj);
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			System.out.println("Greska prilikom snimanja lekara");
		}
	}
	
	public void snimiMedicinskeSestre(String imeFajla) {
		try {
			File file = new File("src/fajlovi/" + imeFajla);
			String sadrzaj = "";
			for (MedicinskaSestra ms : medicinskeSestre) {
				sadrzaj += ms.getUloga().toString() + "|" + ms.getIme() + "|" + ms.getPrezime() + "|" + ms.getJmbg() + "|"
						 + ms.getAdresa() + "|" 
						+ ms.getBrojTelefona() + "|" + ms.getKorisnickoIme() + "|" 
						+ ms.getLozinka() + "|" + ms.getPlata() + "|" + ms.getPol() + "|" 
						+ ms.getSluzba().toString() + "\n"; 
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(sadrzaj);
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("Greska prilikom snimanja medicinkih sestri");
		}
	}
	
	public void snimiPacijente(String imeFajla) {
		try {
			File file = new File("src/fajlovi/" + imeFajla);
			String sadrzaj = "";
			for (Pacijent p : pacijenti) {
				String izabraniLekarKorisnickoIme ;
				if (p.getIzabraniLekar() == null) {
					izabraniLekarKorisnickoIme = "" ;
				}
				else {
					izabraniLekarKorisnickoIme = p.getIzabraniLekar().getKorisnickoIme();
				}
				sadrzaj += p.getUloga().toString() + "|" + p.getIme() + "|" + p.getPrezime() + "|" + p.getJmbg() + "|" + p.getAdresa() + "|" 
						+ p.getBrojTelefona() + "|" + p.getKorisnickoIme() + "|" 
						+ p.getLozinka() + "|" + izabraniLekarKorisnickoIme + "|" 
						+ p.getKnjizica().getBroj() + "|"
						+ p.getPol() + "\n"; 
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(sadrzaj);
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("Greska prilikom snimanja pacijenta");
		}
	}
	
	public void snimiPreglede(String imeFajla) {
		try {
			File file = new File("src/fajlovi/" + imeFajla);
			String sadrzaj = "";
			for (Pregled p : pregledi) {
				String lekarIme = "";
				if (p.getLekar() != null) 
					lekarIme = p.getLekar().getKorisnickoIme();
				sadrzaj += p.getPacijent().getKorisnickoIme() + "|" + lekarIme + "|" 
						+ p.getTerminDatum() + "|" + p.getSoba() + "|"
						+ p.getOpis() + "|" + p.getStatusPregleda().toString() + "\n";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(sadrzaj); 
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("Greska prilikom snimanja pregleda");
		}
	}
	
	public void snimiKnjizice(String imeFajla) {
		try {
			File file = new File("src/fajlovi/" + imeFajla);
			String sadrzaj = "";
			for (ZdravstvenaKnjizica zk : knjizice) {
				sadrzaj += zk.getBroj() + "|" + zk.getDatumIsteka() + "|" 
						+ zk.getKategorijaOsiguranja().toString() + "\n";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(sadrzaj);
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("Greska prilikom snimanja zdravstvenih knjizica");
		}
	}
	
	public void ucitajLekare(String imeFajla) {
		try {
			File file = new File("src/fajlovi/" + imeFajla);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] niz = line.split("\\|");
				Uloga uloga = Uloga.LEKAR;
				String ime = niz[1];
				String prezime = niz[2];
				String jmbg = niz[3];
				String adresa = niz[4];
				String brojTelefona = niz[5];
				String korisnickoIme = niz[6];
				String lozinka = niz[7];
				int plata = Integer.parseInt(niz[8]);
				String specijalizacija = niz[9];
				Pol pol;
				if (niz[10].equals("MUSKI"))
					pol = Pol.MUSKI;
				else 
					pol = Pol.ZENSKI;
				Sluzba sluzba;
				if (niz[11].equals("SLUZBA_OPSTE_MEDICINE"))
					sluzba = Sluzba.SLUZBA_OPSTE_MEDICINE;
				else if (niz[11].equals("SLUZBA_ZDRAVSTVENE_ZASTITE_DECE"))
					sluzba = Sluzba.SLUZBA_ZDRAVSTVENE_ZASTITE_DECE;
				else if (niz[11].equals("STOMATOLOSKA_SLUZBA"))
					sluzba = Sluzba.STOMATOLOSKA_SLUZBA;
				else 
					sluzba = Sluzba.SLUZBA_ZDRAVSTVENE_ZASTITE_RADNIKA;
				Lekar lekar = new Lekar(ime, prezime, jmbg, adresa, brojTelefona, korisnickoIme, lozinka, plata, specijalizacija, pol, sluzba);
				lekari.add(lekar);
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("Greska prilikom ucitavanja lekara");
		}
	}
	
	public void ucitajMedicinskeSestre(String imeFajla) {
		try {
			File file = new File("src/fajlovi/" + imeFajla);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] niz = line.split("\\|");
				Uloga uloga = Uloga.MEDICINSKA_SESTRA;
				String ime = niz[1];
				String prezime = niz[2];
				String jmbg = niz[3];
				String adresa = niz[4];
				String brojTelefona = niz[5];
				String korisnickoIme = niz[6];
				String lozinka = niz[7];
				int plata = Integer.parseInt(niz[8]);
				Pol pol;
				if (niz[9].equals("MUSKI"))
					pol = Pol.MUSKI;
				else 
					pol = Pol.ZENSKI;
				
				Sluzba sluzba;
				if (niz[10].equals("SLUZBA_OPSTE_MEDICINE"))
					sluzba = Sluzba.SLUZBA_OPSTE_MEDICINE;
				else if (niz[10].equals("SLUZBA_ZDRAVSTVENE_ZASTITE_DECE"))
					sluzba = Sluzba.SLUZBA_ZDRAVSTVENE_ZASTITE_DECE;
				else if (niz[10].equals("STOMATOLOSKA_SLUZBA"))
					sluzba = Sluzba.STOMATOLOSKA_SLUZBA;
				else if (niz[10].equals("SLUZBA_ZDRAVSTVENE_ZASTITE_RADNIKA"))
					sluzba = Sluzba.SLUZBA_ZDRAVSTVENE_ZASTITE_RADNIKA;
				else if (niz[10].equals("SLUZBA_ZA_PRAVNE_I_EKONOMSKE_POSLOVE"))
					sluzba = Sluzba.SLUZBA_ZA_PRAVNE_I_EKONOMSKE_POSLOVE;
				else 
					sluzba = Sluzba.SLUZBA_ZA_TEHNICKE_POSLOVE;
				MedicinskaSestra ms = new MedicinskaSestra(ime, prezime, jmbg, adresa, brojTelefona, korisnickoIme, lozinka, plata, pol, sluzba);
				medicinskeSestre.add(ms);
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("Greska prilikom ucitavanja medicinskih sestri");
		}
	}
	
	public void ucitajPacijente(String imeFajla) {
		try {
			File file = new File("src/fajlovi/" + imeFajla);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] niz = line.split("\\|");
				Uloga uloga = Uloga.PACIJENT;
				String ime = niz[1];
				String prezime = niz[2];
				String jmbg = niz[3];
				String adresa = niz[4];
				String brojTelefona = niz[5];
				String korisnickoIme = niz[6];
				String lozinka = niz[7];
				Lekar lekar = nadjiLekara(niz[8]);
				ZdravstvenaKnjizica knjizica = nadjiKnjizicu(Integer.parseInt(niz[9]));
				Pol pol;
				if (niz[10].equals("MUSKI"))
					pol = Pol.MUSKI;
				else 
					pol = Pol.ZENSKI;
				
				Pacijent p = new Pacijent(ime, prezime, jmbg, adresa, brojTelefona, korisnickoIme, lozinka, lekar, knjizica, pol);
				pacijenti.add(p);
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("Greska prilikom ucitavanja pacijenata");
		}
	}
	
	public void ucitajPreglede(String imeFajla) {
		try {
			File file = new File("src/fajlovi/" + imeFajla);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] niz = line.split("\\|");
				Pacijent pacijent = nadjiPacijenta(niz[0]);
				System.out.println("ucitaj " + niz[0]);
				Lekar lekar = nadjiLekara(niz[1]);
				String terminDatum = niz[2];
				int soba = Integer.parseInt(niz[3]);
				String opis = niz[4];
				StatusPregleda status;
				if (niz[5].equals("ZATRAZEN")) {
					status = StatusPregleda.ZATRAZEN;
				}
				else if (niz[5].equals("ZAKAZAN")) {
					status = StatusPregleda.ZAKAZAN;
				}
				else if (niz[5].equals("OTKAZAN")) {
					status = StatusPregleda.OTKAZAN;
				}
				else {
					status = StatusPregleda.ZAVRSEN;
				}
				Pregled pregled = new Pregled(pacijent, lekar, terminDatum, soba, opis, status);
				pregledi.add(pregled);
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("Greska prilikom ucitavanja pregleda");
		}
	}
	
	public void ucitajKnjizice(String imeFajla) {
		try {
			File file = new File("src/fajlovi/" + imeFajla);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] niz = line.split("\\|");
				int broj = Integer.parseInt(niz[0]);
				String datumIsteka = niz[1];
				
				KategorijaOsiguranja kategorijaO;
				if (niz[2].equals("JEDAN")) {
					kategorijaO = KategorijaOsiguranja.JEDAN;
				}
				else if (niz[2].equals("DVA")) {
					kategorijaO = KategorijaOsiguranja.DVA;
				}
				else {
					kategorijaO = KategorijaOsiguranja.TRI;
				}
				ZdravstvenaKnjizica knjizica = new ZdravstvenaKnjizica(broj, datumIsteka, kategorijaO);
				knjizice.add(knjizica);
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("Greska prilikom ucitavanja zdravstvenih knjizica");
		}
	}
	
}
