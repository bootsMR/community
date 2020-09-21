package life.majiang.community.community.controller;


import life.majiang.community.community.mapper.QuestionMapper;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.Question;
import life.majiang.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class publishController {
    @Autowired
    private QuestionMapper questionMapper;//获取Mapper对象，然后调用它的方法
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/publish")//get请求
    public String publish() {
        return "publish";
}
    @PostMapping("/publish")//post请求
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value ="tag",required = false) String tag,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description==null||description==""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if (tag==null||tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        //User user = (User) request.getSession().getAttribute("user");
        User user = new User();
        Cookie[] cookies = request.getCookies();
        if (cookies==null){
            return null;
        }
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                String token=cookie.getValue();
                user = userMapper.findByToken(token);
                if(user !=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
            if (user ==null){
                model.addAttribute("error","用户未登录");
                return "publish";//如果没登陆着返回publish页面
            }
        Question quesstion = new Question();
        quesstion.setTitle(title);
        quesstion.setDescription(description);
        quesstion.setTag(tag);
        quesstion.setCreator(user.getId());
        quesstion.setGmt_create(System.currentTimeMillis());
        quesstion.setGmt_modified(quesstion.getGmt_create());
        questionMapper.create(quesstion);
        return "redirect:publish";//如果登录成功，则重定向回首页
    }return "publish";
    }
}


