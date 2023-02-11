import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AnalitickaMagacinskaKarticaFormService } from './analiticka-magacinska-kartica-form.service';
import { AnalitickaMagacinskaKarticaService } from '../service/analiticka-magacinska-kartica.service';
import { IAnalitickaMagacinskaKartica } from '../analiticka-magacinska-kartica.model';

import { AnalitickaMagacinskaKarticaUpdateComponent } from './analiticka-magacinska-kartica-update.component';

describe('AnalitickaMagacinskaKartica Management Update Component', () => {
  let comp: AnalitickaMagacinskaKarticaUpdateComponent;
  let fixture: ComponentFixture<AnalitickaMagacinskaKarticaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let analitickaMagacinskaKarticaFormService: AnalitickaMagacinskaKarticaFormService;
  let analitickaMagacinskaKarticaService: AnalitickaMagacinskaKarticaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AnalitickaMagacinskaKarticaUpdateComponent],
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
      .overrideTemplate(AnalitickaMagacinskaKarticaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnalitickaMagacinskaKarticaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    analitickaMagacinskaKarticaFormService = TestBed.inject(AnalitickaMagacinskaKarticaFormService);
    analitickaMagacinskaKarticaService = TestBed.inject(AnalitickaMagacinskaKarticaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const analitickaMagacinskaKartica: IAnalitickaMagacinskaKartica = { id: 456 };

      activatedRoute.data = of({ analitickaMagacinskaKartica });
      comp.ngOnInit();

      expect(comp.analitickaMagacinskaKartica).toEqual(analitickaMagacinskaKartica);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnalitickaMagacinskaKartica>>();
      const analitickaMagacinskaKartica = { id: 123 };
      jest.spyOn(analitickaMagacinskaKarticaFormService, 'getAnalitickaMagacinskaKartica').mockReturnValue(analitickaMagacinskaKartica);
      jest.spyOn(analitickaMagacinskaKarticaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ analitickaMagacinskaKartica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: analitickaMagacinskaKartica }));
      saveSubject.complete();

      // THEN
      expect(analitickaMagacinskaKarticaFormService.getAnalitickaMagacinskaKartica).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(analitickaMagacinskaKarticaService.update).toHaveBeenCalledWith(expect.objectContaining(analitickaMagacinskaKartica));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnalitickaMagacinskaKartica>>();
      const analitickaMagacinskaKartica = { id: 123 };
      jest.spyOn(analitickaMagacinskaKarticaFormService, 'getAnalitickaMagacinskaKartica').mockReturnValue({ id: null });
      jest.spyOn(analitickaMagacinskaKarticaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ analitickaMagacinskaKartica: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: analitickaMagacinskaKartica }));
      saveSubject.complete();

      // THEN
      expect(analitickaMagacinskaKarticaFormService.getAnalitickaMagacinskaKartica).toHaveBeenCalled();
      expect(analitickaMagacinskaKarticaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnalitickaMagacinskaKartica>>();
      const analitickaMagacinskaKartica = { id: 123 };
      jest.spyOn(analitickaMagacinskaKarticaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ analitickaMagacinskaKartica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(analitickaMagacinskaKarticaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
