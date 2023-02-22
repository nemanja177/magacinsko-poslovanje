package com.magacin.service.dto;

import javax.persistence.Column;

import com.magacin.domain.MagacinskaKartica;

public class MagacinskaKarticaDTO {
	    private Long id;
	    private Double pocetnoStanjeKolicina;
	    private Double prometUlazaKolicina;
	    private Double prometIzlazaKolicina;
	    private Double ukupnaKolicina;
	    private Double pocetnoStanjeVrednosti;
	    private Double prometUlazaVrednosti;
	    private Double prometIzlazaVrednosti;
	    private Double ukupnaVrednost;
		public MagacinskaKarticaDTO() {
			super();
		}
		public MagacinskaKarticaDTO(Long id, Double pocetnoStanjeKolicina, Double prometUlazaKolicina,
				Double prometIzlazaKolicina, Double ukupnaKolicina, Double pocetnoStanjeVrednosti,
				Double prometUlazaVrednosti, Double prometIzlazaVrednosti, Double ukupnaVrednost) {
			super();
			this.id = id;
			this.pocetnoStanjeKolicina = pocetnoStanjeKolicina;
			this.prometUlazaKolicina = prometUlazaKolicina;
			this.prometIzlazaKolicina = prometIzlazaKolicina;
			this.ukupnaKolicina = ukupnaKolicina;
			this.pocetnoStanjeVrednosti = pocetnoStanjeVrednosti;
			this.prometUlazaVrednosti = prometUlazaVrednosti;
			this.prometIzlazaVrednosti = prometIzlazaVrednosti;
			this.ukupnaVrednost = ukupnaVrednost;
		}
	    
	    public MagacinskaKarticaDTO(MagacinskaKartica magacinskaKartica) {
	    	this(magacinskaKartica.getId(), 
	    			magacinskaKartica.getPocetnoStanjeKolicina(), 
	    			magacinskaKartica.getPrometUlazaKolicina(), magacinskaKartica.getPrometIzlazaKolicina(), 
	    			magacinskaKartica.getUkupnaKolicina(), 
	    			magacinskaKartica.getUkupnaVrednost(),
	    			magacinskaKartica.getPocetnoStanjeVrednosti(),
	    			magacinskaKartica.getPrometUlazaVrednosti(),
	    			magacinskaKartica.getPrometIzlazaVrednosti());
	    }
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Double getPocetnoStanjeKolicina() {
			return pocetnoStanjeKolicina;
		}
		public void setPocetnoStanjeKolicina(Double pocetnoStanjeKolicina) {
			this.pocetnoStanjeKolicina = pocetnoStanjeKolicina;
		}
		public Double getPrometUlazaKolicina() {
			return prometUlazaKolicina;
		}
		public void setPrometUlazaKolicina(Double prometUlazaKolicina) {
			this.prometUlazaKolicina = prometUlazaKolicina;
		}
		public Double getPrometIzlazaKolicina() {
			return prometIzlazaKolicina;
		}
		public void setPrometIzlazaKolicina(Double prometIzlazaKolicina) {
			this.prometIzlazaKolicina = prometIzlazaKolicina;
		}
		public Double getUkupnaKolicina() {
			return ukupnaKolicina;
		}
		public void setUkupnaKolicina(Double ukupnaKolicina) {
			this.ukupnaKolicina = ukupnaKolicina;
		}
		public Double getPocetnoStanjeVrednosti() {
			return pocetnoStanjeVrednosti;
		}
		public void setPocetnoStanjeVrednosti(Double pocetnoStanjeVrednosti) {
			this.pocetnoStanjeVrednosti = pocetnoStanjeVrednosti;
		}
		public Double getPrometUlazaVrednosti() {
			return prometUlazaVrednosti;
		}
		public void setPrometUlazaVrednosti(Double prometUlazaVrednosti) {
			this.prometUlazaVrednosti = prometUlazaVrednosti;
		}
		public Double getPrometIzlazaVrednosti() {
			return prometIzlazaVrednosti;
		}
		public void setPrometIzlazaVrednosti(Double prometIzlazaVrednosti) {
			this.prometIzlazaVrednosti = prometIzlazaVrednosti;
		}
		public Double getUkupnaVrednost() {
			return ukupnaVrednost;
		}
		public void setUkupnaVrednost(Double ukupnaVrednost) {
			this.ukupnaVrednost = ukupnaVrednost;
		}
	    
	    
}
