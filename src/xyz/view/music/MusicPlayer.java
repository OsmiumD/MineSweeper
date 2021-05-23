package xyz.view.music;

import jmp123.demo.MiniPlayer;
import jmp123.output.Audio;
import xyz.listener.Stoppable;

import java.io.File;

public class MusicPlayer implements Stoppable {

    private final MiniPlayer player;
    private boolean loop;

    public MusicPlayer(String path) {
        player = new MiniPlayer(new Audio());
        try {
            player.open(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread playerThread = new Thread(() -> {
            do {
                player.run();
            } while (loop);
        });
        playerThread.start();
        player.pause();
        loop = false;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void play() {
        if(player.isPaused())player.pause();
    }

    @Override
    public void stop() {
        if(!player.isPaused())player.pause();
    }
}