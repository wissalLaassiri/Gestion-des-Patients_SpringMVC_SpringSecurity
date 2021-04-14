package com.enset.tpspringMVC.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

@Entity
@Data // generate getters and setters
@NoArgsConstructor // constructor with no params
@AllArgsConstructor // constructor with all params
@ToString

public class Patient {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	@NotNull
	@Size(min=2,max=25)
	private String nom;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateNaissance;
	@DecimalMin("2")
	private int score;
	@SuppressWarnings("unused")
	private boolean malade;
}
