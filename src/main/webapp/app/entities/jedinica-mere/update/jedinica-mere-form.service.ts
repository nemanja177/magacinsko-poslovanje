import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IJedinicaMere, NewJedinicaMere } from '../jedinica-mere.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJedinicaMere for edit and NewJedinicaMereFormGroupInput for create.
 */
type JedinicaMereFormGroupInput = IJedinicaMere | PartialWithRequiredKeyOf<NewJedinicaMere>;

type JedinicaMereFormDefaults = Pick<NewJedinicaMere, 'id'>;

type JedinicaMereFormGroupContent = {
  id: FormControl<IJedinicaMere['id'] | NewJedinicaMere['id']>;
  nazivJedinice: FormControl<IJedinicaMere['nazivJedinice']>;
  skraceniNaziv: FormControl<IJedinicaMere['skraceniNaziv']>;
};

export type JedinicaMereFormGroup = FormGroup<JedinicaMereFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JedinicaMereFormService {
  createJedinicaMereFormGroup(jedinicaMere: JedinicaMereFormGroupInput = { id: null }): JedinicaMereFormGroup {
    const jedinicaMereRawValue = {
      ...this.getFormDefaults(),
      ...jedinicaMere,
    };
    return new FormGroup<JedinicaMereFormGroupContent>({
      id: new FormControl(
        { value: jedinicaMereRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nazivJedinice: new FormControl(jedinicaMereRawValue.nazivJedinice),
      skraceniNaziv: new FormControl(jedinicaMereRawValue.skraceniNaziv),
    });
  }

  getJedinicaMere(form: JedinicaMereFormGroup): IJedinicaMere | NewJedinicaMere {
    return form.getRawValue() as IJedinicaMere | NewJedinicaMere;
  }

  resetForm(form: JedinicaMereFormGroup, jedinicaMere: JedinicaMereFormGroupInput): void {
    const jedinicaMereRawValue = { ...this.getFormDefaults(), ...jedinicaMere };
    form.reset(
      {
        ...jedinicaMereRawValue,
        id: { value: jedinicaMereRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): JedinicaMereFormDefaults {
    return {
      id: null,
    };
  }
}
