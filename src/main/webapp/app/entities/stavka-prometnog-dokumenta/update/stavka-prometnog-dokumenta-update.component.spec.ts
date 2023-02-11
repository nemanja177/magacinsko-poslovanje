import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StavkaPrometnogDokumentaFormService } from './stavka-prometnog-dokumenta-form.service';
import { StavkaPrometnogDokumentaService } from '../service/stavka-prometnog-dokumenta.service';
import { IStavkaPrometnogDokumenta } from '../stavka-prometnog-dokumenta.model';

import { StavkaPrometnogDokumentaUpdateComponent } from './stavka-prometnog-dokumenta-update.component';

describe('StavkaPrometnogDokumenta Management Update Component', () => {
  let comp: StavkaPrometnogDokumentaUpdateComponent;
  let fixture: ComponentFixture<StavkaPrometnogDokumentaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let stavkaPrometnogDokumentaFormService: StavkaPrometnogDokumentaFormService;
  let stavkaPrometnogDokumentaService: StavkaPrometnogDokumentaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StavkaPrometnogDokumentaUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(StavkaPrometnogDokumentaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StavkaPrometnogDokumentaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    stavkaPrometnogDokumentaFormService = TestBed.inject(StavkaPrometnogDokumentaFormService);
    stavkaPrometnogDokumentaService = TestBed.inject(StavkaPrometnogDokumentaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const stavkaPrometnogDokumenta: IStavkaPrometnogDokumenta = { id: 456 };

      activatedRoute.data = of({ stavkaPrometnogDokumenta });
      comp.ngOnInit();

      expect(comp.stavkaPrometnogDokumenta).toEqual(stavkaPrometnogDokumenta);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStavkaPrometnogDokumenta>>();
      const stavkaPrometnogDokumenta = { id: 123 };
      jest.spyOn(stavkaPrometnogDokumentaFormService, 'getStavkaPrometnogDokumenta').mockReturnValue(stavkaPrometnogDokumenta);
      jest.spyOn(stavkaPrometnogDokumentaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stavkaPrometnogDokumenta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stavkaPrometnogDokumenta }));
      saveSubject.complete();

      // THEN
      expect(stavkaPrometnogDokumentaFormService.getStavkaPrometnogDokumenta).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(stavkaPrometnogDokumentaService.update).toHaveBeenCalledWith(expect.objectContaining(stavkaPrometnogDokumenta));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStavkaPrometnogDokumenta>>();
      const stavkaPrometnogDokumenta = { id: 123 };
      jest.spyOn(stavkaPrometnogDokumentaFormService, 'getStavkaPrometnogDokumenta').mockReturnValue({ id: null });
      jest.spyOn(stavkaPrometnogDokumentaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stavkaPrometnogDokumenta: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stavkaPrometnogDokumenta }));
      saveSubject.complete();

      // THEN
      expect(stavkaPrometnogDokumentaFormService.getStavkaPrometnogDokumenta).toHaveBeenCalled();
      expect(stavkaPrometnogDokumentaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStavkaPrometnogDokumenta>>();
      const stavkaPrometnogDokumenta = { id: 123 };
      jest.spyOn(stavkaPrometnogDokumentaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stavkaPrometnogDokumenta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(stavkaPrometnogDokumentaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
