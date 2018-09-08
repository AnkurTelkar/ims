package com.ims.inventory.purchasing.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
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

import com.ims.ApplicationConstants;
import com.ims.inventory.InventoryConstants;
import com.ims.inventory.dto.SkuDto;
import com.ims.inventory.purchasing.dto.ReceivingAmountDetailDto;
import com.ims.inventory.purchasing.dto.ReceivingDto;
import com.ims.inventory.purchasing.dto.ReceivingItemDto;
import com.ims.inventory.purchasing.service.ReceivingService;
import com.ims.inventory.service.SkuService;
import com.ims.vendor.dto.VendorDTO;
import com.ims.vendor.service.VendorService;

@Controller
@RequestMapping(value = "/inventory/receivings")
public class ReceivingController {

	@Autowired
	private ReceivingService receivingService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private SkuService skuService;

	@RequestMapping(value = "/addReceiving.htm", method = RequestMethod.GET)
	public ModelAndView saveReceiving() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/receivings/addReceiving");
		ReceivingDto receivingDto = new ReceivingDto();

		receivingDto.setReceivingNo(receivingService.getNextCode());
		mv.addObject("vendorDtoList", vendorService.getAllVendors());
		mv.addObject("receivingDto", receivingDto);
		return mv;
	}

	@RequestMapping(value = "/addReceiving.htm", method = RequestMethod.POST)
	public ModelAndView saveReceiving(@ModelAttribute("receivingDto") @Validated ReceivingDto receivingDto,
			BindingResult result) {
		ModelAndView mv = new ModelAndView();

		if (result.hasErrors()) {
			mv.setViewName("/inventory/receivings/addReceiving");
			mv.addObject("receivingDto", receivingDto);
			return mv;
		}
		Integer id = receivingDto.getReceivingId();
		Date createdDate = new Date();
		Date updatedDate = new Date();
		if (id == null || id <= 0) {
			receivingDto.setCreatedAt(createdDate);
		} else {
			ReceivingDto receivingDtoDB = receivingService.findById(id);
			createdDate = receivingDtoDB.getCreatedAt();
			receivingDto.setCreatedAt(createdDate);
		}

		receivingDto.setUpdatedAt(updatedDate);
		List<ReceivingItemDto> receivingItemDtoList = receivingDto.getReceivingItems();
		if (receivingItemDtoList != null) {
			for (ReceivingItemDto receivingItemDto : receivingItemDtoList) {
				receivingItemDto.setDescription("");
				receivingItemDto.setCreatedAt(createdDate);
				receivingItemDto.setUpdatedAt(updatedDate);
				receivingItemDto.setReceiving(receivingDto);
				SkuDto skuDto = receivingItemDto.getSku();
				if( skuDto == null || skuDto.getSkuId() <= 0 ) {
					continue;
				}
				SkuDto skuDtoDB = skuService.findById(skuDto.getSkuId());
				receivingItemDto.setSku(skuDtoDB);
			}
		} else {
			receivingDto.setReceivingItems( new ArrayList<>() );
		}
		double total = 0;
		for (ReceivingAmountDetailDto amountDetailDto : receivingDto.getRecivingAmountDetails()) {
			if (amountDetailDto.getType() == ApplicationConstants.CASH_DISCOUNT
					|| amountDetailDto.getType() == ApplicationConstants.DISPLAY_DISCOUNT) {
				amountDetailDto.setAmount(amountDetailDto.getAmount() * -1);
			}
			amountDetailDto.setCreatedAt(createdDate);
			amountDetailDto.setUpdatedAt(updatedDate);
			amountDetailDto.setReceiving(receivingDto);
			total += amountDetailDto.getAmount();
		}
		receivingDto.setTotal(total);
		VendorDTO vendorDto = receivingDto.getVendor();
		if (vendorDto != null && vendorDto.getId() > 0) {
			receivingDto.setVendor(vendorService.getVendor(vendorDto.getId()));
		} else {
			receivingDto.setVendor(null);
		}

		receivingDto = receivingService.save(receivingDto, InventoryConstants.INV_TRANSACTION_RECEIVE_SKU);

		mv.setViewName("/inventory/receivings/viewReceiving");
		mv.addObject("receivingDto", receivingDto);
		mv.addObject("message", "Receiving " + (id == null || id <= 0 ? "saved" : "updated") + " successfully.");
		return mv;
	}

	@RequestMapping(value = "/{id}/viewReceiving.htm", method = RequestMethod.GET)
	public ModelAndView viewReceiving(@PathVariable(value = "id") int id) {

		ReceivingDto receivingDto = receivingService.findById(id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/receivings/viewReceiving");
		mv.addObject("receivingDto", receivingDto);
		return mv;
	}

	@RequestMapping(value = "/{id}/editReceiving.htm", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView editReceiving(@PathVariable(value = "id") int id) {
		ModelAndView mv = new ModelAndView();
		ReceivingDto receivingDto = receivingService.findById(id);
		mv.setViewName("/inventory/receivings/addReceiving");
		mv.addObject("receivingDto", receivingDto);
		mv.addObject("vendorDtoList", vendorService.getAllVendors());
		return mv;
	}

	@RequestMapping(value = "/listReceivings.htm", method = RequestMethod.GET)
	public ModelAndView listReceivings() {
		List<ReceivingDto> receivingDtoList = receivingService.findAll();
		ModelAndView mv = new ModelAndView("/inventory/receivings/listReceivings");
		mv.addObject("receivingDtoList", receivingDtoList);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/getItemSkuDetailsForReceiving.htm", method = RequestMethod.GET)
	public String getItemSkuDetailsForReceiving(@RequestParam("input") String input, HttpServletRequest request,
			HttpServletResponse response) {
		JSONArray jsonArray = skuService.findSkuDetails(input);
		return jsonArray.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/getItemSkuDetailsByBarcode.htm", method = RequestMethod.GET)
	public String getItemSkuDetailsByBarcode(@RequestParam("input") String input, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObject = skuService.findSkuDetailsByBarcode(input);
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ModelAndView download() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("filename", "sample.xlsx");
	    return mv;
	}
	
	@RequestMapping(value = "/{id}/printReceiving.htm", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView printReceiving(@PathVariable(value = "id") int id) {
		ModelAndView mv = new ModelAndView();
		ReceivingDto receivingDto = receivingService.findById(id);
		mv.setViewName("/inventory/receivings/printReceiving");
		mv.addObject("receivingDto", receivingDto);
		
		return mv;
	}
}