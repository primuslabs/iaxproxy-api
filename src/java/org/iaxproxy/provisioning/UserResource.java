/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iaxproxy.provisioning;

import com.google.gson.Gson;
import org.iaxproxy.provisioning.classes.ResultMessage;
import org.iaxproxy.provisioning.utils.GUID;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
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
    public String representGet() {
        RestfulInterface.getLogInterface().debug("representGet called with username: " + userName);
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
        } finally {
            RestfulInterface.getJedisPool().returnResource(jedis);
        }
    }

    @Put("json")
    public String representPut(Representation entity) {
        RestfulInterface.getLogInterface().debug("representGet called with username: " + userName);
        Jedis jedis = RestfulInterface.getJedisPool().getResource();
        ResultMessage result = new ResultMessage();
        Gson gson = new Gson();
        try {
            IAXUser user = gson.fromJson(entity.getText(), IAXUser.class);
            user.setIaxUsername(userName);
            jedis.set("iaxuser:" + this.userName + ":iaxusername", user.getIaxUsername());
            jedis.set("iaxuser:" + this.userName + ":iaxpassword", user.getIaxPassword());
            jedis.set("iaxuser:" + this.userName + ":allowedcountries", user.getAllowedCountries());
            jedis.set("iaxuser:" + this.userName + ":sipusername", user.getSipUsername());
            jedis.set("iaxuser:" + this.userName + ":sippassword", user.getSipPassword());
            jedis.set("iaxuser:" + this.userName + ":sipauthuser", user.getSipAuthuser());
            jedis.set("iaxuser:" + this.userName + ":sipdomain", user.getSipDomain());
            jedis.set("iaxuser:" + this.userName + ":sipproxy", user.getSipProxy());
            if (user.getUuid().isEmpty()) {
                /* Provisioning system didn't provide the UUID - adding one */
                jedis.set("iaxuser:" + this.userName + ":uuid", new GUID().md55GUID());
            } else {
                jedis.set("iaxuser:" + this.userName + ":uuid", user.getUuid());
            }
            result.setSuccess("true");
            result.setErrorMessage("");
            return gson.toJson(result);
        } catch (Exception ex) {

            result.setSuccess("false");
            result.setErrorMessage("P003: Could not create user: " + ex);
            return gson.toJson(result);

        } finally {
            RestfulInterface.getJedisPool().returnResource(jedis);
        }
    }

    @Delete("json")
    public String representDelete() {

        RestfulInterface.getLogInterface().debug("representGet called with username: " + userName);
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
            jedis.del("iaxuser:" + this.userName + ":iaxusername");
            jedis.del("iaxuser:" + this.userName + ":iaxpassword");
            jedis.del("iaxuser:" + this.userName + ":allowedcountries");
            jedis.del("iaxuser:" + this.userName + ":sipusername");
            jedis.del("iaxuser:" + this.userName + ":sippassword");
            jedis.del("iaxuser:" + this.userName + ":sipauthuser");
            jedis.del("iaxuser:" + this.userName + ":sipdomain");
            jedis.del("iaxuser:" + this.userName + ":sipproxy");
            jedis.del("iaxuser:" + this.userName + ":uuid");
            ResultMessage result = new ResultMessage();
            result.setSuccess("true");
            result.setErrorMessage("");
            return gson.toJson(result);
        } catch (Exception ex) {

            System.out.println("Exception: " + ex);
            return "System Error (P002): " + ex;
        } finally {
            RestfulInterface.getJedisPool().returnResource(jedis);
        }

    }
}