package com.enset.tpspringMVC.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // permet au classe qui sera traite apriori lors du demarrage de l'app
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired // pour utuiliser lqsource des donnees configure dans properties
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// specifier le username and passwd
		// ON AJOUTE {noop} avant le passwd pour specifier qu'on veut utiliser le meme
		// pass et non pas l'encoder
		// auth.inMemoryAuthentication().withUser("user1").password("{noop}111").roles("USER");
		PasswordEncoder passwordEncoder = passwordEncoder();
		System.out.println("*********************************\n");
		System.out.println(passwordEncoder.encode("1234"));
		
		auth.jdbcAuthentication().dataSource(dataSource)
				// principal sont les username et credentials sont password pour spring security
				.usersByUsernameQuery(
						"Select username as principal, password as credentials, active from users where username =? ")
				// role est connu comme role pour spring donc j'ai pas besoin de le renomme
				// sinon(dans bdd j'ai type) je dois ajouter as role
				.authoritiesByUsernameQuery("select username as principal, role from users_roles where username = ?")
				.passwordEncoder(passwordEncoder)
				.rolePrefix("ROLE_"); 

		/*
		 * auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder.
		 * encode("111")).roles("USER");
		 * auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder.
		 * encode("111")).roles("USER");
		 * auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.
		 * encode("111")).roles("USER","ADMIN");
		 */
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login"); // utiliser un formulaire d'authentification du path /login specifier dans le controler

		// demander l'authentification à chaque requette
		http.authorizeRequests().antMatchers("/save**/**", "/delete**/**", "/add**/**", "/edit**/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/patients**/**").hasRole("USER");
		http.authorizeRequests().antMatchers("/user/**","/login**/**","/webjars/**","/css/**").permitAll();
		
		http.authorizeRequests().anyRequest().authenticated();

		http.csrf(); // par defaut active le mecanisme du pirate vers les attacks du types csrf
		http.exceptionHandling().accessDeniedPage("/notAuthorized");
	}

	@Bean // on ajoute cette notion pour specifier que l'objet retourne par cette methode
			// sera un composant spring
			// q'on peut l'injecter dans les parties de l'application on ajoute seulement
			// autowired qu'on l'appel
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
