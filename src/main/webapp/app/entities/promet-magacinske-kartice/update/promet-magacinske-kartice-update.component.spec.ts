import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PrometMagacinskeKarticeFormService } from './promet-magacinske-kartice-form.service';
import { PrometMagacinskeKarticeService } from '../service/promet-magacinske-kartice.service';
import { IPrometMagacinskeKartice } from '../promet-magacinske-kartice.model';

import { PrometMagacinskeKarticeUpdateComponent } from './promet-magacinske-kartice-update.component';

describe('PrometMagacinskeKartice Management Update Component', () => {
  let comp: PrometMagacinskeKarticeUpdateComponent;
  let fixture: ComponentFixture<PrometMagacinskeKarticeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let prometMagacinskeKarticeFormService: PrometMagacinskeKarticeFormService;
  let prometMagacinskeKarticeService: PrometMagacinskeKarticeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PrometMagacinskeKarticeUpdateComponent],
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
      .overrideTemplate(PrometMagacinskeKarticeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrometMagacinskeKarticeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    prometMagacinskeKarticeFormService = TestBed.inject(PrometMagacinskeKarticeFormService);
    prometMagacinskeKarticeService = TestBed.inject(PrometMagacinskeKarticeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const prometMagacinskeKartice: IPrometMagacinskeKartice = { id: 456 };

      activatedRoute.data = of({ prometMagacinskeKartice });
      comp.ngOnInit();

      expect(comp.prometMagacinskeKartice).toEqual(prometMagacinskeKartice);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrometMagacinskeKartice>>();
      const prometMagacinskeKartice = { id: 123 };
      jest.spyOn(prometMagacinskeKarticeFormService, 'getPrometMagacinskeKartice').mockReturnValue(prometMagacinskeKartice);
      jest.spyOn(prometMagacinskeKarticeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prometMagacinskeKartice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prometMagacinskeKartice }));
      saveSubject.complete();

      // THEN
      expect(prometMagacinskeKarticeFormService.getPrometMagacinskeKartice).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(prometMagacinskeKarticeService.update).toHaveBeenCalledWith(expect.objectContaining(prometMagacinskeKartice));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrometMagacinskeKartice>>();
      const prometMagacinskeKartice = { id: 123 };
      jest.spyOn(prometMagacinskeKarticeFormService, 'getPrometMagacinskeKartice').mockReturnValue({ id: null });
      jest.spyOn(prometMagacinskeKarticeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prometMagacinskeKartice: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prometMagacinskeKartice }));
      saveSubject.complete();

      // THEN
      expect(prometMagacinskeKarticeFormService.getPrometMagacinskeKartice).toHaveBeenCalled();
      expect(prometMagacinskeKarticeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrometMagacinskeKartice>>();
      const prometMagacinskeKartice = { id: 123 };
      jest.spyOn(prometMagacinskeKarticeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prometMagacinskeKartice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(prometMagacinskeKarticeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
