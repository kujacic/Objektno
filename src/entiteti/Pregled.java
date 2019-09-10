package entiteti;



import enumi.StatusPregleda;

public class Pregled {
	
	private Pacijent pacijent;
	private Lekar lekar;
	private String terminDatum;
	private int soba;
	private String opis;
	private StatusPregleda statusPregleda;
	
	public Pregled(Pacijent pacijent, Lekar lekar, String terminDatum, int soba, String opis,
			StatusPregleda statusPregleda) {
		super();
		this.pacijent = pacijent;
		this.lekar = lekar;
		this.terminDatum = terminDatum;
		this.soba = soba;
		this.opis = opis;
		this.statusPregleda = statusPregleda;
	}

	public Pregled() {}
	
	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public String getTerminDatum() {
		return terminDatum;
	}

	public void setTerminDatum(String terminDatum) {
		this.terminDatum = terminDatum;
	}

	public int getSoba() {
		return soba;
	}

	public void setSoba(int soba) {
		this.soba = soba;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public StatusPregleda getStatusPregleda() {
		return statusPregleda;
	}

	public void setStatusPregleda(StatusPregleda statusPregleda) {
		this.statusPregleda = statusPregleda;
	}

}
