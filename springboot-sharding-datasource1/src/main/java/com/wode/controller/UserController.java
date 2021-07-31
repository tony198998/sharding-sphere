package com.wode.controller;

import com.wode.entity.User;
import com.wode.service.UserService;
import com.wode.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public Object list() {
		return userService.list();
	}
	
	@GetMapping("/add")
	public Object add() {
		for (long i = 0; i < 4; i++) {
			User user = new User();
		//	user.setId(i);
			user.setCity("深圳");
			user.setName("李四");
			Date tomorrow = DateUtils.dateToSubDays(new Date(), 2);
			user.setCreateTime(tomorrow);
			user.setUpdateTime(tomorrow);
			userService.add(user);
		}
		return "success";
	}
	
	@GetMapping("/users/{id}")
	public Object get(@PathVariable Long id) {
		return userService.findById(id);
	}


	/**
	 * 查询的话，每个表都会去查询
	 *
	 * @param name
	 * @return
	 */
	@GetMapping("/users/query")
	public Object get(String name) {
		return userService.findByName(name);
	}
	
}
