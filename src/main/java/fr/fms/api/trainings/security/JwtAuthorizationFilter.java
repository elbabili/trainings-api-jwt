package fr.fms.api.trainings.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import fr.fms.api.trainings.security.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(SecurityConstants.HEADER_STRING); // récupération du token à partir de l'entête : Authorization

        //permet les accès de domaines différent du back
        response.addHeader("Access-Control-Allow-Origin","*");

        //Tous les headers autorisés
        response.addHeader("Access-Control-Allow-Headers",
                "Origin, Accept, X-Requested-With, Content-Type, " +
                        "Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");

        //Tous les headers exposés donc visible côté front
        response.addHeader("Access-Control-Expose-Headers",
                "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization");

        response.addHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,DELETE,PUT");

        /*if(request.getMethod().equals("OPTIONS")){  //si la requete contient une OPTION renvoyer OK -- côté front : Authorization
            response.setStatus(HttpServletResponse.SC_OK);  //Status code (200) indicating the request succeeded normally
        }
        else*/ if(token != null && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {   //génère des exceptions si pb sur le token : expiration / pb signature
                String jwtToken = token.substring(7);
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SecurityConstants.SECRET)).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);   //vérification si les clés privés reçues et générées sont identiques

                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                for(String role : roles) authorities.add(new SimpleGrantedAuthority(role));

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null,authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                //authentification de l'utilisateur dans le contexte de Spring Security
            }
            catch(Exception e) {    //en cas de pb, renvoi une requete http avec les messages d'erreurs dont 403
                response.setHeader(SecurityConstants.ERROR_MSG,e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
        filterChain.doFilter(request,response);     //une fois les étapes terminées, renvoi vers le filtre suivant
    }
}
