import { IRadnik, NewRadnik } from './radnik.model';

export const sampleWithRequiredData: IRadnik = {
  id: 80614,
};

export const sampleWithPartialData: IRadnik = {
  id: 20748,
  ime: 'synthesize generate',
  prezime: 'Music integrated',
  adresa: 'USB impactful Cambridgeshire',
  telefon: 'violet',
};

export const sampleWithFullData: IRadnik = {
  id: 97456,
  ime: 'collaborative',
  prezime: 'encryption',
  adresa: 'methodology Garden',
  telefon: 'e-business framework',
};

export const sampleWithNewData: NewRadnik = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
