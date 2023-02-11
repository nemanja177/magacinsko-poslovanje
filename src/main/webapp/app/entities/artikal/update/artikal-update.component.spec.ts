import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ArtikalFormService } from './artikal-form.service';
import { ArtikalService } from '../service/artikal.service';
import { IArtikal } from '../artikal.model';

import { ArtikalUpdateComponent } from './artikal-update.component';

describe('Artikal Management Update Component', () => {
  let comp: ArtikalUpdateComponent;
  let fixture: ComponentFixture<ArtikalUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let artikalFormService: ArtikalFormService;
  let artikalService: ArtikalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ArtikalUpdateComponent],
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
      .overrideTemplate(ArtikalUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ArtikalUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    artikalFormService = TestBed.inject(ArtikalFormService);
    artikalService = TestBed.inject(ArtikalService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const artikal: IArtikal = { id: 456 };

      activatedRoute.data = of({ artikal });
      comp.ngOnInit();

      expect(comp.artikal).toEqual(artikal);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArtikal>>();
      const artikal = { id: 123 };
      jest.spyOn(artikalFormService, 'getArtikal').mockReturnValue(artikal);
      jest.spyOn(artikalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ artikal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: artikal }));
      saveSubject.complete();

      // THEN
      expect(artikalFormService.getArtikal).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(artikalService.update).toHaveBeenCalledWith(expect.objectContaining(artikal));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArtikal>>();
      const artikal = { id: 123 };
      jest.spyOn(artikalFormService, 'getArtikal').mockReturnValue({ id: null });
      jest.spyOn(artikalService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ artikal: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: artikal }));
      saveSubject.complete();

      // THEN
      expect(artikalFormService.getArtikal).toHaveBeenCalled();
      expect(artikalService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArtikal>>();
      const artikal = { id: 123 };
      jest.spyOn(artikalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ artikal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(artikalService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
