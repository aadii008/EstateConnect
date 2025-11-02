import { Login } from './login.model';

describe('LoginModel', () => {

  const loginPassword = import.meta.env.NG_APP_LOGIN_PASSWORD;

  fit('frontend_Login_model_should_create_an_instance', () => {
    const login: Login = {
      email: 'abc',
      password: loginPassword
    };
    expect(login).toBeTruthy();
    expect(login.email).toBeDefined();
    expect(login.password).toBeDefined();
  });

});
