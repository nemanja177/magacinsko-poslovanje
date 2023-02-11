import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PrometMagacinskeKarticeService } from '../service/promet-magacinske-kartice.service';

import { PrometMagacinskeKarticeComponent } from './promet-magacinske-kartice.component';

describe('PrometMagacinskeKartice Management Component', () => {
  let comp: PrometMagacinskeKarticeComponent;
  let fixture: ComponentFixture<PrometMagacinskeKarticeComponent>;
  let service: PrometMagacinskeKarticeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'promet-magacinske-kartice', component: PrometMagacinskeKarticeComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [PrometMagacinskeKarticeComponent],
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
      .overrideTemplate(PrometMagacinskeKarticeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrometMagacinskeKarticeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PrometMagacinskeKarticeService);

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
    expect(comp.prometMagacinskeKartices?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to prometMagacinskeKarticeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPrometMagacinskeKarticeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPrometMagacinskeKarticeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
