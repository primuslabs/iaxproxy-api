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

package org.iaxproxy.provisioning;

/**
 *
 * @author mgamble
 */
public class IAXUser {

    private String iaxUsername;
    private String iaxPassword;
    private String allowedCountries;
    private String sipUsername;
    private String sipPassword;
    private String sipAuthuser;
    private String sipDomain;
    private String sipProxy;
    private String uuid;

    /**
     * @return the iaxUsername
     */
    public String getIaxUsername() {
        return iaxUsername;
    }

    /**
     * @param iaxUsername the iaxUsername to set
     */
    public void setIaxUsername(String iaxUsername) {
        this.iaxUsername = iaxUsername;
    }

    /**
     * @return the iaxPassword
     */
    public String getIaxPassword() {
        return iaxPassword;
    }

    /**
     * @param iaxPassword the iaxPassword to set
     */
    public void setIaxPassword(String iaxPassword) {
        this.iaxPassword = iaxPassword;
    }

    /**
     * @return the allowedCountries
     */
    public String getAllowedCountries() {
        return allowedCountries;
    }

    /**
     * @param allowedCountries the allowedCountries to set
     */
    public void setAllowedCountries(String allowedCountries) {
        this.allowedCountries = allowedCountries;
    }

    /**
     * @return the sipUsername
     */
    public String getSipUsername() {
        return sipUsername;
    }

    /**
     * @param sipUsername the sipUsername to set
     */
    public void setSipUsername(String sipUsername) {
        this.sipUsername = sipUsername;
    }

    /**
     * @return the sipPassword
     */
    public String getSipPassword() {
        return sipPassword;
    }

    /**
     * @param sipPassword the sipPassword to set
     */
    public void setSipPassword(String sipPassword) {
        this.sipPassword = sipPassword;
    }

    /**
     * @return the sipAuthuser
     */
    public String getSipAuthuser() {
        return sipAuthuser;
    }

    /**
     * @param sipAuthuser the sipAuthuser to set
     */
    public void setSipAuthuser(String sipAuthuser) {
        this.sipAuthuser = sipAuthuser;
    }

    /**
     * @return the sipDomain
     */
    public String getSipDomain() {
        return sipDomain;
    }

    /**
     * @param sipDomain the sipDomain to set
     */
    public void setSipDomain(String sipDomain) {
        this.sipDomain = sipDomain;
    }

    /**
     * @return the sipProxy
     */
    public String getSipProxy() {
        return sipProxy;
    }

    /**
     * @param sipProxy the sipProxy to set
     */
    public void setSipProxy(String sipProxy) {
        this.sipProxy = sipProxy;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    
}
