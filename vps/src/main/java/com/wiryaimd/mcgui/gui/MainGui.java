package com.wiryaimd.mcgui.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class MainGui extends JFrame{

    private static JTextArea text;

    public MainGui(){
        init();
    }

    public void init(){
        setTitle("Wiryaimd/McConnect");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 400);
        setResizable(true);
        
        showConsole();
    }

    public void showConsole(){
        text = new JTextArea(24, 80);
        text.setBackground(Color.BLACK);
        text.setForeground(Color.WHITE);
        text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        add(text);
    }

    public JTextArea writeConsole(String msg){
        text.append(msg);
        return text;
    }

}