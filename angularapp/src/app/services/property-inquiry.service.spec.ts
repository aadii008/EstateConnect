import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import {PropertyInquiryService} from './property-inquiry.service';

describe('PropertyInquiryService', () => {
  let service: PropertyInquiryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(PropertyInquiryService);
  });

  fit('frontend_should_create_property_inquiry_service', () => {
    expect((service as any)).toBeTruthy();
  });
});
