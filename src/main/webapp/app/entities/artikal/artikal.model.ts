export interface IArtikal {
  id: number;
  naziv?: string | null;
  opis?: string | null;
  pakovanje?: string | null;
}

export type NewArtikal = Omit<IArtikal, 'id'> & { id: null };
