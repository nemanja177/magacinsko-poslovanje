import dayjs from 'dayjs/esm';

import { IPrometMagacinskeKartice, NewPrometMagacinskeKartice } from './promet-magacinske-kartice.model';

export const sampleWithRequiredData: IPrometMagacinskeKartice = {
  id: 62333,
};

export const sampleWithPartialData: IPrometMagacinskeKartice = {
  id: 94834,
  datumPrometa: dayjs('2023-02-11'),
  dokument: 'Orchard',
  smer: 'transmit deliver',
};

export const sampleWithFullData: IPrometMagacinskeKartice = {
  id: 6290,
  datumPrometa: dayjs('2023-02-11'),
  kolicina: 38804,
  cena: 20137,
  vrednost: 93468,
  dokument: 'wireless Manager Concrete',
  smer: 'Jersey',
};

export const sampleWithNewData: NewPrometMagacinskeKartice = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
