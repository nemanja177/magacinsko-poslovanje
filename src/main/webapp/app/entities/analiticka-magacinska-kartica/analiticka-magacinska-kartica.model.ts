import dayjs from 'dayjs/esm';

export interface IAnalitickaMagacinskaKartica {
  id: number;
  datumPrometa?: dayjs.Dayjs | null;
  kolicina?: number | null;
  cena?: number | null;
  vrednost?: number | null;
  dokument?: string | null;
  smer?: boolean | null;
}

export type NewAnalitickaMagacinskaKartica = Omit<IAnalitickaMagacinskaKartica, 'id'> & { id: null };
