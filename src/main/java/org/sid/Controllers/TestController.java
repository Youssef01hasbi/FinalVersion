package org.sid.Controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/stagiaire")
    @PreAuthorize("hasAuthority('Role_Stagiaire') or hasAuthority('Role_Encadrant') or hasAuthority('Role_Admin')")
    public String userAccess() {
        return "Stagiaiare Content.";
    }

    @GetMapping("/encadrant")
    @PreAuthorize("hasAuthority('Role_Encadrant')")
    public String moderatorAccess() {
        return "encadrant Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public String adminAccess() {return "Admin Board.";}
}