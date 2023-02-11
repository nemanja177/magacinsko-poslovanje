import { IPoslovanGodina, NewPoslovanGodina } from './poslovan-godina.model';

export const sampleWithRequiredData: IPoslovanGodina = {
  id: 14278,
};

export const sampleWithPartialData: IPoslovanGodina = {
  id: 23666,
  godina: 8152,
  zakljucena: true,
};

export const sampleWithFullData: IPoslovanGodina = {
  id: 7060,
  godina: 73637,
  zakljucena: true,
};

export const sampleWithNewData: NewPoslovanGodina = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
