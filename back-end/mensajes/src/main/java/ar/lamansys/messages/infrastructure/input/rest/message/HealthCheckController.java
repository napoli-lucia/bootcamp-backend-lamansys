package ar.lamansys.messages.infrastructure.input.rest.message;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @GetMapping
    public String get() {
        log.info("Me pidieron info");
        return "OK";
    }
}
