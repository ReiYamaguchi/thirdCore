package com.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.DaoInterface;
import com.form.*;


@Service
@ComponentScan("com.dao")
public class LoginService implements LoginInterface  {

	
	@Autowired
	public DaoInterface dao;

	ModelAndView model = new ModelAndView();

	public Object Shyouzi(FormLogin form) {
		String text = "　";
		// 表示設定
		model.addObject("isEven", true);
		// SQL生成
		String[] arrayed = { form.getName(), "select count(*) from base.user_login where user_id='" + form.getName()
				+ "' and user_password='" + form.getPassword() + "';" };
		switch (dao.select(arrayed)) {

		case 0:
			text = "I failed to login. Please login again";
			break;
		case 1:
			return "redirect:http://localhost:8080/login/home";
		default:
			break;
		}
		model.addObject("test", "ログイン");
		model.addObject("result",text);
		model.setViewName("login.html");
		return model;
	}

	@Override
	public Object Snew(FormLogin from) {
		String text = null;

		// SQL生成
		String[] arrayed = { from.getName(),
				"insert into userlogin.context values (4,'" + from.getName() + "','" + from.getPassword() + "');" };

		switch (dao.alter(arrayed)) {
		case 0:
			text = "失敗";
			break;
		case 1:
			text = "成功";
		default:
			break;
		}
		model.addObject("test", "新規登録");
		model.addObject("result", text);
		model.setViewName("login.html");
		return model;
	}
}
