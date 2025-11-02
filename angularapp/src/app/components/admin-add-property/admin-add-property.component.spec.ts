import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAddPropertyComponent } from './admin-add-property.component';

describe('AdminAddPropertyComponent', () => {
  let component: AdminAddPropertyComponent;
  let fixture: ComponentFixture<AdminAddPropertyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminAddPropertyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminAddPropertyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
