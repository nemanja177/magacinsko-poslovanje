import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IStavkaPrometnogDokumenta, NewStavkaPrometnogDokumenta } from '../stavka-prometnog-dokumenta.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStavkaPrometnogDokumenta for edit and NewStavkaPrometnogDokumentaFormGroupInput for create.
 */
type StavkaPrometnogDokumentaFormGroupInput = IStavkaPrometnogDokumenta | PartialWithRequiredKeyOf<NewStavkaPrometnogDokumenta>;

type StavkaPrometnogDokumentaFormDefaults = Pick<NewStavkaPrometnogDokumenta, 'id'>;

type StavkaPrometnogDokumentaFormGroupContent = {
  id: FormControl<IStavkaPrometnogDokumenta['id'] | NewStavkaPrometnogDokumenta['id']>;
  kolicina: FormControl<IStavkaPrometnogDokumenta['kolicina']>;
  cena: FormControl<IStavkaPrometnogDokumenta['cena']>;
  vrednost: FormControl<IStavkaPrometnogDokumenta['vrednost']>;
};

export type StavkaPrometnogDokumentaFormGroup = FormGroup<StavkaPrometnogDokumentaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StavkaPrometnogDokumentaFormService {
  createStavkaPrometnogDokumentaFormGroup(
    stavkaPrometnogDokumenta: StavkaPrometnogDokumentaFormGroupInput = { id: null }
  ): StavkaPrometnogDokumentaFormGroup {
    const stavkaPrometnogDokumentaRawValue = {
      ...this.getFormDefaults(),
      ...stavkaPrometnogDokumenta,
    };
    return new FormGroup<StavkaPrometnogDokumentaFormGroupContent>({
      id: new FormControl(
        { value: stavkaPrometnogDokumentaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      kolicina: new FormControl(stavkaPrometnogDokumentaRawValue.kolicina),
      cena: new FormControl(stavkaPrometnogDokumentaRawValue.cena),
      vrednost: new FormControl(stavkaPrometnogDokumentaRawValue.vrednost),
    });
  }

  getStavkaPrometnogDokumenta(form: StavkaPrometnogDokumentaFormGroup): IStavkaPrometnogDokumenta | NewStavkaPrometnogDokumenta {
    return form.getRawValue() as IStavkaPrometnogDokumenta | NewStavkaPrometnogDokumenta;
  }

  resetForm(form: StavkaPrometnogDokumentaFormGroup, stavkaPrometnogDokumenta: StavkaPrometnogDokumentaFormGroupInput): void {
    const stavkaPrometnogDokumentaRawValue = { ...this.getFormDefaults(), ...stavkaPrometnogDokumenta };
    form.reset(
      {
        ...stavkaPrometnogDokumentaRawValue,
        id: { value: stavkaPrometnogDokumentaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): StavkaPrometnogDokumentaFormDefaults {
    return {
      id: null,
    };
  }
}
