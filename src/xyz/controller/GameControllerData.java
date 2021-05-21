package xyz.controller;

import java.io.Serializable;

public class GameControllerData implements Serializable {
    private final byte gameState, stepCount, currentStep, currentPlayer, playerCount;
    private final boolean sequenceOpen;

    public GameControllerData(byte gameState, byte currentStep, byte stepCount, byte currentPlayer, byte playerCount, boolean sequenceOpen) {
        this.gameState = gameState;
        this.stepCount = stepCount;
        this.currentStep = currentStep;
        this.currentPlayer = currentPlayer;
        this.playerCount = playerCount;
        this.sequenceOpen = sequenceOpen;
    }

    public byte getGameState() {
        return gameState;
    }

    public byte getStepCount() {
        return stepCount;
    }

    public byte getCurrentStep() {
        return currentStep;
    }

    public byte getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isSequenceOpen() {
        return sequenceOpen;
    }

    public byte getPlayerCount() {
        return playerCount;
    }
}
