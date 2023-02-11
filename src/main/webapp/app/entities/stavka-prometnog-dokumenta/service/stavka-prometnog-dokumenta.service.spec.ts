import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStavkaPrometnogDokumenta } from '../stavka-prometnog-dokumenta.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../stavka-prometnog-dokumenta.test-samples';

import { StavkaPrometnogDokumentaService } from './stavka-prometnog-dokumenta.service';

const requireRestSample: IStavkaPrometnogDokumenta = {
  ...sampleWithRequiredData,
};

describe('StavkaPrometnogDokumenta Service', () => {
  let service: StavkaPrometnogDokumentaService;
  let httpMock: HttpTestingController;
  let expectedResult: IStavkaPrometnogDokumenta | IStavkaPrometnogDokumenta[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StavkaPrometnogDokumentaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a StavkaPrometnogDokumenta', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const stavkaPrometnogDokumenta = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(stavkaPrometnogDokumenta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StavkaPrometnogDokumenta', () => {
      const stavkaPrometnogDokumenta = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(stavkaPrometnogDokumenta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StavkaPrometnogDokumenta', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StavkaPrometnogDokumenta', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a StavkaPrometnogDokumenta', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStavkaPrometnogDokumentaToCollectionIfMissing', () => {
      it('should add a StavkaPrometnogDokumenta to an empty array', () => {
        const stavkaPrometnogDokumenta: IStavkaPrometnogDokumenta = sampleWithRequiredData;
        expectedResult = service.addStavkaPrometnogDokumentaToCollectionIfMissing([], stavkaPrometnogDokumenta);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stavkaPrometnogDokumenta);
      });

      it('should not add a StavkaPrometnogDokumenta to an array that contains it', () => {
        const stavkaPrometnogDokumenta: IStavkaPrometnogDokumenta = sampleWithRequiredData;
        const stavkaPrometnogDokumentaCollection: IStavkaPrometnogDokumenta[] = [
          {
            ...stavkaPrometnogDokumenta,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStavkaPrometnogDokumentaToCollectionIfMissing(
          stavkaPrometnogDokumentaCollection,
          stavkaPrometnogDokumenta
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StavkaPrometnogDokumenta to an array that doesn't contain it", () => {
        const stavkaPrometnogDokumenta: IStavkaPrometnogDokumenta = sampleWithRequiredData;
        const stavkaPrometnogDokumentaCollection: IStavkaPrometnogDokumenta[] = [sampleWithPartialData];
        expectedResult = service.addStavkaPrometnogDokumentaToCollectionIfMissing(
          stavkaPrometnogDokumentaCollection,
          stavkaPrometnogDokumenta
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stavkaPrometnogDokumenta);
      });

      it('should add only unique StavkaPrometnogDokumenta to an array', () => {
        const stavkaPrometnogDokumentaArray: IStavkaPrometnogDokumenta[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const stavkaPrometnogDokumentaCollection: IStavkaPrometnogDokumenta[] = [sampleWithRequiredData];
        expectedResult = service.addStavkaPrometnogDokumentaToCollectionIfMissing(
          stavkaPrometnogDokumentaCollection,
          ...stavkaPrometnogDokumentaArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const stavkaPrometnogDokumenta: IStavkaPrometnogDokumenta = sampleWithRequiredData;
        const stavkaPrometnogDokumenta2: IStavkaPrometnogDokumenta = sampleWithPartialData;
        expectedResult = service.addStavkaPrometnogDokumentaToCollectionIfMissing([], stavkaPrometnogDokumenta, stavkaPrometnogDokumenta2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stavkaPrometnogDokumenta);
        expect(expectedResult).toContain(stavkaPrometnogDokumenta2);
      });

      it('should accept null and undefined values', () => {
        const stavkaPrometnogDokumenta: IStavkaPrometnogDokumenta = sampleWithRequiredData;
        expectedResult = service.addStavkaPrometnogDokumentaToCollectionIfMissing([], null, stavkaPrometnogDokumenta, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stavkaPrometnogDokumenta);
      });

      it('should return initial array if no StavkaPrometnogDokumenta is added', () => {
        const stavkaPrometnogDokumentaCollection: IStavkaPrometnogDokumenta[] = [sampleWithRequiredData];
        expectedResult = service.addStavkaPrometnogDokumentaToCollectionIfMissing(stavkaPrometnogDokumentaCollection, undefined, null);
        expect(expectedResult).toEqual(stavkaPrometnogDokumentaCollection);
      });
    });

    describe('compareStavkaPrometnogDokumenta', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStavkaPrometnogDokumenta(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStavkaPrometnogDokumenta(entity1, entity2);
        const compareResult2 = service.compareStavkaPrometnogDokumenta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareStavkaPrometnogDokumenta(entity1, entity2);
        const compareResult2 = service.compareStavkaPrometnogDokumenta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareStavkaPrometnogDokumenta(entity1, entity2);
        const compareResult2 = service.compareStavkaPrometnogDokumenta(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
