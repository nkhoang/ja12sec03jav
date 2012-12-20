package com.googleappengine.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    @Path("test")
    public String testWebService() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Running testWebService");
        }
        return "success";
    }
}

