package example.roommate.WebControllers.Errors;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletResponse response) {

        // Check the error status
        if (response.getStatus() == HttpServletResponse.SC_FORBIDDEN) {
            // Return the path to your custom forbidden HTML page
            return "forbidden";
        }
        // For other errors, use Spring Boot's default error handling
        return "error";
    }

}
