package com.teaShop.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teaShop.bean.SysUser;
import com.teaShop.service.SysUserService;
import com.teaShop.utils.Const;
import com.teaShop.utils.MD5Util;
import com.teaShop.vo.Message;


@Controller
@RequestMapping("/user")
public class SysUserController {
	private SysUserService service;
	MD5Util md5 = new MD5Util();
	final static Logger logger = LoggerFactory.getLogger(SysUserController.class);
	Message message = null;

	public SysUserService getServices() {
		return service;
	}

	@Autowired
	public void setServices(SysUserService service) {
		this.service = service;
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 *            返回json数据
	 * @return
	 */
	@RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		try {
			String name = request.getParameter("name").trim();
			String password = request.getParameter("password").trim();
			password = MD5Util.getMd5(password);
			boolean isLogin = false;
			message = new Message();
			if (name == null || name.equals("") || password == null || password.equals("")) {
				message.setFlag(Const.NOT_OK);
				message.setMessage(Const.NULL_NAME_OR_PASSWORD);
			} else {
				List<SysUser> userList = service.getUserByUserName(name);
				if (userList == null || userList.size() == 0) {
					message.setFlag(Const.NOT_OK);
					message.setMessage(Const.NAME_PASSWORD_MESSAGE);
				} else {
					for (SysUser user : userList) {
						if (user.getPassword().equals(password)) {
							HttpSession session = request.getSession();
							session.setAttribute("user", user);
							isLogin = true;
							break;
						}
					}
					if (isLogin == true) {
						message.setFlag(Const.OK);
						message.setMessage(Const.NORMAL);
						logger.info(name + ":登录成功");
					} else {
						message.setFlag(Const.NOT_OK);
						message.setMessage(Const.NAME_PASSWORD_MESSAGE);
					}
				}
			}
			response.getWriter().println(JSONObject.toJSON(message));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 *            返回json数据
	 * @return
	 */
	@RequestMapping(value = "/mobileLogin", produces = "text/html;charset=UTF-8")
	public void loginMobileUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			String name = request.getParameter("name").trim();
			String password = request.getParameter("password").trim();
			boolean isLogin = false;
			message = new Message();

			if (name == null || name.equals("") || password == null || password.equals("")) {
				message.setFlag(Const.NOT_OK);
				message.setMessage(Const.NULL_NAME_OR_PASSWORD);
			} else {
				List<SysUser> userList = service.getUserByUserName(name);
				password = MD5Util.getMd5(password);
				if (userList == null || userList.size() == 0) {
					message.setFlag(Const.NOT_OK);
					message.setMessage(Const.NAME_PASSWORD_MESSAGE);
				} else {
					for (SysUser user : userList) {
						if (user.getPassword().equals(password)) {
							HttpSession session = request.getSession();
							session.setAttribute("user", user);
							isLogin = true;
							break;
						}
					}
					if (isLogin == true) {
						message.setFlag(Const.OK);
						message.setMessage(Const.NORMAL);
					} else {
						message.setFlag(Const.NOT_OK);
						message.setMessage(Const.NAME_PASSWORD_MESSAGE);
					}
				}
			}
			response.getWriter().println(JSONObject.toJSON(message));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 *            返回json数据
	 * @return
	 */
	@RequestMapping(value = "/mobileLoginOut", produces = "text/html;charset=UTF-8")
	public String mobileLoginOut(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		// 清除session
		session.invalidate();
		// 返回到登录界面
		return "mobile/login";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loginOut", produces = "text/html;charset=UTF-8")
	public String loginOut(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		// 清除session
		session.invalidate();
		// 返回到登录界面
		return "index";
	}

	@RequestMapping("/getCurrentUser")
	public void getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session.getAttribute("user");
		Message message = new Message();
		if (user != null) {
			message.setFlag(Const.OK);
			message.setData(user);
			message.setMessage(Const.NORMAL);
		} else {
			message.setFlag(Const.NOT_OK);
			message.setMessage(Const.LOGIN_TIMEOUT);
		}
		response.getWriter().print(JSONObject.toJSON(message));
	}

	/**
	 * 修改用户信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/updateUserInfo")
	public void updateUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session.getAttribute("user");
		String userName = request.getParameter("userName");
		String userDesc = request.getParameter("userDesc");
		String userEmail = request.getParameter("userEmail");
		String gender = request.getParameter("gender");
		user.setUserName(userName);
		user.setEmail(userEmail);
		user.setDescription(userDesc);
		user.setGender(Integer.parseInt(gender));
		user.setUpdateTime(new Date());
		Message message = new Message();
		int flag = service.updateUser(user);
		if (flag < 1) {
			message.setFlag(Const.UPDATE_FAIL);
			message.setMessage(Const.UPDATE_FAIL_MESSAGE);
		} else {
			session.setAttribute("user", user);
			message.setFlag(Const.OK);
			message.setData(user);
			message.setMessage(Const.NORMAL);
		}
		response.getWriter().print(JSONObject.toJSON(message));
	}

	/**
	 * 添加用户
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addUser")
	public void addUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			SysUser bean = (SysUser) session.getAttribute("user");
			SysUser user = new SysUser();
			String userName = request.getParameter("userName");
			message = new Message();
			String password = request.getParameter("password");
			String passwd = request.getParameter("passwd");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String desc = request.getParameter("desc");
			String userType = request.getParameter("userType");
			String companyId = request.getParameter("companyId");
			user.setUserName(userName);
			message = new Message();
			int count = service.getPhone(phone);
			if (count > 0) {
				message.setFlag(Const.NOT_OK);
				message.setMessage(Const.EXIST_PHONE);
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			}
			user.setCompanyId(Integer.parseInt(companyId));
			if (bean.getIsSystem() == 1) {
				user.setIsSystem(2);
			} else if (bean.getIsSystem() == 2) {
				user.setCompanyId(bean.getCompanyId());
				user.setIsSystem(3);
			} else {
				message.setFlag(Const.INSUFFICIENT_PERMISSIONS);
				message.setMessage(Const.INSUFFICIENT_PERMISSIONS_MESSAGE);
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			}
			if (!md5.getMd5(password).equals(md5.getMd5(passwd))) {
				message.setFlag(-1);
				message.setMessage("两次密码不一样");
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			}
			user.setPassword(md5.getMd5(password));
			user.setEmail(email);
			user.setPhone(phone);
			user.setDescription(desc);
			user.setCreateDate(new Date());
			user.setUserType(Integer.parseInt(userType));
			int result = service.addUser(user);
			if (result == 1) {
				message.setFlag(0);
			} else {
				message.setFlag(Const.INSERT_FAIL);
				message.setMessage(Const.INSERT_FAIL_MESSAGE);
			}
			response.getWriter().print(JSONArray.toJSON(message));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 用户注销
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/logOut")
	public String logOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 清除session
		session.invalidate();
		// 返回到登录界面
		return "index";
	}

	/**
	 * 获取所有的用户信息
	 */
	@RequestMapping(value = "/blurryUser", produces = "text/html;charset=UTF-8")
	public void blurryUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			SysUser loginUser = (SysUser) request.getSession().getAttribute("user");
			String userName = request.getParameter("userName");
			String userDesc = request.getParameter("userDesc");
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			SysUser user = new SysUser();
			user.setUserName(userName);
			user.setDescription(userDesc);
			user.setUserId(loginUser.getUserId());
			user.setIsSystem(loginUser.getIsSystem());
			user.setCompanyId(loginUser.getCompanyId());
			user.setPageNumber((pageNumber - 1) * pageSize);
			user.setPageSize(pageSize);
			JSONObject obj = service.blurryUser(user);
			response.getWriter().print(JSONArray.toJSON(obj));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证添加用户是否存在
	 */
	@RequestMapping("/validateUser")
	public void validateUser(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		SysUser user = service.getUser(name);
		JSONObject result = new JSONObject();
		if (user != null) {
			result.put("info", "用户存在");
		} else {
			result.put("info", "用户不存在");
		}
		try {
			response.getWriter().print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除用户
	 */
	@RequestMapping("/deleteUser")
	public void removeUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			int userId = Integer.parseInt(request.getParameter("userId"));
			int result = service.deleteUser(userId);
			message = new Message();
			if (result == 1) {
				message.setFlag(0);
			} else {
				message.setFlag(-1);
				message.setMessage("删除失败");
			}
			response.getWriter().print(JSON.toJSON(message));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 编辑用户
	 */
	@RequestMapping("/updateUser")
	public void updateUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			int userId = Integer.parseInt(request.getParameter("userId"));
			String userName = request.getParameter("userName");
			SysUser user = service.getUserById(userId);
			message = new Message();
			String userDesc = request.getParameter("userDesc");
			String userEmail = request.getParameter("userEmail");
			String userPhone = request.getParameter("userPhone");
			int count = service.getPhone(userPhone);
			if (count > 0 && !user.getPhone().equals(userPhone)) {
				message.setFlag(-1);
				message.setMessage("用户名或电话重复");
			} else {
				user.setUserName(userName);
				user.setDescription(userDesc);
				user.setEmail(userEmail);
				user.setPhone(userPhone);
				user.setUpdateTime(new Date());
				int flag = service.updateUser(user);
				if (flag < 1) {
					message.setFlag(-1);
					message.setMessage("更新用户信息失败");
				} else {
					message.setFlag(0);
				}
			}
			response.getWriter().print(JSONObject.toJSON(message));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 编辑用户
	 */
	@RequestMapping("/resetUserPassword")
	public void resetUserPassword(HttpServletRequest request, HttpServletResponse response) {
		try {
			int userId = Integer.parseInt(request.getParameter("userId"));
			SysUser user = new SysUser();
			message = new Message();
			user.setUserId(userId);
			user.setPassword(md5.getMd5(md5.getMd5(Const.resetPassword)));
			int flag = service.resetUserPassword(user);
			if (flag < 1) {
				message.setFlag(-1);
				message.setMessage("更新用户信息失败");
				response.getWriter().print(JSONObject.toJSON(message));
				return;
			} else {
				message.setFlag(0);
				response.getWriter().print(JSONObject.toJSON(message));
				return;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据id获取用户
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getUserById")
	public void getUserById(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userId = request.getParameter("userId");
			int id = Integer.parseInt(userId);
			SysUser user = service.getUserById(id);
			message = new Message();
			if (user == null) {
				message.setFlag(-1);
				message.setMessage("获取用户信息失败");
				response.getWriter().print(JSONObject.toJSON(message));
				return;
			} else {
				message.setFlag(0);
				message.setData(user);
				response.getWriter().print(JSONObject.toJSON(message));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据name获取用户
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getUserByName")
	public void getUserByName(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		SysUser user = service.getUser(name);
		List<SysUser> users = new ArrayList<SysUser>();
		users.add(user);
		try {
			response.getWriter().print(JSONArray.toJSON(users));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据name获取用户
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/changePassword")
	public void changePassword(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			SysUser user = (SysUser) session.getAttribute("user");
			String pass = request.getParameter("pass");
			String newPasswd = request.getParameter("passNew");
			String newPasswdA = request.getParameter("passAgain");
			message = new Message();
			if (newPasswd != null && newPasswdA != null) {
				if (!md5.getMd5(newPasswd).equals(md5.getMd5(newPasswdA))) {
					message.setFlag(Const.NOT_OK);
					message.setMessage("两次密码不一样");
				} else {
					SysUser bean = service.getUserById(user.getUserId());
					if (!md5.getMd5(pass).equals(user.getPassword())) {
						message.setFlag(Const.NOT_OK);
						message.setMessage("原密码不对");
					} else {
						user.setPassword(md5.getMd5(newPasswd));
						user.setUpdateTime(new Date());
						int flag = service.resetUserPassword(user);
						if (flag < 1) {
							message.setFlag(Const.NOT_OK);
							message.setMessage("更细密码失败");
							return;
						} else {
							session.setAttribute("user", user);
							message.setFlag(0);
						}
					}
				}
			} else {
				message.setFlag(Const.NOT_OK);
				message.setMessage("请确认密码");
			}
			response.getWriter().print(JSONArray.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送修改密码短信验证码
	 */
//	@RequestMapping("/sendCode")
//	public void sendCode(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			message = new Message();
//			String phone = request.getParameter("phone").trim();
//			if (phone == null || phone.isEmpty()) {
//				message.setFlag(Const.NOT_OK);
//				message.setMessage(Const.NULL_PHONE);
//			} else {
//				int userCount = service.getUserCountByPhone(phone);
//				if (userCount < 1) {
//					message.setFlag(Const.NOT_OK);
//					message.setMessage(Const.NO_EXIST_PHONE);
//				} else {
//					SmsMessage msg = SmsUtil.sendMessage(phone);
//					logger.warn(msg.toString());
//					if (msg.getFlag() == 200) {
//						SysUser user = new SysUser();
//						user.setPhone(phone);
//						user.setIdentifyCode(msg.getCode());
//						user.setUpdateTime(new Date());
//						int updateCount = service.updateIdentifyCode(user);
//						if (updateCount > 0) {
//							message.setFlag(Const.OK);
//							message.setMessage(msg.getCode());
//						} else {
//							message.setFlag(Const.NOT_OK);
//							message.setMessage(Const.SEND_FIELD);
//						}
//					} else {
//						message.setFlag(Const.NOT_OK);
//						message.setMessage(Const.SEND_FIELD);
//					}
//				}
//			}
//			response.getWriter().print(JSONObject.toJSON(message));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 通过电话修改密码
//	 *
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping("/changePasswordByPhone")
//	public void changePasswordByPhone(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			String phone = request.getParameter("phone").trim();
//			String code = request.getParameter("code").trim();
//			String password = request.getParameter("password").trim();
//			message = new Message();
//
//			if (phone == null || phone.isEmpty()) {
//				message.setFlag(Const.NOT_OK);
//				message.setMessage(Const.NULL_PHONE);
//			} else if (code == null || code.isEmpty()) {
//				message.setFlag(Const.NOT_OK);
//				message.setMessage(Const.NULL_PHONE);
//			} else if (password == null || password.isEmpty()) {
//				message.setFlag(Const.NOT_OK);
//				message.setMessage(Const.NULL_PHONE);
//			} else {
//				List<SysUser> users = service.getUserByPhone(phone);
//				if (users == null || users.size() == 0) {
//					message.setFlag(Const.NOT_OK);
//					message.setMessage(Const.NO_EXIST_PHONE);
//				} else {
//					SysUser user = users.get(0);
//					if (!user.getIdentifyCode().equals(code)) {
//						message.setFlag(Const.NOT_OK);
//						message.setMessage(Const.WRONG_CODE);
//					} else if (new Date().getTime() - user.getUpdateTime().getTime() > Const.TIMEOUT_NUMBER) {
//						message.setFlag(Const.NOT_OK);
//						message.setMessage(Const.CODE_TIMEOUT);
//					} else {
//						user.setPassword(MD5Util.getMd5(password));
//						user.setUpdateTime(new Date());
//						int changeCount = service.changePassword(user);
//
//						if (changeCount > 0) {
//							message.setFlag(Const.OK);
//							message.setMessage(Const.CHANGE_PASSWORD_SUCCESS);
//						} else {
//							message.setFlag(Const.NOT_OK);
//							message.setMessage(Const.CHANGE_PASSWORD_FAILD);
//						}
//					}
//				}
//			}
//			response.getWriter().print(JSONObject.toJSON(message));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 发送注册短信验证码
//	 */
//	@RequestMapping("/sendRegistCode")
//	public void sendRegistCode(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			message = new Message();
//			String phone = request.getParameter("phone").trim();
//			if (phone == null || phone.isEmpty()) {
//				message.setFlag(Const.NOT_OK);
//				message.setMessage(Const.NULL_PHONE);
//			} else {
//				int userCount = service.getUserCountByPhone(phone);
//				if (userCount > 0) {
//					message.setFlag(Const.NOT_OK);
//					message.setMessage(Const.EXIST_PHONE);
//				} else {
//					SmsMessage msg = SmsUtil.sendMessage(phone);
//					if (msg.getFlag() == 200) {
//						HttpSession session = request.getSession();
//						session.setAttribute(phone, msg.getCode());
//						message.setFlag(Const.OK);
//						message.setMessage(msg.getCode());
//					} else {
//						message.setFlag(Const.NOT_OK);
//						message.setMessage(Const.SEND_FIELD);
//					}
//				}
//			}
//			response.getWriter().print(JSONObject.toJSON(message));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 注册用户
//	 */
//	@RequestMapping("/registUser")
//	public void registUser(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			message = new Message();
//			String userName = request.getParameter("userName").trim();
//			String phone = request.getParameter("phone").trim();
//			String code = request.getParameter("code").trim();
//			String password = request.getParameter("password").trim();
//
//			if (userName == null || userName.isEmpty()) {
//				message.setFlag(Const.NOT_OK);
//				message.setMessage(Const.NULL_USERNAME);
//			} else if (phone == null || phone.isEmpty()) {
//				message.setFlag(Const.NOT_OK);
//				message.setMessage(Const.NULL_PHONE);
//			} else if (code == null || code.isEmpty()) {
//				message.setFlag(Const.NOT_OK);
//				message.setMessage(Const.NULL_PHONE);
//			} else if (password == null || password.isEmpty()) {
//				message.setFlag(Const.NOT_OK);
//				message.setMessage(Const.NULL_PHONE);
//			} else {
//				HttpSession session = request.getSession();
//				String oldCode = (String) session.getAttribute(phone);
//				if (oldCode == null || oldCode.isEmpty()) {
//					message.setFlag(Const.NOT_OK);
//					message.setMessage(Const.NO_EXIST_CODE);
//				} else if (!oldCode.equals(code)) {
//					message.setFlag(Const.NOT_OK);
//					message.setMessage(Const.WRONG_CODE);
//				} else {
//					SysUser user = new SysUser();
//					user.setUserName(userName);
//					user.setPhone(phone);
//					user.setPassword(MD5Util.getMd5(password));
//					user.setCreateDate(new Date());
//					user.setUpdateTime(new Date());
//					int insertCount = service.registUser(user);
//					if (insertCount < 1) {
//						message.setFlag(Const.NOT_OK);
//						message.setMessage(Const.WRONG_NETWORK);
//					} else {
//						message.setFlag(Const.OK);
//						message.setMessage(Const.REIGST_SUCCESS);
//						session.removeAttribute(phone);
//					}
//				}
//			}
//			response.getWriter().print(JSONObject.toJSON(message));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 得到用户信息
	 */
	@RequestMapping("/getPersonInfo")
	public void getPersonInfo(HttpServletRequest request, HttpServletResponse response) {
		message = new Message();
		try {
			HttpSession session = request.getSession();
			SysUser user = (SysUser) session.getAttribute("user");

			// if(user == null) {
			// message.setFlag(Const.NOT_OK);
			// message.setMessage(Const.LOGIN_TIMEOUT);
			// } else {
			message.setData(user);
			// }
			response.getWriter().print(JSONObject.toJSON(message));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改用户信息
	 */
	@RequestMapping("/changePersonInfo")
	public void changePersonInfo(HttpServletRequest request, HttpServletResponse response) {
		message = new Message();
		try {
			HttpSession session = request.getSession();
			SysUser user = (SysUser) session.getAttribute("user");

			// if(user == null) {
			// message.setFlag(Const.NOT_OK);
			// message.setMessage(Const.LOGIN_TIMEOUT);
			// } else {
			String userName = request.getParameter("userName");
			String gender = request.getParameter("gender");

			user.setUserName(userName);
			user.setGender(Integer.parseInt(gender));
			int changeCount = service.updateUser(user);

			if (changeCount == 0) {
				message.setFlag(Const.NOT_OK);
				message.setMessage(Const.WRONG_NETWORK);
			}
			// }
			response.getWriter().print(JSONObject.toJSON(message));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对用户的电话号码进行校验
	 * */
	@RequestMapping(value = "/checkPhone")
	public void checkPhone(HttpServletRequest request, HttpServletResponse response) {
		message = new Message();
		try {
			String phone = request.getParameter("phone");
			int count = service.getPhone(phone);
			if (count > 0) {
				message.setFlag(Const.NOT_OK);
				message.setMessage(Const.EXIST_PHONE);
			} else {
				message.setFlag(Const.OK);
				message.setMessage(Const.NORMAL);
			}
			response.getWriter().print(JSONObject.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对用户的邮箱进行校验
	 * */
	@RequestMapping(value = "/checkMail", method = RequestMethod.GET)
	public void checkMail(HttpServletRequest request, HttpServletResponse response) {
		message = new Message();
		try {
			String email = request.getParameter("email");
			List<String> mailList = service.getMailList();
			for (String string : mailList) {

				if (email.equals(string)) {
					message.setFlag(0);
					message.setMessage(Const.EXIST_PHONE);

					response.getWriter().print(JSONObject.toJSON(message));
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改用户头像
	 */
	@RequestMapping("/changeUserIcon")
	public void changeUserIcon(HttpServletRequest request, HttpServletResponse response) {
		String logPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "logos"
				+ File.separator + "userlogo" + File.separator;
		String path = "../logos/userlogo/";
		File fp = new File(logPath);
		// 创建目录
		if (!fp.exists()) {
			fp.mkdirs();// 目录不存在的情况下，创建目录。
		}
		try {
			message = new Message();
			String imageStr = request.getParameter("imageData");
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			int userId = user.getUserId();
// || imageStr.isEmpty()s
			if (imageStr == null) {
				message.setFlag(Const.NOT_OK);
				message.setMessage(Const.NULL_INFOMATION);
			} else {
				int index = imageStr.lastIndexOf(",");
				imageStr = imageStr.substring(index + 1, imageStr.length());

				BASE64Decoder decoder = new BASE64Decoder();
				byte[] imageData = decoder.decodeBuffer(imageStr);
				// 处理数据
				for (int i = 0; i < imageData.length; ++i) {
					if (imageData[i] < 0) {
						imageData[i] += 256;
					}
				}
				String fileName = "user_" + userId + ".png";
				OutputStream out = new FileOutputStream(logPath + fileName);
				out.write(imageData);
				out.flush();
				out.close();

				SysUser u = new SysUser();
				u.setUserId(userId);
				u.setIcon(path + fileName);
				int count = service.updateUser(u);

				if (count == 0) {
					message.setFlag(Const.NOT_OK);
					message.setMessage(Const.NULL_DATA_MESSAGE);
				} else {
					user.setIcon(path + fileName);
					request.getSession().setAttribute("user", user);
				}
			}
			response.getWriter().print(JSONArray.toJSON(message));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
