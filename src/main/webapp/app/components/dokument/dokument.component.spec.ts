import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DokumentComponent } from './dokument.component';

describe('DokumentComponent', () => {
  let component: DokumentComponent;
  let fixture: ComponentFixture<DokumentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DokumentComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DokumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
