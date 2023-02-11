import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPoslovanGodina } from '../poslovan-godina.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../poslovan-godina.test-samples';

import { PoslovanGodinaService } from './poslovan-godina.service';

const requireRestSample: IPoslovanGodina = {
  ...sampleWithRequiredData,
};

describe('PoslovanGodina Service', () => {
  let service: PoslovanGodinaService;
  let httpMock: HttpTestingController;
  let expectedResult: IPoslovanGodina | IPoslovanGodina[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PoslovanGodinaService);
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

    it('should create a PoslovanGodina', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const poslovanGodina = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(poslovanGodina).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PoslovanGodina', () => {
      const poslovanGodina = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(poslovanGodina).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PoslovanGodina', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PoslovanGodina', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PoslovanGodina', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPoslovanGodinaToCollectionIfMissing', () => {
      it('should add a PoslovanGodina to an empty array', () => {
        const poslovanGodina: IPoslovanGodina = sampleWithRequiredData;
        expectedResult = service.addPoslovanGodinaToCollectionIfMissing([], poslovanGodina);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(poslovanGodina);
      });

      it('should not add a PoslovanGodina to an array that contains it', () => {
        const poslovanGodina: IPoslovanGodina = sampleWithRequiredData;
        const poslovanGodinaCollection: IPoslovanGodina[] = [
          {
            ...poslovanGodina,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPoslovanGodinaToCollectionIfMissing(poslovanGodinaCollection, poslovanGodina);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PoslovanGodina to an array that doesn't contain it", () => {
        const poslovanGodina: IPoslovanGodina = sampleWithRequiredData;
        const poslovanGodinaCollection: IPoslovanGodina[] = [sampleWithPartialData];
        expectedResult = service.addPoslovanGodinaToCollectionIfMissing(poslovanGodinaCollection, poslovanGodina);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(poslovanGodina);
      });

      it('should add only unique PoslovanGodina to an array', () => {
        const poslovanGodinaArray: IPoslovanGodina[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const poslovanGodinaCollection: IPoslovanGodina[] = [sampleWithRequiredData];
        expectedResult = service.addPoslovanGodinaToCollectionIfMissing(poslovanGodinaCollection, ...poslovanGodinaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const poslovanGodina: IPoslovanGodina = sampleWithRequiredData;
        const poslovanGodina2: IPoslovanGodina = sampleWithPartialData;
        expectedResult = service.addPoslovanGodinaToCollectionIfMissing([], poslovanGodina, poslovanGodina2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(poslovanGodina);
        expect(expectedResult).toContain(poslovanGodina2);
      });

      it('should accept null and undefined values', () => {
        const poslovanGodina: IPoslovanGodina = sampleWithRequiredData;
        expectedResult = service.addPoslovanGodinaToCollectionIfMissing([], null, poslovanGodina, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(poslovanGodina);
      });

      it('should return initial array if no PoslovanGodina is added', () => {
        const poslovanGodinaCollection: IPoslovanGodina[] = [sampleWithRequiredData];
        expectedResult = service.addPoslovanGodinaToCollectionIfMissing(poslovanGodinaCollection, undefined, null);
        expect(expectedResult).toEqual(poslovanGodinaCollection);
      });
    });

    describe('comparePoslovanGodina', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePoslovanGodina(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePoslovanGodina(entity1, entity2);
        const compareResult2 = service.comparePoslovanGodina(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePoslovanGodina(entity1, entity2);
        const compareResult2 = service.comparePoslovanGodina(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePoslovanGodina(entity1, entity2);
        const compareResult2 = service.comparePoslovanGodina(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
