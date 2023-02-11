import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../popis.test-samples';

import { PopisFormService } from './popis-form.service';

describe('Popis Form Service', () => {
  let service: PopisFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PopisFormService);
  });

  describe('Service methods', () => {
    describe('createPopisFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPopisFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            datumPopisa: expect.any(Object),
            brojPopisa: expect.any(Object),
            statusPopisa: expect.any(Object),
          })
        );
      });

      it('passing IPopis should create a new form with FormGroup', () => {
        const formGroup = service.createPopisFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            datumPopisa: expect.any(Object),
            brojPopisa: expect.any(Object),
            statusPopisa: expect.any(Object),
          })
        );
      });
    });

    describe('getPopis', () => {
      it('should return NewPopis for default Popis initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPopisFormGroup(sampleWithNewData);

        const popis = service.getPopis(formGroup) as any;

        expect(popis).toMatchObject(sampleWithNewData);
      });

      it('should return NewPopis for empty Popis initial value', () => {
        const formGroup = service.createPopisFormGroup();

        const popis = service.getPopis(formGroup) as any;

        expect(popis).toMatchObject({});
      });

      it('should return IPopis', () => {
        const formGroup = service.createPopisFormGroup(sampleWithRequiredData);

        const popis = service.getPopis(formGroup) as any;

        expect(popis).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPopis should not enable id FormControl', () => {
        const formGroup = service.createPopisFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPopis should disable id FormControl', () => {
        const formGroup = service.createPopisFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
