package life.majiang.community.community.controller;


import life.majiang.community.community.dto.QuestionDTO;
import life.majiang.community.community.service.QuestionService;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;

@Controller
public class publishController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model) {
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        return "publish";
    }
    @Autowired
    private QuestionMapper questionMapper;//获取Mapper对象，然后调用它的方法
    @GetMapping("/publish")//get请求
    public String publish() {
        return "publish";
}
    @PostMapping("/publish")//post请求
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value ="tag",required = false) String tag,
            @RequestParam(value = "id", required = false) Integer id,
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

        User user = (User) request.getSession().getAttribute("user");
            if (user ==null){
                model.addAttribute("error","用户未登录");
                return "publish";//如果没登陆着返回publish页面
            }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());

        question.setId(id);
        questionService.createOrUpdate(question);

        return "redirect:publish";//如果登录成功，则重定向回首页

    }
}


