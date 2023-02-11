import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MagacinDetailComponent } from './magacin-detail.component';

describe('Magacin Management Detail Component', () => {
  let comp: MagacinDetailComponent;
  let fixture: ComponentFixture<MagacinDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MagacinDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ magacin: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MagacinDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MagacinDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load magacin on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.magacin).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
