import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMagacin } from '../magacin.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../magacin.test-samples';

import { MagacinService } from './magacin.service';

const requireRestSample: IMagacin = {
  ...sampleWithRequiredData,
};

describe('Magacin Service', () => {
  let service: MagacinService;
  let httpMock: HttpTestingController;
  let expectedResult: IMagacin | IMagacin[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MagacinService);
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

    it('should create a Magacin', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const magacin = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(magacin).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Magacin', () => {
      const magacin = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(magacin).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Magacin', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Magacin', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Magacin', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMagacinToCollectionIfMissing', () => {
      it('should add a Magacin to an empty array', () => {
        const magacin: IMagacin = sampleWithRequiredData;
        expectedResult = service.addMagacinToCollectionIfMissing([], magacin);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(magacin);
      });

      it('should not add a Magacin to an array that contains it', () => {
        const magacin: IMagacin = sampleWithRequiredData;
        const magacinCollection: IMagacin[] = [
          {
            ...magacin,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMagacinToCollectionIfMissing(magacinCollection, magacin);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Magacin to an array that doesn't contain it", () => {
        const magacin: IMagacin = sampleWithRequiredData;
        const magacinCollection: IMagacin[] = [sampleWithPartialData];
        expectedResult = service.addMagacinToCollectionIfMissing(magacinCollection, magacin);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(magacin);
      });

      it('should add only unique Magacin to an array', () => {
        const magacinArray: IMagacin[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const magacinCollection: IMagacin[] = [sampleWithRequiredData];
        expectedResult = service.addMagacinToCollectionIfMissing(magacinCollection, ...magacinArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const magacin: IMagacin = sampleWithRequiredData;
        const magacin2: IMagacin = sampleWithPartialData;
        expectedResult = service.addMagacinToCollectionIfMissing([], magacin, magacin2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(magacin);
        expect(expectedResult).toContain(magacin2);
      });

      it('should accept null and undefined values', () => {
        const magacin: IMagacin = sampleWithRequiredData;
        expectedResult = service.addMagacinToCollectionIfMissing([], null, magacin, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(magacin);
      });

      it('should return initial array if no Magacin is added', () => {
        const magacinCollection: IMagacin[] = [sampleWithRequiredData];
        expectedResult = service.addMagacinToCollectionIfMissing(magacinCollection, undefined, null);
        expect(expectedResult).toEqual(magacinCollection);
      });
    });

    describe('compareMagacin', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMagacin(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMagacin(entity1, entity2);
        const compareResult2 = service.compareMagacin(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMagacin(entity1, entity2);
        const compareResult2 = service.compareMagacin(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMagacin(entity1, entity2);
        const compareResult2 = service.compareMagacin(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
