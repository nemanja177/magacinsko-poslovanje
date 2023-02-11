import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AnalitickaMagacinskaKarticaDetailComponent } from './analiticka-magacinska-kartica-detail.component';

describe('AnalitickaMagacinskaKartica Management Detail Component', () => {
  let comp: AnalitickaMagacinskaKarticaDetailComponent;
  let fixture: ComponentFixture<AnalitickaMagacinskaKarticaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AnalitickaMagacinskaKarticaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ analitickaMagacinskaKartica: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AnalitickaMagacinskaKarticaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnalitickaMagacinskaKarticaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load analitickaMagacinskaKartica on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.analitickaMagacinskaKartica).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
