import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../analiticka-magacinska-kartica.test-samples';

import { AnalitickaMagacinskaKarticaFormService } from './analiticka-magacinska-kartica-form.service';

describe('AnalitickaMagacinskaKartica Form Service', () => {
  let service: AnalitickaMagacinskaKarticaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnalitickaMagacinskaKarticaFormService);
  });

  describe('Service methods', () => {
    describe('createAnalitickaMagacinskaKarticaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnalitickaMagacinskaKarticaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            datumPrometa: expect.any(Object),
            kolicina: expect.any(Object),
            cena: expect.any(Object),
            vrednost: expect.any(Object),
            dokument: expect.any(Object),
            smer: expect.any(Object),
          })
        );
      });

      it('passing IAnalitickaMagacinskaKartica should create a new form with FormGroup', () => {
        const formGroup = service.createAnalitickaMagacinskaKarticaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            datumPrometa: expect.any(Object),
            kolicina: expect.any(Object),
            cena: expect.any(Object),
            vrednost: expect.any(Object),
            dokument: expect.any(Object),
            smer: expect.any(Object),
          })
        );
      });
    });

    describe('getAnalitickaMagacinskaKartica', () => {
      it('should return NewAnalitickaMagacinskaKartica for default AnalitickaMagacinskaKartica initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAnalitickaMagacinskaKarticaFormGroup(sampleWithNewData);

        const analitickaMagacinskaKartica = service.getAnalitickaMagacinskaKartica(formGroup) as any;

        expect(analitickaMagacinskaKartica).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnalitickaMagacinskaKartica for empty AnalitickaMagacinskaKartica initial value', () => {
        const formGroup = service.createAnalitickaMagacinskaKarticaFormGroup();

        const analitickaMagacinskaKartica = service.getAnalitickaMagacinskaKartica(formGroup) as any;

        expect(analitickaMagacinskaKartica).toMatchObject({});
      });

      it('should return IAnalitickaMagacinskaKartica', () => {
        const formGroup = service.createAnalitickaMagacinskaKarticaFormGroup(sampleWithRequiredData);

        const analitickaMagacinskaKartica = service.getAnalitickaMagacinskaKartica(formGroup) as any;

        expect(analitickaMagacinskaKartica).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnalitickaMagacinskaKartica should not enable id FormControl', () => {
        const formGroup = service.createAnalitickaMagacinskaKarticaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnalitickaMagacinskaKartica should disable id FormControl', () => {
        const formGroup = service.createAnalitickaMagacinskaKarticaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
