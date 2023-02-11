import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PopisFormService } from './popis-form.service';
import { PopisService } from '../service/popis.service';
import { IPopis } from '../popis.model';

import { PopisUpdateComponent } from './popis-update.component';

describe('Popis Management Update Component', () => {
  let comp: PopisUpdateComponent;
  let fixture: ComponentFixture<PopisUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let popisFormService: PopisFormService;
  let popisService: PopisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PopisUpdateComponent],
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
      .overrideTemplate(PopisUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PopisUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    popisFormService = TestBed.inject(PopisFormService);
    popisService = TestBed.inject(PopisService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const popis: IPopis = { id: 456 };

      activatedRoute.data = of({ popis });
      comp.ngOnInit();

      expect(comp.popis).toEqual(popis);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPopis>>();
      const popis = { id: 123 };
      jest.spyOn(popisFormService, 'getPopis').mockReturnValue(popis);
      jest.spyOn(popisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ popis });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: popis }));
      saveSubject.complete();

      // THEN
      expect(popisFormService.getPopis).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(popisService.update).toHaveBeenCalledWith(expect.objectContaining(popis));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPopis>>();
      const popis = { id: 123 };
      jest.spyOn(popisFormService, 'getPopis').mockReturnValue({ id: null });
      jest.spyOn(popisService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ popis: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: popis }));
      saveSubject.complete();

      // THEN
      expect(popisFormService.getPopis).toHaveBeenCalled();
      expect(popisService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPopis>>();
      const popis = { id: 123 };
      jest.spyOn(popisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ popis });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(popisService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
