package analyze;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Set;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/go")
    public String analyze() throws IOException {
        System.out.println("aaaaaaaaaaa");
        return "index";
    }
    @PostMapping("/analyze")
    public String analyze(@RequestParam("text") String text, Model model) throws IOException {
        model.addAttribute("verdict",new Order(text));
        return "index";
    }
}
