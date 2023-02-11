import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRadnik, NewRadnik } from '../radnik.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRadnik for edit and NewRadnikFormGroupInput for create.
 */
type RadnikFormGroupInput = IRadnik | PartialWithRequiredKeyOf<NewRadnik>;

type RadnikFormDefaults = Pick<NewRadnik, 'id'>;

type RadnikFormGroupContent = {
  id: FormControl<IRadnik['id'] | NewRadnik['id']>;
  ime: FormControl<IRadnik['ime']>;
  prezime: FormControl<IRadnik['prezime']>;
  adresa: FormControl<IRadnik['adresa']>;
  telefon: FormControl<IRadnik['telefon']>;
};

export type RadnikFormGroup = FormGroup<RadnikFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RadnikFormService {
  createRadnikFormGroup(radnik: RadnikFormGroupInput = { id: null }): RadnikFormGroup {
    const radnikRawValue = {
      ...this.getFormDefaults(),
      ...radnik,
    };
    return new FormGroup<RadnikFormGroupContent>({
      id: new FormControl(
        { value: radnikRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      ime: new FormControl(radnikRawValue.ime),
      prezime: new FormControl(radnikRawValue.prezime),
      adresa: new FormControl(radnikRawValue.adresa),
      telefon: new FormControl(radnikRawValue.telefon),
    });
  }

  getRadnik(form: RadnikFormGroup): IRadnik | NewRadnik {
    return form.getRawValue() as IRadnik | NewRadnik;
  }

  resetForm(form: RadnikFormGroup, radnik: RadnikFormGroupInput): void {
    const radnikRawValue = { ...this.getFormDefaults(), ...radnik };
    form.reset(
      {
        ...radnikRawValue,
        id: { value: radnikRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RadnikFormDefaults {
    return {
      id: null,
    };
  }
}
