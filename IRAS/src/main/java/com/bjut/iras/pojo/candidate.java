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
public class candidate {
    @TableId(type = IdType.AUTO)
    private Integer candkey;

    private Integer candage;
    private Short candgender;
    private String candemail;
    private String candtel;
    private String candloc;
    private String candpo;
    private String candschool;
    private String candtitle;
    private String candjob;
    private String candskill;
    private String candaward;
    private String candeval;
    private String candtarget;

    private Integer candresume;
}
