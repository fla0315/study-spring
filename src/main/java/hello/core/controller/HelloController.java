package hello.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        //key , data
        model.addAttribute("data", "hello!!!");
        return "hello"; //해당 이름 화면을 실행시켜라.
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name",name);
        return "hello-template";
    }

    //이 방법은 거의 안씀
    @GetMapping("hello-string")
    @ResponseBody //http body에 이 내용을 직접 넣어주겠다.
    public String helloString (@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody //-> 객체가 오면 default로 json 방식으로 만들어서 http 방식으로 반환하겠다~ 라는 거다.
    public Hello helloApi (@RequestParam("name")String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        //이렇게 선언해서 하는 방법을 프로퍼티 접근 방식이라고도 한다.
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
