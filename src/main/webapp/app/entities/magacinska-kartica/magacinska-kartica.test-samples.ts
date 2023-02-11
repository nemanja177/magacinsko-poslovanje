import { IMagacinskaKartica, NewMagacinskaKartica } from './magacinska-kartica.model';

export const sampleWithRequiredData: IMagacinskaKartica = {
  id: 29638,
};

export const sampleWithPartialData: IMagacinskaKartica = {
  id: 8776,
  pocetnoStanjeKolicina: 57854,
  pocetnoStanjeVrednosti: 4782,
  prometUlazaVrednosti: 68059,
};

export const sampleWithFullData: IMagacinskaKartica = {
  id: 9892,
  pocetnoStanjeKolicina: 79212,
  prometUlazaKolicina: 20183,
  prometIzlazaKolicina: 55183,
  ukupnaKolicina: 77421,
  pocetnoStanjeVrednosti: 40847,
  prometUlazaVrednosti: 85079,
  prometIzlazaVrednosti: 21297,
  ukupnaVrednost: 44111,
};

export const sampleWithNewData: NewMagacinskaKartica = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
