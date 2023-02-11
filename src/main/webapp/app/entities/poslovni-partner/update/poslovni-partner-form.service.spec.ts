import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../poslovni-partner.test-samples';

import { PoslovniPartnerFormService } from './poslovni-partner-form.service';

describe('PoslovniPartner Form Service', () => {
  let service: PoslovniPartnerFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PoslovniPartnerFormService);
  });

  describe('Service methods', () => {
    describe('createPoslovniPartnerFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPoslovniPartnerFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ime: expect.any(Object),
            prezime: expect.any(Object),
            email: expect.any(Object),
            jmbg: expect.any(Object),
            adresa: expect.any(Object),
          })
        );
      });

      it('passing IPoslovniPartner should create a new form with FormGroup', () => {
        const formGroup = service.createPoslovniPartnerFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ime: expect.any(Object),
            prezime: expect.any(Object),
            email: expect.any(Object),
            jmbg: expect.any(Object),
            adresa: expect.any(Object),
          })
        );
      });
    });

    describe('getPoslovniPartner', () => {
      it('should return NewPoslovniPartner for default PoslovniPartner initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPoslovniPartnerFormGroup(sampleWithNewData);

        const poslovniPartner = service.getPoslovniPartner(formGroup) as any;

        expect(poslovniPartner).toMatchObject(sampleWithNewData);
      });

      it('should return NewPoslovniPartner for empty PoslovniPartner initial value', () => {
        const formGroup = service.createPoslovniPartnerFormGroup();

        const poslovniPartner = service.getPoslovniPartner(formGroup) as any;

        expect(poslovniPartner).toMatchObject({});
      });

      it('should return IPoslovniPartner', () => {
        const formGroup = service.createPoslovniPartnerFormGroup(sampleWithRequiredData);

        const poslovniPartner = service.getPoslovniPartner(formGroup) as any;

        expect(poslovniPartner).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPoslovniPartner should not enable id FormControl', () => {
        const formGroup = service.createPoslovniPartnerFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPoslovniPartner should disable id FormControl', () => {
        const formGroup = service.createPoslovniPartnerFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
