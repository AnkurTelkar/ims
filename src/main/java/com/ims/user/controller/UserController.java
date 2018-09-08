package com.ims.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ims.user.dto.UserDto;
import com.ims.user.dto.UserRightsDto;
import com.ims.user.entity.UserEntity;
import com.ims.user.service.UserService;
import com.ims.util.dto.RightsDto;
import com.ims.util.service.RightsService;

@RestController
@RequestMapping(value = { "", "/users" })
public class UserController {

	Logger log = LoggerFactory.getLogger(UserController.class.getName());

	@Autowired
	UserService userService;

	@Autowired
	RightsService rightsService;

	UserDto dto;

	@RequestMapping(value = "/addUser.htm", method = RequestMethod.GET)
	public ModelAndView addUser() {
		ModelAndView modelAndView = new ModelAndView();
		UserDto userDto = new UserDto();
		userDto.setUserCode(userService.getUserCode());
		//Integer maxUserId = userService.findMaxIdForUser();

		List<RightsDto> rightsDtos = rightsService.getAllRights();

		modelAndView.addObject("user", userDto);
		modelAndView.addObject("x", "n");
		modelAndView.addObject("rights", rightsDtos);
		modelAndView.addObject("action", "Add");
		modelAndView.setViewName("/users/addUser");
		return modelAndView;
	}

	@RequestMapping(value = "/addUser.htm", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateUser(@ModelAttribute("user") @Validated UserDto userDto, BindingResult result) {


		List<UserRightsDto> list = new ArrayList<>();
		for (UserRightsDto dto : userDto.getUserRights()) {
			if (dto.getRight() == null) {
				continue;
			}

			dto.setUser(userDto);
			Integer rightId = dto.getRight().getId();

			RightsDto rightsDto = rightsService.findRightById(rightId);
			dto.setRight(rightsDto);
			list.add(dto);
		}
		userDto.setUserRights(list);

		boolean isEmailPresent = userService.checkEmailAvailability(userDto);
		boolean isLoginIdPresent = userService.chckLoginIdAvailability(userDto);
		boolean isUserCodePresent = userService.checkUserCodeAvailability(userDto);


		ModelAndView modelAndView = new ModelAndView();

		String url = "/users/addUser";
		String message = "";
		if (isEmailPresent && isLoginIdPresent && isUserCodePresent) {

			try {
				if (result.hasErrors()) {
					modelAndView.setViewName(url);
					modelAndView.addObject("user", userDto);
					return modelAndView;
				}
				UserEntity userEntity = userService.save(userDto);
				UserDto dto = userService.entityToDto(userEntity);
				this.dto = dto;
				if (dto != null && dto.getUserId() <= 0) {
					throw new Exception("User not saved. !");
				} else {
					url = "/users/viewUser";
					// message = "User " + (dto.getUserId() <= 0 ? "saved" :
					// "update") + " successfully !!";
					message = "Success !!!";
				}

			} catch (Exception e) {
				log.error( e.getMessage(), e );
			}

			modelAndView.addObject("user", dto);
			modelAndView.addObject("message", message);
			modelAndView.setViewName(url);
			return modelAndView;
		} else {
			if (!isEmailPresent)
				message = "Email is already present";

			else if (!isLoginIdPresent)
				message = "Login id is already present.";

			else
				message = "User Code is already present.";

			modelAndView.addObject("message", message);
			modelAndView.setViewName("/users/addUser");
			modelAndView.addObject("user", userDto);
			return modelAndView;
		}
	}

	@RequestMapping(value = "/listUsers.htm", method = RequestMethod.GET)
	public ModelAndView listUsers() {
		ModelAndView modelAndView = new ModelAndView();
		List<UserDto> allUsers = userService.getAllUsers();
		modelAndView.addObject("users", allUsers);
		modelAndView.setViewName("/users/listUsers");
		return modelAndView;
	}

	@RequestMapping(value = "/{userId}/viewUser.htm", method = RequestMethod.GET)
	public ModelAndView viewUser(@PathVariable(value = "userId") int userId) {

		ModelAndView mv = new ModelAndView();
		UserDto dto = userService.findUserByUserId(userId);
		mv.setViewName("/users/viewUser");
		List<RightsDto> rightsDtos = rightsService.getAllRights();
		mv.addObject("rightsDtos", rightsDtos);
		mv.addObject("user", dto);
		mv.addObject("rights", dto.getUserRights());
		return mv;
	}

	@RequestMapping(value = "/{userId}/editUser.htm", method = RequestMethod.GET)
	public ModelAndView editUser(@PathVariable(value = "userId") int userId) {
		ModelAndView mv = new ModelAndView();
		List<RightsDto> rightsDtos = rightsService.getAllRights();
		mv.addObject("rights", rightsDtos);
		mv.setViewName("/users/addUser");
		UserDto userDto = userService.findUserByUserId(userId);
		mv.addObject("user", userDto);
		return mv;
	}

	@RequestMapping(value = "/listUsersRoles.htm", method = RequestMethod.GET)
	public ModelAndView listUsersRoles() {
		List<RightsDto> rightsDtos = rightsService.getAllRights();
		ModelAndView modelAndView = new ModelAndView();
		List<UserDto> allUsers = userService.getAllUsers();
		modelAndView.addObject("users", allUsers);
		modelAndView.addObject("rights", rightsDtos);
		modelAndView.setViewName("/users/listUsersRoles");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/updateUserRights.htm", method = RequestMethod.POST)
	public String updateUserRights(@RequestBody String data) {

		String toReturn = "false";

		try {
			JSONObject jsonObject = new JSONObject(data);
			int userId = ( Integer ) jsonObject.get( "userId" );
			JSONArray rightIds = ( JSONArray ) jsonObject.get( "userRightIds" );

			UserDto userDto = userService.findUserByUserId( userId );
			userDto.getUserRights().clear();
			
			for( int index = 0; index < rightIds.length(); index++ ) {
				int rightId = Integer.parseInt( rightIds.get( index ) + "" );
				UserRightsDto rightDto = new UserRightsDto();
				rightDto.setRight( rightsService.findRightById( rightId ) );
				rightDto.setUser( userDto );
				userDto.getUserRights().add(rightDto);
			}
			
			toReturn = userService.updateUser(userDto) + "";
		} catch(Exception e) {
			log.error( e.getMessage(), e );
		}
		return toReturn;
	}
}