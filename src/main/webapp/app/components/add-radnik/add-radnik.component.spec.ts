import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddRadnikComponent } from './add-radnik.component';

describe('AddRadnikComponent', () => {
  let component: AddRadnikComponent;
  let fixture: ComponentFixture<AddRadnikComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddRadnikComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AddRadnikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
