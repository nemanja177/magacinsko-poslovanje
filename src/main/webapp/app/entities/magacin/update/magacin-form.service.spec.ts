import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../magacin.test-samples';

import { MagacinFormService } from './magacin-form.service';

describe('Magacin Form Service', () => {
  let service: MagacinFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MagacinFormService);
  });

  describe('Service methods', () => {
    describe('createMagacinFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMagacinFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            naziv: expect.any(Object),
          })
        );
      });

      it('passing IMagacin should create a new form with FormGroup', () => {
        const formGroup = service.createMagacinFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            naziv: expect.any(Object),
          })
        );
      });
    });

    describe('getMagacin', () => {
      it('should return NewMagacin for default Magacin initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMagacinFormGroup(sampleWithNewData);

        const magacin = service.getMagacin(formGroup) as any;

        expect(magacin).toMatchObject(sampleWithNewData);
      });

      it('should return NewMagacin for empty Magacin initial value', () => {
        const formGroup = service.createMagacinFormGroup();

        const magacin = service.getMagacin(formGroup) as any;

        expect(magacin).toMatchObject({});
      });

      it('should return IMagacin', () => {
        const formGroup = service.createMagacinFormGroup(sampleWithRequiredData);

        const magacin = service.getMagacin(formGroup) as any;

        expect(magacin).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMagacin should not enable id FormControl', () => {
        const formGroup = service.createMagacinFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMagacin should disable id FormControl', () => {
        const formGroup = service.createMagacinFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
