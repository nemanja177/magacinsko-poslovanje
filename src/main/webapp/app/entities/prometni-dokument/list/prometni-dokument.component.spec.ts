import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PrometniDokumentService } from '../service/prometni-dokument.service';

import { PrometniDokumentComponent } from './prometni-dokument.component';

describe('PrometniDokument Management Component', () => {
  let comp: PrometniDokumentComponent;
  let fixture: ComponentFixture<PrometniDokumentComponent>;
  let service: PrometniDokumentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'prometni-dokument', component: PrometniDokumentComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [PrometniDokumentComponent],
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
      .overrideTemplate(PrometniDokumentComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrometniDokumentComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PrometniDokumentService);

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
    expect(comp.prometniDokuments?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to prometniDokumentService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPrometniDokumentIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPrometniDokumentIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
