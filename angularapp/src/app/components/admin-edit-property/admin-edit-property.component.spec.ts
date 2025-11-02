import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminEditPropertyComponent } from './admin-edit-property.component';

describe('AdminEditPropertyComponent', () => {
  let component: AdminEditPropertyComponent;
  let fixture: ComponentFixture<AdminEditPropertyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminEditPropertyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminEditPropertyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
