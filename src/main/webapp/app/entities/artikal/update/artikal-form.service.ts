import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IArtikal, NewArtikal } from '../artikal.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IArtikal for edit and NewArtikalFormGroupInput for create.
 */
type ArtikalFormGroupInput = IArtikal | PartialWithRequiredKeyOf<NewArtikal>;

type ArtikalFormDefaults = Pick<NewArtikal, 'id'>;

type ArtikalFormGroupContent = {
  id: FormControl<IArtikal['id'] | NewArtikal['id']>;
  naziv: FormControl<IArtikal['naziv']>;
  opis: FormControl<IArtikal['opis']>;
  pakovanje: FormControl<IArtikal['pakovanje']>;
};

export type ArtikalFormGroup = FormGroup<ArtikalFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ArtikalFormService {
  createArtikalFormGroup(artikal: ArtikalFormGroupInput = { id: null }): ArtikalFormGroup {
    const artikalRawValue = {
      ...this.getFormDefaults(),
      ...artikal,
    };
    return new FormGroup<ArtikalFormGroupContent>({
      id: new FormControl(
        { value: artikalRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      naziv: new FormControl(artikalRawValue.naziv),
      opis: new FormControl(artikalRawValue.opis),
      pakovanje: new FormControl(artikalRawValue.pakovanje),
    });
  }

  getArtikal(form: ArtikalFormGroup): IArtikal | NewArtikal {
    return form.getRawValue() as IArtikal | NewArtikal;
  }

  resetForm(form: ArtikalFormGroup, artikal: ArtikalFormGroupInput): void {
    const artikalRawValue = { ...this.getFormDefaults(), ...artikal };
    form.reset(
      {
        ...artikalRawValue,
        id: { value: artikalRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ArtikalFormDefaults {
    return {
      id: null,
    };
  }
}
