import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPrometMagacinskeKartice, NewPrometMagacinskeKartice } from '../promet-magacinske-kartice.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrometMagacinskeKartice for edit and NewPrometMagacinskeKarticeFormGroupInput for create.
 */
type PrometMagacinskeKarticeFormGroupInput = IPrometMagacinskeKartice | PartialWithRequiredKeyOf<NewPrometMagacinskeKartice>;

type PrometMagacinskeKarticeFormDefaults = Pick<NewPrometMagacinskeKartice, 'id'>;

type PrometMagacinskeKarticeFormGroupContent = {
  id: FormControl<IPrometMagacinskeKartice['id'] | NewPrometMagacinskeKartice['id']>;
  datumPrometa: FormControl<IPrometMagacinskeKartice['datumPrometa']>;
  kolicina: FormControl<IPrometMagacinskeKartice['kolicina']>;
  cena: FormControl<IPrometMagacinskeKartice['cena']>;
  vrednost: FormControl<IPrometMagacinskeKartice['vrednost']>;
  dokument: FormControl<IPrometMagacinskeKartice['dokument']>;
  smer: FormControl<IPrometMagacinskeKartice['smer']>;
};

export type PrometMagacinskeKarticeFormGroup = FormGroup<PrometMagacinskeKarticeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrometMagacinskeKarticeFormService {
  createPrometMagacinskeKarticeFormGroup(
    prometMagacinskeKartice: PrometMagacinskeKarticeFormGroupInput = { id: null }
  ): PrometMagacinskeKarticeFormGroup {
    const prometMagacinskeKarticeRawValue = {
      ...this.getFormDefaults(),
      ...prometMagacinskeKartice,
    };
    return new FormGroup<PrometMagacinskeKarticeFormGroupContent>({
      id: new FormControl(
        { value: prometMagacinskeKarticeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      datumPrometa: new FormControl(prometMagacinskeKarticeRawValue.datumPrometa),
      kolicina: new FormControl(prometMagacinskeKarticeRawValue.kolicina),
      cena: new FormControl(prometMagacinskeKarticeRawValue.cena),
      vrednost: new FormControl(prometMagacinskeKarticeRawValue.vrednost),
      dokument: new FormControl(prometMagacinskeKarticeRawValue.dokument),
      smer: new FormControl(prometMagacinskeKarticeRawValue.smer),
    });
  }

  getPrometMagacinskeKartice(form: PrometMagacinskeKarticeFormGroup): IPrometMagacinskeKartice | NewPrometMagacinskeKartice {
    return form.getRawValue() as IPrometMagacinskeKartice | NewPrometMagacinskeKartice;
  }

  resetForm(form: PrometMagacinskeKarticeFormGroup, prometMagacinskeKartice: PrometMagacinskeKarticeFormGroupInput): void {
    const prometMagacinskeKarticeRawValue = { ...this.getFormDefaults(), ...prometMagacinskeKartice };
    form.reset(
      {
        ...prometMagacinskeKarticeRawValue,
        id: { value: prometMagacinskeKarticeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PrometMagacinskeKarticeFormDefaults {
    return {
      id: null,
    };
  }
}
