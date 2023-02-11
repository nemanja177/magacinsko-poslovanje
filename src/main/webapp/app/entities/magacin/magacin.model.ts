export interface IMagacin {
  id: number;
  naziv?: string | null;
}

export type NewMagacin = Omit<IMagacin, 'id'> & { id: null };
