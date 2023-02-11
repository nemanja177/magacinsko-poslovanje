import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPoslovniPartner } from '../poslovni-partner.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../poslovni-partner.test-samples';

import { PoslovniPartnerService } from './poslovni-partner.service';

const requireRestSample: IPoslovniPartner = {
  ...sampleWithRequiredData,
};

describe('PoslovniPartner Service', () => {
  let service: PoslovniPartnerService;
  let httpMock: HttpTestingController;
  let expectedResult: IPoslovniPartner | IPoslovniPartner[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PoslovniPartnerService);
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

    it('should create a PoslovniPartner', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const poslovniPartner = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(poslovniPartner).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PoslovniPartner', () => {
      const poslovniPartner = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(poslovniPartner).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PoslovniPartner', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PoslovniPartner', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PoslovniPartner', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPoslovniPartnerToCollectionIfMissing', () => {
      it('should add a PoslovniPartner to an empty array', () => {
        const poslovniPartner: IPoslovniPartner = sampleWithRequiredData;
        expectedResult = service.addPoslovniPartnerToCollectionIfMissing([], poslovniPartner);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(poslovniPartner);
      });

      it('should not add a PoslovniPartner to an array that contains it', () => {
        const poslovniPartner: IPoslovniPartner = sampleWithRequiredData;
        const poslovniPartnerCollection: IPoslovniPartner[] = [
          {
            ...poslovniPartner,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPoslovniPartnerToCollectionIfMissing(poslovniPartnerCollection, poslovniPartner);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PoslovniPartner to an array that doesn't contain it", () => {
        const poslovniPartner: IPoslovniPartner = sampleWithRequiredData;
        const poslovniPartnerCollection: IPoslovniPartner[] = [sampleWithPartialData];
        expectedResult = service.addPoslovniPartnerToCollectionIfMissing(poslovniPartnerCollection, poslovniPartner);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(poslovniPartner);
      });

      it('should add only unique PoslovniPartner to an array', () => {
        const poslovniPartnerArray: IPoslovniPartner[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const poslovniPartnerCollection: IPoslovniPartner[] = [sampleWithRequiredData];
        expectedResult = service.addPoslovniPartnerToCollectionIfMissing(poslovniPartnerCollection, ...poslovniPartnerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const poslovniPartner: IPoslovniPartner = sampleWithRequiredData;
        const poslovniPartner2: IPoslovniPartner = sampleWithPartialData;
        expectedResult = service.addPoslovniPartnerToCollectionIfMissing([], poslovniPartner, poslovniPartner2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(poslovniPartner);
        expect(expectedResult).toContain(poslovniPartner2);
      });

      it('should accept null and undefined values', () => {
        const poslovniPartner: IPoslovniPartner = sampleWithRequiredData;
        expectedResult = service.addPoslovniPartnerToCollectionIfMissing([], null, poslovniPartner, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(poslovniPartner);
      });

      it('should return initial array if no PoslovniPartner is added', () => {
        const poslovniPartnerCollection: IPoslovniPartner[] = [sampleWithRequiredData];
        expectedResult = service.addPoslovniPartnerToCollectionIfMissing(poslovniPartnerCollection, undefined, null);
        expect(expectedResult).toEqual(poslovniPartnerCollection);
      });
    });

    describe('comparePoslovniPartner', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePoslovniPartner(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePoslovniPartner(entity1, entity2);
        const compareResult2 = service.comparePoslovniPartner(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePoslovniPartner(entity1, entity2);
        const compareResult2 = service.comparePoslovniPartner(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePoslovniPartner(entity1, entity2);
        const compareResult2 = service.comparePoslovniPartner(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
