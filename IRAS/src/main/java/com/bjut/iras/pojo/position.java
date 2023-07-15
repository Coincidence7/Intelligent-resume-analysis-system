package com.bjut.iras.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class position {
    @TableId(type = IdType.AUTO)
    private Integer positionkey;
    private String posname;
    private String possalary;
    private String title;
    private String sex;
    private String major;
    private String workcity;
    private String worktime;
    private String posskill;
    private String posresponsibility;
}
