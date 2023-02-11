import { IJedinicaMere, NewJedinicaMere } from './jedinica-mere.model';

export const sampleWithRequiredData: IJedinicaMere = {
  id: 23682,
};

export const sampleWithPartialData: IJedinicaMere = {
  id: 20613,
};

export const sampleWithFullData: IJedinicaMere = {
  id: 60279,
  nazivJedinice: 'Planner Uzbekistan protocol',
  skraceniNaziv: 'Security actuating repurpose',
};

export const sampleWithNewData: NewJedinicaMere = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
