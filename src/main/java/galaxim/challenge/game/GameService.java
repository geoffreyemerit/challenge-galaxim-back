package galaxim.challenge.game;

import galaxim.challenge.office.Office;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public List<Game> getAll(String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return gameRepository.findAll();
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public Game getById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
    }

    public List<Game> getByJob(String job) {
        return gameRepository.findByJob(job);
    }

    public Game add(Game game, String username, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            if (game.getNameGame() != null && gameRepository.findByNameGame(game.getNameGame().toLowerCase()).isPresent()) {
                throw new IllegalArgumentException("Office already exists.");
            }
            game.setNameGame(game.getNameGame());
            game.setDescription(game.getDescription());
            game.setImg(game.getImg());
            game.setJob(game.getJob());

            return gameRepository.save(game);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public Game update(Game updatedGame, String username, String role) {
        Game currentGame = gameRepository.findById(updatedGame.getId())
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + updatedGame.getId()));

        if (role.equals("[ROLE_ADMIN]")) {
            String newNameGame = updatedGame.getNameGame();
            if(newNameGame  != null && !newNameGame.equalsIgnoreCase(currentGame.getNameGame())){
                if (gameRepository.findByNameGame(newNameGame.toLowerCase()).isPresent()) {
                    throw new IllegalArgumentException("Game with the name already exists.");
                }
            }

            currentGame.setNameGame(updatedGame.getNameGame());
            currentGame.setDescription(updatedGame.getDescription());
            currentGame.setImg(updatedGame.getImg());
            currentGame.setJob(updatedGame.getJob());

            return gameRepository.save(currentGame);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to update this resource");
        }
        }

    public void delete(Long id, String username, String role) {

        // Recherche de l'agence par id
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));

        // Vérification si l'utilisateur connecté a le droit de supprimer l'utilisateur
        if (role.equals("[ROLE_ADMIN]")) {
            gameRepository.deleteById(id);
        } else {
            // Gestion du cas où l'utilisateur connecté n'a pas le droit de supprimer l'utilisateur
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }



}
