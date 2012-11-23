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
package org.iaxproxy.provisioning.classes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mgamble
 */
public class ProvisionedUsers {
    private List<UserInfo> users;

    /**
     * @return the users
     */
    public List<UserInfo> getUsers() {
        if (users == null) {
            users = new ArrayList<UserInfo>();
        }
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

}
