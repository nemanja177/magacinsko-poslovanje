import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IJedinicaMere } from '../jedinica-mere.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../jedinica-mere.test-samples';

import { JedinicaMereService } from './jedinica-mere.service';

const requireRestSample: IJedinicaMere = {
  ...sampleWithRequiredData,
};

describe('JedinicaMere Service', () => {
  let service: JedinicaMereService;
  let httpMock: HttpTestingController;
  let expectedResult: IJedinicaMere | IJedinicaMere[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(JedinicaMereService);
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

    it('should create a JedinicaMere', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const jedinicaMere = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(jedinicaMere).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a JedinicaMere', () => {
      const jedinicaMere = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(jedinicaMere).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a JedinicaMere', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of JedinicaMere', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a JedinicaMere', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addJedinicaMereToCollectionIfMissing', () => {
      it('should add a JedinicaMere to an empty array', () => {
        const jedinicaMere: IJedinicaMere = sampleWithRequiredData;
        expectedResult = service.addJedinicaMereToCollectionIfMissing([], jedinicaMere);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(jedinicaMere);
      });

      it('should not add a JedinicaMere to an array that contains it', () => {
        const jedinicaMere: IJedinicaMere = sampleWithRequiredData;
        const jedinicaMereCollection: IJedinicaMere[] = [
          {
            ...jedinicaMere,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addJedinicaMereToCollectionIfMissing(jedinicaMereCollection, jedinicaMere);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a JedinicaMere to an array that doesn't contain it", () => {
        const jedinicaMere: IJedinicaMere = sampleWithRequiredData;
        const jedinicaMereCollection: IJedinicaMere[] = [sampleWithPartialData];
        expectedResult = service.addJedinicaMereToCollectionIfMissing(jedinicaMereCollection, jedinicaMere);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(jedinicaMere);
      });

      it('should add only unique JedinicaMere to an array', () => {
        const jedinicaMereArray: IJedinicaMere[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const jedinicaMereCollection: IJedinicaMere[] = [sampleWithRequiredData];
        expectedResult = service.addJedinicaMereToCollectionIfMissing(jedinicaMereCollection, ...jedinicaMereArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const jedinicaMere: IJedinicaMere = sampleWithRequiredData;
        const jedinicaMere2: IJedinicaMere = sampleWithPartialData;
        expectedResult = service.addJedinicaMereToCollectionIfMissing([], jedinicaMere, jedinicaMere2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(jedinicaMere);
        expect(expectedResult).toContain(jedinicaMere2);
      });

      it('should accept null and undefined values', () => {
        const jedinicaMere: IJedinicaMere = sampleWithRequiredData;
        expectedResult = service.addJedinicaMereToCollectionIfMissing([], null, jedinicaMere, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(jedinicaMere);
      });

      it('should return initial array if no JedinicaMere is added', () => {
        const jedinicaMereCollection: IJedinicaMere[] = [sampleWithRequiredData];
        expectedResult = service.addJedinicaMereToCollectionIfMissing(jedinicaMereCollection, undefined, null);
        expect(expectedResult).toEqual(jedinicaMereCollection);
      });
    });

    describe('compareJedinicaMere', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareJedinicaMere(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareJedinicaMere(entity1, entity2);
        const compareResult2 = service.compareJedinicaMere(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareJedinicaMere(entity1, entity2);
        const compareResult2 = service.compareJedinicaMere(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareJedinicaMere(entity1, entity2);
        const compareResult2 = service.compareJedinicaMere(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
