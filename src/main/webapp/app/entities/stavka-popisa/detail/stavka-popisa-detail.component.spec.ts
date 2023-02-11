import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StavkaPopisaDetailComponent } from './stavka-popisa-detail.component';

describe('StavkaPopisa Management Detail Component', () => {
  let comp: StavkaPopisaDetailComponent;
  let fixture: ComponentFixture<StavkaPopisaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StavkaPopisaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ stavkaPopisa: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StavkaPopisaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StavkaPopisaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load stavkaPopisa on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.stavkaPopisa).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
