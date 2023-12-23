package com.dwq.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dwq.entity.BaseDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("dwq_admin")
@AllArgsConstructor
@NoArgsConstructor
public class Account implements BaseDate {
    @TableId(type= IdType.AUTO)
    Integer id;
    String username;
    String password;
    String email;
    String role;
    Date registerTime;
}

