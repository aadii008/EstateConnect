import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserViewPropertiesComponent } from './user-view-properties.component';

describe('UserViewPropertiesComponent', () => {
  let component: UserViewPropertiesComponent;
  let fixture: ComponentFixture<UserViewPropertiesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserViewPropertiesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserViewPropertiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
