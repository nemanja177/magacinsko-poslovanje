export interface IPoslovniPartner {
  id: number;
  ime?: string | null;
  prezime?: string | null;
  email?: string | null;
  jmbg?: string | null;
  adresa?: string | null;
}

export type NewPoslovniPartner = Omit<IPoslovniPartner, 'id'> & { id: null };
