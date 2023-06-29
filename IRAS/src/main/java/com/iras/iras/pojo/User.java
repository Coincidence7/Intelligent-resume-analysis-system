package com.iras.iras.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(type = IdType.AUTO, value = "userKey")
    private Integer userKey;
    @TableField("userName")
    private String userName;
    @TableField("userPwd")
    private String userPwd;
    @TableField("avatarURI")
    private String avatarURI;
}
