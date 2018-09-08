package com.ims.inventory.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ims.inventory.dto.BrandDto;
import com.ims.inventory.dto.CategoryDto;
import com.ims.inventory.dto.ItemDto;
import com.ims.inventory.dto.SkuDto;
import com.ims.inventory.dto.UnitDto;
import com.ims.inventory.service.BrandService;
import com.ims.inventory.service.CategoryService;
import com.ims.inventory.service.ItemService;
import com.ims.inventory.service.SkuService;
import com.ims.inventory.service.UnitService;
import com.ims.util.utility.Utility;

@Controller
@RequestMapping( value = "/inventory/items")
public class ItemController {

	private org.slf4j.Logger logger = LoggerFactory.getLogger( ItemController.class.getName() ) ;
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private SkuService skuService;

	@RequestMapping( value = "/addItem.htm", method = RequestMethod.GET )
	public ModelAndView saveItem() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/items/addItem");
		populateEntities(mv);
		ItemDto itemDto = new ItemDto();
		itemDto.setItemCode( itemService.getNextCode() );
		mv.addObject( "itemDto", itemDto );
		return mv;
	}
	
	@RequestMapping( value = "/{itemId}/addSkus.htm", method = RequestMethod.GET )
	public ModelAndView addSkus( @PathVariable(value="itemId") int itemId ) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/items/addSkus");
		ItemDto itemDto = itemService.findById( itemId );
		List<SkuDto> skuDtoList = skuService.findByItemId( itemId );		
		mv.addObject( "itemDto", itemDto );
		mv.addObject( "skuDtoList", skuDtoList );
		return mv;
	}

	@RequestMapping( value = "/addItem.htm", method = RequestMethod.POST )
	public ModelAndView saveItem(  @ModelAttribute( "item" ) @Validated ItemDto itemDto, BindingResult result ) throws Exception {
		ModelAndView mv = new ModelAndView();
		populateEntities(mv);
		if (result.hasErrors()) {
			mv.setViewName("/inventory/items/addItem");
			mv.addObject( "itemDto", itemDto );
			return mv;
		}
		int id = Utility.getValidInt( itemDto.getItemId() );
		if( id <= 0 ) {
			itemDto.setCreatedAt( new Date() );
		} else {
			ItemDto itemDtoDB = itemService.findById( id );
			itemDto.setCreatedAt( itemDtoDB.getCreatedAt() );
		}

		itemDto.setUpdatedAt( new Date() );
		int brandId = itemDto.getBrand().getBrandId();
		itemDto.setBrand( brandService.findById( brandId ) );
		int categoryId = itemDto.getCategory().getCategoryId();
		itemDto.setCategory( categoryService.findById( categoryId ) );
		int unitId = itemDto.getTrackUnit().getUnitId();
		itemDto.setTrackUnit( unitService.findById( unitId ) );
		itemDto = itemService.save( itemDto );

		mv.setViewName("/inventory/items/viewItem");
		mv.addObject( "itemDto", itemDto);
		mv.addObject( "message", "Inventory Item " + (  id <= 0 ? "saved" : "updated" ) + " successfully." );
		return mv;
	}

	@RequestMapping( value = "/{id}/viewItem.htm", method = RequestMethod.GET )
	public ModelAndView viewItem( @PathVariable(value="id") int id ) {
		
		ItemDto itemDto = itemService.findById( id );
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/items/viewItem");
		mv.addObject( "itemDto", itemDto);
		return mv;
	}
	
	@RequestMapping( value = "/{id}/listSkus.htm", method = RequestMethod.GET )
	public ModelAndView listSkus( @PathVariable(value="id") int id ) {
		List<SkuDto> skuDtoList = skuService.findByItemId( id );
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/inventory/skus/listSkusDetails");
		mv.addObject( "skuDtoList", skuDtoList);
		return mv;
	}

	@RequestMapping( value = "/{id}/editItem.htm", method = { RequestMethod.GET, RequestMethod.POST } )
	public ModelAndView editItem( @PathVariable(value="id") int id ) {
		ModelAndView mv = new ModelAndView();
		populateEntities(mv);
		ItemDto itemDto = itemService.findById( id );
		mv.setViewName("/inventory/items/addItem");
		mv.addObject( "itemDto", itemDto);
		return mv;
	}

	@RequestMapping( value = "/listItems.htm", method = RequestMethod.GET )
	public ModelAndView listItems( ) {
		List<ItemDto> itemDtoList = itemService.findAll();
		ModelAndView mv = new ModelAndView( "/inventory/items/listItems" );
		mv.addObject( "itemDtoList", itemDtoList );
		return mv;
	}
	
	private void populateEntities(ModelAndView mv) {
        populateInvUnits(mv);
        populateInvCategories(mv);
        populateInvBrands(mv);
    }
	
	private void populateInvUnits(ModelAndView mv) {
        List<UnitDto> invUnits = unitService.findAll();
        mv.addObject("invUnits", invUnits);
    }

    private void populateInvCategories(ModelAndView mv) {
        List<CategoryDto> invCategories = categoryService.findAll();
        mv.addObject("invCategories", invCategories);
    }

    private void populateInvBrands(ModelAndView mv) {
        List<BrandDto> invBrands = brandService.findAll();
        mv.addObject("invBrands", invBrands);
    }
    
    @ExceptionHandler( Exception.class )
    public ModelAndView handleException( HttpServletRequest request, Exception e ) {
    	logger.error( "ERROR: " + e.getMessage(), e );
    	String message = "ERROR: Unable to update: ";
    	if( e instanceof DataIntegrityViolationException ) {
    		String[] error = e.getCause().getCause().getMessage().split( "for" );
    		message += error[0];
    	} else {
    		message += e.getMessage();
    	}
    	ModelAndView mv = saveItem();
    	mv.addObject( "message", message );
		return mv;
    }
}