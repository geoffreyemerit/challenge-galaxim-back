package galaxim.challenge.game;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/all")
    public List<Game> getAll() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return gameService.getAll(role);
    }

    @GetMapping("{id}")
    public Game getById(@PathVariable Long id) {
        return gameService.getById(id);
    }

    @GetMapping("/team/{job}")
    public List<Game> getByJob(@PathVariable String job) {
        return gameService.getByJob(job);
    }

    @PostMapping("/add")
    public Game add(@RequestBody Game game){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        return gameService.add(game, username, role);
    }


    @PutMapping("/update")
    public ResponseEntity<Game> update(@RequestBody Game updatedGame) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        Game updated = gameService.update(updatedGame, username, role);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        gameService.delete(id, username, role);
    }
}
