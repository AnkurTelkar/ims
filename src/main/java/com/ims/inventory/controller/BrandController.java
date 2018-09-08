/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.inventory.controller;

import java.util.Date;
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

import com.ims.inventory.dto.BrandDto;
import com.ims.inventory.service.BrandService;

/**
 *
 * @author HK Dev
 */

@Controller
@RequestMapping( value = "/inventory/brands")
public class BrandController {

	@Autowired
	private BrandService brandService;

	@RequestMapping( value = "/addBrand.htm", method = RequestMethod.GET )
	public ModelAndView saveBrand() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/brands/addBrand");
		BrandDto brandDto = new BrandDto();
		brandDto.setBrandCode( brandService.getNextCode() );
		mv.addObject( "brandDto", brandDto );
		return mv;
	}

	@RequestMapping( value = "/addBrand.htm", method = RequestMethod.POST )
	public ModelAndView saveBrand(  @ModelAttribute( "brand" ) @Validated BrandDto brandDto, BindingResult result ) {
		ModelAndView mv = new ModelAndView();

		if (result.hasErrors()) {
			mv.setViewName("/inventory/brands/addBrand");
			mv.addObject( "brandDto", brandDto );
			return mv;
		}
		Integer id = brandDto.getBrandId();

		if( id == null || id <= 0 ) {
			brandDto.setCreatedAt( new Date() );
		} else {
			BrandDto brandDtoDB = brandService.findById( id );
			brandDto.setCreatedAt( brandDtoDB.getCreatedAt() );
		}

		brandDto.setUpdatedAt( new Date() );
		brandDto = brandService.save( brandDto );

		mv.setViewName("/inventory/brands/viewBrand");
		mv.addObject( "brandDto", brandDto);
		mv.addObject( "message", "Inventory Brand " + ( id == null || id <= 0 ? "saved" : "updated" ) + " successfully." );
		return mv;
	}

	@RequestMapping( value = "/{id}/viewBrand.htm", method = RequestMethod.GET )
	public ModelAndView viewBrand( @PathVariable(value="id") int id ) {
		
		BrandDto brandDto = brandService.findById( id );
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/brands/viewBrand");
		mv.addObject( "brandDto", brandDto);
		return mv;
	}

	@RequestMapping( value = "/{id}/editBrand.htm", method = { RequestMethod.GET, RequestMethod.POST } )
	public ModelAndView editBrand( @PathVariable(value="id") int id ) {
		ModelAndView mv = new ModelAndView();
		BrandDto brandDto = brandService.findById( id );
		mv.setViewName("/inventory/brands/addBrand");
		mv.addObject( "brandDto", brandDto);
		return mv;
	}

	@RequestMapping( value = "/listBrands.htm", method = RequestMethod.GET )
	public ModelAndView listBrands( ) {
		List<BrandDto> brandDtoList = brandService.findAll();
		ModelAndView mv = new ModelAndView( "/inventory/brands/listBrands" );
		mv.addObject( "brandDtoList", brandDtoList );
		return mv;
	}
}