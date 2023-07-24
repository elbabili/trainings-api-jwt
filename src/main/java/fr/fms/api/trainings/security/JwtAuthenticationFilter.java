package fr.fms.api.trainings.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //méthode qui en charge la demande d'authentification
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = null;
        try {
            if(request != null) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            }
            else return null;
            //après avoir récupérer les crédentials, on interroge spring, authentifié ou pas ?
        } catch(Exception e) {
            log.error("PB d'authentification {}",e.getMessage());
        }
        return authenticationManager.authenticate(authenticationToken);
    }

    // une fois authentifié spring security fait appel à cette méthode
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String jwtToken = null;
        try {
            User springUser = (User) authResult.getPrincipal();   //récupération des infos sur l'user connecté

            //dès à présent, il faut créer le token Jwt pour l'utilisateur connecté :
            jwtToken = JWT.create()
                    .withSubject(springUser.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))    //expiration du token dans 10 minutes
                    .withIssuer(request.getRequestURL().toString())     //nom de l'appli qui a généré le token
                    .withClaim("roles", springUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    //à partir de la liste de GrantedAuthority on récupère une liste de String contenant chaque role
                    .sign(Algorithm.HMAC256(SecurityConstants.SECRET));
        }
        catch (Exception e){
            response.setHeader(SecurityConstants.ERROR_MSG,e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.setHeader(SecurityConstants.HEADER_STRING , SecurityConstants.TOKEN_PREFIX + jwtToken);   //renvoi une requete contenant : Autorisation + token
    }
}

  /*String refreshToken = JWT.create()
                .withSubject(springUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME * SecurityConstants.EXPIRATION_TIME))    //expiration dans 100 minutes
                .withIssuer(request.getRequestURL().toString())
                .sign(Algorithm.HMAC256(SecurityConstants.SECRET));

        Map<String,String> allTokens = new HashMap<>();
        allTokens.put("access-token",jwtToken);
        allTokens.put("refresh-token",refreshToken);
        //injection dans la réponse http des 2 tokens au format Json
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(),allTokens);*/