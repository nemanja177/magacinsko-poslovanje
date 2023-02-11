export interface IPoslovanGodina {
  id: number;
  godina?: number | null;
  zakljucena?: boolean | null;
}

export type NewPoslovanGodina = Omit<IPoslovanGodina, 'id'> & { id: null };
