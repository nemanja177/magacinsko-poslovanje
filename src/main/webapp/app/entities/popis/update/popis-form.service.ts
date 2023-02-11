import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPopis, NewPopis } from '../popis.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPopis for edit and NewPopisFormGroupInput for create.
 */
type PopisFormGroupInput = IPopis | PartialWithRequiredKeyOf<NewPopis>;

type PopisFormDefaults = Pick<NewPopis, 'id' | 'statusPopisa'>;

type PopisFormGroupContent = {
  id: FormControl<IPopis['id'] | NewPopis['id']>;
  datumPopisa: FormControl<IPopis['datumPopisa']>;
  brojPopisa: FormControl<IPopis['brojPopisa']>;
  statusPopisa: FormControl<IPopis['statusPopisa']>;
};

export type PopisFormGroup = FormGroup<PopisFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PopisFormService {
  createPopisFormGroup(popis: PopisFormGroupInput = { id: null }): PopisFormGroup {
    const popisRawValue = {
      ...this.getFormDefaults(),
      ...popis,
    };
    return new FormGroup<PopisFormGroupContent>({
      id: new FormControl(
        { value: popisRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      datumPopisa: new FormControl(popisRawValue.datumPopisa),
      brojPopisa: new FormControl(popisRawValue.brojPopisa),
      statusPopisa: new FormControl(popisRawValue.statusPopisa),
    });
  }

  getPopis(form: PopisFormGroup): IPopis | NewPopis {
    return form.getRawValue() as IPopis | NewPopis;
  }

  resetForm(form: PopisFormGroup, popis: PopisFormGroupInput): void {
    const popisRawValue = { ...this.getFormDefaults(), ...popis };
    form.reset(
      {
        ...popisRawValue,
        id: { value: popisRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PopisFormDefaults {
    return {
      id: null,
      statusPopisa: false,
    };
  }
}
