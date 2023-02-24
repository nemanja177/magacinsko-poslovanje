import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PoslovniPartnerComponent } from './poslovni-partner.component';

describe('PoslovniPartnerComponent', () => {
  let component: PoslovniPartnerComponent;
  let fixture: ComponentFixture<PoslovniPartnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PoslovniPartnerComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PoslovniPartnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
