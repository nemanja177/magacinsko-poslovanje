import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MagacinService } from '../service/magacin.service';

import { MagacinComponent } from './magacin.component';

describe('Magacin Management Component', () => {
  let comp: MagacinComponent;
  let fixture: ComponentFixture<MagacinComponent>;
  let service: MagacinService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'magacin', component: MagacinComponent }]), HttpClientTestingModule],
      declarations: [MagacinComponent],
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
      .overrideTemplate(MagacinComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MagacinComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MagacinService);

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
    expect(comp.magacins?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to magacinService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getMagacinIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMagacinIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
