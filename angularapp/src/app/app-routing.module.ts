import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AdminAddPropertyComponent } from './components/admin-add-property/admin-add-property.component';
import { AdminEditPropertyComponent } from './components/admin-edit-property/admin-edit-property.component';
import { AdminControlPanelComponent } from './components/admin-control-panel/admin-control-panel.component';
import { UserViewInquiryComponent } from './components/user-view-inquiry/user-view-inquiry.component';
import { UserViewPropertiesComponent } from './components/user-view-properties/user-view-properties.component';
import { HomePageComponent } from './components/home-page/home-page.component'

import { UserViewFeedbackComponent } from './components/user-view-feedback/user-view-feedback.component';
import { AdminViewFeedbackComponent } from './components/admin-view-feedback/admin-view-feedback.component';


import { AdminViewPropertyComponent } from './components/admin-view-property/admin-view-property.component';
import { AdminViewInquiryComponent } from './components/admin-view-inquiry/admin-view-inquiry.component';

import { UserAddInquiryComponent } from './components/user-add-inquiry/user-add-inquiry.component';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { UserAddFeedbackComponent } from './components/user-add-feedback/user-add-feedback.component';
import { AuthGuard } from './auth.guard';
import { ErrorComponent } from './components/error/error.component';

const routes: Routes = [
  {path: "", component: LoginComponent},
  {path: "view-feedback", component: UserViewFeedbackComponent, canActivate: [AuthGuard], data: {role : 'USER'}},
  {path: "add-property", component: AdminAddPropertyComponent, canActivate: [AuthGuard], data: {role : 'ADMIN'}},
  {path: "edit-property/:propertyId", component: AdminEditPropertyComponent, canActivate: [AuthGuard], data: {role : 'ADMIN'}},
  {path: "admin-control-panel", component : AdminControlPanelComponent, canActivate: [AuthGuard], data: {role : 'ADMIN'}},
  {path: 'propertyInquiries/:userId', component: UserViewInquiryComponent, canActivate: [AuthGuard], data: {role : 'USER'}},
  {path:'properties',component:UserViewPropertiesComponent, canActivate: [AuthGuard], data: {role : 'USER'}},
  {path:'admin-view-feedback',component:AdminViewFeedbackComponent, canActivate: [AuthGuard], data: {role : 'ADMIN'}},
  {path:'admin-view-inquiry',component:AdminViewInquiryComponent, canActivate: [AuthGuard], data: {role : 'ADMIN'}},
  {path:'admin-view-properties',component:AdminViewPropertyComponent, canActivate: [AuthGuard], data: {role : 'ADMIN'}},
  {path:'home',component:HomePageComponent},
  {path:'user-view-inquiry',component:UserViewInquiryComponent, canActivate: [AuthGuard], data: {role : 'USER'}},
  {path:'user-view-feedback',component:UserViewFeedbackComponent, canActivate: [AuthGuard], data: {role : 'USER'}},
  {path:'user-view-properties',component:UserViewPropertiesComponent, canActivate: [AuthGuard], data: {role : 'USER'}},
  {path:'myfeedbacks/:userId', component:UserViewFeedbackComponent, canActivate: [AuthGuard], data: {role : 'USER'}},
  {path:'feedbacks', component:AdminViewFeedbackComponent, canActivate: [AuthGuard], data: {role : 'ADMIN'}},
  {path:'view-properties',component:AdminViewPropertyComponent, canActivate: [AuthGuard], data: {role : 'ADMIN'}},
  {path:'view-inquiries', component:AdminViewInquiryComponent, canActivate: [AuthGuard], data: {role : 'ADMIN'}},
  {path: 'user-add-inquiry/:propertyId', component: UserAddInquiryComponent, canActivate: [AuthGuard], data: {role : 'USER'}},
  {path: 'register', component: SignupComponent},
  {path: 'login', component: LoginComponent},
  {path: "add-feedback", component: UserAddFeedbackComponent, canActivate: [AuthGuard], data: {role : 'USER'}},
  {path : 'error', component : ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
