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
public class result {
    @TableId(type = IdType.AUTO)
    private Integer resultkey;
    private Integer resumekey;
    private String resumename;
    private String parseresult;
    private String state;
    private Timestamp createdtime;
}