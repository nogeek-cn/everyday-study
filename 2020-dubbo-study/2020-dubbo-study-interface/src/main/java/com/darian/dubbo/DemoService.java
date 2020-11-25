package com.darian.dubbo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/")
public interface DemoService {

    @GET
    @Path("sayHello")
    String sayHello(@QueryParam(value = "name") String name);

}