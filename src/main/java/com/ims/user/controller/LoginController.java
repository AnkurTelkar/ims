/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.user.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ims.user.dto.UserDto;
import com.ims.user.service.UserService;
import com.ims.util.dto.DashboardDto;
import com.ims.util.service.DashboardService;

@Controller
@SessionAttributes("loggedInUser")
public class LoginController {
	
	Logger logger = LoggerFactory.getLogger( this.getClass().getName() );

	@Autowired
	private UserService userService;
	@Autowired
	private DashboardService dashboardService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView preLogin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		return mv;
	}

	@RequestMapping(value = "/postLogin.htm", method = RequestMethod.POST)
	public ModelAndView postLogin(@RequestParam Map<String, String> requestMap, HttpSession session) {
		ModelAndView mv = postLogin(session);
		if ((Boolean) mv.getModel().get("userFound")) {
			return mv;
		}

		String loginId = requestMap.get("username");
		String password = requestMap.get("password");
		String viewName = "login";
		String message = "Invalid loginId and/or password.";
		
		boolean loginStatus = userService.doLogin(loginId, password);
		logger.debug("Status is  = "+ loginStatus );

		List<UserDto> userDtoList = userService.getAllUsers();

		if (loginStatus) {
			UserDto user = userDtoList.get(0);
			mv = new ModelAndView();
			viewName = "dashboard";
			message = "Logged in successfully.";
			session.setAttribute("loggedInUser", user);
			populateDashboardDto(mv);
			mv.setViewName("dashboard");
			return mv;

		} else {
			mv = new ModelAndView();
			mv.setViewName(viewName);
			mv.addObject("message", message);
			mv.addObject("loginId", loginId);
			mv.addObject("password", password);
			return mv;
		}
	}

	private void populateDashboardDto(ModelAndView mv) {
		DashboardDto dashboardDto = dashboardService.getDashboardData();
		mv.addObject( "dashboardDto" , dashboardDto );
	}

	@RequestMapping(value = { "/postLogin.htm", "/login.htm" }, method = RequestMethod.GET)
	public ModelAndView postLogin(HttpSession session) {
		UserDto user = (UserDto) session.getAttribute("loggedInUser");
		boolean userFound = false;
		String viewName = "login";
		ModelAndView mv = new ModelAndView();
		if (user != null) {
			viewName = "dashboard";
			userFound = true;
			populateDashboardDto(mv);
		}

		
		mv.setViewName(viewName);
		mv.addObject("userFound", userFound);
		return mv;
	}

	@RequestMapping(value = "/logout.htm", method = { RequestMethod.GET, RequestMethod.POST })
	public static ModelAndView logout(HttpSession session, SessionStatus status) {
		status.setComplete();
		String viewName = "login";
		ModelAndView mv = new ModelAndView();
		mv.setViewName(viewName);
		mv.addObject("message", "User logged out successfully");
		return mv;
	}
}
