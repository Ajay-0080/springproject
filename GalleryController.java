package com.gallery.controller;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	    List<Details> detailsList = galleryService.getAllDetails();

	    
		
		for (Details detail : detailsList) {
            if (detail.getThumb() != null) { // Assuming `thumb` is the byte[] field
                String encodedImage = Base64.getEncoder().encodeToString(detail.getThumb());
                detail.setThumbBase64(encodedImage);
                System.out.println("Base64 Encoded Image: " + encodedImage);
            } else {
                detail.setThumbBase64(""); 
            }
        }
		
		
		

	    model.addAttribute("listDetails", detailsList);
		
		
		
		
		
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

	   
	    return "redirect:/";

	}
	
	

	
	

	
}
