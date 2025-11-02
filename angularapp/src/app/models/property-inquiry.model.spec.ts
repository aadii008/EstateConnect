import { PropertyInquiry } from './property-inquiry.model';

describe('PropertyInquiryModel', () => {

  fit('frontend_Property_Inquiry_model_should_create_an_instance', () => {
    const propertyInquiry: PropertyInquiry = {
      inquiryId: 1,
      user: {
        userId: 123
      },
      property: {
        propertyId: 456
      },
      message: 'Can I schedule a viewing for this property?',
      status: 'Pending',
      inquiryDate: '2025-01-22T10:00:00.000Z',
      responseDate: '2025-01-23T10:00:00.000Z',
      adminResponse: 'Sure, we will arrange a viewing for next week.',
      priority: 'High',
      contactDetails: 'john.doe@example.com'
    };

    expect(propertyInquiry).toBeTruthy();
  });

});
