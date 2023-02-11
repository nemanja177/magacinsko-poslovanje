import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MagacinskaKarticaFormService } from './magacinska-kartica-form.service';
import { MagacinskaKarticaService } from '../service/magacinska-kartica.service';
import { IMagacinskaKartica } from '../magacinska-kartica.model';

import { MagacinskaKarticaUpdateComponent } from './magacinska-kartica-update.component';

describe('MagacinskaKartica Management Update Component', () => {
  let comp: MagacinskaKarticaUpdateComponent;
  let fixture: ComponentFixture<MagacinskaKarticaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let magacinskaKarticaFormService: MagacinskaKarticaFormService;
  let magacinskaKarticaService: MagacinskaKarticaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MagacinskaKarticaUpdateComponent],
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
      .overrideTemplate(MagacinskaKarticaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MagacinskaKarticaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    magacinskaKarticaFormService = TestBed.inject(MagacinskaKarticaFormService);
    magacinskaKarticaService = TestBed.inject(MagacinskaKarticaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const magacinskaKartica: IMagacinskaKartica = { id: 456 };

      activatedRoute.data = of({ magacinskaKartica });
      comp.ngOnInit();

      expect(comp.magacinskaKartica).toEqual(magacinskaKartica);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMagacinskaKartica>>();
      const magacinskaKartica = { id: 123 };
      jest.spyOn(magacinskaKarticaFormService, 'getMagacinskaKartica').mockReturnValue(magacinskaKartica);
      jest.spyOn(magacinskaKarticaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ magacinskaKartica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: magacinskaKartica }));
      saveSubject.complete();

      // THEN
      expect(magacinskaKarticaFormService.getMagacinskaKartica).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(magacinskaKarticaService.update).toHaveBeenCalledWith(expect.objectContaining(magacinskaKartica));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMagacinskaKartica>>();
      const magacinskaKartica = { id: 123 };
      jest.spyOn(magacinskaKarticaFormService, 'getMagacinskaKartica').mockReturnValue({ id: null });
      jest.spyOn(magacinskaKarticaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ magacinskaKartica: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: magacinskaKartica }));
      saveSubject.complete();

      // THEN
      expect(magacinskaKarticaFormService.getMagacinskaKartica).toHaveBeenCalled();
      expect(magacinskaKarticaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMagacinskaKartica>>();
      const magacinskaKartica = { id: 123 };
      jest.spyOn(magacinskaKarticaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ magacinskaKartica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(magacinskaKarticaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
