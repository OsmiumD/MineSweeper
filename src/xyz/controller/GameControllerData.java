package xyz.controller;

import java.io.Serializable;

public class GameControllerData implements Serializable {
    private final byte gameState, stepCount, currentStep, currentPlayerId, playerCount;
    private final boolean sequenceOpen;

    public GameControllerData(byte gameState, byte currentStep, byte stepCount, byte currentPlayerId, byte playerCount, boolean sequenceOpen) {
        this.gameState = gameState;
        this.stepCount = stepCount;
        this.currentStep = currentStep;
        this.currentPlayerId = currentPlayerId;
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

    public byte getCurrentPlayerId() {
        return currentPlayerId;
    }

    public boolean isSequenceOpen() {
        return sequenceOpen;
    }

    public byte getPlayerCount() {
        return playerCount;
    }
}
