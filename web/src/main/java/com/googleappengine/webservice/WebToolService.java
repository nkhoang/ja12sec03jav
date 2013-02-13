package com.googleappengine.webservice;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Service created mainly for web tools.
 */
@Service(value = "webToolService")
@Path("web")
public class WebToolService {
    private static StringBuilder sb = new StringBuilder();
    private static Logger LOG = LoggerFactory.getLogger(WebToolService.class.getCanonicalName());

    @POST
    @Path("sendlog")
    @Produces(MediaType.TEXT_HTML)
    @CrossOriginResourceSharing(allowAllOrigins = true)
    /**
     * Receive log message sent from the client.
     * @param log the log message sent as form param.
     */
    public String sendLog(@FormParam(value = "log") String log) {
        LOG.info(log);
        sb.append(log);
        return "ok";
    }

    @GET
    @Path("getlog")
    @Produces(MediaType.TEXT_HTML)
    @CrossOriginResourceSharing(allowAllOrigins = true)
    /**
     * Get the log message saved in a {@code StringBuilder}. Clear the {@code StringBuilder} after return.
     *
     */
    public String getLog() {
        String result = sb.toString();
        sb = new StringBuilder();

        return result;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("loadcities")
    @CrossOriginResourceSharing(allowAllOrigins = true)
    /**
     * Create a request to deal14.vn to get the data needed. This is a good example of using server to get
     * information from another site.
     *
     * {@link CrossOriginResourceSharing} is used to allow local javascript to send a request to a local
     * server.
     */
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
}
