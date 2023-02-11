import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MagacinFormService } from './magacin-form.service';
import { MagacinService } from '../service/magacin.service';
import { IMagacin } from '../magacin.model';

import { MagacinUpdateComponent } from './magacin-update.component';

describe('Magacin Management Update Component', () => {
  let comp: MagacinUpdateComponent;
  let fixture: ComponentFixture<MagacinUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let magacinFormService: MagacinFormService;
  let magacinService: MagacinService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MagacinUpdateComponent],
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
      .overrideTemplate(MagacinUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MagacinUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    magacinFormService = TestBed.inject(MagacinFormService);
    magacinService = TestBed.inject(MagacinService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const magacin: IMagacin = { id: 456 };

      activatedRoute.data = of({ magacin });
      comp.ngOnInit();

      expect(comp.magacin).toEqual(magacin);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMagacin>>();
      const magacin = { id: 123 };
      jest.spyOn(magacinFormService, 'getMagacin').mockReturnValue(magacin);
      jest.spyOn(magacinService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ magacin });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: magacin }));
      saveSubject.complete();

      // THEN
      expect(magacinFormService.getMagacin).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(magacinService.update).toHaveBeenCalledWith(expect.objectContaining(magacin));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMagacin>>();
      const magacin = { id: 123 };
      jest.spyOn(magacinFormService, 'getMagacin').mockReturnValue({ id: null });
      jest.spyOn(magacinService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ magacin: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: magacin }));
      saveSubject.complete();

      // THEN
      expect(magacinFormService.getMagacin).toHaveBeenCalled();
      expect(magacinService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMagacin>>();
      const magacin = { id: 123 };
      jest.spyOn(magacinService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ magacin });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(magacinService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
