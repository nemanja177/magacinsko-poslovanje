import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAnalitickaMagacinskaKartica } from '../analiticka-magacinska-kartica.model';
import { AnalitickaMagacinskaKarticaService } from '../service/analiticka-magacinska-kartica.service';

import { AnalitickaMagacinskaKarticaRoutingResolveService } from './analiticka-magacinska-kartica-routing-resolve.service';

describe('AnalitickaMagacinskaKartica routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AnalitickaMagacinskaKarticaRoutingResolveService;
  let service: AnalitickaMagacinskaKarticaService;
  let resultAnalitickaMagacinskaKartica: IAnalitickaMagacinskaKartica | null | undefined;

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
    routingResolveService = TestBed.inject(AnalitickaMagacinskaKarticaRoutingResolveService);
    service = TestBed.inject(AnalitickaMagacinskaKarticaService);
    resultAnalitickaMagacinskaKartica = undefined;
  });

  describe('resolve', () => {
    it('should return IAnalitickaMagacinskaKartica returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAnalitickaMagacinskaKartica = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAnalitickaMagacinskaKartica).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAnalitickaMagacinskaKartica = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAnalitickaMagacinskaKartica).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAnalitickaMagacinskaKartica>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAnalitickaMagacinskaKartica = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAnalitickaMagacinskaKartica).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
