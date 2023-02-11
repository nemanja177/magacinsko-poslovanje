import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../jedinica-mere.test-samples';

import { JedinicaMereFormService } from './jedinica-mere-form.service';

describe('JedinicaMere Form Service', () => {
  let service: JedinicaMereFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JedinicaMereFormService);
  });

  describe('Service methods', () => {
    describe('createJedinicaMereFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createJedinicaMereFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nazivJedinice: expect.any(Object),
            skraceniNaziv: expect.any(Object),
          })
        );
      });

      it('passing IJedinicaMere should create a new form with FormGroup', () => {
        const formGroup = service.createJedinicaMereFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nazivJedinice: expect.any(Object),
            skraceniNaziv: expect.any(Object),
          })
        );
      });
    });

    describe('getJedinicaMere', () => {
      it('should return NewJedinicaMere for default JedinicaMere initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createJedinicaMereFormGroup(sampleWithNewData);

        const jedinicaMere = service.getJedinicaMere(formGroup) as any;

        expect(jedinicaMere).toMatchObject(sampleWithNewData);
      });

      it('should return NewJedinicaMere for empty JedinicaMere initial value', () => {
        const formGroup = service.createJedinicaMereFormGroup();

        const jedinicaMere = service.getJedinicaMere(formGroup) as any;

        expect(jedinicaMere).toMatchObject({});
      });

      it('should return IJedinicaMere', () => {
        const formGroup = service.createJedinicaMereFormGroup(sampleWithRequiredData);

        const jedinicaMere = service.getJedinicaMere(formGroup) as any;

        expect(jedinicaMere).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IJedinicaMere should not enable id FormControl', () => {
        const formGroup = service.createJedinicaMereFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewJedinicaMere should disable id FormControl', () => {
        const formGroup = service.createJedinicaMereFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
