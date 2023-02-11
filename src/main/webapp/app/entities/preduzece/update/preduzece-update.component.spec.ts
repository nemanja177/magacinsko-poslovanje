import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PreduzeceFormService } from './preduzece-form.service';
import { PreduzeceService } from '../service/preduzece.service';
import { IPreduzece } from '../preduzece.model';

import { PreduzeceUpdateComponent } from './preduzece-update.component';

describe('Preduzece Management Update Component', () => {
  let comp: PreduzeceUpdateComponent;
  let fixture: ComponentFixture<PreduzeceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let preduzeceFormService: PreduzeceFormService;
  let preduzeceService: PreduzeceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PreduzeceUpdateComponent],
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
      .overrideTemplate(PreduzeceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PreduzeceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    preduzeceFormService = TestBed.inject(PreduzeceFormService);
    preduzeceService = TestBed.inject(PreduzeceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const preduzece: IPreduzece = { id: 456 };

      activatedRoute.data = of({ preduzece });
      comp.ngOnInit();

      expect(comp.preduzece).toEqual(preduzece);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPreduzece>>();
      const preduzece = { id: 123 };
      jest.spyOn(preduzeceFormService, 'getPreduzece').mockReturnValue(preduzece);
      jest.spyOn(preduzeceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ preduzece });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: preduzece }));
      saveSubject.complete();

      // THEN
      expect(preduzeceFormService.getPreduzece).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(preduzeceService.update).toHaveBeenCalledWith(expect.objectContaining(preduzece));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPreduzece>>();
      const preduzece = { id: 123 };
      jest.spyOn(preduzeceFormService, 'getPreduzece').mockReturnValue({ id: null });
      jest.spyOn(preduzeceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ preduzece: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: preduzece }));
      saveSubject.complete();

      // THEN
      expect(preduzeceFormService.getPreduzece).toHaveBeenCalled();
      expect(preduzeceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPreduzece>>();
      const preduzece = { id: 123 };
      jest.spyOn(preduzeceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ preduzece });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(preduzeceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
