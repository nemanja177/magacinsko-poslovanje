import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../promet-magacinske-kartice.test-samples';

import { PrometMagacinskeKarticeFormService } from './promet-magacinske-kartice-form.service';

describe('PrometMagacinskeKartice Form Service', () => {
  let service: PrometMagacinskeKarticeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrometMagacinskeKarticeFormService);
  });

  describe('Service methods', () => {
    describe('createPrometMagacinskeKarticeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPrometMagacinskeKarticeFormGroup();

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

      it('passing IPrometMagacinskeKartice should create a new form with FormGroup', () => {
        const formGroup = service.createPrometMagacinskeKarticeFormGroup(sampleWithRequiredData);

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

    describe('getPrometMagacinskeKartice', () => {
      it('should return NewPrometMagacinskeKartice for default PrometMagacinskeKartice initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPrometMagacinskeKarticeFormGroup(sampleWithNewData);

        const prometMagacinskeKartice = service.getPrometMagacinskeKartice(formGroup) as any;

        expect(prometMagacinskeKartice).toMatchObject(sampleWithNewData);
      });

      it('should return NewPrometMagacinskeKartice for empty PrometMagacinskeKartice initial value', () => {
        const formGroup = service.createPrometMagacinskeKarticeFormGroup();

        const prometMagacinskeKartice = service.getPrometMagacinskeKartice(formGroup) as any;

        expect(prometMagacinskeKartice).toMatchObject({});
      });

      it('should return IPrometMagacinskeKartice', () => {
        const formGroup = service.createPrometMagacinskeKarticeFormGroup(sampleWithRequiredData);

        const prometMagacinskeKartice = service.getPrometMagacinskeKartice(formGroup) as any;

        expect(prometMagacinskeKartice).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPrometMagacinskeKartice should not enable id FormControl', () => {
        const formGroup = service.createPrometMagacinskeKarticeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPrometMagacinskeKartice should disable id FormControl', () => {
        const formGroup = service.createPrometMagacinskeKarticeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
