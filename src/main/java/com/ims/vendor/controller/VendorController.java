/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.vendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ims.vendor.dto.VendorDTO;
import com.ims.vendor.service.VendorService;

/**
 *
 * @author HK Dev
 */

@Controller
@RequestMapping( value = "/vendors")
public class VendorController {
	
	@Autowired
	private VendorService vendorService;
	
    @RequestMapping( value = "/addVendor.htm", method = RequestMethod.GET )
    public ModelAndView saveVendor() {
        
        ModelAndView mv = new ModelAndView();
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setVendorCode( vendorService.getVendorCode() );
        mv.addObject( "vendor", vendorDTO );
        mv.setViewName("/vendors/addVendor");
        return mv;
    }
    
    /**
     *
     * @param vendor
     * @param result
     * @return
     */
    @RequestMapping( value = "/addVendor.htm", method = RequestMethod.POST )
    public ModelAndView postSaveVendor(  @ModelAttribute( "vendor" ) @Validated VendorDTO vendorDTO, BindingResult result ) {
        ModelAndView mv = new ModelAndView();
        String url = "/vendors/addVendor";
        String message = "";
        try {
        	if (result.hasErrors()) {
            	mv.setViewName( url );
            	mv.addObject( "vendor", vendorDTO );
                return mv;
            }
        	
        	vendorDTO = vendorService.saveOrUpdate( vendorDTO );
            if( vendorDTO != null && vendorDTO.getId() <= 0 ) {
            	throw new Exception( "Vendor not saved." );
            } else {
            	url = "/vendors/viewVendor";
            	message = "Vendor " + ( vendorDTO.getId() <= 0 ? "saved" : "updated" ) + " successfully.";        	
            }
        } catch( Exception e ) {
        	message = e.getMessage();
        	
        }
        mv.addObject( "vendor", vendorDTO );
        mv.addObject( "message", message );
        mv.setViewName( url );
        return mv;
    }
    
    @RequestMapping( value = "/{id}/viewVendor.htm", method = RequestMethod.GET )
    public ModelAndView viewVendor( @PathVariable(value="id") int id ) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/vendors/viewVendor");
        mv.addObject( "vendor", vendorService.getVendor( id ) );
        return mv;
    }
    
    @RequestMapping( value = "/{id}/editVendor.htm", method = { RequestMethod.GET, RequestMethod.POST } )
    public ModelAndView editVendor( @PathVariable(value="id") int id ) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/vendors/addVendor");
        VendorDTO vendorDto = vendorService.getVendor( id );
        mv.addObject( "vendor", vendorDto );
        return mv;
    }

    @RequestMapping( value = "/listVendors.htm", method = RequestMethod.GET )
    public ModelAndView listVendors( ) {
         List<VendorDTO> vendorsList = vendorService.getAllVendors();
        ModelAndView mv = new ModelAndView( "/vendors/listVendors" );
        mv.addObject( "vendorsList", vendorsList );
        return mv;
    }
}