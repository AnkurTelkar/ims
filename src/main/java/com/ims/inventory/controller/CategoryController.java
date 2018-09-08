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

import com.ims.inventory.dto.CategoryDto;
import com.ims.inventory.service.CategoryService;

/**
 *
 * @author HK Dev
 */

@Controller
@RequestMapping( value = "/inventory/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping( value = "/addCategory.htm", method = RequestMethod.GET )
	public ModelAndView saveCategory() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/categories/addCategory");
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCategoryCode( categoryService.getNextCode() );
		mv.addObject( "categoryDto", categoryDto );
		return mv;
	}

	@RequestMapping( value = "/addCategory.htm", method = RequestMethod.POST )
	public ModelAndView saveCategory(  @ModelAttribute( "category" ) @Validated CategoryDto categoryDto, BindingResult result ) {
		ModelAndView mv = new ModelAndView();

		if (result.hasErrors()) {
			mv.setViewName("/inventory/categories/addCategory");
			mv.addObject( "categoryDto", categoryDto );
			return mv;
		}
		Integer id = categoryDto.getCategoryId();

		if( id == null || id <= 0 ) {
			categoryDto.setCreatedAt( new Date() );
		} else {
			CategoryDto categoryDtoDB = categoryService.findById( id );
			categoryDto.setCreatedAt( categoryDtoDB.getCreatedAt() );
		}

		categoryDto.setUpdatedAt( new Date() );
		categoryDto = categoryService.save( categoryDto );

		mv.setViewName("/inventory/categories/viewCategory");
		mv.addObject( "categoryDto", categoryDto);
		mv.addObject( "message", "Inventory Category " + ( id == null || id <= 0 ? "saved" : "updated" ) + " successfully." );
		return mv;
	}

	@RequestMapping( value = "/{id}/viewCategory.htm", method = RequestMethod.GET )
	public ModelAndView viewCategory( @PathVariable(value="id") int id ) {
		
		CategoryDto categoryDto = categoryService.findById( id );
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/categories/viewCategory");
		mv.addObject( "categoryDto", categoryDto);
		return mv;
	}

	@RequestMapping( value = "/{id}/editCategory.htm", method = { RequestMethod.GET, RequestMethod.POST } )
	public ModelAndView editCategory( @PathVariable(value="id") int id ) {
		ModelAndView mv = new ModelAndView();
		CategoryDto categoryDto = categoryService.findById( id );
		mv.setViewName("/inventory/categories/addCategory");
		mv.addObject( "categoryDto", categoryDto);
		return mv;
	}

	@RequestMapping( value = "/listCategories.htm", method = RequestMethod.GET )
	public ModelAndView listCategories( ) {
		List<CategoryDto> categoryDtoList = categoryService.findAll();
		ModelAndView mv = new ModelAndView( "/inventory/categories/listCategories" );
		mv.addObject( "categoryDtoList", categoryDtoList );
		return mv;
	}
}