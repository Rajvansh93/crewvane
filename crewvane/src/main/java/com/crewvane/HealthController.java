package com.crewvane;
import com.crewvane.service.GreetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HealthController {
    private final GreetingService greetingService;
    public HealthController(GreetingService greetingService){
        this.greetingService=greetingService;
    }
    @GetMapping("/api/health")
    public String health(){
        return "Crewvane is running!";
    }
    @GetMapping("/api/hello")
    public GreetingResponse hello(){
        return new GreetingResponse(
                greetingService.getWelcomeMessage(),
                "Crewvane",
                "running"
        );
    }
    @GetMapping("/api/greet")
    public String greet(@RequestParam String name){
        return "Hello, "+name+"! Welcome to crewvane.";

    }


}
