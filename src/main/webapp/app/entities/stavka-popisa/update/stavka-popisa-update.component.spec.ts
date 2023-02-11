import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StavkaPopisaFormService } from './stavka-popisa-form.service';
import { StavkaPopisaService } from '../service/stavka-popisa.service';
import { IStavkaPopisa } from '../stavka-popisa.model';

import { StavkaPopisaUpdateComponent } from './stavka-popisa-update.component';

describe('StavkaPopisa Management Update Component', () => {
  let comp: StavkaPopisaUpdateComponent;
  let fixture: ComponentFixture<StavkaPopisaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let stavkaPopisaFormService: StavkaPopisaFormService;
  let stavkaPopisaService: StavkaPopisaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StavkaPopisaUpdateComponent],
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
      .overrideTemplate(StavkaPopisaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StavkaPopisaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    stavkaPopisaFormService = TestBed.inject(StavkaPopisaFormService);
    stavkaPopisaService = TestBed.inject(StavkaPopisaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const stavkaPopisa: IStavkaPopisa = { id: 456 };

      activatedRoute.data = of({ stavkaPopisa });
      comp.ngOnInit();

      expect(comp.stavkaPopisa).toEqual(stavkaPopisa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStavkaPopisa>>();
      const stavkaPopisa = { id: 123 };
      jest.spyOn(stavkaPopisaFormService, 'getStavkaPopisa').mockReturnValue(stavkaPopisa);
      jest.spyOn(stavkaPopisaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stavkaPopisa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stavkaPopisa }));
      saveSubject.complete();

      // THEN
      expect(stavkaPopisaFormService.getStavkaPopisa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(stavkaPopisaService.update).toHaveBeenCalledWith(expect.objectContaining(stavkaPopisa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStavkaPopisa>>();
      const stavkaPopisa = { id: 123 };
      jest.spyOn(stavkaPopisaFormService, 'getStavkaPopisa').mockReturnValue({ id: null });
      jest.spyOn(stavkaPopisaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stavkaPopisa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stavkaPopisa }));
      saveSubject.complete();

      // THEN
      expect(stavkaPopisaFormService.getStavkaPopisa).toHaveBeenCalled();
      expect(stavkaPopisaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStavkaPopisa>>();
      const stavkaPopisa = { id: 123 };
      jest.spyOn(stavkaPopisaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stavkaPopisa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(stavkaPopisaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
