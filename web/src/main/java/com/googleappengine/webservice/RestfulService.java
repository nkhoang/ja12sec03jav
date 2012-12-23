package com.googleappengine.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service(value = "restfulService")
@Path("/")
public class RestfulService {
    private static Logger LOG = LoggerFactory.getLogger(RestfulService.class.getCanonicalName());

    /**
     * A simple test.
     *
     * @return a simple string.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("lookup")
    public String lookupWord(@PathParam(value = "word") String word) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Lookup word: %s", word));
        }


        return "success";
    }
}

