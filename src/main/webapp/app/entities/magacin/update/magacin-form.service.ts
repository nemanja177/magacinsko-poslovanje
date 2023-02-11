import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMagacin, NewMagacin } from '../magacin.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMagacin for edit and NewMagacinFormGroupInput for create.
 */
type MagacinFormGroupInput = IMagacin | PartialWithRequiredKeyOf<NewMagacin>;

type MagacinFormDefaults = Pick<NewMagacin, 'id'>;

type MagacinFormGroupContent = {
  id: FormControl<IMagacin['id'] | NewMagacin['id']>;
  naziv: FormControl<IMagacin['naziv']>;
};

export type MagacinFormGroup = FormGroup<MagacinFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MagacinFormService {
  createMagacinFormGroup(magacin: MagacinFormGroupInput = { id: null }): MagacinFormGroup {
    const magacinRawValue = {
      ...this.getFormDefaults(),
      ...magacin,
    };
    return new FormGroup<MagacinFormGroupContent>({
      id: new FormControl(
        { value: magacinRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      naziv: new FormControl(magacinRawValue.naziv),
    });
  }

  getMagacin(form: MagacinFormGroup): IMagacin | NewMagacin {
    return form.getRawValue() as IMagacin | NewMagacin;
  }

  resetForm(form: MagacinFormGroup, magacin: MagacinFormGroupInput): void {
    const magacinRawValue = { ...this.getFormDefaults(), ...magacin };
    form.reset(
      {
        ...magacinRawValue,
        id: { value: magacinRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MagacinFormDefaults {
    return {
      id: null,
    };
  }
}
