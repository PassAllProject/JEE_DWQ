package com.dwq.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dwq.entity.dto.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    @Select("SELECT * FROM dwq_admin WHERE username = #{username}")
    Account findByUsername(String username);
    @Update("update dwq_admin set username = #{username},email=#{email},role=#{role} WHERE id=${id} ")
    boolean updateA(Account account);
}