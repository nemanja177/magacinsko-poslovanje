export interface IRadnik {
  id: number;
  ime?: string | null;
  prezime?: string | null;
  adresa?: string | null;
  telefon?: string | null;
}

export type NewRadnik = Omit<IRadnik, 'id'> & { id: null };
