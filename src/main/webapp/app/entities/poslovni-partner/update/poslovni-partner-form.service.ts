import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPoslovniPartner, NewPoslovniPartner } from '../poslovni-partner.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPoslovniPartner for edit and NewPoslovniPartnerFormGroupInput for create.
 */
type PoslovniPartnerFormGroupInput = IPoslovniPartner | PartialWithRequiredKeyOf<NewPoslovniPartner>;

type PoslovniPartnerFormDefaults = Pick<NewPoslovniPartner, 'id'>;

type PoslovniPartnerFormGroupContent = {
  id: FormControl<IPoslovniPartner['id'] | NewPoslovniPartner['id']>;
  ime: FormControl<IPoslovniPartner['ime']>;
  prezime: FormControl<IPoslovniPartner['prezime']>;
  email: FormControl<IPoslovniPartner['email']>;
  jmbg: FormControl<IPoslovniPartner['jmbg']>;
  adresa: FormControl<IPoslovniPartner['adresa']>;
};

export type PoslovniPartnerFormGroup = FormGroup<PoslovniPartnerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PoslovniPartnerFormService {
  createPoslovniPartnerFormGroup(poslovniPartner: PoslovniPartnerFormGroupInput = { id: null }): PoslovniPartnerFormGroup {
    const poslovniPartnerRawValue = {
      ...this.getFormDefaults(),
      ...poslovniPartner,
    };
    return new FormGroup<PoslovniPartnerFormGroupContent>({
      id: new FormControl(
        { value: poslovniPartnerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      ime: new FormControl(poslovniPartnerRawValue.ime),
      prezime: new FormControl(poslovniPartnerRawValue.prezime),
      email: new FormControl(poslovniPartnerRawValue.email),
      jmbg: new FormControl(poslovniPartnerRawValue.jmbg),
      adresa: new FormControl(poslovniPartnerRawValue.adresa),
    });
  }

  getPoslovniPartner(form: PoslovniPartnerFormGroup): IPoslovniPartner | NewPoslovniPartner {
    return form.getRawValue() as IPoslovniPartner | NewPoslovniPartner;
  }

  resetForm(form: PoslovniPartnerFormGroup, poslovniPartner: PoslovniPartnerFormGroupInput): void {
    const poslovniPartnerRawValue = { ...this.getFormDefaults(), ...poslovniPartner };
    form.reset(
      {
        ...poslovniPartnerRawValue,
        id: { value: poslovniPartnerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PoslovniPartnerFormDefaults {
    return {
      id: null,
    };
  }
}
