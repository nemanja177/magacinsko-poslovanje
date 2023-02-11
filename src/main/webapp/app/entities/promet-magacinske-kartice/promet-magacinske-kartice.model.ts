import dayjs from 'dayjs/esm';

export interface IPrometMagacinskeKartice {
  id: number;
  datumPrometa?: dayjs.Dayjs | null;
  kolicina?: number | null;
  cena?: number | null;
  vrednost?: number | null;
  dokument?: string | null;
  smer?: string | null;
}

export type NewPrometMagacinskeKartice = Omit<IPrometMagacinskeKartice, 'id'> & { id: null };
