import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IStavkaPrometnogDokumenta } from '../stavka-prometnog-dokumenta.model';
import { StavkaPrometnogDokumentaService } from '../service/stavka-prometnog-dokumenta.service';

import { StavkaPrometnogDokumentaRoutingResolveService } from './stavka-prometnog-dokumenta-routing-resolve.service';

describe('StavkaPrometnogDokumenta routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: StavkaPrometnogDokumentaRoutingResolveService;
  let service: StavkaPrometnogDokumentaService;
  let resultStavkaPrometnogDokumenta: IStavkaPrometnogDokumenta | null | undefined;

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
    routingResolveService = TestBed.inject(StavkaPrometnogDokumentaRoutingResolveService);
    service = TestBed.inject(StavkaPrometnogDokumentaService);
    resultStavkaPrometnogDokumenta = undefined;
  });

  describe('resolve', () => {
    it('should return IStavkaPrometnogDokumenta returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStavkaPrometnogDokumenta = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStavkaPrometnogDokumenta).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStavkaPrometnogDokumenta = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultStavkaPrometnogDokumenta).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IStavkaPrometnogDokumenta>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStavkaPrometnogDokumenta = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStavkaPrometnogDokumenta).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
