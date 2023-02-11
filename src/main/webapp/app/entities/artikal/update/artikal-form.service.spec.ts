import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../artikal.test-samples';

import { ArtikalFormService } from './artikal-form.service';

describe('Artikal Form Service', () => {
  let service: ArtikalFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArtikalFormService);
  });

  describe('Service methods', () => {
    describe('createArtikalFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createArtikalFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            naziv: expect.any(Object),
            opis: expect.any(Object),
            pakovanje: expect.any(Object),
          })
        );
      });

      it('passing IArtikal should create a new form with FormGroup', () => {
        const formGroup = service.createArtikalFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            naziv: expect.any(Object),
            opis: expect.any(Object),
            pakovanje: expect.any(Object),
          })
        );
      });
    });

    describe('getArtikal', () => {
      it('should return NewArtikal for default Artikal initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createArtikalFormGroup(sampleWithNewData);

        const artikal = service.getArtikal(formGroup) as any;

        expect(artikal).toMatchObject(sampleWithNewData);
      });

      it('should return NewArtikal for empty Artikal initial value', () => {
        const formGroup = service.createArtikalFormGroup();

        const artikal = service.getArtikal(formGroup) as any;

        expect(artikal).toMatchObject({});
      });

      it('should return IArtikal', () => {
        const formGroup = service.createArtikalFormGroup(sampleWithRequiredData);

        const artikal = service.getArtikal(formGroup) as any;

        expect(artikal).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IArtikal should not enable id FormControl', () => {
        const formGroup = service.createArtikalFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewArtikal should disable id FormControl', () => {
        const formGroup = service.createArtikalFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
