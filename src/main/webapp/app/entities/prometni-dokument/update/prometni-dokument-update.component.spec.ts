import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PrometniDokumentFormService } from './prometni-dokument-form.service';
import { PrometniDokumentService } from '../service/prometni-dokument.service';
import { IPrometniDokument } from '../prometni-dokument.model';

import { PrometniDokumentUpdateComponent } from './prometni-dokument-update.component';

describe('PrometniDokument Management Update Component', () => {
  let comp: PrometniDokumentUpdateComponent;
  let fixture: ComponentFixture<PrometniDokumentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let prometniDokumentFormService: PrometniDokumentFormService;
  let prometniDokumentService: PrometniDokumentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PrometniDokumentUpdateComponent],
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
      .overrideTemplate(PrometniDokumentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrometniDokumentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    prometniDokumentFormService = TestBed.inject(PrometniDokumentFormService);
    prometniDokumentService = TestBed.inject(PrometniDokumentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const prometniDokument: IPrometniDokument = { id: 456 };

      activatedRoute.data = of({ prometniDokument });
      comp.ngOnInit();

      expect(comp.prometniDokument).toEqual(prometniDokument);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrometniDokument>>();
      const prometniDokument = { id: 123 };
      jest.spyOn(prometniDokumentFormService, 'getPrometniDokument').mockReturnValue(prometniDokument);
      jest.spyOn(prometniDokumentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prometniDokument });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prometniDokument }));
      saveSubject.complete();

      // THEN
      expect(prometniDokumentFormService.getPrometniDokument).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(prometniDokumentService.update).toHaveBeenCalledWith(expect.objectContaining(prometniDokument));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrometniDokument>>();
      const prometniDokument = { id: 123 };
      jest.spyOn(prometniDokumentFormService, 'getPrometniDokument').mockReturnValue({ id: null });
      jest.spyOn(prometniDokumentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prometniDokument: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prometniDokument }));
      saveSubject.complete();

      // THEN
      expect(prometniDokumentFormService.getPrometniDokument).toHaveBeenCalled();
      expect(prometniDokumentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrometniDokument>>();
      const prometniDokument = { id: 123 };
      jest.spyOn(prometniDokumentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prometniDokument });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(prometniDokumentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
