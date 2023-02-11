export interface IJedinicaMere {
  id: number;
  nazivJedinice?: string | null;
  skraceniNaziv?: string | null;
}

export type NewJedinicaMere = Omit<IJedinicaMere, 'id'> & { id: null };
