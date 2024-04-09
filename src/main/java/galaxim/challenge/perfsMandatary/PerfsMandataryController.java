package galaxim.challenge.perfsMandatary;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/perfsagent")
@RequiredArgsConstructor
public class PerfsMandataryController {

    private final PerfsMandataryService perfsMandataryService;
}
