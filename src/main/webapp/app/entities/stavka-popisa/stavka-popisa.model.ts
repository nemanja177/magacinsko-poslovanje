export interface IStavkaPopisa {
  id: number;
  kolicinaPopisa?: number | null;
  kolicinaPoKnjigama?: number | null;
}

export type NewStavkaPopisa = Omit<IStavkaPopisa, 'id'> & { id: null };
