/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.customer.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ims.customer.dto.CustomerDetailDto;
import com.ims.customer.dto.CustomerDto;
import com.ims.customer.service.CustomerService;

/**
 *
 * @author HK Dev
 */

@Controller
@RequestMapping( value = "/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
    @RequestMapping( value = "/addCustomer.htm", method = RequestMethod.GET )
    public ModelAndView saveCustomer() {
        
        ModelAndView mv = new ModelAndView();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerCode( customerService.getNextCode() );
        mv.addObject( "customer", customerDto );
        mv.setViewName("/customers/addCustomer");
        return mv;
    }
    
    /**
     *
     * @param customer
     * @param result
     * @return
     */
    @RequestMapping( value = "/addCustomer.htm", method = RequestMethod.POST )
    public ModelAndView postSaveCustomer(  @ModelAttribute( "customer" ) @Validated CustomerDto customerDto, BindingResult result ) {
        ModelAndView mv = new ModelAndView();
        String url = "/customers/addCustomer";
        String message = "";
        try {
        	if (result.hasErrors()) {
            	mv.setViewName( url );
            	mv.addObject( "customer", customerDto );
                return mv;
            }
        	
        	Integer id = customerDto.getCustomerId();
        	
        	if( id == null || id <= 0 ) {
        		customerDto.setCreatedAt( new Date() );
    		} else {
    			CustomerDto customerDtoDB = customerService.findById( id );
    			customerDto.setCreatedAt( customerDtoDB.getCreatedAt() );
    		}
        	Iterator<CustomerDetailDto> iterator = customerDto.getCustomerDetails().iterator();
			while( iterator.hasNext() ) {
				CustomerDetailDto customerDetailsDTO = iterator.next();
				if( customerDetailsDTO == null || 
						( StringUtils.isEmpty( customerDetailsDTO.getContactPerson() ) 
								&& StringUtils.isEmpty( customerDetailsDTO.getEmail() )
								&& StringUtils.isEmpty( customerDetailsDTO.getPhoneNo() ) ) ) {
					iterator.remove();
				} else {
					customerDetailsDTO.setCustomer(customerDto);
				}
			}

        	customerDto.setUpdatedAt( new Date() );
        	if( StringUtils.isEmpty( customerDto.getGstNo() ) ) {
        		customerDto.setGstNo( null );
        	}
        	customerDto = customerService.save( customerDto );
            if( customerDto != null && customerDto.getCustomerId() <= 0 ) {
            	throw new Exception( "Customer not saved." );
            } else {
            	url = "/customers/viewCustomer";
            	message = "Customer " + ( customerDto.getCustomerId() <= 0 ? "saved" : "updated" ) + " successfully.";        	
            }
        } catch( Exception e ) {
        	message = e.getMessage();
        	
        }
        mv.addObject( "customer", customerDto );
        mv.addObject( "message", message );
        mv.setViewName( url );
        return mv;
    }
    
    @RequestMapping( value = "/{id}/viewCustomer.htm", method = RequestMethod.GET )
    public ModelAndView viewCustomer( @PathVariable(value="id") int id ) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/customers/viewCustomer");
        mv.addObject( "customer", customerService.findById( id ) );
        return mv;
    }
    
    @RequestMapping( value = "/{id}/editCustomer.htm", method = { RequestMethod.GET, RequestMethod.POST } )
    public ModelAndView editCustomer( @PathVariable(value="id") int id ) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/customers/addCustomer");
        CustomerDto customerDto = customerService.findById( id );
        mv.addObject( "customer", customerDto );
        return mv;
    }
    
    @RequestMapping( value = "/{id}/deleteCustomer.htm", method = { RequestMethod.GET, RequestMethod.POST } )
    public ModelAndView deleteCustomer( @PathVariable(value="id") int id ) {
    	String message = customerService.delete(id);
        List<CustomerDto> customersList = customerService.findAll();
        ModelAndView mv = new ModelAndView( "/customers/listCustomers" );
        mv.addObject( "customersList", customersList );
        mv.addObject("message", message);
        return mv;
    }

    @RequestMapping( value = "/listCustomers.htm", method = RequestMethod.GET )
    public ModelAndView listCustomers( ) {
         List<CustomerDto> customersList = customerService.findAll();
        ModelAndView mv = new ModelAndView( "/customers/listCustomers" );
        mv.addObject( "customersList", customersList );
        return mv;
    }
}