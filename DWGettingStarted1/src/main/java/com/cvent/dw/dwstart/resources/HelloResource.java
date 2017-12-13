package com.cvent.dw.dwstart.resources;

import com.cvent.dw.dwstart.DWGettingStartedConfiguration;
import com.cvent.dw.dwstart.model.Greeting;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.MediaType;

/**
 * Created by a.srivastava on 4/23/16.
 */

@Path("/hello")
public class HelloResource {
    private String message;
    private int count;




    public HelloResource(String message, int messageRepetitions) {
        this.count = messageRepetitions;
        this.message = message;
    }

    @GET
    @Path("/path_param/{title}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getGreeting(@DefaultValue("Srivastava")  @PathParam(value = "title") String title, @DefaultValue("Abhijeet")  @QueryParam("name") String  name) {
        return "Hello " + name + " " + title;
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Greeting getDefaultGreeting(@DefaultValue("Srivastava")  @PathParam(value = "title") String title, @DefaultValue("Abhijeet")  @QueryParam("name") String  name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count ; i++) {
            sb.append(message);
            sb.append('\n');
        }
        sb.append(name);
        sb.append('\t');
        sb.append(title);
        return new Greeting(sb.toString());
    }
}
