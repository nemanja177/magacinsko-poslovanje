import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPoslovanGodina, NewPoslovanGodina } from '../poslovan-godina.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPoslovanGodina for edit and NewPoslovanGodinaFormGroupInput for create.
 */
type PoslovanGodinaFormGroupInput = IPoslovanGodina | PartialWithRequiredKeyOf<NewPoslovanGodina>;

type PoslovanGodinaFormDefaults = Pick<NewPoslovanGodina, 'id' | 'zakljucena'>;

type PoslovanGodinaFormGroupContent = {
  id: FormControl<IPoslovanGodina['id'] | NewPoslovanGodina['id']>;
  godina: FormControl<IPoslovanGodina['godina']>;
  zakljucena: FormControl<IPoslovanGodina['zakljucena']>;
};

export type PoslovanGodinaFormGroup = FormGroup<PoslovanGodinaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PoslovanGodinaFormService {
  createPoslovanGodinaFormGroup(poslovanGodina: PoslovanGodinaFormGroupInput = { id: null }): PoslovanGodinaFormGroup {
    const poslovanGodinaRawValue = {
      ...this.getFormDefaults(),
      ...poslovanGodina,
    };
    return new FormGroup<PoslovanGodinaFormGroupContent>({
      id: new FormControl(
        { value: poslovanGodinaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      godina: new FormControl(poslovanGodinaRawValue.godina),
      zakljucena: new FormControl(poslovanGodinaRawValue.zakljucena),
    });
  }

  getPoslovanGodina(form: PoslovanGodinaFormGroup): IPoslovanGodina | NewPoslovanGodina {
    return form.getRawValue() as IPoslovanGodina | NewPoslovanGodina;
  }

  resetForm(form: PoslovanGodinaFormGroup, poslovanGodina: PoslovanGodinaFormGroupInput): void {
    const poslovanGodinaRawValue = { ...this.getFormDefaults(), ...poslovanGodina };
    form.reset(
      {
        ...poslovanGodinaRawValue,
        id: { value: poslovanGodinaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PoslovanGodinaFormDefaults {
    return {
      id: null,
      zakljucena: false,
    };
  }
}
