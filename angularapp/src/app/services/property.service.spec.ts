import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { PropertyService } from './property.service';

describe('PropertyService', () => {
  let service: PropertyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(PropertyService);
  });

  fit('frontend_should_create_property_service', () => {
    expect((service as any)).toBeTruthy();
  });
});
