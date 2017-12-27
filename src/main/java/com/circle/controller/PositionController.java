package com.circle.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.circle.service.IDepartmentService;
import com.circle.service.IPositionService;
import com.circle.utils.json.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "position")
public class PositionController extends AbstractController {

	@Autowired
	private IDepartmentService departmentService;
	@Autowired
	private IPositionService positionService;

	@ResponseBody
	@RequestMapping(value = "findAllDepartment")
	public JsonReturn findAllDepartment() {
		return departmentService.findAllDepartment();
	}

	@ResponseBody
	@RequestMapping(value = "findPositionListInfo")
	public JsonReturn findPositionListInfo(@RequestParam int page, @RequestParam long deptId,
			@RequestParam String searchValue, HttpServletRequest request) {
		return positionService.findPositionListInfo(page, deptId, searchValue, acctName(request));
	}

	@ResponseBody
	@RequestMapping(value = "findPositionPage")
	public JsonReturn findPositionPage(@RequestParam int page, @RequestParam long deptId,
			@RequestParam String searchValue, HttpServletRequest request) {
		return positionService.findPositionPage(page, deptId, searchValue, acctName(request));
	}

	@ResponseBody
	@RequestMapping(value = "addPosition")
	public JsonReturn addPosition(@RequestParam long deptId, @RequestParam String name, @RequestParam String desc,
								  HttpServletRequest request) {
		return positionService.addPosition(deptId, name, desc, acctName(request));
	}

	@ResponseBody
	@RequestMapping(value = "deletePosition")
	public JsonReturn deletePosition(@RequestParam long id, HttpServletRequest request) {
		return positionService.deletePosition(id, acctName(request));
	}

	@ResponseBody
	@RequestMapping(value = "findPositionById")
	public JsonReturn findPositionById(@RequestParam long id, HttpSession httpSession) {
		return positionService.findPositionById(id);
	}

	@ResponseBody
	@RequestMapping(value = "modifyPosition")
	public JsonReturn modifyPosition(@RequestParam long id, @RequestParam long deptId, @RequestParam String name,
			@RequestParam String desc, HttpServletRequest request) {
		return positionService.modifyPosition(id, deptId, name, desc, acctName(request));
	}

}
