export interface IPreduzece {
  id: number;
  naziv?: string | null;
  adresa?: string | null;
  telefon?: string | null;
  mib?: string | null;
  pib?: string | null;
}

export type NewPreduzece = Omit<IPreduzece, 'id'> & { id: null };
