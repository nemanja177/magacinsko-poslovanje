import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RadnikFormService } from './radnik-form.service';
import { RadnikService } from '../service/radnik.service';
import { IRadnik } from '../radnik.model';

import { RadnikUpdateComponent } from './radnik-update.component';

describe('Radnik Management Update Component', () => {
  let comp: RadnikUpdateComponent;
  let fixture: ComponentFixture<RadnikUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let radnikFormService: RadnikFormService;
  let radnikService: RadnikService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RadnikUpdateComponent],
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
      .overrideTemplate(RadnikUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RadnikUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    radnikFormService = TestBed.inject(RadnikFormService);
    radnikService = TestBed.inject(RadnikService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const radnik: IRadnik = { id: 456 };

      activatedRoute.data = of({ radnik });
      comp.ngOnInit();

      expect(comp.radnik).toEqual(radnik);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRadnik>>();
      const radnik = { id: 123 };
      jest.spyOn(radnikFormService, 'getRadnik').mockReturnValue(radnik);
      jest.spyOn(radnikService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ radnik });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: radnik }));
      saveSubject.complete();

      // THEN
      expect(radnikFormService.getRadnik).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(radnikService.update).toHaveBeenCalledWith(expect.objectContaining(radnik));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRadnik>>();
      const radnik = { id: 123 };
      jest.spyOn(radnikFormService, 'getRadnik').mockReturnValue({ id: null });
      jest.spyOn(radnikService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ radnik: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: radnik }));
      saveSubject.complete();

      // THEN
      expect(radnikFormService.getRadnik).toHaveBeenCalled();
      expect(radnikService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRadnik>>();
      const radnik = { id: 123 };
      jest.spyOn(radnikService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ radnik });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(radnikService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
