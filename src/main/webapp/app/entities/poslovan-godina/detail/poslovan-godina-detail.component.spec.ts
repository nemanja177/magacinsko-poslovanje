import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PoslovanGodinaDetailComponent } from './poslovan-godina-detail.component';

describe('PoslovanGodina Management Detail Component', () => {
  let comp: PoslovanGodinaDetailComponent;
  let fixture: ComponentFixture<PoslovanGodinaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PoslovanGodinaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ poslovanGodina: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PoslovanGodinaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PoslovanGodinaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load poslovanGodina on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.poslovanGodina).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
