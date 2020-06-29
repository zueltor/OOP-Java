package ru.nsu.g.mustafin.tetris.view.gui;

import javax.swing.*;

public class AboutPanel extends JPanel{
    private JButton menuButton;

    public AboutPanel() {
        menuButton = new JButton("Menu");
        String text="<html>Tetris by Mustafin Damir<br>Group 18201</html>";
        JLabel jl=new JLabel(text);
        jl.setSize(this.getSize());
        add(jl);
        add(menuButton);
    }

    public JButton getMenuButton(){
        return menuButton;
    }
}
