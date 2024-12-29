package com.gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.gallery.entity.Details;
import com.gallery.entity.Gallery;
import com.gallery.service.GalleryService;

@Controller
public class GalleryController {
	@Autowired
	GalleryService galleryService;

	@GetMapping("/")
	public String viewHomePage(Model model) {
		model.addAttribute("listGallery", galleryService.getAllGallery());
		return "index";
	}

	@GetMapping("/new")
	public String showNewArtistForm(Model model) {
		model.addAttribute("artist", new Gallery());
		return "new";
	}

	@PostMapping("/saveArtist")
	public String saveArtist(@ModelAttribute("gallery") Gallery gallery) {
		galleryService.saveArtist(gallery);
		return "redirect:/";
	}

	@GetMapping("/signin")
	public String showNewSigninForm(Model model) {
		model.addAttribute("login", new Gallery());
		return "signin";

	}

	@PostMapping("/checkArtist")
	public String checkArtist(@ModelAttribute("login") Gallery gallery) {
		boolean b = galleryService.checkArtist(gallery);

		if (b == true) {
			 return "redirect:/details";//return "details";
		}

		else {
			return "index";
		}

	}

	@GetMapping("/details")
	public String showDetailsForm(Model model) {
		model.addAttribute("details", new Details());
		return "details";
	}

 
	
	@PostMapping("/saveDetails")
	public String saveDetails(@ModelAttribute("details") Details details, Model model) {
	  
	    galleryService.saveDetails(details);

	    
	    Details savedDetails = galleryService.getArtistById(details.getId());

	   
	    model.addAttribute("artist", savedDetails);

	   
	    return "index";
	}

	
	
	
	
	
	
	
 
        

	
	


}  
