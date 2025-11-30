package com.Web.Plamilhas.Entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data @Builder @NoArgsConstructor @AllArgsConstructor 
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioEntity implements UserDetails{
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;
@Column(nullable = false, length = 200)
private String nomeCompleto;

@Column(nullable = false, unique = true, length = 255)
private String email;

@Column(nullable = false)
private String senhaHash;

private boolean ativo = true;
@Column(name = "criado_em")
private OffsetDateTime criadoEm;

@Column(name = "atualizado_em")
private OffsetDateTime atualizadoEm;

@Column(name = "ultimo_login")
private OffsetDateTime ultimoLogin;

@PrePersist
public void prePersist() {
    if (this.criadoEm == null)
        this.criadoEm = OffsetDateTime.now();
    if (this.atualizadoEm == null)
        this.atualizadoEm = OffsetDateTime.now();
}



//@Column(columnDefinition = "jsonb")
private String perfil;

//Relacionamentos

@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
private List<CartaoUsuarioEntity> cartoes = new ArrayList<>();


@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
private List<CompraEntity> compras = new ArrayList<>();

@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
private List<SaldoPontosEntity> saldos = new ArrayList<>();

@Override
public Collection<? extends GrantedAuthority> getAuthorities(){
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
}

    @Override 
    public String getPassword() { return this.senhaHash;}

    @Override
    public String getUsername() { return this.email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return this.ativo; }



} 


