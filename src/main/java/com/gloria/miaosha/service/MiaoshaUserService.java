package com.gloria.miaosha.service;

import com.gloria.miaosha.dao.MiaoshaUserDao;
import com.gloria.miaosha.domain.MiaoshaUser;
import com.gloria.miaosha.exception.GlobalException;
import com.gloria.miaosha.redis.MiaoshaUserKey;
import com.gloria.miaosha.redis.RedisService;
import com.gloria.miaosha.result.CodeMsg;
import com.gloria.miaosha.util.MD5Util;
import com.gloria.miaosha.util.UUIDUtil;
import com.gloria.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {
	public static final String COOKIE1_NAME_TOKEN="token";
	
	@Autowired
	MiaoshaUserDao miaoshaUserDao;
	@Autowired
	RedisService redisService;
	/**
	 * 根据id取得对象，先去缓存中取
	 * @param id
	 * @return
	 */
	public MiaoshaUser getById(long id) {
		//1.取缓存	---先根据id来取得缓存
		MiaoshaUser user=redisService.get(MiaoshaUserKey.getById, ""+id, MiaoshaUser.class);
		//能再缓存中拿到
		String dbPass=user.getPassword();
		String dbSalt=user.getSalt();
		System.out.println("getById"+RedisService.beanToString(user));
		System.out.println("getById:dbPass:"+dbPass+"   dbSalt:"+dbSalt);

		if(user!=null) {
			return user;
		}
		//2.缓存中拿不到，那么就去取数据库
		user=miaoshaUserDao.getById(id);
		//3.设置缓存
		if(user!=null) {
			redisService.set(MiaoshaUserKey.getById, ""+id, user);
		}
		return user;
	}
	/**
	 * 注意数据修改时候，保持缓存与数据库的一致性
	 * 需要传入token
	 * @param id
	 * @return
	 */
	public boolean updatePassword(String token,long id,String passNew) {
		//1.取user对象，查看是否存在
		MiaoshaUser user=getById(id);
		if(user==null) {
			throw new GlobalException(CodeMsg.MOBILE_NOTEXIST);
		}
		//2.更新密码
		MiaoshaUser toupdateuser=new MiaoshaUser();
		toupdateuser.setId(id);
		toupdateuser.setPassword(MD5Util.inputPassToDbPass(passNew, user.getSalt()));
		miaoshaUserDao.update(toupdateuser);
		//3.更新数据库与缓存，一定保证数据一致性，修改token关联的对象以及id关联的对象
		redisService.delete(MiaoshaUserKey.getById, ""+id);
		//不能直接删除token，删除之后就不能登录了，所以只能是修改
		user.setPassword(toupdateuser.getPassword());
		redisService.set(MiaoshaUserKey.token, token,user);
		return true;
	}
	
	/**
	 * 从缓存里面取得值，取得value
	 * @return
	 */
	public MiaoshaUser getByToken(String token,HttpServletResponse response) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		
		MiaoshaUser user=redisService.get(MiaoshaUserKey.token, token,MiaoshaUser.class);
		// 再次请求时候，延长有效期
		// 重新设置缓存里面的值，使用之前cookie里面的token
		if(user!=null) {
			addCookie(user,token,response);
		}
		System.out.println("@MiaoshaUserService-getByToken  user:"+user);
		return user;
	}
	
	public String loginString(HttpServletResponse response,LoginVo loginVo) {
		if(loginVo==null) {
			return CodeMsg.SERVER_ERROR.getMsg();
		}
		//经过了依次MD5的密码
		String mobile=loginVo.getMobile();
		String formPass=loginVo.getPassword();
		//判断手机号是否存在
		MiaoshaUser user=getById(Long.parseLong(mobile));
		//查询不到该手机号的用户
		if(user==null) {
			return CodeMsg.MOBILE_NOTEXIST.getMsg();
		}
		//手机号存在的情况，验证密码，获取数据库里面的密码与salt去验证
		//111111--->e5d22cfc746c7da8da84e0a996e0fffa
		String dbPass=user.getPassword();
		String dbSalt=user.getSalt();
		System.out.println("dbPass:"+dbPass+"   dbSalt:"+dbSalt);
		//验证密码，计算二次MD5出来的pass是否与数据库一致
		String tmppass=MD5Util.formPassToDBPass(formPass, dbSalt);
		System.out.println("formPass:"+formPass);
		System.out.println("tmppass:"+tmppass);
		if(!tmppass.equals(dbPass)) {
			return CodeMsg.PASSWORD_ERROR.getMsg();
		}
		//生成cookie
		String token = UUIDUtil.uuid();
		addCookie(user,token,response);
//		return CodeMsg.SUCCESS;
		return token;
	}
	
	public CodeMsg login(HttpServletResponse response,LoginVo loginVo) {
		if(loginVo==null) {
			return CodeMsg.SERVER_ERROR;
		}
		//经过了依次MD5的密码
		String mobile=loginVo.getMobile();
		String formPass=loginVo.getPassword();
		//判断手机号是否存在
		MiaoshaUser user=getById(Long.parseLong(mobile));
		//查询不到该手机号的用户
		if(user==null) {
			return CodeMsg.MOBILE_NOTEXIST;
		}
		//手机号存在的情况，验证密码，获取数据库里面的密码与salt去验证
		//111111--->e5d22cfc746c7da8da84e0a996e0fffa
		String dbPass=user.getPassword();
		String dbSalt=user.getSalt();
		System.out.println("dbPass:"+dbPass+"   dbSalt:"+dbSalt);
		//验证密码，计算二次MD5出来的pass是否与数据库一致
		String tmppass=MD5Util.formPassToDBPass(formPass, dbSalt);
		System.out.println("formPass:"+formPass);
		System.out.println("tmppass:"+tmppass);
		if(!tmppass.equals(dbPass)) {
			return CodeMsg.PASSWORD_ERROR;
		}
		//生成cookie
		String token = UUIDUtil.uuid();
		addCookie(user,token,response);
		return CodeMsg.SUCCESS;
		
	}
	/**
	 *
	 * 添加/更新cookie
	 * 将MiaoshaUserKey前缀+sessionId（sessionId即token）组成了一个完整的Key，
	 * 例如：“MiaoshaUserKey:tke67ad5b4ebbd4aef8e8bb36dab70c4fc”,
	 * 其中MiaoshaUserKey前缀=“MiaoshaUserKey:tk”,token=“e67ad5b4ebbd4aef8e8bb36dab70c4fc”，拼接成Key,
	 * 和对应的用户信息user（user对象信息会转换为字符串类型）一起存入Redis 缓存中。
	 * 此token对应的是哪一个用户,将我们的私人信息存放到一个第三方的缓存中，
	 * 当访问其他页面的时候，就可以从cookie中获取 token,再访问redis 拿到用户信息来判断登录情况了。

	 */
	public void addCookie(MiaoshaUser user,String token,HttpServletResponse response) {
		// 可以用老的token，不用每次都生成cookie，可以用之前的
		System.out.println("uuid:" + token);
		// 将token写到cookie当中，然后传递给客户端
		// 此token对应的是哪一个用户,将我们的私人信息存放到一个第三方的缓存中
		// prefix:MiaoshaUserKey.token key:token value:用户的信息 -->以后拿到了token就知道对应的用户信息。
		// MiaoshaUserKey.token-->
		redisService.set(MiaoshaUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKIE1_NAME_TOKEN, token);
		// 设置cookie的有效期，与session有效期一致
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
		// 设置网站的根目录
		cookie.setPath("/");
		// 需要写到response中
		response.addCookie(cookie);
	}
}
