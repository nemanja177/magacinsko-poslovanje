import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../radnik.test-samples';

import { RadnikFormService } from './radnik-form.service';

describe('Radnik Form Service', () => {
  let service: RadnikFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RadnikFormService);
  });

  describe('Service methods', () => {
    describe('createRadnikFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRadnikFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ime: expect.any(Object),
            prezime: expect.any(Object),
            adresa: expect.any(Object),
            telefon: expect.any(Object),
          })
        );
      });

      it('passing IRadnik should create a new form with FormGroup', () => {
        const formGroup = service.createRadnikFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ime: expect.any(Object),
            prezime: expect.any(Object),
            adresa: expect.any(Object),
            telefon: expect.any(Object),
          })
        );
      });
    });

    describe('getRadnik', () => {
      it('should return NewRadnik for default Radnik initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRadnikFormGroup(sampleWithNewData);

        const radnik = service.getRadnik(formGroup) as any;

        expect(radnik).toMatchObject(sampleWithNewData);
      });

      it('should return NewRadnik for empty Radnik initial value', () => {
        const formGroup = service.createRadnikFormGroup();

        const radnik = service.getRadnik(formGroup) as any;

        expect(radnik).toMatchObject({});
      });

      it('should return IRadnik', () => {
        const formGroup = service.createRadnikFormGroup(sampleWithRequiredData);

        const radnik = service.getRadnik(formGroup) as any;

        expect(radnik).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRadnik should not enable id FormControl', () => {
        const formGroup = service.createRadnikFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRadnik should disable id FormControl', () => {
        const formGroup = service.createRadnikFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
