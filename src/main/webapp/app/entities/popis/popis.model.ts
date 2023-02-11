import dayjs from 'dayjs/esm';

export interface IPopis {
  id: number;
  datumPopisa?: dayjs.Dayjs | null;
  brojPopisa?: number | null;
  statusPopisa?: boolean | null;
}

export type NewPopis = Omit<IPopis, 'id'> & { id: null };
