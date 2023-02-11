import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPrometMagacinskeKartice } from '../promet-magacinske-kartice.model';
import { PrometMagacinskeKarticeService } from '../service/promet-magacinske-kartice.service';

import { PrometMagacinskeKarticeRoutingResolveService } from './promet-magacinske-kartice-routing-resolve.service';

describe('PrometMagacinskeKartice routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PrometMagacinskeKarticeRoutingResolveService;
  let service: PrometMagacinskeKarticeService;
  let resultPrometMagacinskeKartice: IPrometMagacinskeKartice | null | undefined;

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
    routingResolveService = TestBed.inject(PrometMagacinskeKarticeRoutingResolveService);
    service = TestBed.inject(PrometMagacinskeKarticeService);
    resultPrometMagacinskeKartice = undefined;
  });

  describe('resolve', () => {
    it('should return IPrometMagacinskeKartice returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrometMagacinskeKartice = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPrometMagacinskeKartice).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrometMagacinskeKartice = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPrometMagacinskeKartice).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPrometMagacinskeKartice>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrometMagacinskeKartice = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPrometMagacinskeKartice).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
