package com.enset.tpspringMVC;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.enset.tpspringMVC.entities.Patient;
import com.enset.tpspringMVC.repositories.PatientRepository;

@SpringBootApplication
public class TpSpringMvcApplication implements CommandLineRunner {
	@Autowired
	private PatientRepository patientRepository;
	public static void main(String[] args) {
		SpringApplication.run(TpSpringMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		patientRepository.save(new Patient(null,"Hassan",new Date(),2300,false));
		patientRepository.save(new Patient(null,"Wissal",new Date(),1400,false));
		patientRepository.save(new Patient(null,"Farah",new Date(),2500,false));
		patientRepository.save(new Patient(null,"Yassine",new Date(),1200,true));
	}
}
