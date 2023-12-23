package com.dwq.controller;

import com.dwq.entity.RestBean;
import com.dwq.entity.dto.Account;
import com.dwq.entity.vo.request.ConfirmResetVO;
import com.dwq.entity.vo.request.EmailRegisterVO;
import com.dwq.entity.vo.request.EmailResetVO;
import com.dwq.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@Validated
@RestController
@RequestMapping("/api/auth")
@Tag(name = "登录校验相关", description = "包括用户登录、注册、验证码请求等操作。")
public class AuthorizeController {

    @Resource
    AccountService accountService;

    @GetMapping("/ask-code")
    @Operation(summary = "请求邮件验证码")
    public RestBean<Void> askVerifyCode(@RequestParam @Email String email,
                                        @RequestParam @Pattern(regexp = "(register|reset)")  String type,
                                        HttpServletRequest request){
        return this.messageHandle(() ->
                accountService.registerEmailVerifyCode(type, String.valueOf(email), request.getRemoteAddr()));
    }


    @PostMapping("/register/{id}")
    @Operation(summary = "用户注册操作")
    public RestBean<Void> register(@RequestBody @Valid EmailRegisterVO vo, @PathVariable("id") String role){
        return this.messageHandle(() ->
                accountService.registerEmailAccount(vo,role));
    }


    @PostMapping("/reset-confirm")
    @Operation(summary = "密码重置确认")
    public RestBean<Void> resetConfirm(@RequestBody @Valid ConfirmResetVO vo){
        return this.messageHandle(() -> accountService.resetConfirm(vo));
    }


    @PostMapping("/reset-password")
    @Operation(summary = "密码重置操作")
    public RestBean<Void> resetPassword(@RequestBody @Valid EmailResetVO vo){
        return this.messageHandle(() ->
                accountService.resetEmailAccountPassword(vo));
    }


    private <T> RestBean<T> messageHandle(Supplier<String> action){
        String message = action.get();
        if(message == null)
            return RestBean.success();
        else
            return RestBean.failure(400, message);
    }

    @GetMapping("/search")
    public List<Account> getAll(){
        return accountService.getAll();
    }
    @GetMapping("/one")
    public Account getOne(String username){
        return accountService.getOne(username);
    }
    @PutMapping("/update/{id}")
    public void updateA(@RequestBody Account account,@PathVariable("id") Integer id){
        account.setId(id);
        accountService.updateA(account);
    }
    @DeleteMapping("/delete/{id}")
    public void delelet(@PathVariable("id") Integer id){
        Account account=new Account();
        account.setId(id);
        accountService.removeById(account);
    }
}
