package ysx.Interceptor;

import java.lang.annotation.Annotation;

import javax.interceptor.Interceptor;

public class myInterceptor implements Interceptor {

    public Class<? extends Annotation> annotationType() {
        // TODO Auto-generated method stub
        return null;
    }

  

//    @POST
//    @Path("bao-customer/v1.2.0/addressPhoneChange/get")
//    @Consumes({ MediaType.APPLICATION_JSON })
//    @Produces({ MediaType.APPLICATION_JSON })
//    public void post(Request request) {
//        System.out.println("this is myServer.post");
//    }

}
