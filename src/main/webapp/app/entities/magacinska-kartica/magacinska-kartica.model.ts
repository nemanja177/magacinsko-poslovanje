export interface IMagacinskaKartica {
  id: number;
  pocetnoStanjeKolicina?: number | null;
  prometUlazaKolicina?: number | null;
  prometIzlazaKolicina?: number | null;
  ukupnaKolicina?: number | null;
  pocetnoStanjeVrednosti?: number | null;
  prometUlazaVrednosti?: number | null;
  prometIzlazaVrednosti?: number | null;
  ukupnaVrednost?: number | null;
}

export type NewMagacinskaKartica = Omit<IMagacinskaKartica, 'id'> & { id: null };
