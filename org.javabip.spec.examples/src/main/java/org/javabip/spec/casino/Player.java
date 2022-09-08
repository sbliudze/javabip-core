package org.javabip.spec.casino;

import org.javabip.annotations.*;
import org.javabip.api.PortType;

import static org.javabip.spec.casino.Coin.HEADS;
import static org.javabip.spec.casino.Coin.TAILS;
import static org.javabip.spec.casino.Constants.*;
@Ports({
        @Port(name = PREPARE_BET, type = PortType.enforceable),
        @Port(name = PLACE_BET, type = PortType.enforceable),
        @Port(name = RECEIVE_MONEY, type = PortType.enforceable),
})

@ComponentType(initial = GAME_AVAILABLE, name = PLAYER_SPEC)
@Invariant(expr = "bet <= purse")
public class Player {
    final Integer id;
    Integer bet;
    Coin guess;
    Integer purse;

    Player(Integer id, Integer purse) {
        this.id = id;
        this.purse = purse;
        System.out.println("PLAYER" + id + ": INITIALIZED");
    }
    
    // Player places a bet
    @Transition(name = PREPARE_BET, source = GAME_AVAILABLE, target = BET_PREPARED)
    public void prepareBet() {
        bet = (int)((Math.random() * purse));
        guess = (Math.random() < 0.5)? HEADS : TAILS;
        purse = purse - bet;
        System.out.println("PLAYER" + id + ": bet " + bet + " prepared, purse: " + purse);
    }

    // Player places a bet
    @Transition(name = PLACE_BET, source = BET_PREPARED, target = GAME_AVAILABLE)
    public void placeBet() {
        System.out.println("PLAYER" + id + ": bet " + bet + " placed, purse: " + purse);
        bet = 0;
        guess = null;
    }

    // Player receives a contribution
    @Transition(name = RECEIVE_MONEY, source = GAME_AVAILABLE, target = GAME_AVAILABLE)
    public void receiveContribution(@Data(name = INCOMING_MONEY) Integer win) {
        purse += win;
        System.out.println("PLAYER" + id + ": won " + win + " purse: " + purse);
    }

    @Data(name = OUTGOING_BET)
    public Integer getBet() {
        return bet;
    }

    @Data(name = OUTGOING_GUESS)
    public Coin getGuess() {
        return guess;
    }

    @Data(name = ID)
    public Integer id() {
        return id;
    }
}
