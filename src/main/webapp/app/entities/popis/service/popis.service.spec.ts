import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPopis } from '../popis.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../popis.test-samples';

import { PopisService, RestPopis } from './popis.service';

const requireRestSample: RestPopis = {
  ...sampleWithRequiredData,
  datumPopisa: sampleWithRequiredData.datumPopisa?.format(DATE_FORMAT),
};

describe('Popis Service', () => {
  let service: PopisService;
  let httpMock: HttpTestingController;
  let expectedResult: IPopis | IPopis[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PopisService);
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

    it('should create a Popis', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const popis = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(popis).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Popis', () => {
      const popis = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(popis).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Popis', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Popis', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Popis', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPopisToCollectionIfMissing', () => {
      it('should add a Popis to an empty array', () => {
        const popis: IPopis = sampleWithRequiredData;
        expectedResult = service.addPopisToCollectionIfMissing([], popis);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(popis);
      });

      it('should not add a Popis to an array that contains it', () => {
        const popis: IPopis = sampleWithRequiredData;
        const popisCollection: IPopis[] = [
          {
            ...popis,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPopisToCollectionIfMissing(popisCollection, popis);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Popis to an array that doesn't contain it", () => {
        const popis: IPopis = sampleWithRequiredData;
        const popisCollection: IPopis[] = [sampleWithPartialData];
        expectedResult = service.addPopisToCollectionIfMissing(popisCollection, popis);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(popis);
      });

      it('should add only unique Popis to an array', () => {
        const popisArray: IPopis[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const popisCollection: IPopis[] = [sampleWithRequiredData];
        expectedResult = service.addPopisToCollectionIfMissing(popisCollection, ...popisArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const popis: IPopis = sampleWithRequiredData;
        const popis2: IPopis = sampleWithPartialData;
        expectedResult = service.addPopisToCollectionIfMissing([], popis, popis2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(popis);
        expect(expectedResult).toContain(popis2);
      });

      it('should accept null and undefined values', () => {
        const popis: IPopis = sampleWithRequiredData;
        expectedResult = service.addPopisToCollectionIfMissing([], null, popis, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(popis);
      });

      it('should return initial array if no Popis is added', () => {
        const popisCollection: IPopis[] = [sampleWithRequiredData];
        expectedResult = service.addPopisToCollectionIfMissing(popisCollection, undefined, null);
        expect(expectedResult).toEqual(popisCollection);
      });
    });

    describe('comparePopis', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePopis(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePopis(entity1, entity2);
        const compareResult2 = service.comparePopis(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePopis(entity1, entity2);
        const compareResult2 = service.comparePopis(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePopis(entity1, entity2);
        const compareResult2 = service.comparePopis(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
