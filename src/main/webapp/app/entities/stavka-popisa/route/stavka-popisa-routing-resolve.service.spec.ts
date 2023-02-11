import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IStavkaPopisa } from '../stavka-popisa.model';
import { StavkaPopisaService } from '../service/stavka-popisa.service';

import { StavkaPopisaRoutingResolveService } from './stavka-popisa-routing-resolve.service';

describe('StavkaPopisa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: StavkaPopisaRoutingResolveService;
  let service: StavkaPopisaService;
  let resultStavkaPopisa: IStavkaPopisa | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(StavkaPopisaRoutingResolveService);
    service = TestBed.inject(StavkaPopisaService);
    resultStavkaPopisa = undefined;
  });

  describe('resolve', () => {
    it('should return IStavkaPopisa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStavkaPopisa = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStavkaPopisa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStavkaPopisa = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultStavkaPopisa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IStavkaPopisa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStavkaPopisa = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStavkaPopisa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
