package com.pc.dto.Usuario;

import java.util.Objects;

public class UsuarioLocatarioDto {

    private Long id;

    private String email;

    private String nome;

    public UsuarioLocatarioDto(Long id) {}

    public UsuarioLocatarioDto(Long id, String email, String nome) {
        this.id = id;
        this.email = email;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioLocatarioDto that = (UsuarioLocatarioDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
