package com.gloria.miaosha.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.gloria.miaosha.domain.User;

@Mapper
/**
 * 添加了@Mapper注解之后这个接口在编译时会生成相应的实现类
 *
 * 需要注意的是：这个接口中不可以定义同名的方法，因为会生成相同的id
 * 也就是说这个接口是不支持重载的
 */
public interface UserDao {
	@Select("select * from t_user where id=#{id}")//@Param("id")进行引用
	public User getById(@Param("id") int id);

	@Insert("insert into t_user(id,name) values(#{id},#{name})")  //id为自增的，所以可以不用设置id
	public void insert(User user);
}
