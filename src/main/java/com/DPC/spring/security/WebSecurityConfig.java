package com.DPC.spring.security;

import com.DPC.spring.security.jwt.JwtAuthenticationEntryPoint;
import com.DPC.spring.security.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
     auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/Laboratoire/affichelist").permitAll() 
                .antMatchers("/ville/affichbyid").permitAll()
                .antMatchers("/ville/listville").permitAll()
                .antMatchers("/RendezVous/ajout").permitAll()
                .antMatchers("/Question/ajoutQuestion").permitAll()
                .antMatchers("/Question/affichelist").permitAll()
                .antMatchers("/Question/modifierquestion").permitAll()
                .antMatchers("/Reponse/afficherlistbyquestion").permitAll()
                .antMatchers("/Radiologe/afficherlistbyville").permitAll()
                .antMatchers("/Radiologe/affichelist").permitAll()
                .antMatchers("/Radiologe/ajoutradio").permitAll()
                .antMatchers("/Radiologe/getbyid").permitAll()
                .antMatchers("/Evenement/ajouterEvenement").permitAll()
                .antMatchers("/Evenement/allevents").permitAll()
                .antMatchers("/Magasine/afficherlistmagasinefinetdebut").permitAll()
                .antMatchers("/Laboratoire/afficherlistbyville").permitAll()
                .antMatchers("/Laboratoire/tablebynom").permitAll()
                .antMatchers("/RendezVous/afficherRDV").permitAll()  
                .antMatchers("/Laboratoire/affichelist").permitAll()
                .antMatchers("/Laboratoire/tablebyid").permitAll()
                .antMatchers("/users/add").permitAll()
                .antMatchers("/users/affichepagehome").permitAll()
                .antMatchers("/users/archiver").permitAll()
                .antMatchers("/users/getbyemail").permitAll()
                .antMatchers("/users/ajouterV2").permitAll()
                .antMatchers("/users/changemp").permitAll()
                .antMatchers("/users/getbyid").permitAll()
                .antMatchers("/users/update").permitAll()
                .antMatchers("/users/getbyemail").permitAll()
                .antMatchers("/users/countuser").permitAll()
                .antMatchers("/users/countmembre").permitAll()
                .antMatchers("/users/countjoueur").permitAll()
                .antMatchers("/users/countdoc").permitAll()
                .antMatchers("/users/comptclient").permitAll()
                .antMatchers("/users/envoyeremail").permitAll()
                .antMatchers("/users/afficherlist").permitAll()
                .antMatchers("/users/afficherformationbyiduser").permitAll()
                .antMatchers("/RendezVous/afficherRDVbyemailClient").permitAll()
                .antMatchers("/image/uploadOffre").permitAll()
                .antMatchers("/imagetest/ajouterimagetest").permitAll()
                .antMatchers("/imagetest/all").permitAll()
                .antMatchers("/login2").permitAll()
                .antMatchers("/RendezVous/afficherRDVbydate").permitAll()
                .antMatchers("/Specialite/listSpecialite").permitAll() 
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated().and()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
