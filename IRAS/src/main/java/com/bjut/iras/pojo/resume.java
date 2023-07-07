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
public class resume {
    @TableId(type = IdType.AUTO)
    private Integer resumekey;
    private String resumename;
    private String path;
    private String filename;
    private Timestamp uploadtime;
    private String state;
}