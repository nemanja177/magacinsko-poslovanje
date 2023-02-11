import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../magacinska-kartica.test-samples';

import { MagacinskaKarticaFormService } from './magacinska-kartica-form.service';

describe('MagacinskaKartica Form Service', () => {
  let service: MagacinskaKarticaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MagacinskaKarticaFormService);
  });

  describe('Service methods', () => {
    describe('createMagacinskaKarticaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMagacinskaKarticaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pocetnoStanjeKolicina: expect.any(Object),
            prometUlazaKolicina: expect.any(Object),
            prometIzlazaKolicina: expect.any(Object),
            ukupnaKolicina: expect.any(Object),
            pocetnoStanjeVrednosti: expect.any(Object),
            prometUlazaVrednosti: expect.any(Object),
            prometIzlazaVrednosti: expect.any(Object),
            ukupnaVrednost: expect.any(Object),
          })
        );
      });

      it('passing IMagacinskaKartica should create a new form with FormGroup', () => {
        const formGroup = service.createMagacinskaKarticaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pocetnoStanjeKolicina: expect.any(Object),
            prometUlazaKolicina: expect.any(Object),
            prometIzlazaKolicina: expect.any(Object),
            ukupnaKolicina: expect.any(Object),
            pocetnoStanjeVrednosti: expect.any(Object),
            prometUlazaVrednosti: expect.any(Object),
            prometIzlazaVrednosti: expect.any(Object),
            ukupnaVrednost: expect.any(Object),
          })
        );
      });
    });

    describe('getMagacinskaKartica', () => {
      it('should return NewMagacinskaKartica for default MagacinskaKartica initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMagacinskaKarticaFormGroup(sampleWithNewData);

        const magacinskaKartica = service.getMagacinskaKartica(formGroup) as any;

        expect(magacinskaKartica).toMatchObject(sampleWithNewData);
      });

      it('should return NewMagacinskaKartica for empty MagacinskaKartica initial value', () => {
        const formGroup = service.createMagacinskaKarticaFormGroup();

        const magacinskaKartica = service.getMagacinskaKartica(formGroup) as any;

        expect(magacinskaKartica).toMatchObject({});
      });

      it('should return IMagacinskaKartica', () => {
        const formGroup = service.createMagacinskaKarticaFormGroup(sampleWithRequiredData);

        const magacinskaKartica = service.getMagacinskaKartica(formGroup) as any;

        expect(magacinskaKartica).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMagacinskaKartica should not enable id FormControl', () => {
        const formGroup = service.createMagacinskaKarticaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMagacinskaKartica should disable id FormControl', () => {
        const formGroup = service.createMagacinskaKarticaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
