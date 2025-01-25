package com.money.GuessingGame;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class GameController {
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("targetNumber") == null) {
            initializeGame(session);
        }
        return "game";
    }
    @PostMapping("/guess")
    public String guess(@RequestParam int userGuess, HttpSession session, Model model) {
        Integer targetNumber = (Integer) session.getAttribute("targetNumber");
        Integer attempts = (Integer) session.getAttribute("attempts");
        session.setAttribute("attempts", ++attempts);
        String message;
        boolean gameOver = false;

        if (userGuess == targetNumber) {
            message = "Congratulations! You've guessed the number in " + attempts + " attempts!";
            gameOver = true;
        } else if (userGuess < targetNumber) {
            message = "Try higher!";
        } else {
            message = "Try lower!";
        }
        model.addAttribute("message", message);
        model.addAttribute("attempts", attempts);
        model.addAttribute("gameOver", gameOver);
        return "game";
    }
    @PostMapping("/restart")
    public String restart(HttpSession session) {
        initializeGame(session);
        return "redirect:/";
    }
    private void initializeGame(HttpSession session) {
        session.setAttribute("targetNumber", (int) (Math.random() * 100) + 1);
        session.setAttribute("attempts", 0);
    }
}

