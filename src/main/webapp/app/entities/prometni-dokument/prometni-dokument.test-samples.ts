import dayjs from 'dayjs/esm';

import { IPrometniDokument, NewPrometniDokument } from './prometni-dokument.model';

export const sampleWithRequiredData: IPrometniDokument = {
  id: 41299,
};

export const sampleWithPartialData: IPrometniDokument = {
  id: 96264,
  brojDokumenata: 17074,
  vrsta: 'calculating',
  status: 'Sports sensor Regional',
};

export const sampleWithFullData: IPrometniDokument = {
  id: 12053,
  brojDokumenata: 52844,
  datum: dayjs('2023-02-11'),
  vrsta: 'haptic',
  status: 'Fresh',
};

export const sampleWithNewData: NewPrometniDokument = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
