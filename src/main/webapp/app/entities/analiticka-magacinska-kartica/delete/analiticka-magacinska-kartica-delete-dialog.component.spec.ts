jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AnalitickaMagacinskaKarticaService } from '../service/analiticka-magacinska-kartica.service';

import { AnalitickaMagacinskaKarticaDeleteDialogComponent } from './analiticka-magacinska-kartica-delete-dialog.component';

describe('AnalitickaMagacinskaKartica Management Delete Component', () => {
  let comp: AnalitickaMagacinskaKarticaDeleteDialogComponent;
  let fixture: ComponentFixture<AnalitickaMagacinskaKarticaDeleteDialogComponent>;
  let service: AnalitickaMagacinskaKarticaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AnalitickaMagacinskaKarticaDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(AnalitickaMagacinskaKarticaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnalitickaMagacinskaKarticaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnalitickaMagacinskaKarticaService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
