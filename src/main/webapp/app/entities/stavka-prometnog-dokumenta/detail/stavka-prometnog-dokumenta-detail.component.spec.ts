import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StavkaPrometnogDokumentaDetailComponent } from './stavka-prometnog-dokumenta-detail.component';

describe('StavkaPrometnogDokumenta Management Detail Component', () => {
  let comp: StavkaPrometnogDokumentaDetailComponent;
  let fixture: ComponentFixture<StavkaPrometnogDokumentaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StavkaPrometnogDokumentaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ stavkaPrometnogDokumenta: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StavkaPrometnogDokumentaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StavkaPrometnogDokumentaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load stavkaPrometnogDokumenta on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.stavkaPrometnogDokumenta).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
