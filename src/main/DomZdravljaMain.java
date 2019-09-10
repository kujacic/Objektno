package main;

import domZdravlja.DomZdravlja;
import entiteti.Lekar;
import entiteti.MedicinskaSestra;
import entiteti.Pacijent;
import entiteti.Pregled;
import entiteti.ZdravstvenaKnjizica;
import gui.LoginProzor;

public class DomZdravljaMain {

		private static String LEKARI_FAJL = "lekari.txt";
		private static String MEDICINSKE_SESTRE_FAJL = "medicinskeSestre.txt";
		private static String ZDRAVSTVENE_KNJIZICE_FAJL = "zdravstveneKnjizice.txt";
		private static String PACIJENTI_FAJL = "pacijenti.txt";
		private static String PREGLEDI_FAJL = "pregledi.txt";
		
		public static void main(String[] args) {

			DomZdravlja domZdravlja = new DomZdravlja();
			
			domZdravlja.ucitajLekare(LEKARI_FAJL);
			domZdravlja.ucitajMedicinskeSestre(MEDICINSKE_SESTRE_FAJL);
			domZdravlja.ucitajKnjizice(ZDRAVSTVENE_KNJIZICE_FAJL);
			domZdravlja.ucitajPacijente(PACIJENTI_FAJL);
			domZdravlja.ucitajPreglede(PREGLEDI_FAJL);

			
			LoginProzor login = new LoginProzor(domZdravlja);
			login.setVisible(true);
			
//			ispisiSvePodatke(domZdravlja);
//			
//			Lekar l1 = new Lekar("Milana", "Milanovic", "jmbg1", "zensko", "Adresa1",
//					"broj telefona1", "korisnickoIme1", "lozinka1", 30000, "specijalizacija1",
//			Sluzba.SLUZBA_ZA_PRAVNE_I_EKONOMSKE_POSLOVE); 
//			Lekar l2 = new Lekar("Marko",
//			 "Markovic", "jmbg1", "musko", "Adresa2", "broj telefona2", "korisnickoIme2",
//			 "lozinka2", 40000, "specijalizacija2", Sluzba.SLUZBA_ZA_TEHNICKE_POSLOVE);
//			domZdravlja.dodajLekara(l1); 
//			domZdravlja.dodajLekara(l2);
//			domZdravlja.snimiLekare(LEKARI_FAJL);
//			
//			MedicinskaSestra ms = new MedicinskaSestra("Ivana", "Ivanovic", "jmbg4", "zensko", "adresa4", "brojTel4", "korisnickoIme4", "lozinka4", 35566, Sluzba.SLUZBA_OPSTE_MEDICINE);
//			domZdravlja.dodajMedicinskuSestru(ms);
//			domZdravlja.snimiMedicinskeSestre(MEDICINSKE_SESTRE_FAJL);
//			ispisiSvePodatke(domZdravlja);
//			
//			ZdravstvenaKnjizica zk = new ZdravstvenaKnjizica(1, "datumISteka", KategorijaOsiguranja.DVA);
//			domZdravlja.dodajKnjizicu(zk);
//			domZdravlja.snimiKnjizice(ZDRAVSTVENE_KNJIZICE_FAJL);
//			
//			Pacijent pa = new Pacijent("Dejan", "Rakic", "jmbg", "musko", "Ravanicka", "062244333", "Raka", "raka96", l1 , zk);
//			domZdravlja.dodajPacijenta(pa);
//			domZdravlja.snimiPacijente(PACIJENTI_FAJL);
//
//			Pregled preg = new Pregled(pa, l1, "1. maj", 3, "Pregled", StatusPregleda.ZAKAZAN);
//			domZdravlja.dodajPregled(preg);
//			domZdravlja.snimiPreglede(PREGLEDI_FAJL);
		}
		
		
		public static void ispisiSvePodatke(DomZdravlja domZdravlja) {
			for (Lekar lekar : domZdravlja.getLekari()) {
				System.out.println(lekar + "\n");
			}
			
			for (MedicinskaSestra ms : domZdravlja.getMedicinskeSestre()) {
				System.out.println(ms + "\n");
			}
			
			for (Pacijent p : domZdravlja.getPacijenti()) {
				System.out.println(p + "\n");
			}
			
			for (Pregled p : domZdravlja.getPregledi()) {
				System.out.println(p + "\n");
			}
			
			for (ZdravstvenaKnjizica zk : domZdravlja.getKnjizice()) {
				System.out.println(zk + "\n");
			}
			
		}

}
