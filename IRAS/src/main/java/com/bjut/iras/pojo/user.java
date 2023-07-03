package com.bjut.iras.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class user {
    @TableId(type = IdType.AUTO)
    private Integer userkey;

    private Integer userid;
    private String username;
    private String userpwd;
    private String avataruri;
}
