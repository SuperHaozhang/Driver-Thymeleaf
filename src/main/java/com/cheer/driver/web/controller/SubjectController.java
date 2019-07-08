package com.cheer.driver.web.controller;



import com.cheer.spring.mybatis.pojo.Subject;
import com.cheer.spring.mybatis.pojo.User;
import com.cheer.spring.mybatis.pojo.Xueyuan;
import com.cheer.spring.mybatis.service.SubjectService;
import com.cheer.spring.mybatis.service.XueService;
import com.cheer.spring.mybatis.service.impl.SubjectServiceImp;
import com.cheer.spring.mybatis.service.impl.XueyuanImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SubjectController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    @Resource
    private SubjectService subjectService;

    @Resource
    private XueService xueService;

    @GetMapping("/SubjectList")
    public String getEmpList(Model model){
        List<Subject> list = this.subjectService.getSubjectList();
        for (Subject subject : list) {
            System.out.println(subject);
        }
        model.addAttribute("list",list);
        return "SubList";
    }


    // http://localhost:8080/springmvc/login
    @GetMapping("login1")
    // @RequestMapping(path = "login", method = RequestMethod.GET)
    public String login() {
        LOGGER.debug("login()-------------------->");
        return "login1"; // login是逻辑视图名称，待会儿视图解析器把会把它转换成一个实际存在物理文件login.jsp
    }


    @PostMapping("login1")
    public String login(@RequestParam("name") String name, @RequestParam String pwd, HttpServletRequest request) {
        LOGGER.debug("username={}, password{}", name, pwd);
        if(xueService.getXueYuan(name,pwd)!=null){
            //获取session对象
            HttpSession hs=request.getSession();
            Xueyuan x = xueService.getXueYuan(name,pwd);
            int id = x.getId();
            //将用户数据存储到session对象中
            hs.setAttribute("id",id);
            hs.setAttribute("username",name);
            //重定向
            return "answer1";
        }else {
            return "redirect:/login1";
        }
    }

    @RequestMapping("getList")
    @ResponseBody
    public List getList(){
        List<Subject> list = this.subjectService.getSubjectList();
        return list;
    }

    @PostMapping("result")
    @ResponseBody
    public Integer getList(HttpServletRequest request,Model model){
        //获取session对象
        HttpSession hs=request.getSession();
        //获取登录学员的id
        int id = (Integer)(hs.getAttribute("id"));
        //获取登录学员的名字
        String name = (String)(hs.getAttribute("username"));

        //获取数据库中的所有答案
        List<String> keyList = subjectService.getKey();
        //获取前台发送过来的答案
        String[] ans = request.getParameterValues("ans");
        int j = 0;//记录正确答案
        int k = 0;//记录错误答案
        for (int i = 0; i <ans.length ; i++) {
            if(ans[i].equals(keyList.get(i).split("：")[1])){
                j++;
            }else {
                k++;
            }
        }
        //创建答题对象，存储提交答案的数据
        Xueyuan x = new Xueyuan(id,null,null,j,k,keyList.size()-(j+k),j*10);
        //调用'学员'业务层
        int update = xueService.update(x);
        return j;
    }

    @RequestMapping("result1")
    public String result(HttpServletRequest request,Model model){
        //获取session对象
        HttpSession hs=request.getSession();
        //获取登录学员的id
        int id = (Integer)(hs.getAttribute("id"));
        Xueyuan xueyuan = xueService.getOne(id);
        model.addAttribute("xueyuan",xueyuan);
        return "result";
    }
}
