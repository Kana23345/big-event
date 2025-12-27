package com.myproject.controller;


import com.myproject.pojo.Result;
import com.myproject.pojo.User;
import com.myproject.service.UserService;
import com.myproject.utils.JwtUtil;
import com.myproject.utils.Md5Util;
import com.myproject.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 * 处理用户相关的请求，包括用户注册、登录和获取用户信息
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     * 接收用户名和密码，验证参数后进行用户注册
     *
     * @param username 用户名，必须为5-16位非空白字符
     * @param password 密码，必须为5-16位非空白字符
     * @return Result 返回操作结果，成功返回Result.success()，失败返回错误信息
     */
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //（废弃）@validated替代了
//        if(!(username != null && username.length() >= 5 && username.length() <= 16 &&password!=null&& password.length() >= 5 && password.length() <= 16))
//            return Result.error("非法参数");
        //查询
        User u = userService.findByUserName(username);
        if (u == null) {
            //未占用
            //注册
            userService.register(username, password);
            return Result.success();
        } else {
            //占用，失败
            return Result.error("用户名已占用");
        }
    }

    /**
     * 用户登录接口
     * 验证用户名和密码，验证通过后生成JWT令牌返回
     *
     * @param username 用户名，必须为5-16位非空白字符
     * @param password 密码，必须为5-16位非空白字符
     * @return Result<String> 返回JWT令牌，登录失败返回错误信息
     */
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查用户

        User loginU = userService.findByUserName(username);
        //存在否
        if (loginU == null) {
            return Result.error("用户名错误");
        }
        //密码对否
        if (Md5Util.getMD5String(password).equals(loginU.getPassword())) {
            // 创建JWT令牌的载荷，包含用户ID和用户名
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginU.getId());
            claims.put("Username", loginU.getUsername());
            // 生成JWT令牌
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        } else {
            return Result.error("密码错误");
        }
    }

    /**
     * 获取用户信息接口
     * 从请求头Authorization中获取JWT令牌，解析后获取当前登录用户信息
     * <p>
     * //@param token JWT令牌，包含用户身份信息(废案)
     *
     * @return Result<User> 包含用户详细信息的响应结果
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
        //简单参数检索方式（废案）
//        // 解析JWT令牌获取用户信息
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        // 从令牌中获取用户名（存储时使用了"Username"键名）
//        String username = (String) map.get("Username");
//        // 根据用户名查询用户完整信息

        // 从ThreadLocal中获取用户信息
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("Username");

        // 根据用户名查询用户完整信息
        User user = userService.findByUserName(username);
        // 返回用户信息
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    /**
     * 更新用户头像接口
     * 用于更新当前登录用户的头像URL
     *
     * @param avatarUrl 头像图片的URL地址，必须是合法的URL格式
     * @return Result 返回操作结果，成功返回Result.success()，失败返回错误信息
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    /**
     * 更新用户密码接口
     * 用于修改当前登录用户的密码，需要验证原密码正确性
     *
     * @param map 包含旧密码、新密码和确认密码的参数集合
     * @return Result 返回操作结果，成功返回Result.success()，失败返回错误信息
     */
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> map) {
        String old_pwd = map.get("old_pwd");
        String new_pwd = map.get("new_pwd");
        String re_pwd = map.get("re_pwd");
//        System.out.println(old_pwd);
//        System.out.println(new_pwd);
//        System.out.println(re_pwd);

        // 校验密码是否为空
        if (!StringUtils.hasLength(old_pwd) || !StringUtils.hasLength(new_pwd) || !StringUtils.hasLength(re_pwd))
            return Result.error("密码不能为空");
        
        // 校验密码长度是否在5-16位之间
        if (old_pwd.length() < 5 || old_pwd.length() > 16) {
            return Result.error("旧密码必须为5-16位字符");
        }
        if (new_pwd.length() < 5 || new_pwd.length() > 16) {
            return Result.error("新密码必须为5-16位字符");
        }
        if (re_pwd.length() < 5 || re_pwd.length() > 16) {
            return Result.error("确认密码必须为5-16位字符");
        }
        
        // 获取当前登录用户信息
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("Username");
        User user = userService.findByUserName(username);
        
        // 验证原密码是否正确
        if (!user.getPassword().equals(Md5Util.getMD5String(old_pwd)))
            return Result.error("原密码错误");
        
        // 验证新密码和确认密码是否一致
        if (!new_pwd.equals(re_pwd))
            return Result.error("两次密码不一致");
        
        // 更新用户密码
        userService.updatePwd(new_pwd);
        return Result.success();
    }

}