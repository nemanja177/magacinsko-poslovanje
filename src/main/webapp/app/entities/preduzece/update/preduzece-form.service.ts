import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPreduzece, NewPreduzece } from '../preduzece.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPreduzece for edit and NewPreduzeceFormGroupInput for create.
 */
type PreduzeceFormGroupInput = IPreduzece | PartialWithRequiredKeyOf<NewPreduzece>;

type PreduzeceFormDefaults = Pick<NewPreduzece, 'id'>;

type PreduzeceFormGroupContent = {
  id: FormControl<IPreduzece['id'] | NewPreduzece['id']>;
  naziv: FormControl<IPreduzece['naziv']>;
  adresa: FormControl<IPreduzece['adresa']>;
  telefon: FormControl<IPreduzece['telefon']>;
  mib: FormControl<IPreduzece['mib']>;
  pib: FormControl<IPreduzece['pib']>;
};

export type PreduzeceFormGroup = FormGroup<PreduzeceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PreduzeceFormService {
  createPreduzeceFormGroup(preduzece: PreduzeceFormGroupInput = { id: null }): PreduzeceFormGroup {
    const preduzeceRawValue = {
      ...this.getFormDefaults(),
      ...preduzece,
    };
    return new FormGroup<PreduzeceFormGroupContent>({
      id: new FormControl(
        { value: preduzeceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      naziv: new FormControl(preduzeceRawValue.naziv),
      adresa: new FormControl(preduzeceRawValue.adresa),
      telefon: new FormControl(preduzeceRawValue.telefon),
      mib: new FormControl(preduzeceRawValue.mib),
      pib: new FormControl(preduzeceRawValue.pib),
    });
  }

  getPreduzece(form: PreduzeceFormGroup): IPreduzece | NewPreduzece {
    return form.getRawValue() as IPreduzece | NewPreduzece;
  }

  resetForm(form: PreduzeceFormGroup, preduzece: PreduzeceFormGroupInput): void {
    const preduzeceRawValue = { ...this.getFormDefaults(), ...preduzece };
    form.reset(
      {
        ...preduzeceRawValue,
        id: { value: preduzeceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PreduzeceFormDefaults {
    return {
      id: null,
    };
  }
}
