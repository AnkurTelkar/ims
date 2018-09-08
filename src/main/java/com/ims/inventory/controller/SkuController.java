/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.inventory.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ims.inventory.InventoryConstants;
import com.ims.inventory.dto.ItemDto;
import com.ims.inventory.dto.SkuDto;
import com.ims.inventory.service.ItemService;
import com.ims.inventory.service.SkuService;
import com.ims.util.utility.Utility;

@Controller
@RequestMapping( value = "/inventory/skus")
public class SkuController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private SkuService skuService;
	
	@RequestMapping( value = "/addSku.htm", method = RequestMethod.GET )
	public ModelAndView saveSku() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/skus/addSku");
		populateEntities(mv);
		SkuDto skuDto = new SkuDto();
		String code = skuService.getNextCode( 0 );
		skuDto.setBarcode(code);
		skuDto.setSkuCode(code);
		mv.addObject( "skuDto", skuDto );
		return mv;
	}

	@RequestMapping( value = "/addSku.htm", method = RequestMethod.POST )
	public ModelAndView saveSku(  @ModelAttribute( "sku" ) @Validated SkuDto skuDto, BindingResult result ) {
		String message = "";
		ModelAndView mv = new ModelAndView();
		populateEntities(mv);
		try {
			if (result.hasErrors()) {
				mv.setViewName("/inventory/skus/addSku");
				mv.addObject( "skuDto", skuDto );
				return mv;
			}
			int id = Utility.getValidInt( skuDto.getSkuId() );

			if( id <= 0 ) {
				skuDto.setCreatedAt( new Date() );
			} else {
				SkuDto skuDtoDB = skuService.findById( id );
				skuDto.setCreatedAt( skuDtoDB.getCreatedAt() );
			}

			skuDto.setUpdatedAt( new Date() );
			int itemId = skuDto.getItem().getItemId();
			skuDto.setItem( itemService.findById( itemId ) );
			skuDto = skuService.save( skuDto, InventoryConstants.INV_TRANSACTION_CREATE_UPDATE_SKU );
			mv.setViewName("/inventory/skus/viewSku");
			message = "Inventory Sku " + ( id <= 0 ? "saved" : "updated" ) + " successfully.";
		} catch( Exception e ) {
            if (e.getCause().toString().contains("for key 'CONSTAINT'")) {
                message = "Unable to create/update item sku due to: Duplicate entry for field 'Code'";
            } else {
                message = "Unable to create/update item sku due to: " + e.getCause().toString() + ", " + e.getMessage();
            }
            mv.setViewName("/inventory/skus/addSku");
		}
		mv.addObject( "message", message );
		mv.addObject( "skuDto", skuDto);
		return mv;
	}

	@RequestMapping( value = "/{id}/viewSku.htm", method = RequestMethod.GET )
	public ModelAndView viewSku( @PathVariable(value="id") int id ) {
		
		SkuDto skuDto = skuService.findById( id );
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/skus/viewSku");
		mv.addObject( "skuDto", skuDto);
		return mv;
	}

	@RequestMapping( value = "/{id}/editSku.htm", method = { RequestMethod.GET, RequestMethod.POST } )
	public ModelAndView editSku( @PathVariable(value="id") int id ) {
		ModelAndView mv = new ModelAndView();
		populateEntities(mv);
		SkuDto skuDto = skuService.findById( id );
		mv.setViewName("/inventory/skus/addSku");
		mv.addObject( "skuDto", skuDto);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getNextSkuCode.htm", method = RequestMethod.GET)
	public String getItemSkuDetailsByBarcode( @RequestParam("input") int addedRows ) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("skuCode", skuService.getNextCode( addedRows ));
		return jsonObject.toString();
	}

	@RequestMapping( value = "/listSkus.htm", method = RequestMethod.GET )
	public ModelAndView listSkus( ) {
		List<SkuDto> skuDtoList = skuService.findAll();
		ModelAndView mv = new ModelAndView( "/inventory/skus/listSkus" );
		mv.addObject( "skuDtoList", skuDtoList );
		return mv;
	}
	
	private void populateEntities(ModelAndView mv) {
        populateInvItems(mv);
    }
	
	private void populateInvItems(ModelAndView mv) {
        List<ItemDto> itemDtoList = itemService.findAll();
        mv.addObject("itemDtoList", itemDtoList);
    }
	
	@ResponseBody
	@RequestMapping(value = "/updateSkus.htm", method = RequestMethod.GET)
	public String updateSkus(@RequestParam("input") String input, @RequestParam("itemId") int itemId, 
			HttpServletRequest request, HttpServletResponse response) {
		boolean result = false;
		String message = "Unable to Update SKUs due to some error!";
		try {
			result = skuService.updateSkus(input, itemId);
			if( result ) {
				message = "SKUs updates successfully!";
			}
				
		} catch( Exception e ) {
			message = "ERROR: "  + e.getMessage();
			result = false;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result + "");
		jsonObject.put("message", message);
		return jsonObject.toString();
		
		
		
	}
	
}