package entiteti;

import enumi.KategorijaOsiguranja;

public class ZdravstvenaKnjizica {

	private int broj;
	private String datumIsteka;
	
	private KategorijaOsiguranja kategorijaOsiguranja; 
	
	public ZdravstvenaKnjizica() {
		
	}
	
	public ZdravstvenaKnjizica(int broj, String datumIsteka, KategorijaOsiguranja kategorijaOsiguranja) {
		this.broj = broj;
		this.datumIsteka = datumIsteka;
		this.kategorijaOsiguranja = kategorijaOsiguranja;
	}

	public int getBroj() {
		return broj;
	}

	public void setBroj(int broj) {
		this.broj = broj;
	}

	public String getDatumIsteka() {
		return datumIsteka;
	}

	public void setDatumIsteka(String datumIsteka) {
		this.datumIsteka = datumIsteka;
	}

	public KategorijaOsiguranja getKategorijaOsiguranja() {
		return kategorijaOsiguranja;
	}

	public void setKategorijaOsiguranja(KategorijaOsiguranja kategorijaOsiguranja) {
		this.kategorijaOsiguranja = kategorijaOsiguranja;
	}
	
	public void update(ZdravstvenaKnjizica zk) {
		if (zk.getBroj() != 0) {
			setBroj(zk.getBroj());
		}
		if (!zk.getDatumIsteka().isEmpty()) {
			setDatumIsteka(zk.getDatumIsteka());
		}
	}
	
}
