import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;

public class Notepad implements ActionListener {
    JFrame f;
    JMenuBar mb;
    JMenu file, edit, help;
    JMenuItem cut, copy, paste, selectAll, open,newFile,save,saveAs,exit;
    JTextArea ta;
    File currentFile;

    Notepad() {
        f = new JFrame();

        open = new JMenuItem("Open File");
        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        save = new JMenuItem("Save");
        saveAs = new JMenuItem("SaveAs");
        newFile = new JMenuItem("New");
        exit = new JMenuItem("Exit");
        
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        open.addActionListener(this);
        newFile.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);
        exit.addActionListener(this);

        mb = new JMenuBar();
        mb.setBounds(5, 3, 460, 20);

        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");

        file.add(open);
        file.add(newFile);
        file.add(save);
        file.add(saveAs);
        file.add(exit);

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);

        mb.add(file);
        mb.add(edit);
        mb.add(help);

        ta = new JTextArea();
        ta.setBounds(5, 20, 470, 460);  // Set rows and columns for JTextArea

        // Add the JTextArea to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(ta);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       
         f.add(mb);  // Add the menu bar to the top
        f.add(scrollPane);  
        // Add the JScrollPane to the JFrame
         f.setJMenuBar(mb);
        f.getContentPane().add(scrollPane);
        f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));


        
        f.setSize(500, 500);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == open) {
            JFileChooser fl = new JFileChooser();
            int i = fl.showOpenDialog(f);
            if (i == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fl.getSelectedFile();
                String filepath = selectedFile.getPath();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(filepath));
                    String s1, s2 = "";
                    while ((s1 = br.readLine()) != null) {
                        s2 += s1 + "\n";
                    }
                    ta.setText(s2);
                    br.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (e.getSource() == cut)
            ta.cut();
        if (e.getSource() == paste)
            ta.paste();
        if (e.getSource() == copy)
            ta.copy();
        if (e.getSource() == selectAll)
            ta.selectAll();
        if (e.getSource() == newFile)
            newFile();
        if (e.getSource() == save)
            saveFile();
        if (e.getSource() == saveAs)
            saveFileAs();
        if (e.getSource() == exit)
            System.exit(0);
    }

    public void newFile() {
        ta.setText("");
        currentFile = null;
    }
    public void saveFile() {
        if (currentFile != null) {
            try {
                FileWriter fw = new FileWriter(currentFile);
                fw.write(ta.getText());
                fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            saveFileAs();
        }
    }

    public void saveFileAs() {
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showSaveDialog(f);
        if (response == JFileChooser.APPROVE_OPTION) {
            try {
                currentFile = fileChooser.getSelectedFile();
                FileWriter fw = new FileWriter(currentFile);
                fw.write(ta.getText());
                fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Notepad();
    }
}
