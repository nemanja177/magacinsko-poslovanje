import { IStavkaPrometnogDokumenta, NewStavkaPrometnogDokumenta } from './stavka-prometnog-dokumenta.model';

export const sampleWithRequiredData: IStavkaPrometnogDokumenta = {
  id: 63731,
};

export const sampleWithPartialData: IStavkaPrometnogDokumenta = {
  id: 51503,
  kolicina: 42313,
  cena: 54446,
  vrednost: 13312,
};

export const sampleWithFullData: IStavkaPrometnogDokumenta = {
  id: 12038,
  kolicina: 32099,
  cena: 35457,
  vrednost: 29333,
};

export const sampleWithNewData: NewStavkaPrometnogDokumenta = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
