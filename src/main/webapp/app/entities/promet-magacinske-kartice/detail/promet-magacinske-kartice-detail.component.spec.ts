import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrometMagacinskeKarticeDetailComponent } from './promet-magacinske-kartice-detail.component';

describe('PrometMagacinskeKartice Management Detail Component', () => {
  let comp: PrometMagacinskeKarticeDetailComponent;
  let fixture: ComponentFixture<PrometMagacinskeKarticeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrometMagacinskeKarticeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ prometMagacinskeKartice: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PrometMagacinskeKarticeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PrometMagacinskeKarticeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load prometMagacinskeKartice on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.prometMagacinskeKartice).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
