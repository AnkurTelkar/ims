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

import com.ims.inventory.dto.UnitDto;
import com.ims.inventory.service.UnitService;
import com.ims.util.dto.MeasuringUnitDto;
import com.ims.util.service.MeasuringUnitService;

/**
 *
 * @author HK Dev
 */

@Controller
@RequestMapping( value = "/inventory/units")
public class UnitController {

	@Autowired
	private UnitService unitService;
	
	@Autowired
	private MeasuringUnitService measuringUnitService;

	@RequestMapping( value = "/addUnit.htm", method = RequestMethod.GET )
	public ModelAndView saveUnit() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/units/addUnit");
		populateInvMeasuringUnits( mv );
		UnitDto unitDto = new UnitDto();
		mv.addObject( "unitDto", unitDto );
		return mv;
	}

	@RequestMapping( value = "/addUnit.htm", method = RequestMethod.POST )
	public ModelAndView saveUnit(  @ModelAttribute( "unitDto" ) @Validated UnitDto unitDto, BindingResult result ) {
		ModelAndView mv = new ModelAndView();
		populateInvMeasuringUnits( mv );
		if (result.hasErrors()) {
			mv.setViewName("/inventory/units/addUnit");
			mv.addObject( "unitDto", unitDto );
			return mv;
		}
		Integer id = unitDto.getUnitId();

		if( id == null || id <= 0 ) {
			unitDto.setCreatedAt( new Date() );
		} else {
			UnitDto unitDtoDB = unitService.findById( id );
			unitDto.setCreatedAt( unitDtoDB.getCreatedAt() );
		}
		MeasuringUnitDto measuringUnit = unitDto.getMeasuringUnit();
		unitDto.setMeasuringUnit( measuringUnitService.findById( measuringUnit.getId() ) );
		unitDto.setUpdatedAt( new Date() );
		unitDto = unitService.save( unitDto );

		mv.setViewName("/inventory/units/viewUnit");
		mv.addObject( "unitDto", unitDto);
		populateInvMeasuringUnits( mv );
		mv.addObject( "message", "Inventory Unit " + ( id == null || id <= 0 ? "saved" : "updated" ) + " successfully." );
		return mv;
	}

	@RequestMapping( value = "/{id}/viewUnit.htm", method = RequestMethod.GET )
	public ModelAndView viewUnit( @PathVariable(value="id") int id ) {
		
		UnitDto unitDto = unitService.findById( id );
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/units/viewUnit");
		populateInvMeasuringUnits( mv );
		mv.addObject( "unitDto", unitDto);
		return mv;
	}

	@RequestMapping( value = "/{id}/editUnit.htm", method = { RequestMethod.GET, RequestMethod.POST } )
	public ModelAndView editUnit( @PathVariable(value="id") int id ) {
		ModelAndView mv = new ModelAndView();
		UnitDto unitDto = unitService.findById( id );
		mv.setViewName("/inventory/units/addUnit");
		populateInvMeasuringUnits( mv );
		mv.addObject( "unitDto", unitDto);
		return mv;
	}

	@RequestMapping( value = "/listUnits.htm", method = RequestMethod.GET )
	public ModelAndView listUnits( ) {
		List<UnitDto> unitDtoList = unitService.findAll();
		ModelAndView mv = new ModelAndView( "/inventory/units/listUnits" );
		populateInvMeasuringUnits( mv );
		mv.addObject( "unitDtoList", unitDtoList );
		return mv;
	}
	
	 private void populateInvMeasuringUnits(ModelAndView mv) {
	        List<MeasuringUnitDto> measuringUnitDtoList = measuringUnitService.findAll();
	        mv.addObject( "measuringUnitDtoList", measuringUnitDtoList );
	    }
	 
	 public String getDisplayName() {
	        String displayName = "";
	        return displayName;
	        /*InvMeasuringUnits invMeasuringUnits = new InvMeasuringUnits();
	        CommonCrudDao invMeasuringUnitDao = new CommonCrudDao( invMeasuringUnits );
	        if( this.getMeasuringUnitId() > 0 ) {
	            invMeasuringUnits = ( InvMeasuringUnits ) invMeasuringUnitDao.get( this.getMeasuringUnitId() );
	            if( invMeasuringUnits != null ) {
	               displayName = invMeasuringUnits.getName() + " (" + invMeasuringUnits.getDescription() + ")";
	            }
	        }
	        return displayName;*/
	    }
}