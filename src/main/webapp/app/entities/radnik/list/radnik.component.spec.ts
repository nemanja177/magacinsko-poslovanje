import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { RadnikService } from '../service/radnik.service';

import { RadnikComponent } from './radnik.component';

describe('Radnik Management Component', () => {
  let comp: RadnikComponent;
  let fixture: ComponentFixture<RadnikComponent>;
  let service: RadnikService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'radnik', component: RadnikComponent }]), HttpClientTestingModule],
      declarations: [RadnikComponent],
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
      .overrideTemplate(RadnikComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RadnikComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(RadnikService);

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
    expect(comp.radniks?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to radnikService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getRadnikIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getRadnikIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
