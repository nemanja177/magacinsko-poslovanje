import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PreduzeceService } from '../service/preduzece.service';

import { PreduzeceComponent } from './preduzece.component';

describe('Preduzece Management Component', () => {
  let comp: PreduzeceComponent;
  let fixture: ComponentFixture<PreduzeceComponent>;
  let service: PreduzeceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'preduzece', component: PreduzeceComponent }]), HttpClientTestingModule],
      declarations: [PreduzeceComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PreduzeceComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PreduzeceComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PreduzeceService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.preduzeces?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to preduzeceService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPreduzeceIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPreduzeceIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
