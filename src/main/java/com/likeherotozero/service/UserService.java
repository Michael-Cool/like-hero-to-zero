package com.likeherotozero.service;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import java.security.Principal;

@ApplicationScoped
public class UserService {

    public String getCurrentUsername() {
        FacesContext context = FacesContext.getCurrentInstance();
        Principal principal = context.getExternalContext().getUserPrincipal();
        return principal != null ? principal.getName() : "anonymous"; // Default to "anonymous" if not logged in
    }
}