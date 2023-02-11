import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMagacinskaKartica, NewMagacinskaKartica } from '../magacinska-kartica.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMagacinskaKartica for edit and NewMagacinskaKarticaFormGroupInput for create.
 */
type MagacinskaKarticaFormGroupInput = IMagacinskaKartica | PartialWithRequiredKeyOf<NewMagacinskaKartica>;

type MagacinskaKarticaFormDefaults = Pick<NewMagacinskaKartica, 'id'>;

type MagacinskaKarticaFormGroupContent = {
  id: FormControl<IMagacinskaKartica['id'] | NewMagacinskaKartica['id']>;
  pocetnoStanjeKolicina: FormControl<IMagacinskaKartica['pocetnoStanjeKolicina']>;
  prometUlazaKolicina: FormControl<IMagacinskaKartica['prometUlazaKolicina']>;
  prometIzlazaKolicina: FormControl<IMagacinskaKartica['prometIzlazaKolicina']>;
  ukupnaKolicina: FormControl<IMagacinskaKartica['ukupnaKolicina']>;
  pocetnoStanjeVrednosti: FormControl<IMagacinskaKartica['pocetnoStanjeVrednosti']>;
  prometUlazaVrednosti: FormControl<IMagacinskaKartica['prometUlazaVrednosti']>;
  prometIzlazaVrednosti: FormControl<IMagacinskaKartica['prometIzlazaVrednosti']>;
  ukupnaVrednost: FormControl<IMagacinskaKartica['ukupnaVrednost']>;
};

export type MagacinskaKarticaFormGroup = FormGroup<MagacinskaKarticaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MagacinskaKarticaFormService {
  createMagacinskaKarticaFormGroup(magacinskaKartica: MagacinskaKarticaFormGroupInput = { id: null }): MagacinskaKarticaFormGroup {
    const magacinskaKarticaRawValue = {
      ...this.getFormDefaults(),
      ...magacinskaKartica,
    };
    return new FormGroup<MagacinskaKarticaFormGroupContent>({
      id: new FormControl(
        { value: magacinskaKarticaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      pocetnoStanjeKolicina: new FormControl(magacinskaKarticaRawValue.pocetnoStanjeKolicina),
      prometUlazaKolicina: new FormControl(magacinskaKarticaRawValue.prometUlazaKolicina),
      prometIzlazaKolicina: new FormControl(magacinskaKarticaRawValue.prometIzlazaKolicina),
      ukupnaKolicina: new FormControl(magacinskaKarticaRawValue.ukupnaKolicina),
      pocetnoStanjeVrednosti: new FormControl(magacinskaKarticaRawValue.pocetnoStanjeVrednosti),
      prometUlazaVrednosti: new FormControl(magacinskaKarticaRawValue.prometUlazaVrednosti),
      prometIzlazaVrednosti: new FormControl(magacinskaKarticaRawValue.prometIzlazaVrednosti),
      ukupnaVrednost: new FormControl(magacinskaKarticaRawValue.ukupnaVrednost),
    });
  }

  getMagacinskaKartica(form: MagacinskaKarticaFormGroup): IMagacinskaKartica | NewMagacinskaKartica {
    return form.getRawValue() as IMagacinskaKartica | NewMagacinskaKartica;
  }

  resetForm(form: MagacinskaKarticaFormGroup, magacinskaKartica: MagacinskaKarticaFormGroupInput): void {
    const magacinskaKarticaRawValue = { ...this.getFormDefaults(), ...magacinskaKartica };
    form.reset(
      {
        ...magacinskaKarticaRawValue,
        id: { value: magacinskaKarticaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MagacinskaKarticaFormDefaults {
    return {
      id: null,
    };
  }
}
