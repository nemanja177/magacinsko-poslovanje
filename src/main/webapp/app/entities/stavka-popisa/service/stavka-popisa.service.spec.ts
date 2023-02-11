import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStavkaPopisa } from '../stavka-popisa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../stavka-popisa.test-samples';

import { StavkaPopisaService } from './stavka-popisa.service';

const requireRestSample: IStavkaPopisa = {
  ...sampleWithRequiredData,
};

describe('StavkaPopisa Service', () => {
  let service: StavkaPopisaService;
  let httpMock: HttpTestingController;
  let expectedResult: IStavkaPopisa | IStavkaPopisa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StavkaPopisaService);
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

    it('should create a StavkaPopisa', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const stavkaPopisa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(stavkaPopisa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StavkaPopisa', () => {
      const stavkaPopisa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(stavkaPopisa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StavkaPopisa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StavkaPopisa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a StavkaPopisa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStavkaPopisaToCollectionIfMissing', () => {
      it('should add a StavkaPopisa to an empty array', () => {
        const stavkaPopisa: IStavkaPopisa = sampleWithRequiredData;
        expectedResult = service.addStavkaPopisaToCollectionIfMissing([], stavkaPopisa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stavkaPopisa);
      });

      it('should not add a StavkaPopisa to an array that contains it', () => {
        const stavkaPopisa: IStavkaPopisa = sampleWithRequiredData;
        const stavkaPopisaCollection: IStavkaPopisa[] = [
          {
            ...stavkaPopisa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStavkaPopisaToCollectionIfMissing(stavkaPopisaCollection, stavkaPopisa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StavkaPopisa to an array that doesn't contain it", () => {
        const stavkaPopisa: IStavkaPopisa = sampleWithRequiredData;
        const stavkaPopisaCollection: IStavkaPopisa[] = [sampleWithPartialData];
        expectedResult = service.addStavkaPopisaToCollectionIfMissing(stavkaPopisaCollection, stavkaPopisa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stavkaPopisa);
      });

      it('should add only unique StavkaPopisa to an array', () => {
        const stavkaPopisaArray: IStavkaPopisa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const stavkaPopisaCollection: IStavkaPopisa[] = [sampleWithRequiredData];
        expectedResult = service.addStavkaPopisaToCollectionIfMissing(stavkaPopisaCollection, ...stavkaPopisaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const stavkaPopisa: IStavkaPopisa = sampleWithRequiredData;
        const stavkaPopisa2: IStavkaPopisa = sampleWithPartialData;
        expectedResult = service.addStavkaPopisaToCollectionIfMissing([], stavkaPopisa, stavkaPopisa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stavkaPopisa);
        expect(expectedResult).toContain(stavkaPopisa2);
      });

      it('should accept null and undefined values', () => {
        const stavkaPopisa: IStavkaPopisa = sampleWithRequiredData;
        expectedResult = service.addStavkaPopisaToCollectionIfMissing([], null, stavkaPopisa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stavkaPopisa);
      });

      it('should return initial array if no StavkaPopisa is added', () => {
        const stavkaPopisaCollection: IStavkaPopisa[] = [sampleWithRequiredData];
        expectedResult = service.addStavkaPopisaToCollectionIfMissing(stavkaPopisaCollection, undefined, null);
        expect(expectedResult).toEqual(stavkaPopisaCollection);
      });
    });

    describe('compareStavkaPopisa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStavkaPopisa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStavkaPopisa(entity1, entity2);
        const compareResult2 = service.compareStavkaPopisa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareStavkaPopisa(entity1, entity2);
        const compareResult2 = service.compareStavkaPopisa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareStavkaPopisa(entity1, entity2);
        const compareResult2 = service.compareStavkaPopisa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
