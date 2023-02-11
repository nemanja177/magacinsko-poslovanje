import { IArtikal, NewArtikal } from './artikal.model';

export const sampleWithRequiredData: IArtikal = {
  id: 9939,
};

export const sampleWithPartialData: IArtikal = {
  id: 72556,
  naziv: 'Australian Rupee',
};

export const sampleWithFullData: IArtikal = {
  id: 74866,
  naziv: 'SSL enterprise New',
  opis: 'lime',
  pakovanje: 'Plaza',
};

export const sampleWithNewData: NewArtikal = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
