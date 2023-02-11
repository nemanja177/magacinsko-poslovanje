import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { JedinicaMereFormService } from './jedinica-mere-form.service';
import { JedinicaMereService } from '../service/jedinica-mere.service';
import { IJedinicaMere } from '../jedinica-mere.model';

import { JedinicaMereUpdateComponent } from './jedinica-mere-update.component';

describe('JedinicaMere Management Update Component', () => {
  let comp: JedinicaMereUpdateComponent;
  let fixture: ComponentFixture<JedinicaMereUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let jedinicaMereFormService: JedinicaMereFormService;
  let jedinicaMereService: JedinicaMereService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [JedinicaMereUpdateComponent],
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
      .overrideTemplate(JedinicaMereUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JedinicaMereUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    jedinicaMereFormService = TestBed.inject(JedinicaMereFormService);
    jedinicaMereService = TestBed.inject(JedinicaMereService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const jedinicaMere: IJedinicaMere = { id: 456 };

      activatedRoute.data = of({ jedinicaMere });
      comp.ngOnInit();

      expect(comp.jedinicaMere).toEqual(jedinicaMere);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJedinicaMere>>();
      const jedinicaMere = { id: 123 };
      jest.spyOn(jedinicaMereFormService, 'getJedinicaMere').mockReturnValue(jedinicaMere);
      jest.spyOn(jedinicaMereService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jedinicaMere });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jedinicaMere }));
      saveSubject.complete();

      // THEN
      expect(jedinicaMereFormService.getJedinicaMere).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(jedinicaMereService.update).toHaveBeenCalledWith(expect.objectContaining(jedinicaMere));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJedinicaMere>>();
      const jedinicaMere = { id: 123 };
      jest.spyOn(jedinicaMereFormService, 'getJedinicaMere').mockReturnValue({ id: null });
      jest.spyOn(jedinicaMereService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jedinicaMere: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jedinicaMere }));
      saveSubject.complete();

      // THEN
      expect(jedinicaMereFormService.getJedinicaMere).toHaveBeenCalled();
      expect(jedinicaMereService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJedinicaMere>>();
      const jedinicaMere = { id: 123 };
      jest.spyOn(jedinicaMereService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jedinicaMere });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(jedinicaMereService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
