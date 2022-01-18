package ysx.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import ysx.tool.reader;

@Provider
@Priority(Priorities.USER + 100)
public class ContainerFilter implements ContainerRequestFilter, ContainerResponseFilter {

    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        // TODO Auto-generated method stub
        System.out.println("this is ContainerFilter request.response filter ");
    }

    public void filter(ContainerRequestContext requestContext) throws IOException {
        // TODO Auto-generated method stub
        System.out.println("this is ContainerFilter request filter ");
        String body = reader.getString(requestContext.getEntityStream());
        System.out.println("this is ContainerFilter request filter .body: " + body);

    }

}
