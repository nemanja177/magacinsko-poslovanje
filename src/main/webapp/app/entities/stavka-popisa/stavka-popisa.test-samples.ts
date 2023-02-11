import { IStavkaPopisa, NewStavkaPopisa } from './stavka-popisa.model';

export const sampleWithRequiredData: IStavkaPopisa = {
  id: 7383,
};

export const sampleWithPartialData: IStavkaPopisa = {
  id: 37572,
  kolicinaPoKnjigama: 50798,
};

export const sampleWithFullData: IStavkaPopisa = {
  id: 62180,
  kolicinaPopisa: 80064,
  kolicinaPoKnjigama: 69375,
};

export const sampleWithNewData: NewStavkaPopisa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
