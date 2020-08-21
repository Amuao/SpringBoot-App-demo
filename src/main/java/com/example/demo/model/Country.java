package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Log
@NoArgsConstructor
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "country_code" ,nullable = false,unique = true)
    @NotBlank(message = "代码不能为空")
    private String countryCode;

    @Column(name = "country_name",nullable = false )
    @NotBlank(message = "名称不能为空")
    private String countryName;

    @Column(name = "is_deleted",columnDefinition ="tinyint default 0")
    private Byte isDeleted = 0;

    @Column(name = "create_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @CreatedDate
    private Date createTime;

    @Column(name = "update_time",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @CreatedDate
    private Date updateTime;
}

