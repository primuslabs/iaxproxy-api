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
package org.iaxproxy.provisioning.utils;
/**
 *
 * @author mgamble
 */
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.server.UID;
import java.security.*;
import java.math.*;


public class GUID implements Serializable {

    static final long serialVersionUID = -1236479052041904164L;
    /**
     * holds the hostname of the local machine.
     */
    private static String localIPAddress;
    /**
     * String representation of the GUID
     */
    private String guid;

    /**
     * compute the local IP-Address
     */
    static {
        try {
            localIPAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            localIPAddress = "localhost";
        }
    }

    /**
     * protected no args constructor.
     */
    public GUID() {
        UID uid = new UID();
        StringBuffer buf = new StringBuffer();
        /* Make it a bit more random - add milliseconds */
        /* MG */
        long l = System.currentTimeMillis();
        buf.append(uid.toString());
        buf.append("-");
        buf.append(l);
        buf.append("-");
        buf.append(localIPAddress);
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.reset();
            byte[] GUIDBytes = buf.toString().getBytes();
            digest.update(GUIDBytes);

            BigInteger intHash = new BigInteger(1, digest.digest());
            //  hashword = intHash.toString(16);
            guid = pad(intHash.toString(16), 32, '0');


        } catch (Exception e) {
            guid = null;
        }


    }

    private String pad(String s, int length, char pad) {
        StringBuffer buffer = new StringBuffer(s);
        while (buffer.length() < length) {
            buffer.insert(0, pad);
        }
        return buffer.toString();
    }

    /**
     * protected constructor. The caller is responsible to feed a globally
     * unique String into the theGuidString parameter
     *
     * @param theGuidString a globally unique String
     */
    protected GUID(String theGuidString) {
        guid = theGuidString;
    }

    /**
     * returns the String representation of the GUID
     */
    public String toString() {
        return guid;
    }

    public String md55GUID() {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(guid.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object obj) {
        if (obj instanceof GUID) {
            if (guid.equals(((GUID) obj).guid)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return guid.hashCode();
    }
}