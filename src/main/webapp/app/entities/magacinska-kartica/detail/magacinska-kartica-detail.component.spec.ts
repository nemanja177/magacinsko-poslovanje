import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MagacinskaKarticaDetailComponent } from './magacinska-kartica-detail.component';

describe('MagacinskaKartica Management Detail Component', () => {
  let comp: MagacinskaKarticaDetailComponent;
  let fixture: ComponentFixture<MagacinskaKarticaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MagacinskaKarticaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ magacinskaKartica: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MagacinskaKarticaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MagacinskaKarticaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load magacinskaKartica on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.magacinskaKartica).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
