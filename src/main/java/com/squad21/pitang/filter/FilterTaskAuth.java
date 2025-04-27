package com.squad21.pitang.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.squad21.pitang.User.Repository.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.Filter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                var serverletPath = request.getServletPath();
                if(serverletPath.equals("/tasks/")){
                // Pegar a autenticação(usuario e senha)
               var authorization = request.getHeader("Authorization");
               System.out.println(authorization);

               var authencoded = authorization.substring("Basic".length()).trim();
               byte[] authDecode = Base64.getDecoder().decode(authencoded);
               var authString = new String(authDecode);
               System.out.println("Authorization");
               System.out.println(authString);

               //[danieleleao]
               String[] credentials = authString.split(":");
               String nome = credentials[0];
               String password = credentials[1];
               System.out.println(nome);
               System.out.println(password);
                // Validar usuário
                
                var user = this.userRepository.findBynome(nome);
                if(user == null){
                    response.sendError(401);
                } else {
                // Validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getSenha());
                if(passwordVerify.verified){
                    filterChain.doFilter(request, response);
                } else{
                    response.sendError(401);
                } 
                // Segue a viagem

            }
    } else {
        filterChain.doFilter(request, response);
    }
}
    
}