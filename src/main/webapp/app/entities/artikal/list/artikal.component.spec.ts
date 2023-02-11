import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ArtikalService } from '../service/artikal.service';

import { ArtikalComponent } from './artikal.component';

describe('Artikal Management Component', () => {
  let comp: ArtikalComponent;
  let fixture: ComponentFixture<ArtikalComponent>;
  let service: ArtikalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'artikal', component: ArtikalComponent }]), HttpClientTestingModule],
      declarations: [ArtikalComponent],
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
      .overrideTemplate(ArtikalComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ArtikalComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ArtikalService);

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
    expect(comp.artikals?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to artikalService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getArtikalIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getArtikalIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
