package com.squad21.pitang.filterManager;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.squad21.pitang.User.Manager.ManagerRepository.MnRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class FilterManagerAuth extends OncePerRequestFilter {
    /*
     * Isso garante que o filtro será executado uma única vez por requisição, mesmo se houver redirecionamentos internos.

     */
    @Autowired
    private MnRepository loginMnRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    /*
    - Recebe:
    - request: a requisição HTTP
    - response: a resposta HTTP
    - filterChain: cadeia de filtros, usada para continuar o processamento
    */ 
            throws ServletException, IOException {
            var serverletPath = request.getServletPath();
                if(serverletPath.equals("/manager/users")){
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
               String username = credentials[0];
               String password = credentials[1];
               System.out.println(password);
                // Validar usuário
                if(username.matches("\\d+")){
                    response.setStatus(401);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Informe o nome do gerente!.\"}");
                    return;
                }
                var user = this.loginMnRepository.findBynome(username);
                if(user == null){
                    response.setStatus(401);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"O gerente não existe!\"}");
                } else {
                // Validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getSenha());
                if(passwordVerify.verified){
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else{
                    response.setStatus(401); // Código 401
                    response.setContentType("application/json"); // Define o tipo de conteúdo como JSON
                    response.getWriter().write("{\"error\": \"Senha incorreta!\"}"); // Mensagem JSON
                    
                } 
                // Segue a viagem

            }
    } else {
        filterChain.doFilter(request, response);
    }
}
    
}