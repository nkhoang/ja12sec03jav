package com.googleappengine.webservice;

import com.googleappengine.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
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
    public String lookupWord(@QueryParam(value = "word") String word) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Lookup word: %s", word));
        }

        return "success";
    }
}

