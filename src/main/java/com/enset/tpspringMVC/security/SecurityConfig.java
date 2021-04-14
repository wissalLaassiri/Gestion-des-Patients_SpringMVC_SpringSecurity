package com.enset.tpspringMVC.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // permet au classe qui sera traite apriori lors du demarrage de l'app
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// specifier le username and passwd
		// ON AJOUTE {noop} avant le passwd pour specifier qu'on veut utiliser le meme pass et non pas l'encoder
		auth.inMemoryAuthentication().withUser("user1").password("{noop}111").roles("USER");
		auth.inMemoryAuthentication().withUser("user2").password("111").roles("USER");
		auth.inMemoryAuthentication().withUser("user1").password("111").roles("USER","ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin(); // utiliser un formulaire d'authentification
		
		//demander l'authentification à chaque requette
		http.authorizeRequests().anyRequest().authenticated();
	}
}
