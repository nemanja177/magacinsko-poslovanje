export interface IStavkaPrometnogDokumenta {
  id: number;
  kolicina?: number | null;
  cena?: number | null;
  vrednost?: number | null;
}

export type NewStavkaPrometnogDokumenta = Omit<IStavkaPrometnogDokumenta, 'id'> & { id: null };
