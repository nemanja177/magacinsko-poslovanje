import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PoslovniPartnerFormService } from './poslovni-partner-form.service';
import { PoslovniPartnerService } from '../service/poslovni-partner.service';
import { IPoslovniPartner } from '../poslovni-partner.model';

import { PoslovniPartnerUpdateComponent } from './poslovni-partner-update.component';

describe('PoslovniPartner Management Update Component', () => {
  let comp: PoslovniPartnerUpdateComponent;
  let fixture: ComponentFixture<PoslovniPartnerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let poslovniPartnerFormService: PoslovniPartnerFormService;
  let poslovniPartnerService: PoslovniPartnerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PoslovniPartnerUpdateComponent],
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
      .overrideTemplate(PoslovniPartnerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PoslovniPartnerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    poslovniPartnerFormService = TestBed.inject(PoslovniPartnerFormService);
    poslovniPartnerService = TestBed.inject(PoslovniPartnerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const poslovniPartner: IPoslovniPartner = { id: 456 };

      activatedRoute.data = of({ poslovniPartner });
      comp.ngOnInit();

      expect(comp.poslovniPartner).toEqual(poslovniPartner);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPoslovniPartner>>();
      const poslovniPartner = { id: 123 };
      jest.spyOn(poslovniPartnerFormService, 'getPoslovniPartner').mockReturnValue(poslovniPartner);
      jest.spyOn(poslovniPartnerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ poslovniPartner });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: poslovniPartner }));
      saveSubject.complete();

      // THEN
      expect(poslovniPartnerFormService.getPoslovniPartner).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(poslovniPartnerService.update).toHaveBeenCalledWith(expect.objectContaining(poslovniPartner));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPoslovniPartner>>();
      const poslovniPartner = { id: 123 };
      jest.spyOn(poslovniPartnerFormService, 'getPoslovniPartner').mockReturnValue({ id: null });
      jest.spyOn(poslovniPartnerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ poslovniPartner: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: poslovniPartner }));
      saveSubject.complete();

      // THEN
      expect(poslovniPartnerFormService.getPoslovniPartner).toHaveBeenCalled();
      expect(poslovniPartnerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPoslovniPartner>>();
      const poslovniPartner = { id: 123 };
      jest.spyOn(poslovniPartnerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ poslovniPartner });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(poslovniPartnerService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
