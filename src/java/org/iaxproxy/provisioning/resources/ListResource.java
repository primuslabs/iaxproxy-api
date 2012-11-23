/*
    IAXProxy Reference API Implementation
    Copyright (C) 2012 iaxproxy.org 

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.iaxproxy.provisioning.resources;

import com.google.gson.Gson;
import java.util.Iterator;
import java.util.Set;
import org.iaxproxy.provisioning.RestfulInterface;
import org.iaxproxy.provisioning.classes.ProvisionedUsers;
import org.iaxproxy.provisioning.classes.UserInfo;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import redis.clients.jedis.Jedis;

/**
 *
 * @author mgamble
 */
public class ListResource extends ServerResource {

    String userName = "";

    @Override
    protected void doInit() throws ResourceException {
    }

    @Get("json")
    public String representGet() {
        Jedis jedis = RestfulInterface.getJedisPool().getResource();
        Gson gson = new Gson();
        try {
            ProvisionedUsers result = new ProvisionedUsers();
            Set<String> users = jedis.keys("iaxuser:*:iaxusername");
            Iterator<String> it = users.iterator();
            while (it.hasNext()) {
                UserInfo userInfo = new UserInfo();
                String rawString[] = it.next().split(":");
                userInfo.setIaxUsername(rawString[1]);
                result.getUsers().add(userInfo);
            }
            String json = gson.toJson(result);
            return json;
        } catch (Exception ex) {

            System.out.println("Exception: " + ex);
            return "System Error (P001): " + ex;
        } finally {
            RestfulInterface.getJedisPool().returnResource(jedis);
        }
    }

}