import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PoslovanGodinaService } from '../service/poslovan-godina.service';

import { PoslovanGodinaComponent } from './poslovan-godina.component';

describe('PoslovanGodina Management Component', () => {
  let comp: PoslovanGodinaComponent;
  let fixture: ComponentFixture<PoslovanGodinaComponent>;
  let service: PoslovanGodinaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'poslovan-godina', component: PoslovanGodinaComponent }]), HttpClientTestingModule],
      declarations: [PoslovanGodinaComponent],
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
      .overrideTemplate(PoslovanGodinaComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PoslovanGodinaComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PoslovanGodinaService);

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
    expect(comp.poslovanGodinas?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to poslovanGodinaService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPoslovanGodinaIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPoslovanGodinaIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
