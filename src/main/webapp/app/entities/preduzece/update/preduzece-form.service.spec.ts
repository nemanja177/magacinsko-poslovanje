import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../preduzece.test-samples';

import { PreduzeceFormService } from './preduzece-form.service';

describe('Preduzece Form Service', () => {
  let service: PreduzeceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PreduzeceFormService);
  });

  describe('Service methods', () => {
    describe('createPreduzeceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPreduzeceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            naziv: expect.any(Object),
            adresa: expect.any(Object),
            telefon: expect.any(Object),
            mib: expect.any(Object),
            pib: expect.any(Object),
          })
        );
      });

      it('passing IPreduzece should create a new form with FormGroup', () => {
        const formGroup = service.createPreduzeceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            naziv: expect.any(Object),
            adresa: expect.any(Object),
            telefon: expect.any(Object),
            mib: expect.any(Object),
            pib: expect.any(Object),
          })
        );
      });
    });

    describe('getPreduzece', () => {
      it('should return NewPreduzece for default Preduzece initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPreduzeceFormGroup(sampleWithNewData);

        const preduzece = service.getPreduzece(formGroup) as any;

        expect(preduzece).toMatchObject(sampleWithNewData);
      });

      it('should return NewPreduzece for empty Preduzece initial value', () => {
        const formGroup = service.createPreduzeceFormGroup();

        const preduzece = service.getPreduzece(formGroup) as any;

        expect(preduzece).toMatchObject({});
      });

      it('should return IPreduzece', () => {
        const formGroup = service.createPreduzeceFormGroup(sampleWithRequiredData);

        const preduzece = service.getPreduzece(formGroup) as any;

        expect(preduzece).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPreduzece should not enable id FormControl', () => {
        const formGroup = service.createPreduzeceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPreduzece should disable id FormControl', () => {
        const formGroup = service.createPreduzeceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
