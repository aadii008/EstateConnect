import { Feedback } from './feedback.model';

describe('FeedbackModel', () => {

  fit('frontend_Feedback_model_should_create_an_instance', () => {
    const feedback: Feedback = {
      feedbackId: 1,
      feedbackText: 'Excellent service!',
      date: '2025-01-22T10:00:00.000Z',
      user: {
        userId: 123
      },
      property: {
        propertyId: 456
      },
      category: 'Service'
    };

    expect(feedback).toBeTruthy();
  });

});
