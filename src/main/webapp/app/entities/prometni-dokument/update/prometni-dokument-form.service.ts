import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPrometniDokument, NewPrometniDokument } from '../prometni-dokument.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrometniDokument for edit and NewPrometniDokumentFormGroupInput for create.
 */
type PrometniDokumentFormGroupInput = IPrometniDokument | PartialWithRequiredKeyOf<NewPrometniDokument>;

type PrometniDokumentFormDefaults = Pick<NewPrometniDokument, 'id'>;

type PrometniDokumentFormGroupContent = {
  id: FormControl<IPrometniDokument['id'] | NewPrometniDokument['id']>;
  brojDokumenata: FormControl<IPrometniDokument['brojDokumenata']>;
  datum: FormControl<IPrometniDokument['datum']>;
  vrsta: FormControl<IPrometniDokument['vrsta']>;
  status: FormControl<IPrometniDokument['status']>;
};

export type PrometniDokumentFormGroup = FormGroup<PrometniDokumentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrometniDokumentFormService {
  createPrometniDokumentFormGroup(prometniDokument: PrometniDokumentFormGroupInput = { id: null }): PrometniDokumentFormGroup {
    const prometniDokumentRawValue = {
      ...this.getFormDefaults(),
      ...prometniDokument,
    };
    return new FormGroup<PrometniDokumentFormGroupContent>({
      id: new FormControl(
        { value: prometniDokumentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      brojDokumenata: new FormControl(prometniDokumentRawValue.brojDokumenata),
      datum: new FormControl(prometniDokumentRawValue.datum),
      vrsta: new FormControl(prometniDokumentRawValue.vrsta),
      status: new FormControl(prometniDokumentRawValue.status),
    });
  }

  getPrometniDokument(form: PrometniDokumentFormGroup): IPrometniDokument | NewPrometniDokument {
    return form.getRawValue() as IPrometniDokument | NewPrometniDokument;
  }

  resetForm(form: PrometniDokumentFormGroup, prometniDokument: PrometniDokumentFormGroupInput): void {
    const prometniDokumentRawValue = { ...this.getFormDefaults(), ...prometniDokument };
    form.reset(
      {
        ...prometniDokumentRawValue,
        id: { value: prometniDokumentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PrometniDokumentFormDefaults {
    return {
      id: null,
    };
  }
}
