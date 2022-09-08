package org.javabip.spec.casino;

import org.javabip.annotations.*;
import org.javabip.api.DataOut;
import org.javabip.api.PortType;

import static org.javabip.spec.casino.Coin.HEADS;
import static org.javabip.spec.casino.Coin.TAILS;
import static org.javabip.spec.casino.Constants.*;

@Ports({
        @Port(name = ADD_TO_POT, type = PortType.enforceable),
        @Port(name = REMOVE_FROM_POT, type = PortType.enforceable),
        @Port(name = CREATE_GAME, type = PortType.enforceable),
        @Port(name = RECEIVE_BET, type = PortType.enforceable),
        @Port(name = CASINO_WIN, type = PortType.enforceable),
        @Port(name = PLAYER_WIN, type = PortType.enforceable),
})

@ComponentType(initial = IDLE, name = CASINO_SPEC)
@Invariant("bet >= 0 && pot >= bet")
@StatePredicates({
        @StatePredicate(state = IDLE, expr = "false"),
        @StatePredicate(state = GAME_AVAILABLE, expr = "true"),
        @StatePredicate(state = BET_PLACED, expr = "guess != null")
})
public class Casino {
    final Integer id;
    Integer operator;
    Integer pot;
    Integer secret;
    Integer player;

    Coin guess;
    int bet;

    public Casino(Integer id, Integer operator) {
        this.id = id;
        this.operator = operator;
        pot = 0;
        bet = 0;
        System.out.println("CASINO" + id + ": INITIALIZED");
    }

    // Add money to pot
    @Transitions({
            @Transition(name = ADD_TO_POT, source = IDLE, target = IDLE, guard = IS_OPERATOR),
            @Transition(name = ADD_TO_POT, source = GAME_AVAILABLE, target = GAME_AVAILABLE, guard = IS_OPERATOR),
            @Transition(name = ADD_TO_POT, source = BET_PLACED, target = BET_PLACED, guard = IS_OPERATOR)
    })
    public void addToPot(@Data(name = OPERATOR) Integer sender, @Data(name = INCOMING_FUNDS) Integer funds) {
        pot = pot + funds;
        System.out.println("CASINO" + id + ": " + funds +
                " received from operator " + sender +
                ", pot: " + pot);
    }

    // Remove money from pot
    @Transitions({
            @Transition(name = REMOVE_FROM_POT, source = IDLE, target = IDLE, guard = IS_OPERATOR, pre = "bet <= pot", post = "pot >= 0"),
            @Transition(name = REMOVE_FROM_POT, source = GAME_AVAILABLE, target = GAME_AVAILABLE, guard = IS_OPERATOR, pre = "bet <= pot", post = "pot >= 0"),
    })
    public void removeFromPot(@Data(name = OPERATOR) Integer sender, @Data(name = INCOMING_FUNDS) Integer funds) {
        pot = pot - funds;
        System.out.println("CASINO" + id + ": " + funds +
                " removed by operator " + sender +
                ", pot: " + pot);
    }

    // Operator opens the game
    @Transition(name = CREATE_GAME, source = IDLE, target = GAME_AVAILABLE, guard = IS_OPERATOR, post = "secret != null")
    public void createGame(@Data(name = OPERATOR) Integer sender) {
        secret = new Integer ((int) (Math.random() * 100));
        System.out.println("CASINO" + id + ": GAME CREATED");
    }

    // Operator receives a bet
    @Transition(name = RECEIVE_BET, source = GAME_AVAILABLE, target = BET_PLACED, guard = IS_NOT_OPERATOR, pre = "bet <= pot", post = "bet >= 0 && guess != null")
    public void receiveBet(@Data(name = PLAYER) Integer sender,
                           @Data(name = INCOMING_GUESS) Coin guess, @Data(name = INCOMING_BET) Integer bet) {
        player = sender;
        this.guess = guess;
        this.bet = bet;
        System.out.println("CASINO" + id + ": received bet: " + bet +
                ", guess: " + guess +
                " from player " + player);
    }

    @Transition(name = CASINO_WIN, source = BET_PLACED, target = IDLE, guard = "IS_OPERATOR & !GUESSED")
    public void casinoWin() {
        int won = bet;
        pot = pot + bet;
        bet = 0;
        guess = null;
        player = null;
        System.out.println("CASINO" + id + ": " + won + " won" +
                ", pot: " + pot);
    }

    @Transition(name = PLAYER_WIN, source = BET_PLACED, target = IDLE, guard = "IS_OPERATOR & GUESSED & IS_PLAYER")
    public void playerWin() { // @Data(name = PLAYER) Integer player, @Data(name = OPERATOR) Integer operator
        int lost = bet;
        pot = pot - bet;
        bet = 0;
        guess = null;
        player = null;
        System.out.println("CASINO" + id + ": " + lost + " lost" +
                ", pot: " + pot);
    }

    @Pure
    @Guard(name = GUESSED)
    public boolean isGuessed() {
        Coin secret = (this.secret % 2 == 0) ? HEADS : TAILS;
        return secret == guess;
    }

    @Pure
    @Guard(name = IS_OPERATOR)
    public boolean isOperator(@Data(name = OPERATOR) Integer sender) {
        return sender == operator;
    }

    @Pure
    @Guard(name = IS_NOT_OPERATOR)
    public boolean isNotOperator(@Data(name = PLAYER) Integer sender) {
        return sender != operator;
    }

    @Pure
    @Guard(name = IS_PLAYER)
    public boolean isPlayer(@Data(name = PLAYER) Integer sender) {
        return sender == player;
    }

    @Pure
    @Data(name = OUTGOING_MONEY, accessTypePort = DataOut.AccessType.allowed, ports = {PLAYER_WIN})
    public Integer getWin() {
        return 2*bet;
    }

    @Pure
    @Data(name = AVAILABLE_FUNDS)
    public Integer getPot() {
        return pot;
    }
}
