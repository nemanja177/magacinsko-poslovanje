import dayjs from 'dayjs/esm';

import { IAnalitickaMagacinskaKartica, NewAnalitickaMagacinskaKartica } from './analiticka-magacinska-kartica.model';

export const sampleWithRequiredData: IAnalitickaMagacinskaKartica = {
  id: 51274,
};

export const sampleWithPartialData: IAnalitickaMagacinskaKartica = {
  id: 47641,
  datumPrometa: dayjs('2023-02-11'),
  kolicina: 26779,
  smer: true,
};

export const sampleWithFullData: IAnalitickaMagacinskaKartica = {
  id: 30585,
  datumPrometa: dayjs('2023-02-11'),
  kolicina: 46115,
  cena: 89489,
  vrednost: 42025,
  dokument: 'Unbranded transform Global',
  smer: false,
};

export const sampleWithNewData: NewAnalitickaMagacinskaKartica = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
