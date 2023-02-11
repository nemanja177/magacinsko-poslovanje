import dayjs from 'dayjs/esm';

import { IPopis, NewPopis } from './popis.model';

export const sampleWithRequiredData: IPopis = {
  id: 92972,
};

export const sampleWithPartialData: IPopis = {
  id: 55917,
  brojPopisa: 41584,
};

export const sampleWithFullData: IPopis = {
  id: 85825,
  datumPopisa: dayjs('2023-02-11'),
  brojPopisa: 44586,
  statusPopisa: false,
};

export const sampleWithNewData: NewPopis = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
