import { IPoslovniPartner, NewPoslovniPartner } from './poslovni-partner.model';

export const sampleWithRequiredData: IPoslovniPartner = {
  id: 10598,
};

export const sampleWithPartialData: IPoslovniPartner = {
  id: 64020,
  adresa: 'maroon Meadows',
};

export const sampleWithFullData: IPoslovniPartner = {
  id: 26889,
  ime: 'transmit Alabama',
  prezime: 'turquoise',
  email: 'Duncan_Kassulke@hotmail.com',
  jmbg: 'magenta Toys',
  adresa: 'Versatile Hat',
};

export const sampleWithNewData: NewPoslovniPartner = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
