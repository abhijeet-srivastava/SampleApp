package com.cvent.dw.dwstart.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * HelloResource Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 23, 2016</pre>
 */
public class HelloResourceTest {

    @Rule
    public ResourceTestRule resource = ResourceTestRule.builder()
            .addResource(new HelloResource("dsdffd", 4 )).build();

    /**
     * Method: getGreeting()
     */
    @Test
    public void testGetGreeting() throws Exception {
        String expected = "Hello Suresh Sinha";
        //Obtain client from @Rule.
        Client client = resource.client();
        //Get WebTarget from client using URI of root resource.
        WebTarget helloTarget = client.target("http://localhost:8080/hello/hello");
        //To invoke response we use Invocation.Builder
        //and specify the media type of representation asked from resource.
        Invocation.Builder builder = helloTarget.request(MediaType.APPLICATION_JSON);
        //Obtain response.
        Response response = builder.get();

        //Do assertions.
        assertEquals(Response.Status.OK, response.getStatusInfo());
        String actual = response.readEntity(String.class);
        //assertEquals(expected, actual);
    }


} 
