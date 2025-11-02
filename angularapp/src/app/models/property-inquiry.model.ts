import { Property } from "./property.model";
import { User } from "./user.model";

export interface PropertyInquiry{
    inquiryId?:number;
    user:Partial<User>;
    property:Partial<Property>;
    message:string;
    status:string;
    inquiryDate?:string;
    responseDate?:string;
    adminResponse?:string;
    priority:string;
    contactDetails?:string;
}