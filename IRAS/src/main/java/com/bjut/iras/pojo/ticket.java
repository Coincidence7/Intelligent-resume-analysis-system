package com.bjut.iras.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ticket {
    @TableId(type = IdType.AUTO)
    private Integer ticketkey;

    private Integer jobtype;
    private Timestamp jobtime;
    private Integer jobstatus;
    private Integer invokerkey;
}