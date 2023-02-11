import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AnalitickaMagacinskaKarticaService } from '../service/analiticka-magacinska-kartica.service';

import { AnalitickaMagacinskaKarticaComponent } from './analiticka-magacinska-kartica.component';

describe('AnalitickaMagacinskaKartica Management Component', () => {
  let comp: AnalitickaMagacinskaKarticaComponent;
  let fixture: ComponentFixture<AnalitickaMagacinskaKarticaComponent>;
  let service: AnalitickaMagacinskaKarticaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'analiticka-magacinska-kartica', component: AnalitickaMagacinskaKarticaComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [AnalitickaMagacinskaKarticaComponent],
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
      .overrideTemplate(AnalitickaMagacinskaKarticaComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnalitickaMagacinskaKarticaComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnalitickaMagacinskaKarticaService);

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
    expect(comp.analitickaMagacinskaKarticas?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to analitickaMagacinskaKarticaService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAnalitickaMagacinskaKarticaIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAnalitickaMagacinskaKarticaIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
