import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdminAddPropertyComponent } from './components/admin-add-property/admin-add-property.component';
import { AdminControlPanelComponent } from './components/admin-control-panel/admin-control-panel.component';

import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';

import { UserViewPropertiesComponent } from './components/user-view-properties/user-view-properties.component';
import { AdminViewPropertyComponent } from './components/admin-view-property/admin-view-property.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { ErrorComponent } from './components/error/error.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AdminEditPropertyComponent } from './components/admin-edit-property/admin-edit-property.component';

import { UserViewFeedbackComponent } from './components/user-view-feedback/user-view-feedback.component';

import { UserViewInquiryComponent } from './components/user-view-inquiry/user-view-inquiry.component';
import { AdminViewFeedbackComponent } from './components/admin-view-feedback/admin-view-feedback.component';

import { AdminViewInquiryComponent } from './components/admin-view-inquiry/admin-view-inquiry.component';

import { UserAddInquiryComponent } from './components/user-add-inquiry/user-add-inquiry.component';
import { UserAddFeedbackComponent } from './components/user-add-feedback/user-add-feedback.component';


import { UsernavComponent } from './components/usernav/usernav.component';
import { AdminnavComponent } from './components/adminnav/adminnav.component';
import { AuthInterceptor } from './services/authInterceptor.service';


@NgModule({
  declarations: [
    AppComponent,
    AdminAddPropertyComponent,
    AdminControlPanelComponent,
    UserViewPropertiesComponent,
    AdminViewPropertyComponent,
    HomePageComponent,
    ErrorComponent,
    LoginComponent,
    SignupComponent,
    AdminEditPropertyComponent,
    UserViewFeedbackComponent,
    UserViewInquiryComponent,
    UsernavComponent,
    AdminnavComponent,
    AdminViewFeedbackComponent,
    AdminViewInquiryComponent,
    UserAddInquiryComponent,
    UserAddFeedbackComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [{
    provide:HTTP_INTERCEPTORS,
    useClass:AuthInterceptor,
    multi:true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
