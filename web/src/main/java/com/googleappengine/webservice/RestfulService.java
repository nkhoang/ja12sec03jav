package com.googleappengine.webservice;

import com.googleappengine.model.WordEntity;
import com.googleappengine.service.WordService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Service(value = "restfulService")
@Path("/")
public class RestfulService {
    private static Logger LOG = LoggerFactory.getLogger(WebToolService.class.getCanonicalName());

    @Autowired
    private WordService wordService;

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

        // test save word
        wordService.lookup(word);

        return "success";
    }

    /**
     *
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search")
    public Object searchWord(@QueryParam(value = "word") String word) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Searching for word: %s", word));
        }

        List<WordEntity> result = wordService.search(word);
        if (CollectionUtils.isNotEmpty(result)) {
            return result.get(0);
        }
        return "nothing found";
    }


}

