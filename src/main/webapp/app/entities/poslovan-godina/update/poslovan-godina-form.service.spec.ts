import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../poslovan-godina.test-samples';

import { PoslovanGodinaFormService } from './poslovan-godina-form.service';

describe('PoslovanGodina Form Service', () => {
  let service: PoslovanGodinaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PoslovanGodinaFormService);
  });

  describe('Service methods', () => {
    describe('createPoslovanGodinaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPoslovanGodinaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            godina: expect.any(Object),
            zakljucena: expect.any(Object),
          })
        );
      });

      it('passing IPoslovanGodina should create a new form with FormGroup', () => {
        const formGroup = service.createPoslovanGodinaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            godina: expect.any(Object),
            zakljucena: expect.any(Object),
          })
        );
      });
    });

    describe('getPoslovanGodina', () => {
      it('should return NewPoslovanGodina for default PoslovanGodina initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPoslovanGodinaFormGroup(sampleWithNewData);

        const poslovanGodina = service.getPoslovanGodina(formGroup) as any;

        expect(poslovanGodina).toMatchObject(sampleWithNewData);
      });

      it('should return NewPoslovanGodina for empty PoslovanGodina initial value', () => {
        const formGroup = service.createPoslovanGodinaFormGroup();

        const poslovanGodina = service.getPoslovanGodina(formGroup) as any;

        expect(poslovanGodina).toMatchObject({});
      });

      it('should return IPoslovanGodina', () => {
        const formGroup = service.createPoslovanGodinaFormGroup(sampleWithRequiredData);

        const poslovanGodina = service.getPoslovanGodina(formGroup) as any;

        expect(poslovanGodina).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPoslovanGodina should not enable id FormControl', () => {
        const formGroup = service.createPoslovanGodinaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPoslovanGodina should disable id FormControl', () => {
        const formGroup = service.createPoslovanGodinaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
