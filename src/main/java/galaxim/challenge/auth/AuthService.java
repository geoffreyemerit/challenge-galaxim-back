package galaxim.challenge.auth;

import galaxim.challenge.job.Job;
import galaxim.challenge.job.JobRepository;
import galaxim.challenge.user.User;
import galaxim.challenge.user.UserRepository;
import galaxim.challenge.util.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Map<String, String> register(RegisterRequest request, HttpServletRequest httpRequest) throws Exception {

        if (!userRepository.findByEmail(request.getEmail()).isPresent()) {

            // Vérifier si un job est spécifié dans la demande
            Job job;
            if (request.getJob() != null) {
                // Si un job est spécifié dans la demande, utilisez-le
                job = request.getJob();
            } else {
                // Sinon, récupérer ou créer le job par défaut
                job = getOrCreateDefaultJob();
            }
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRequired_role())
                    .isActive(request.getIsActive())
                    .job(job)
                    .build();

            userRepository.save(user);

            Map<String, String> body = new HashMap<>();
            body.put("message", "Account successfully created");
            return body;

        } else if (userRepository.findByEmail(request.getEmail()).isPresent()){
            httpRequest.setAttribute("username_taken_exception", "Username already taken");
            throw new Exception("Username already taken");
        } else {
            httpRequest.setAttribute("pseudo_taken_exception", "Pseudo already taken");
            throw new Exception("Pseudo already taken");
        }
    }

    private Job getOrCreateDefaultJob() {
        Optional<Job> defaultJobOptional = jobRepository.findByNameJob("GALAXIM");

        if (defaultJobOptional.isPresent()) {
            return defaultJobOptional.get();
        } else {
            Job defaultJob = Job.builder()
                    .nameJob("GALAXIM")
                    .build();
            return jobRepository.save(defaultJob);
        }
    }

    public AuthResponse authenticate(AuthRequest request, HttpServletRequest httpRequest) {


        /* Permet de comparer le pwd reçu de la request reçue avec le pwd haché de la BDD.
         * La méthode authenticate() permet surtout de garantir que les informations d'identification sont exactes
         * Permet de transmettre au contexte de Spring l'utilisateur qui a été trouvé.
         * Cela permet de l'utiliser pour autoriser/refuser l'accès aux ressources protégées
         * S'il n'est pas trouvé, une erreur est levée et la méthode s'arrête.
         */
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            /* Si tout va bien et que les informations sont OK, on peut récupérer l'utilisateur */
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

            /* On extrait le rôle de l'utilisateur */
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("role", user.getRole());

            /* On génère le token avec le rôle */
            String jwtToken = jwtService.generateToken(new HashMap<>(extraClaims), user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .message("Logged In")
                    .build();

        } catch (BadCredentialsException ex) {
            httpRequest.setAttribute("bad_credentials", ex.getMessage());
            throw new BadCredentialsException("Bad credentials");
        }

    }
}
