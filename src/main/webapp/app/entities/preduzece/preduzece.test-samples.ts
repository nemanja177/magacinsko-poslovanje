import { IPreduzece, NewPreduzece } from './preduzece.model';

export const sampleWithRequiredData: IPreduzece = {
  id: 63915,
};

export const sampleWithPartialData: IPreduzece = {
  id: 31376,
  adresa: 'Mews',
  pib: 'Practical Argentine morph',
};

export const sampleWithFullData: IPreduzece = {
  id: 18930,
  naziv: 'Re-contextualized',
  adresa: 'invoice',
  telefon: 'Toys strategize',
  mib: 'middleware International',
  pib: 'Account Mexico',
};

export const sampleWithNewData: NewPreduzece = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
