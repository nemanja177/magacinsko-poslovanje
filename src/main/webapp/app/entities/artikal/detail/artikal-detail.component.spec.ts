import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArtikalDetailComponent } from './artikal-detail.component';

describe('Artikal Management Detail Component', () => {
  let comp: ArtikalDetailComponent;
  let fixture: ComponentFixture<ArtikalDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ArtikalDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ artikal: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ArtikalDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ArtikalDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load artikal on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.artikal).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
