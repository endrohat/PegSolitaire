package com.example.pegsolitaire;

public class BoardItem {
    boolean isPeg;

    public BoardItem(boolean isPeg) {
        this.isPeg = isPeg;
    }

    public boolean isPeg() {
        return isPeg;
    }

    public void setPeg(boolean peg) {
        isPeg = peg;
    }

}
