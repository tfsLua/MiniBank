package com.squad21.pitang.FiltroGeral;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.squad21.pitang.User.Client.ClientController.ClientRepository.ClientRepository;
import com.squad21.pitang.User.Manager.ManagerRepository.MnRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroGeral extends OncePerRequestFilter {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MnRepository managerRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var servletPath = request.getServletPath();
        
        if (servletPath.equals("/login")) {
            var authorization = request.getHeader("Authorization");
            System.out.println(authorization);

            var authencoded = authorization.substring("Basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(authencoded);
            var authString = new String(authDecode);
            System.out.println("Authorization");
            System.out.println(authString);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];
            System.out.println(username);
            System.out.println(password);

            Long numeroConta;
            // Primeiro tenta converter o username para Long
            try {
                numeroConta = Long.parseLong(username);
                // Verifica se o usuário é um cliente
                var user = clientRepository.findByNumeroConta(numeroConta);
                if (user == null) {
                    response.setStatus(401);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Usuário não encontrado.\"}");
                    return;
                } else {
                    // Valida a senha do cliente
                    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getSenha());
                    if (passwordVerify.verified) {
                        request.setAttribute("idUser", user.getId());
                        request.setAttribute("saldo", user.getSaldo());
                        filterChain.doFilter(request, response);
                    } else {
                        response.setStatus(401);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\": \"Senha incorreta!\"}");
                    }
                }
            } catch (NumberFormatException exception) {
                // Caso o username não seja um número, tenta buscar o gerente
                var gerente = managerRepository.findBynome(username);
                if (gerente == null) {
                    response.setStatus(401);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Usuário não encontrado.\"}");
                    return;
                } else {
                    // Valida a senha do gerente
                    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), gerente.getSenha());
                    if (passwordVerify.verified) {
                        request.setAttribute("idUser", gerente.getId());
                        filterChain.doFilter(request, response);
                    } else {
                        response.setStatus(401);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\": \"Senha incorreta!\"}");
                    }
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
