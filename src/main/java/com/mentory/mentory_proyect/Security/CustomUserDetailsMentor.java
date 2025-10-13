package com.mentory.mentory_proyect.Security;

import com.mentory.mentory_proyect.model.MentorModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetailsMentor implements UserDetails {

    private final MentorModel user;

    public CustomUserDetailsMentor(MentorModel user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getContraseñaUsuario();
    }

    @Override
    public String getUsername() {
        return user.getEmailUsuario();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombreUsuario() {
        return user.getNombreUsuario();
    }

    public String getEspecialidad() {
        return user.getEspecialidad();
    }
}