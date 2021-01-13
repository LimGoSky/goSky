package com.example.demo.pojo;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long id;

    @NotNull(message ="用户名不能为空", groups = ADD.class)
    @Size(min = 3, max = 5, message = "用户名长度为{min}-{max}之间")
    private String username;

    @NotNull(message ="昵称不能为空", groups = Select.class)
    private String name;

    @NotNull(message ="密码不能为空", groups = ADD.class)
    private String password;

    @Email(message = "邮箱格式不合法", groups = Select.class)
    private String email;

    @Gender
    private String gender;
}