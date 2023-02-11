import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrometniDokumentDetailComponent } from './prometni-dokument-detail.component';

describe('PrometniDokument Management Detail Component', () => {
  let comp: PrometniDokumentDetailComponent;
  let fixture: ComponentFixture<PrometniDokumentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrometniDokumentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ prometniDokument: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PrometniDokumentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PrometniDokumentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load prometniDokument on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.prometniDokument).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
