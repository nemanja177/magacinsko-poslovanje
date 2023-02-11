import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../stavka-popisa.test-samples';

import { StavkaPopisaFormService } from './stavka-popisa-form.service';

describe('StavkaPopisa Form Service', () => {
  let service: StavkaPopisaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StavkaPopisaFormService);
  });

  describe('Service methods', () => {
    describe('createStavkaPopisaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStavkaPopisaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            kolicinaPopisa: expect.any(Object),
            kolicinaPoKnjigama: expect.any(Object),
          })
        );
      });

      it('passing IStavkaPopisa should create a new form with FormGroup', () => {
        const formGroup = service.createStavkaPopisaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            kolicinaPopisa: expect.any(Object),
            kolicinaPoKnjigama: expect.any(Object),
          })
        );
      });
    });

    describe('getStavkaPopisa', () => {
      it('should return NewStavkaPopisa for default StavkaPopisa initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createStavkaPopisaFormGroup(sampleWithNewData);

        const stavkaPopisa = service.getStavkaPopisa(formGroup) as any;

        expect(stavkaPopisa).toMatchObject(sampleWithNewData);
      });

      it('should return NewStavkaPopisa for empty StavkaPopisa initial value', () => {
        const formGroup = service.createStavkaPopisaFormGroup();

        const stavkaPopisa = service.getStavkaPopisa(formGroup) as any;

        expect(stavkaPopisa).toMatchObject({});
      });

      it('should return IStavkaPopisa', () => {
        const formGroup = service.createStavkaPopisaFormGroup(sampleWithRequiredData);

        const stavkaPopisa = service.getStavkaPopisa(formGroup) as any;

        expect(stavkaPopisa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStavkaPopisa should not enable id FormControl', () => {
        const formGroup = service.createStavkaPopisaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStavkaPopisa should disable id FormControl', () => {
        const formGroup = service.createStavkaPopisaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
