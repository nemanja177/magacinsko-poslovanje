import dayjs from 'dayjs/esm';

export interface IPrometniDokument {
  id: number;
  brojDokumenata?: number | null;
  datum?: dayjs.Dayjs | null;
  vrsta?: string | null;
  status?: string | null;
}

export type NewPrometniDokument = Omit<IPrometniDokument, 'id'> & { id: null };
