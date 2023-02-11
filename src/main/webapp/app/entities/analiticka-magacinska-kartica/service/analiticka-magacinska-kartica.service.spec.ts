import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IAnalitickaMagacinskaKartica } from '../analiticka-magacinska-kartica.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../analiticka-magacinska-kartica.test-samples';

import { AnalitickaMagacinskaKarticaService, RestAnalitickaMagacinskaKartica } from './analiticka-magacinska-kartica.service';

const requireRestSample: RestAnalitickaMagacinskaKartica = {
  ...sampleWithRequiredData,
  datumPrometa: sampleWithRequiredData.datumPrometa?.format(DATE_FORMAT),
};

describe('AnalitickaMagacinskaKartica Service', () => {
  let service: AnalitickaMagacinskaKarticaService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnalitickaMagacinskaKartica | IAnalitickaMagacinskaKartica[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AnalitickaMagacinskaKarticaService);
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

    it('should create a AnalitickaMagacinskaKartica', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const analitickaMagacinskaKartica = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(analitickaMagacinskaKartica).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnalitickaMagacinskaKartica', () => {
      const analitickaMagacinskaKartica = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(analitickaMagacinskaKartica).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnalitickaMagacinskaKartica', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnalitickaMagacinskaKartica', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnalitickaMagacinskaKartica', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnalitickaMagacinskaKarticaToCollectionIfMissing', () => {
      it('should add a AnalitickaMagacinskaKartica to an empty array', () => {
        const analitickaMagacinskaKartica: IAnalitickaMagacinskaKartica = sampleWithRequiredData;
        expectedResult = service.addAnalitickaMagacinskaKarticaToCollectionIfMissing([], analitickaMagacinskaKartica);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(analitickaMagacinskaKartica);
      });

      it('should not add a AnalitickaMagacinskaKartica to an array that contains it', () => {
        const analitickaMagacinskaKartica: IAnalitickaMagacinskaKartica = sampleWithRequiredData;
        const analitickaMagacinskaKarticaCollection: IAnalitickaMagacinskaKartica[] = [
          {
            ...analitickaMagacinskaKartica,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnalitickaMagacinskaKarticaToCollectionIfMissing(
          analitickaMagacinskaKarticaCollection,
          analitickaMagacinskaKartica
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnalitickaMagacinskaKartica to an array that doesn't contain it", () => {
        const analitickaMagacinskaKartica: IAnalitickaMagacinskaKartica = sampleWithRequiredData;
        const analitickaMagacinskaKarticaCollection: IAnalitickaMagacinskaKartica[] = [sampleWithPartialData];
        expectedResult = service.addAnalitickaMagacinskaKarticaToCollectionIfMissing(
          analitickaMagacinskaKarticaCollection,
          analitickaMagacinskaKartica
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(analitickaMagacinskaKartica);
      });

      it('should add only unique AnalitickaMagacinskaKartica to an array', () => {
        const analitickaMagacinskaKarticaArray: IAnalitickaMagacinskaKartica[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const analitickaMagacinskaKarticaCollection: IAnalitickaMagacinskaKartica[] = [sampleWithRequiredData];
        expectedResult = service.addAnalitickaMagacinskaKarticaToCollectionIfMissing(
          analitickaMagacinskaKarticaCollection,
          ...analitickaMagacinskaKarticaArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const analitickaMagacinskaKartica: IAnalitickaMagacinskaKartica = sampleWithRequiredData;
        const analitickaMagacinskaKartica2: IAnalitickaMagacinskaKartica = sampleWithPartialData;
        expectedResult = service.addAnalitickaMagacinskaKarticaToCollectionIfMissing(
          [],
          analitickaMagacinskaKartica,
          analitickaMagacinskaKartica2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(analitickaMagacinskaKartica);
        expect(expectedResult).toContain(analitickaMagacinskaKartica2);
      });

      it('should accept null and undefined values', () => {
        const analitickaMagacinskaKartica: IAnalitickaMagacinskaKartica = sampleWithRequiredData;
        expectedResult = service.addAnalitickaMagacinskaKarticaToCollectionIfMissing([], null, analitickaMagacinskaKartica, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(analitickaMagacinskaKartica);
      });

      it('should return initial array if no AnalitickaMagacinskaKartica is added', () => {
        const analitickaMagacinskaKarticaCollection: IAnalitickaMagacinskaKartica[] = [sampleWithRequiredData];
        expectedResult = service.addAnalitickaMagacinskaKarticaToCollectionIfMissing(
          analitickaMagacinskaKarticaCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(analitickaMagacinskaKarticaCollection);
      });
    });

    describe('compareAnalitickaMagacinskaKartica', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnalitickaMagacinskaKartica(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnalitickaMagacinskaKartica(entity1, entity2);
        const compareResult2 = service.compareAnalitickaMagacinskaKartica(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnalitickaMagacinskaKartica(entity1, entity2);
        const compareResult2 = service.compareAnalitickaMagacinskaKartica(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnalitickaMagacinskaKartica(entity1, entity2);
        const compareResult2 = service.compareAnalitickaMagacinskaKartica(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
