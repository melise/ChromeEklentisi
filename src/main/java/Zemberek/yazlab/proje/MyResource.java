package Zemberek.yazlab.proje;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
	

	@Path("api")
	public class MyResource {

	    @GET
	    @Path("/get/{text}")
	    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	    public static Response test(@PathParam("text") String text) {
	        String output = ZemberekIslemler.Process(text);
	        return Response.ok(output).header("Access-Control-Allow-Origin", "*").build();
	    }

	    @POST
	    @Path("/post")
	    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	    public static Response testpost(String data) {
	        String output = ZemberekIslemler.Process(data);
	        return Response.ok(output).header("Access-Control-Allow-Origin", "*").build();
	    }
}
