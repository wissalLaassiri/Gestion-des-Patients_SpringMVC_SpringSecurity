package com.enset.tpspringMVC.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.enset.tpspringMVC.entities.Patient;
import com.enset.tpspringMVC.repositories.PatientRepository;

@Controller
public class PatientController {
	@Autowired
	PatientRepository patientRepository;
	@GetMapping(path="/index")
	
	//utiliser un objet request avec le parametre mot cle saisi dans la page html
	// si j'appell ans parametre je vais chercher par une chaine vide, sinon je cherche avec le mot cle saisi
	/*
	public String listPatients(Model model,@RequestParam(name="motCle",defaultValue="")String mc) {
		// je récupère les patients et le stocke dans le model
		List<Patient> patients = patientRepository.findByNomContains(mc);
		model.addAttribute("listePatients", patients);
		model.addAttribute("motCle", mc);
		
		// je retourne le nom de la vue "patientsView.html
		return "patientsView";
	}*/
	public String listPatients(Model model,
			@RequestParam(name="motCle",defaultValue="")String mc,
			@RequestParam(name="page",defaultValue = "0")int page,
			@RequestParam(name="size",defaultValue = "3")int size) {
		// je récupère les patients et le stocke dans le model
		Page<Patient> patientsPage = patientRepository.findByNomContains(mc,PageRequest.of(page, size));
		model.addAttribute("motCle", mc);
		model.addAttribute("patientsPage", patientsPage);
		model.addAttribute("pageCourante", page);
		
		int[] pages = new int[patientsPage.getTotalPages()];
		for(int i=0;i<pages.length;i++) pages[i]=i;
		model.addAttribute("pages",pages);
		
		// je retourne le nom de la vue "patientsView.html
		return "patientsView";
	}
	
	@GetMapping(path="/deletePatient")
	// le parametre du request s'apele id et la variable s'appelle id donc c'est pa la peine d'utiliser @tequestParam=...
	public String delete(Long id) {
		patientRepository.deleteById(id);
		return "redirect:/index"; // va redirectionner vers la page /index 
	}
	@GetMapping(path="/add")
	public String form(Model model) {
		model.addAttribute("patient",new Patient());
		return "patientsForm";
	}
	@PostMapping(path="/savePatient")
	public String save(@Valid Patient patient,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) 		return "patientsForm";
		patientRepository.save(patient);
		return "redirect:/index";
	}

	@GetMapping(path="/editPatient")
	public String edit(Model model,Long id) {
		Patient p = patientRepository.findById(id).get();
		model.addAttribute("patient", p);
		return "patientsForm"; 
	}
}
