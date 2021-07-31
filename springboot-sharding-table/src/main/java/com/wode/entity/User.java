package com.wode.entity;


import lombok.Data;
import java.util.Date;

/**
 * @description:
 * @author: jitao
 * @createDate: 2021/7/28
 */
@Data
public class User {

    private Long id;

    private String city = "";

    private String name = "";

    private Date createTime;

    private Date updateTime;
}
