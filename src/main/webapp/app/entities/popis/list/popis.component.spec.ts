import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PopisService } from '../service/popis.service';

import { PopisComponent } from './popis.component';

describe('Popis Management Component', () => {
  let comp: PopisComponent;
  let fixture: ComponentFixture<PopisComponent>;
  let service: PopisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'popis', component: PopisComponent }]), HttpClientTestingModule],
      declarations: [PopisComponent],
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
      .overrideTemplate(PopisComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PopisComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PopisService);

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
    expect(comp.popis?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to popisService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPopisIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPopisIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
