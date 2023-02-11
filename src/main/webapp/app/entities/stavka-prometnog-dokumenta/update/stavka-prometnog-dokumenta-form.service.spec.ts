import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../stavka-prometnog-dokumenta.test-samples';

import { StavkaPrometnogDokumentaFormService } from './stavka-prometnog-dokumenta-form.service';

describe('StavkaPrometnogDokumenta Form Service', () => {
  let service: StavkaPrometnogDokumentaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StavkaPrometnogDokumentaFormService);
  });

  describe('Service methods', () => {
    describe('createStavkaPrometnogDokumentaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStavkaPrometnogDokumentaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            kolicina: expect.any(Object),
            cena: expect.any(Object),
            vrednost: expect.any(Object),
          })
        );
      });

      it('passing IStavkaPrometnogDokumenta should create a new form with FormGroup', () => {
        const formGroup = service.createStavkaPrometnogDokumentaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            kolicina: expect.any(Object),
            cena: expect.any(Object),
            vrednost: expect.any(Object),
          })
        );
      });
    });

    describe('getStavkaPrometnogDokumenta', () => {
      it('should return NewStavkaPrometnogDokumenta for default StavkaPrometnogDokumenta initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createStavkaPrometnogDokumentaFormGroup(sampleWithNewData);

        const stavkaPrometnogDokumenta = service.getStavkaPrometnogDokumenta(formGroup) as any;

        expect(stavkaPrometnogDokumenta).toMatchObject(sampleWithNewData);
      });

      it('should return NewStavkaPrometnogDokumenta for empty StavkaPrometnogDokumenta initial value', () => {
        const formGroup = service.createStavkaPrometnogDokumentaFormGroup();

        const stavkaPrometnogDokumenta = service.getStavkaPrometnogDokumenta(formGroup) as any;

        expect(stavkaPrometnogDokumenta).toMatchObject({});
      });

      it('should return IStavkaPrometnogDokumenta', () => {
        const formGroup = service.createStavkaPrometnogDokumentaFormGroup(sampleWithRequiredData);

        const stavkaPrometnogDokumenta = service.getStavkaPrometnogDokumenta(formGroup) as any;

        expect(stavkaPrometnogDokumenta).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStavkaPrometnogDokumenta should not enable id FormControl', () => {
        const formGroup = service.createStavkaPrometnogDokumentaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStavkaPrometnogDokumenta should disable id FormControl', () => {
        const formGroup = service.createStavkaPrometnogDokumentaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
