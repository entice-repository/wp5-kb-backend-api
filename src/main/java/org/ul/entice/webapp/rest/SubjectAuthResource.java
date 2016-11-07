package org.ul.entice.webapp.rest;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.secnod.shiro.jaxrs.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/auth/")
@Produces(MediaType.TEXT_PLAIN)
@RequiresPermissions("protected:read")
public class SubjectAuthResource {

    @GET
    public String get(@Auth Subject subject) {
        if (!subject.isAuthenticated()) throw new UnauthenticatedException();

        return Double.toString(Math.random());
    }
}