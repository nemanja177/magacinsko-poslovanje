import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPrometniDokument } from '../prometni-dokument.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../prometni-dokument.test-samples';

import { PrometniDokumentService, RestPrometniDokument } from './prometni-dokument.service';

const requireRestSample: RestPrometniDokument = {
  ...sampleWithRequiredData,
  datum: sampleWithRequiredData.datum?.format(DATE_FORMAT),
};

describe('PrometniDokument Service', () => {
  let service: PrometniDokumentService;
  let httpMock: HttpTestingController;
  let expectedResult: IPrometniDokument | IPrometniDokument[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PrometniDokumentService);
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

    it('should create a PrometniDokument', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const prometniDokument = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(prometniDokument).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PrometniDokument', () => {
      const prometniDokument = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(prometniDokument).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PrometniDokument', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PrometniDokument', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PrometniDokument', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPrometniDokumentToCollectionIfMissing', () => {
      it('should add a PrometniDokument to an empty array', () => {
        const prometniDokument: IPrometniDokument = sampleWithRequiredData;
        expectedResult = service.addPrometniDokumentToCollectionIfMissing([], prometniDokument);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prometniDokument);
      });

      it('should not add a PrometniDokument to an array that contains it', () => {
        const prometniDokument: IPrometniDokument = sampleWithRequiredData;
        const prometniDokumentCollection: IPrometniDokument[] = [
          {
            ...prometniDokument,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPrometniDokumentToCollectionIfMissing(prometniDokumentCollection, prometniDokument);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PrometniDokument to an array that doesn't contain it", () => {
        const prometniDokument: IPrometniDokument = sampleWithRequiredData;
        const prometniDokumentCollection: IPrometniDokument[] = [sampleWithPartialData];
        expectedResult = service.addPrometniDokumentToCollectionIfMissing(prometniDokumentCollection, prometniDokument);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prometniDokument);
      });

      it('should add only unique PrometniDokument to an array', () => {
        const prometniDokumentArray: IPrometniDokument[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const prometniDokumentCollection: IPrometniDokument[] = [sampleWithRequiredData];
        expectedResult = service.addPrometniDokumentToCollectionIfMissing(prometniDokumentCollection, ...prometniDokumentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const prometniDokument: IPrometniDokument = sampleWithRequiredData;
        const prometniDokument2: IPrometniDokument = sampleWithPartialData;
        expectedResult = service.addPrometniDokumentToCollectionIfMissing([], prometniDokument, prometniDokument2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prometniDokument);
        expect(expectedResult).toContain(prometniDokument2);
      });

      it('should accept null and undefined values', () => {
        const prometniDokument: IPrometniDokument = sampleWithRequiredData;
        expectedResult = service.addPrometniDokumentToCollectionIfMissing([], null, prometniDokument, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prometniDokument);
      });

      it('should return initial array if no PrometniDokument is added', () => {
        const prometniDokumentCollection: IPrometniDokument[] = [sampleWithRequiredData];
        expectedResult = service.addPrometniDokumentToCollectionIfMissing(prometniDokumentCollection, undefined, null);
        expect(expectedResult).toEqual(prometniDokumentCollection);
      });
    });

    describe('comparePrometniDokument', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePrometniDokument(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePrometniDokument(entity1, entity2);
        const compareResult2 = service.comparePrometniDokument(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePrometniDokument(entity1, entity2);
        const compareResult2 = service.comparePrometniDokument(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePrometniDokument(entity1, entity2);
        const compareResult2 = service.comparePrometniDokument(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
