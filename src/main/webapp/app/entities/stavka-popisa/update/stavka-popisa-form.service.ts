import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IStavkaPopisa, NewStavkaPopisa } from '../stavka-popisa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStavkaPopisa for edit and NewStavkaPopisaFormGroupInput for create.
 */
type StavkaPopisaFormGroupInput = IStavkaPopisa | PartialWithRequiredKeyOf<NewStavkaPopisa>;

type StavkaPopisaFormDefaults = Pick<NewStavkaPopisa, 'id'>;

type StavkaPopisaFormGroupContent = {
  id: FormControl<IStavkaPopisa['id'] | NewStavkaPopisa['id']>;
  kolicinaPopisa: FormControl<IStavkaPopisa['kolicinaPopisa']>;
  kolicinaPoKnjigama: FormControl<IStavkaPopisa['kolicinaPoKnjigama']>;
};

export type StavkaPopisaFormGroup = FormGroup<StavkaPopisaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StavkaPopisaFormService {
  createStavkaPopisaFormGroup(stavkaPopisa: StavkaPopisaFormGroupInput = { id: null }): StavkaPopisaFormGroup {
    const stavkaPopisaRawValue = {
      ...this.getFormDefaults(),
      ...stavkaPopisa,
    };
    return new FormGroup<StavkaPopisaFormGroupContent>({
      id: new FormControl(
        { value: stavkaPopisaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      kolicinaPopisa: new FormControl(stavkaPopisaRawValue.kolicinaPopisa),
      kolicinaPoKnjigama: new FormControl(stavkaPopisaRawValue.kolicinaPoKnjigama),
    });
  }

  getStavkaPopisa(form: StavkaPopisaFormGroup): IStavkaPopisa | NewStavkaPopisa {
    return form.getRawValue() as IStavkaPopisa | NewStavkaPopisa;
  }

  resetForm(form: StavkaPopisaFormGroup, stavkaPopisa: StavkaPopisaFormGroupInput): void {
    const stavkaPopisaRawValue = { ...this.getFormDefaults(), ...stavkaPopisa };
    form.reset(
      {
        ...stavkaPopisaRawValue,
        id: { value: stavkaPopisaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): StavkaPopisaFormDefaults {
    return {
      id: null,
    };
  }
}
