import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { StavkaPrometnogDokumentaService } from '../service/stavka-prometnog-dokumenta.service';

import { StavkaPrometnogDokumentaComponent } from './stavka-prometnog-dokumenta.component';

describe('StavkaPrometnogDokumenta Management Component', () => {
  let comp: StavkaPrometnogDokumentaComponent;
  let fixture: ComponentFixture<StavkaPrometnogDokumentaComponent>;
  let service: StavkaPrometnogDokumentaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'stavka-prometnog-dokumenta', component: StavkaPrometnogDokumentaComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [StavkaPrometnogDokumentaComponent],
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
      .overrideTemplate(StavkaPrometnogDokumentaComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StavkaPrometnogDokumentaComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(StavkaPrometnogDokumentaService);

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
    expect(comp.stavkaPrometnogDokumentas?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to stavkaPrometnogDokumentaService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getStavkaPrometnogDokumentaIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getStavkaPrometnogDokumentaIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
