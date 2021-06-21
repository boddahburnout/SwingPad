package darth.frames;

import darth.encryption.Encrypt;
import darth.file.TextUtils;
import darth.object.SwingPad;
import darth.object.SwingPadProtected;
import darth.serializer.Serialize;
import darth.serializer.Unserialize;
import darth.settings.UserSettings;
import say.swing.JFontChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;

public class PadFrame {
    JFrame jFrame = new JFrame("Untitled - SwingPad");
    MenuBar menuBar = new MenuBar();

    Menu file = new Menu("File");
    MenuItem saveItem = new MenuItem("Save");
    MenuItem saveAsItem = new MenuItem("Save As");
    MenuItem openItem = new MenuItem("Open");
    MenuItem newItem = new MenuItem("New");
    MenuItem newWindowItem = new MenuItem("New Window");
    MenuItem printSetupItem = new MenuItem("Print setup");
    MenuItem printItem = new MenuItem("Print");
    MenuItem exitItem = new MenuItem("Exit");

    Menu format = new Menu("Format");
    MenuItem font = new MenuItem("Font");
    CheckboxMenuItem wordWrapCB = new CheckboxMenuItem("Word Wrap", true);

    Menu editor = new Menu("Editor");
    MenuItem fontColor = new MenuItem("Font Color");
    MenuItem bgColor = new MenuItem("Background Color");

    Menu help = new Menu("Help");
    MenuItem aboutItem = new MenuItem("About SwingPad");

    TextUtils textUtils = new TextUtils();

    File textFile = new File("Untitled");
    File userData = new File(System.getProperty("user.home"));


    FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Text File","txt");
    FileNameExtensionFilter protectedFilter = new FileNameExtensionFilter("SwingPad Protected", "spp");
    FileNameExtensionFilter swingPadFilter = new FileNameExtensionFilter("SwingPad Text", "sp");

    JFileChooser jFileChooser = new JFileChooser();
    JFontChooser jFontChooser;
    JTextArea padField = new JTextArea(40, 100);
    JScrollPane scroll = new JScrollPane(padField);
    JColorChooser jColorChooser = new JColorChooser(Color.black);
    PrinterJob printerJob = PrinterJob.getPrinterJob();

    UserSettings userSettings = new UserSettings(padField.getBackground(), padField.getForeground(), font.getFont(), wordWrapCB.getState());

    SwingPadProtected swingPadProtected;
    SwingPad swingPad;

    String asterisk = "\\*".replaceAll("\\\\", "");

    public void createAndShowGui(Encrypt encrypt) {
        if (Paths.get(userData.getAbsolutePath()+"\\"+"userdata.swingpad").toFile().exists()) {
            userSettings = (UserSettings) new Unserialize().Unserialize(userData.getAbsolutePath(), "userdata.swingpad");
        } else {
            updateData();
        }

        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jFrame.setLayout(new BorderLayout());
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setSize(1600, 1600);
        jFrame.add(scroll);

        help.add(aboutItem);
        format.add(wordWrapCB);
        format.add(font);
        editor.add(fontColor);
        editor.add(bgColor);
        file.add(newItem);
        file.add(newWindowItem);
        file.add(openItem);
        file.add(saveItem);
        file.add(saveAsItem);
        file.add(printSetupItem);
        file.add(printItem);
        file.add(exitItem);
        menuBar.add(file);
        menuBar.add(format);
        menuBar.add(editor);
        menuBar.add(help);
        jFrame.setMenuBar(menuBar);

        jFileChooser.addChoosableFileFilter(textFilter);
        jFileChooser.addChoosableFileFilter(protectedFilter);
        jFileChooser.addChoosableFileFilter(swingPadFilter);

        try {
            wordWrapCB.setState(userSettings.getWordWrap());
            padField.setForeground(userSettings.getFontColor());
            padField.setBackground(userSettings.getBgColor());
            padField.setFont(userSettings.getFont());
            padField.setLineWrap(userSettings.getWordWrap());
            padField.setCaretColor(userSettings.getFontColor());
        } catch (Exception e) {
            if (userData.exists()) { userData.delete(); }
            updateData();
            jFrame.dispose();
            initGui(encrypt);
        }

        saveAsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!padField.getText().isEmpty()) {
                    jFileChooser.setDialogTitle("Save file");
                    jFileChooser.setSelectedFile(textFile.getAbsoluteFile());
                    int option = jFileChooser.showOpenDialog(jFrame);

                    if (option == JFileChooser.APPROVE_OPTION) {
                        try {
                            String path = jFileChooser.getSelectedFile().getPath();
                            if (jFileChooser.getFileFilter().accept(userData)) {
                                if (jFileChooser.getFileFilter().getDescription().equals("Text File")) {
                                    if (!path.contains(".txt")) {
                                        path = path+".txt";
                                    }
                                    textUtils.save(Paths.get(path), padField.getText());
                                    textFile = jFileChooser.getSelectedFile();
                                    jFrame.setTitle(textFile.getName()+" - SwingPad");
                                }

                                if (jFileChooser.getFileFilter().getDescription().equals("SwingPad Text")) {
                                    if (!path.contains(".sp")) {
                                        path = path+".sp";
                                    }
                                    swingPad = new SwingPad(padField.getFont(), padField.getBackground(), padField.getForeground());
                                    new Serialize(path, "" , swingPadProtected);
                                    textFile = jFileChooser.getSelectedFile();
                                    jFrame.setTitle(textFile.getName()+" - SwingPad");
                                }

                                if (jFileChooser.getFileFilter().getDescription().equals("SwingPad Protected")) {
                                    if (!path.contains(".spp")) {
                                        path = path+".spp";
                                    }
                                    Object o = JOptionPane.showInputDialog(jFrame, "Password", "");
                                    swingPadProtected = new SwingPadProtected(padField.getFont(), padField.getBackground(), padField.getForeground(), o, padField.getText());
                                    swingPadProtected.setData(encrypt.Encrypt(swingPadProtected.getData()));
                                    swingPadProtected.setPass(encrypt.Encrypt((String) swingPadProtected.getPass()));
                                    new Serialize(path, "" , swingPadProtected);
                                    textFile = jFileChooser.getSelectedFile();
                                    jFrame.setTitle(textFile.getName()+" - SwingPad");
                                }

                               if (jFileChooser.getFileFilter().getDescription() == "All Files") {
                                   if (!jFileChooser.getSelectedFile().getName().contains(".")) {
                                       JOptionPane.showMessageDialog(jFrame, "Please select an extension to save a file");
                                   } else {
                                       textUtils.save(Paths.get(path), padField.getText());
                                       textFile = jFileChooser.getSelectedFile();
                                       jFrame.setTitle(textFile.getName()+" - SwingPad");
                                   }
                               }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(jFrame, "Text File is Blank!", "SwingPad", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!textFile.getName().equals("Untitled")) {
                    if (textFile.getAbsoluteFile().getName().contains(".txt")) {
                        try {
                            textUtils.save(textFile.toPath(), padField.getText());
                            jFrame.setTitle(jFrame.getTitle().replaceAll("\\"+asterisk, ""));
                        } catch (AccessDeniedException exception) {
                            JOptionPane.showMessageDialog(jFrame, "Text File is Blank!", "SwingPad", JOptionPane.WARNING_MESSAGE);
                        } catch (IOException e) {

                        }
                    }

                    if (textFile.getAbsoluteFile().getName().contains(".sp")) {
                        swingPad = new SwingPad(padField.getFont(), padField.getBackground(), padField.getForeground());
                        new Serialize(textFile.toPath().toString(), "" , swingPadProtected);
                        jFrame.setTitle(jFrame.getTitle().replaceAll("\\"+asterisk, ""));
                    }

                    if (textFile.getAbsoluteFile().getName().contains(".spp")) {
                        SwingPadProtected saveProtected = new SwingPadProtected(jFrame.getFont(), jFrame.getBackground(), jFrame.getForeground(), swingPadProtected.getPass(), padField.getText());
                        new Serialize(textFile.getAbsoluteFile().getAbsolutePath(), "", saveProtected);
                        jFrame.setTitle(jFrame.getTitle().replaceAll("\\"+asterisk, ""));
                    }
                } else {
                    JOptionPane.showMessageDialog(jFrame, "Text File is Blank!", "SwingPad", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jFileChooser.setDialogTitle("Open file");
                int option = jFileChooser.showOpenDialog(jFrame);

                if (jFileChooser.getSelectedFile() == null) return;

                if (jFileChooser.getSelectedFile().getAbsoluteFile().getName().contains(".spp")) {
                    swingPadProtected = (SwingPadProtected) new Unserialize().Unserialize(jFileChooser.getSelectedFile().getAbsolutePath(), "");
                    Object o = JOptionPane.showInputDialog(jFrame, "Password");
                    swingPadProtected.setPass(encrypt.Decrypt((String) swingPadProtected.getPass()));
                    swingPadProtected.setData(encrypt.Decrypt(swingPadProtected.getData()));
                    if (o.equals(swingPadProtected.getPass())) {
                        textFile = new File(jFileChooser.getSelectedFile().getPath());
                        jFrame.setTitle(jFileChooser.getSelectedFile().getName() + " - SwingPad");
                        padField.setText(swingPadProtected.getData());
                    } else {
                        JOptionPane.showMessageDialog(jFrame, "Incorrect Password!");
                    }
                }

                if (jFileChooser.getFileFilter().getDescription().equals("SwingPad Text")) {
                    String path = jFileChooser.getSelectedFile().toPath().toString();
                    if (!path.contains(".sp")) {
                        path = path+".sp";
                    }
                    swingPad = new SwingPad(padField.getFont(), padField.getBackground(), padField.getForeground());
                    new Serialize(path, "" , swingPadProtected);
                    textFile = jFileChooser.getSelectedFile();
                    jFrame.setTitle(textFile.getName()+" - SwingPad");
                }

                if (jFileChooser.getSelectedFile().getAbsoluteFile().getName().contains(".txt")) {
                    try {
                        String text = textUtils.load(Paths.get(jFileChooser.getSelectedFile().getPath()));
                        padField.setText(text);
                        jFrame.setTitle(jFileChooser.getSelectedFile().getName() + " - SwingPad");
                        textFile = new File(jFileChooser.getSelectedFile().getPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                padField.setText("");
                textFile = new File("Untitled");
                jFrame.setTitle("Untitled - SwingPad");
            }
        });

        newWindowItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                initGui(encrypt);
            }
        });

        font.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jFontChooser = new JFontChooser();
                jFontChooser.showDialog(jColorChooser);
                userSettings.setFont(jFontChooser.getSelectedFont());
                updateData();
                padField.setFont(userSettings.getFont());
            }
        });

        fontColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JDialog dialog = JColorChooser.createDialog(null, "Font Color", true, jColorChooser,null,null);
                dialog.setVisible(true);
                userSettings.setFontColor(jColorChooser.getColor());
                updateData();
                padField.setForeground(userSettings.getFontColor());
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jFrame.dispose();
            }
        });

        wordWrapCB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (!wordWrapCB.getState()) {
                    userSettings.setWordWrap(false);
                    updateData();
                    wordWrapCB.setState(userSettings.getWordWrap());
                } else {
                    userSettings.setWordWrap(true);
                    updateData();
                    wordWrapCB.setState(userSettings.getWordWrap());
                }
                updateData();
                padField.setLineWrap(userSettings.getWordWrap());
            }
        });

        bgColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JDialog dialog = JColorChooser.createDialog(null, "Font Color", true, jColorChooser,null,null);
                dialog.setVisible(true);
                userSettings.setBgColor(jColorChooser.getColor());
                updateData();
                padField.setBackground(userSettings.getBgColor());
            }
        });

        printSetupItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PageFormat pf = printerJob.pageDialog(printerJob.defaultPage());
            }
        });

        printItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (printerJob.printDialog()) {
                    try {
                        printerJob.print();
                    }
                    catch (PrinterException e) {

                    }
                }
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(jFrame, "SwingPad Ver 1.0 by Darthkota98", "SwingPad About", JOptionPane.INFORMATION_MESSAGE);
            }
        });

       padField.addKeyListener(new KeyListener() {
           @Override
           public void keyTyped(KeyEvent keyEvent) {

           }

           @Override
          public void keyPressed(KeyEvent keyEvent) {

           }

           @Override
           public void keyReleased(KeyEvent keyEvent) {
               if (!padField.getText().isEmpty() && !jFrame.getTitle().contains("*")) {
                   jFrame.setTitle(asterisk+jFrame.getTitle());
               } else {
                   if (padField.getText().isEmpty()) {
                       jFrame.setTitle(jFrame.getTitle().replaceAll("\\"+asterisk, ""));
                   }
               }
           }
        });

        jFrame.setVisible(true);
        jFrame.pack();
    }

    public void initGui(Encrypt encrypt) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PadFrame().createAndShowGui(encrypt);
            }
        });
    }

    public void updateData() {
        new Serialize(userData.getAbsolutePath(), "userdata.swingpad", userSettings);
    }
}
