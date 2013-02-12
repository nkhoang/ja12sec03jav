package com.googleappengine.webservice;

import com.googleappengine.model.WordEntity;
import com.googleappengine.service.WordService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

@Service(value = "restfulService")
@Path("/")
public class RestfulService {
    private static Logger LOG = LoggerFactory.getLogger(RestfulService.class.getCanonicalName());
    private static StringBuilder sb = new StringBuilder();

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

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("loadcities")
    @CrossOriginResourceSharing(allowAllOrigins = true)
    public String loadCities(@QueryParam("queryStr") String queryStr, @QueryParam("url") String urlStr) {
        try {
            if (StringUtils.isBlank(urlStr)) {
                urlStr = "http://thoitrangdshop.vn/loadselect_ajax.aspx";
            }
            URL url = new URL(urlStr);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            connection.setRequestMethod("POST");

            //Send request
            if (StringUtils.isNotBlank(queryStr)) {
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(queryStr);
                wr.flush();
                wr.close();
            }
            // get inputStream
            InputStream is = connection.getInputStream();

            String s = new String(IOUtils.toByteArray(is), Charset.forName("UTF-8"));

            return s;
        } catch (Exception ex) {
            LOG.debug("Something wrong.", ex);
        }
        return null;
    }

    @POST
    @Path("sendlog")
    @Produces(MediaType.TEXT_HTML)
    @CrossOriginResourceSharing(allowAllOrigins = true)
    public String sendLog(@FormParam(value = "log") String log) {
        LOG.info(log);
        sb.append(log);
        return "ok";
    }

    @GET
    @Path("getlog")
    @Produces(MediaType.TEXT_HTML)
    @CrossOriginResourceSharing(allowAllOrigins = true)
    public String getLog() {
        String result = sb.toString();
        sb = new StringBuilder();

        return result;
    }
}

