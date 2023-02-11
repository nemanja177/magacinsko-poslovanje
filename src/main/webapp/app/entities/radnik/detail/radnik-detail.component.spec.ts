import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RadnikDetailComponent } from './radnik-detail.component';

describe('Radnik Management Detail Component', () => {
  let comp: RadnikDetailComponent;
  let fixture: ComponentFixture<RadnikDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RadnikDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ radnik: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RadnikDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RadnikDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load radnik on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.radnik).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
