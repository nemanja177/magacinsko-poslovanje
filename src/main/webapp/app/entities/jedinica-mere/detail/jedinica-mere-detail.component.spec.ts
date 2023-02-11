import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JedinicaMereDetailComponent } from './jedinica-mere-detail.component';

describe('JedinicaMere Management Detail Component', () => {
  let comp: JedinicaMereDetailComponent;
  let fixture: ComponentFixture<JedinicaMereDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JedinicaMereDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ jedinicaMere: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(JedinicaMereDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(JedinicaMereDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load jedinicaMere on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.jedinicaMere).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
