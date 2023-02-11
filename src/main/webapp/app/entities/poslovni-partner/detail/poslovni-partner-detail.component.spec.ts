import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PoslovniPartnerDetailComponent } from './poslovni-partner-detail.component';

describe('PoslovniPartner Management Detail Component', () => {
  let comp: PoslovniPartnerDetailComponent;
  let fixture: ComponentFixture<PoslovniPartnerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PoslovniPartnerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ poslovniPartner: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PoslovniPartnerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PoslovniPartnerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load poslovniPartner on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.poslovniPartner).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
