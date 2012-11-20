/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iaxproxy.provisioning;

import com.google.gson.Gson;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import redis.clients.jedis.Jedis;

/**
 *
 * @author mgamble
 */
public class UserResource extends ServerResource {

    String userName = "";
    @Override
    protected void doInit() throws ResourceException {
        // Get the "itemName" attribute value taken from the URI template  
        // /items/{itemName}.  
        //{version}/user/{userid}/{type}/{function}
        if (getRequest().getAttributes().containsKey("username")) {
            this.userName = getRequest().getAttributes().get("username").toString();

        } 

    }
 
    @Get("json")
    public String represent() {
        RestfulInterface.getLogInterface().debug("represent called with username: " + userName);
        Jedis jedis = RestfulInterface.getJedisPool().getResource();
        Gson gson = new Gson();
        if (this.userName.contentEquals("")) {
            getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            RestfulInterface.getJedisPool().returnResource(jedis);
            return "";
        }
        if (!jedis.exists("iaxuser:" + this.userName + ":iaxusername")) {
            /* Assume that if someone doesn't have an iaxusername they don't exist */
            getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            RestfulInterface.getJedisPool().returnResource(jedis);
            return "";
        }
        try {
            
            IAXUser user = new IAXUser();
            user.setIaxUsername(jedis.get("iaxuser:" + this.userName + ":iaxusername"));
            user.setIaxPassword(jedis.get("iaxuser:" + this.userName + ":iaxpassword"));
            user.setAllowedCountries(jedis.get("iaxuser:" + this.userName + ":allowedcountries"));
            user.setSipUsername(jedis.get("iaxuser:" + this.userName + ":sipusername"));
            user.setSipPassword(jedis.get("iaxuser:" + this.userName + ":sippassword"));
            user.setSipAuthuser(jedis.get("iaxuser:" + this.userName + ":sipauthuser"));
            user.setSipDomain(jedis.get("iaxuser:" + this.userName + ":sipdomain"));
            user.setSipProxy(jedis.get("iaxuser:" + this.userName + ":sipproxy"));
            user.setUuid(jedis.get("iaxuser:" + this.userName + ":uuid"));

            String json = gson.toJson(user);
            return json;
        } catch (Exception ex) {

            System.out.println("Exception: " + ex);
            return "System Error (P001): " + ex;
        }
    }

}