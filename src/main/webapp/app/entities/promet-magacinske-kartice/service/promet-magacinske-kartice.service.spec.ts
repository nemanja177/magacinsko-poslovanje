import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPrometMagacinskeKartice } from '../promet-magacinske-kartice.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../promet-magacinske-kartice.test-samples';

import { PrometMagacinskeKarticeService, RestPrometMagacinskeKartice } from './promet-magacinske-kartice.service';

const requireRestSample: RestPrometMagacinskeKartice = {
  ...sampleWithRequiredData,
  datumPrometa: sampleWithRequiredData.datumPrometa?.format(DATE_FORMAT),
};

describe('PrometMagacinskeKartice Service', () => {
  let service: PrometMagacinskeKarticeService;
  let httpMock: HttpTestingController;
  let expectedResult: IPrometMagacinskeKartice | IPrometMagacinskeKartice[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PrometMagacinskeKarticeService);
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

    it('should create a PrometMagacinskeKartice', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const prometMagacinskeKartice = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(prometMagacinskeKartice).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PrometMagacinskeKartice', () => {
      const prometMagacinskeKartice = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(prometMagacinskeKartice).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PrometMagacinskeKartice', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PrometMagacinskeKartice', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PrometMagacinskeKartice', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPrometMagacinskeKarticeToCollectionIfMissing', () => {
      it('should add a PrometMagacinskeKartice to an empty array', () => {
        const prometMagacinskeKartice: IPrometMagacinskeKartice = sampleWithRequiredData;
        expectedResult = service.addPrometMagacinskeKarticeToCollectionIfMissing([], prometMagacinskeKartice);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prometMagacinskeKartice);
      });

      it('should not add a PrometMagacinskeKartice to an array that contains it', () => {
        const prometMagacinskeKartice: IPrometMagacinskeKartice = sampleWithRequiredData;
        const prometMagacinskeKarticeCollection: IPrometMagacinskeKartice[] = [
          {
            ...prometMagacinskeKartice,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPrometMagacinskeKarticeToCollectionIfMissing(
          prometMagacinskeKarticeCollection,
          prometMagacinskeKartice
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PrometMagacinskeKartice to an array that doesn't contain it", () => {
        const prometMagacinskeKartice: IPrometMagacinskeKartice = sampleWithRequiredData;
        const prometMagacinskeKarticeCollection: IPrometMagacinskeKartice[] = [sampleWithPartialData];
        expectedResult = service.addPrometMagacinskeKarticeToCollectionIfMissing(
          prometMagacinskeKarticeCollection,
          prometMagacinskeKartice
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prometMagacinskeKartice);
      });

      it('should add only unique PrometMagacinskeKartice to an array', () => {
        const prometMagacinskeKarticeArray: IPrometMagacinskeKartice[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const prometMagacinskeKarticeCollection: IPrometMagacinskeKartice[] = [sampleWithRequiredData];
        expectedResult = service.addPrometMagacinskeKarticeToCollectionIfMissing(
          prometMagacinskeKarticeCollection,
          ...prometMagacinskeKarticeArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const prometMagacinskeKartice: IPrometMagacinskeKartice = sampleWithRequiredData;
        const prometMagacinskeKartice2: IPrometMagacinskeKartice = sampleWithPartialData;
        expectedResult = service.addPrometMagacinskeKarticeToCollectionIfMissing([], prometMagacinskeKartice, prometMagacinskeKartice2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prometMagacinskeKartice);
        expect(expectedResult).toContain(prometMagacinskeKartice2);
      });

      it('should accept null and undefined values', () => {
        const prometMagacinskeKartice: IPrometMagacinskeKartice = sampleWithRequiredData;
        expectedResult = service.addPrometMagacinskeKarticeToCollectionIfMissing([], null, prometMagacinskeKartice, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prometMagacinskeKartice);
      });

      it('should return initial array if no PrometMagacinskeKartice is added', () => {
        const prometMagacinskeKarticeCollection: IPrometMagacinskeKartice[] = [sampleWithRequiredData];
        expectedResult = service.addPrometMagacinskeKarticeToCollectionIfMissing(prometMagacinskeKarticeCollection, undefined, null);
        expect(expectedResult).toEqual(prometMagacinskeKarticeCollection);
      });
    });

    describe('comparePrometMagacinskeKartice', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePrometMagacinskeKartice(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePrometMagacinskeKartice(entity1, entity2);
        const compareResult2 = service.comparePrometMagacinskeKartice(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePrometMagacinskeKartice(entity1, entity2);
        const compareResult2 = service.comparePrometMagacinskeKartice(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePrometMagacinskeKartice(entity1, entity2);
        const compareResult2 = service.comparePrometMagacinskeKartice(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
