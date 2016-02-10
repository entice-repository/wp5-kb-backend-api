package org.fri.entice.webapp.rest;

import org.fri.entice.webapp.entry.User;

import javax.ws.rs.core.Response;

public interface IUserService {
    String performUserLogin(String username, String password);

    String performUserLogout(String sessionID);

    Response registerUser(User user);
}
