package lab6.model;

import lombok.Data;

@Data
public class Voter {
    public final String name;
    private LoginInfo loginInfo;
    private Token token;
    private Program program;

    public Voter(String name) {
        this.name = name;
    }

    public void receive(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
        System.out.println("Received login info");
    }

    public void receive(Token token) {
        this.token = token;
        System.out.println("Received token!");
    }

    public void installProgram() {
        program = new Program();
        program.loginInfo = loginInfo;
        program.token = token;
        System.out.println("Program installed!");
    }

    public void vote(Candidate candidate) {
        program.vote(candidate);
    }
}
