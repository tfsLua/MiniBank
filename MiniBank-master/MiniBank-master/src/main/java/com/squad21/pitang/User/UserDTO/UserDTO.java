package com.squad21.pitang.User.UserDTO;
import jakarta.validation.constraints.*;

public record UserDTO(

        @NotBlank(message = "O nome completo não pode estar em branco")
        @Size(min = 3, max = 100, message = "O nome completo deve ter entre 3 e 100 caracteres")
        String nome,

        @NotBlank(message = "O CPF não pode estar em branco")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos")
        // ADICIONAR VERIFICAÇÃO REAL DE CPF
        String cpf,

        @NotBlank(message = "O e-mail não pode estar em branco")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotBlank(message = "A senha não pode estar em branco")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotBlank(message = "O endereço não pode estar em branco")
        String endereco
) {
}