import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../prometni-dokument.test-samples';

import { PrometniDokumentFormService } from './prometni-dokument-form.service';

describe('PrometniDokument Form Service', () => {
  let service: PrometniDokumentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrometniDokumentFormService);
  });

  describe('Service methods', () => {
    describe('createPrometniDokumentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPrometniDokumentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            brojDokumenata: expect.any(Object),
            datum: expect.any(Object),
            vrsta: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });

      it('passing IPrometniDokument should create a new form with FormGroup', () => {
        const formGroup = service.createPrometniDokumentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            brojDokumenata: expect.any(Object),
            datum: expect.any(Object),
            vrsta: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });
    });

    describe('getPrometniDokument', () => {
      it('should return NewPrometniDokument for default PrometniDokument initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPrometniDokumentFormGroup(sampleWithNewData);

        const prometniDokument = service.getPrometniDokument(formGroup) as any;

        expect(prometniDokument).toMatchObject(sampleWithNewData);
      });

      it('should return NewPrometniDokument for empty PrometniDokument initial value', () => {
        const formGroup = service.createPrometniDokumentFormGroup();

        const prometniDokument = service.getPrometniDokument(formGroup) as any;

        expect(prometniDokument).toMatchObject({});
      });

      it('should return IPrometniDokument', () => {
        const formGroup = service.createPrometniDokumentFormGroup(sampleWithRequiredData);

        const prometniDokument = service.getPrometniDokument(formGroup) as any;

        expect(prometniDokument).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPrometniDokument should not enable id FormControl', () => {
        const formGroup = service.createPrometniDokumentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPrometniDokument should disable id FormControl', () => {
        const formGroup = service.createPrometniDokumentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
