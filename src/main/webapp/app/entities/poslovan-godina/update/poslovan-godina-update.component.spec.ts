import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PoslovanGodinaFormService } from './poslovan-godina-form.service';
import { PoslovanGodinaService } from '../service/poslovan-godina.service';
import { IPoslovanGodina } from '../poslovan-godina.model';

import { PoslovanGodinaUpdateComponent } from './poslovan-godina-update.component';

describe('PoslovanGodina Management Update Component', () => {
  let comp: PoslovanGodinaUpdateComponent;
  let fixture: ComponentFixture<PoslovanGodinaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let poslovanGodinaFormService: PoslovanGodinaFormService;
  let poslovanGodinaService: PoslovanGodinaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PoslovanGodinaUpdateComponent],
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
      .overrideTemplate(PoslovanGodinaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PoslovanGodinaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    poslovanGodinaFormService = TestBed.inject(PoslovanGodinaFormService);
    poslovanGodinaService = TestBed.inject(PoslovanGodinaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const poslovanGodina: IPoslovanGodina = { id: 456 };

      activatedRoute.data = of({ poslovanGodina });
      comp.ngOnInit();

      expect(comp.poslovanGodina).toEqual(poslovanGodina);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPoslovanGodina>>();
      const poslovanGodina = { id: 123 };
      jest.spyOn(poslovanGodinaFormService, 'getPoslovanGodina').mockReturnValue(poslovanGodina);
      jest.spyOn(poslovanGodinaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ poslovanGodina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: poslovanGodina }));
      saveSubject.complete();

      // THEN
      expect(poslovanGodinaFormService.getPoslovanGodina).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(poslovanGodinaService.update).toHaveBeenCalledWith(expect.objectContaining(poslovanGodina));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPoslovanGodina>>();
      const poslovanGodina = { id: 123 };
      jest.spyOn(poslovanGodinaFormService, 'getPoslovanGodina').mockReturnValue({ id: null });
      jest.spyOn(poslovanGodinaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ poslovanGodina: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: poslovanGodina }));
      saveSubject.complete();

      // THEN
      expect(poslovanGodinaFormService.getPoslovanGodina).toHaveBeenCalled();
      expect(poslovanGodinaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPoslovanGodina>>();
      const poslovanGodina = { id: 123 };
      jest.spyOn(poslovanGodinaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ poslovanGodina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(poslovanGodinaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
