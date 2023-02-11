import { IMagacin, NewMagacin } from './magacin.model';

export const sampleWithRequiredData: IMagacin = {
  id: 45341,
};

export const sampleWithPartialData: IMagacin = {
  id: 27663,
  naziv: 'target',
};

export const sampleWithFullData: IMagacin = {
  id: 15202,
  naziv: 'repurpose feed',
};

export const sampleWithNewData: NewMagacin = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
