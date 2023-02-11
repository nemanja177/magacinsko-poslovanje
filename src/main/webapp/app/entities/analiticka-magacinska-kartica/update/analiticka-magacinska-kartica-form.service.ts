import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnalitickaMagacinskaKartica, NewAnalitickaMagacinskaKartica } from '../analiticka-magacinska-kartica.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnalitickaMagacinskaKartica for edit and NewAnalitickaMagacinskaKarticaFormGroupInput for create.
 */
type AnalitickaMagacinskaKarticaFormGroupInput = IAnalitickaMagacinskaKartica | PartialWithRequiredKeyOf<NewAnalitickaMagacinskaKartica>;

type AnalitickaMagacinskaKarticaFormDefaults = Pick<NewAnalitickaMagacinskaKartica, 'id' | 'smer'>;

type AnalitickaMagacinskaKarticaFormGroupContent = {
  id: FormControl<IAnalitickaMagacinskaKartica['id'] | NewAnalitickaMagacinskaKartica['id']>;
  datumPrometa: FormControl<IAnalitickaMagacinskaKartica['datumPrometa']>;
  kolicina: FormControl<IAnalitickaMagacinskaKartica['kolicina']>;
  cena: FormControl<IAnalitickaMagacinskaKartica['cena']>;
  vrednost: FormControl<IAnalitickaMagacinskaKartica['vrednost']>;
  dokument: FormControl<IAnalitickaMagacinskaKartica['dokument']>;
  smer: FormControl<IAnalitickaMagacinskaKartica['smer']>;
};

export type AnalitickaMagacinskaKarticaFormGroup = FormGroup<AnalitickaMagacinskaKarticaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnalitickaMagacinskaKarticaFormService {
  createAnalitickaMagacinskaKarticaFormGroup(
    analitickaMagacinskaKartica: AnalitickaMagacinskaKarticaFormGroupInput = { id: null }
  ): AnalitickaMagacinskaKarticaFormGroup {
    const analitickaMagacinskaKarticaRawValue = {
      ...this.getFormDefaults(),
      ...analitickaMagacinskaKartica,
    };
    return new FormGroup<AnalitickaMagacinskaKarticaFormGroupContent>({
      id: new FormControl(
        { value: analitickaMagacinskaKarticaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      datumPrometa: new FormControl(analitickaMagacinskaKarticaRawValue.datumPrometa),
      kolicina: new FormControl(analitickaMagacinskaKarticaRawValue.kolicina),
      cena: new FormControl(analitickaMagacinskaKarticaRawValue.cena),
      vrednost: new FormControl(analitickaMagacinskaKarticaRawValue.vrednost),
      dokument: new FormControl(analitickaMagacinskaKarticaRawValue.dokument),
      smer: new FormControl(analitickaMagacinskaKarticaRawValue.smer),
    });
  }

  getAnalitickaMagacinskaKartica(
    form: AnalitickaMagacinskaKarticaFormGroup
  ): IAnalitickaMagacinskaKartica | NewAnalitickaMagacinskaKartica {
    return form.getRawValue() as IAnalitickaMagacinskaKartica | NewAnalitickaMagacinskaKartica;
  }

  resetForm(form: AnalitickaMagacinskaKarticaFormGroup, analitickaMagacinskaKartica: AnalitickaMagacinskaKarticaFormGroupInput): void {
    const analitickaMagacinskaKarticaRawValue = { ...this.getFormDefaults(), ...analitickaMagacinskaKartica };
    form.reset(
      {
        ...analitickaMagacinskaKarticaRawValue,
        id: { value: analitickaMagacinskaKarticaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AnalitickaMagacinskaKarticaFormDefaults {
    return {
      id: null,
      smer: false,
    };
  }
}
