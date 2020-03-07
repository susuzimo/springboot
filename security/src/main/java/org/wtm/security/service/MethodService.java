package org.wtm.security.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MethodService {

    @PreAuthorize("hasRole('admin')")
    public String admin(){
        return "admin";
    }

    @Secured("ROLE_user")
    public String user(){
        return "user";
    }
}
