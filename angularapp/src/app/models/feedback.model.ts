export interface Feedback{
    feedbackId?:number;
    feedbackText:string;
    date:string;
    user:{
        userId:number
    };
    property?:{
        propertyId:number
    };
    category:string;
}