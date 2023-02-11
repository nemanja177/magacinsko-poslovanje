import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PreduzeceDetailComponent } from './preduzece-detail.component';

describe('Preduzece Management Detail Component', () => {
  let comp: PreduzeceDetailComponent;
  let fixture: ComponentFixture<PreduzeceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PreduzeceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ preduzece: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PreduzeceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PreduzeceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load preduzece on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.preduzece).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
