import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMagacinskaKartica } from '../magacinska-kartica.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../magacinska-kartica.test-samples';

import { MagacinskaKarticaService } from './magacinska-kartica.service';

const requireRestSample: IMagacinskaKartica = {
  ...sampleWithRequiredData,
};

describe('MagacinskaKartica Service', () => {
  let service: MagacinskaKarticaService;
  let httpMock: HttpTestingController;
  let expectedResult: IMagacinskaKartica | IMagacinskaKartica[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MagacinskaKarticaService);
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

    it('should create a MagacinskaKartica', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const magacinskaKartica = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(magacinskaKartica).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MagacinskaKartica', () => {
      const magacinskaKartica = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(magacinskaKartica).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MagacinskaKartica', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MagacinskaKartica', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MagacinskaKartica', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMagacinskaKarticaToCollectionIfMissing', () => {
      it('should add a MagacinskaKartica to an empty array', () => {
        const magacinskaKartica: IMagacinskaKartica = sampleWithRequiredData;
        expectedResult = service.addMagacinskaKarticaToCollectionIfMissing([], magacinskaKartica);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(magacinskaKartica);
      });

      it('should not add a MagacinskaKartica to an array that contains it', () => {
        const magacinskaKartica: IMagacinskaKartica = sampleWithRequiredData;
        const magacinskaKarticaCollection: IMagacinskaKartica[] = [
          {
            ...magacinskaKartica,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMagacinskaKarticaToCollectionIfMissing(magacinskaKarticaCollection, magacinskaKartica);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MagacinskaKartica to an array that doesn't contain it", () => {
        const magacinskaKartica: IMagacinskaKartica = sampleWithRequiredData;
        const magacinskaKarticaCollection: IMagacinskaKartica[] = [sampleWithPartialData];
        expectedResult = service.addMagacinskaKarticaToCollectionIfMissing(magacinskaKarticaCollection, magacinskaKartica);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(magacinskaKartica);
      });

      it('should add only unique MagacinskaKartica to an array', () => {
        const magacinskaKarticaArray: IMagacinskaKartica[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const magacinskaKarticaCollection: IMagacinskaKartica[] = [sampleWithRequiredData];
        expectedResult = service.addMagacinskaKarticaToCollectionIfMissing(magacinskaKarticaCollection, ...magacinskaKarticaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const magacinskaKartica: IMagacinskaKartica = sampleWithRequiredData;
        const magacinskaKartica2: IMagacinskaKartica = sampleWithPartialData;
        expectedResult = service.addMagacinskaKarticaToCollectionIfMissing([], magacinskaKartica, magacinskaKartica2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(magacinskaKartica);
        expect(expectedResult).toContain(magacinskaKartica2);
      });

      it('should accept null and undefined values', () => {
        const magacinskaKartica: IMagacinskaKartica = sampleWithRequiredData;
        expectedResult = service.addMagacinskaKarticaToCollectionIfMissing([], null, magacinskaKartica, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(magacinskaKartica);
      });

      it('should return initial array if no MagacinskaKartica is added', () => {
        const magacinskaKarticaCollection: IMagacinskaKartica[] = [sampleWithRequiredData];
        expectedResult = service.addMagacinskaKarticaToCollectionIfMissing(magacinskaKarticaCollection, undefined, null);
        expect(expectedResult).toEqual(magacinskaKarticaCollection);
      });
    });

    describe('compareMagacinskaKartica', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMagacinskaKartica(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMagacinskaKartica(entity1, entity2);
        const compareResult2 = service.compareMagacinskaKartica(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMagacinskaKartica(entity1, entity2);
        const compareResult2 = service.compareMagacinskaKartica(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMagacinskaKartica(entity1, entity2);
        const compareResult2 = service.compareMagacinskaKartica(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
