import { Property } from './property.model';

describe('PropertyModel', () => {

  fit('frontend_Property_model_should_create_an_instance', () => {
    const property: Property = {
      propertyId: 1,
      title: 'Cozy Apartment',
      description: 'A lovely two-bedroom apartment in the heart of the city.',
      location: '123 Main St, Cityville',
      price: 250000,
      type: 'Residential',
      status: 'Available'
    };

    expect(property).toBeTruthy();
  });

});
