package org.controllers;


import org.models.users.User;
import org.services.UserService;

/**
 * Questa classe ha la responsabilità di gestire le chiamate e le risposte
 * da e verso una interfaccia utente tramite Api e protocollo http.
 */
public class UserController extends Controller<User> {

    public  UserController() {
        super(new UserService());
    }
}
