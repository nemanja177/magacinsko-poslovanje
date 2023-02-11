import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PopisDetailComponent } from './popis-detail.component';

describe('Popis Management Detail Component', () => {
  let comp: PopisDetailComponent;
  let fixture: ComponentFixture<PopisDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PopisDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ popis: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PopisDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PopisDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load popis on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.popis).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
