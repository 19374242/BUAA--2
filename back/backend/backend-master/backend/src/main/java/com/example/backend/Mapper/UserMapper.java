package com.example.backend.Mapper;

import com.example.backend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
//    邮箱是否被占用
    @Select("select * from userdata where email like #{email}")
    User isEmailOccupy(String email);
//注册插入数据
    @Insert("insert into userdata(email,password,name,nickname,checkout) values(#{email},#{password},#{name},#{nickname},#{checkout})")
    Integer insertUser(User user);
//    密码是否正确
    @Select("select password from userdata where email like #{email}")
    String emailForPassword(String email);
//邮箱找名字
    @Select("select name from userdata where email like #{email}")
    String emailForName(String email);
//    邮箱找昵称
    @Select("select nickname from userdata where email like #{email}")
    String emailForNickname(String email);
//    邮箱改密码
    @Update("update userdata set password=#{newPassword} where email=#{email}")
    Integer emailChangePassword(String email,String newPassword);
}
