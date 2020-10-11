package life.majiang.community.community.mapper;

import life.majiang.community.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user(NAME,ACCOUNT_ID,TOKEN,GMT_MODIFIED,GMT_CREATE,avatar_url) values(#{name},#{accountId},#{token},#{gmtModified},#{gmtCreate},#{avatarUrl})")

    void insert(User user);

    @Select("select * from user where token=#{token}")

    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")

    User findById(@Param("id")Integer id);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId")String accountId);
    @Update("update user set NAME = #{name},TOKEN = #{token} , GMT_MODIFIED = #{gmtModified} , avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);
}
