package com.mentory.mentory_proyect.Security;

import com.mentory.mentory_proyect.model.AprendizModel;
import com.mentory.mentory_proyect.model.MentorModel;
import com.mentory.mentory_proyect.model.UsuarioBaseModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final String role;
    private final Long id;
    private final String nombre;
    private final Boolean perfilCompletado;

    // 🔹 Constructor para Aprendiz
    public CustomUserDetails(AprendizModel user) {
        this.email = user.getEmailUsuario();
        this.password = user.getContraseñaUsuario();
        this.role = "ROLE_APRENDIZ";
        this.id = user.getId();
        this.nombre = user.getNombreUsuario();
        this.perfilCompletado = true; // Ya tiene perfil completo
    }

    // 🔹 Constructor para Mentor
    public CustomUserDetails(MentorModel user) {
        this.email = user.getEmailUsuario();
        this.password = user.getContraseñaUsuario();
        this.role = "ROLE_MENTOR";
        this.id = user.getId();
        this.nombre = user.getNombreUsuario();
        this.perfilCompletado = true; // Ya tiene perfil completo
    }

    // 🔹 Constructor para UsuarioBase (sin perfil completo)
    public CustomUserDetails(UsuarioBaseModel user) {
        this.email = user.getEmailUsuario();
        this.password = user.getContraseñaUsuario();
        this.role = "ROLE_" + user.getRol(); // ROLE_PENDIENTE, ROLE_MENTOR, ROLE_APRENDIZ
        this.id = user.getId();
        this.nombre = user.getNombreUsuario();
        this.perfilCompletado = user.getPerfilCompletado();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    public String getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Boolean getPerfilCompletado() {
        return perfilCompletado;
    }
}