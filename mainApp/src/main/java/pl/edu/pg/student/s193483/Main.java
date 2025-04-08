package pl.edu.pg.student.s193483;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main app");
        JPanel mainPanel = new JPanel();
        JPanel signPanel = new JPanel();
        JPanel verifyPanel = new JPanel();

        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);
        cards.add(mainPanel, "MAIN");
        cards.add(signPanel, "SIGN");
        cards.add(verifyPanel, "VERIFY");

        JButton signButton = new JButton("Sign a PDF");
        JButton verifyButton = new JButton("Verify a signature");

        signButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "SIGN");
            }
        });

        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "VERIFY");
            }
        });

        // move functionality somewhere else later
        JButton choosePublicButton = new JButton("(temporary) Choose file with public key");
        JButton choosePrivateButton = new JButton("(temporary) Choose file with private key");
        JButton choosePDFButton = new JButton("(temporary) Choose PDF file");
        JButton choosePDFButton2 = new JButton("(temporary) Choose PDF file");
        JButton returnButton = new JButton("Return");
        JButton returnButton2 = new JButton("Return");

        choosePublicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Text Files", "txt");
                fileChooser.addChoosableFileFilter(fileFilter);
                fileChooser.setAcceptAllFileFilterUsed(false);

                int openReturn = fileChooser.showOpenDialog(null);

                if (openReturn == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String fullPath = file.getAbsolutePath();

                    try (FileInputStream fileInputStream = new FileInputStream(fullPath)) {
                        byte[] bytes = fileInputStream.readAllBytes();
                        PublicKey publicKey = KeyExtractor.extractPublicKey(bytes);

                        System.out.println(publicKey);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (NoSuchAlgorithmException ex) {
                        throw new RuntimeException(ex);
                    } catch (InvalidKeySpecException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        choosePrivateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Text Files", "txt");
                fileChooser.addChoosableFileFilter(fileFilter);
                fileChooser.setAcceptAllFileFilterUsed(false);

                int openReturn = fileChooser.showOpenDialog(null);

                if (openReturn == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String fullPath = file.getAbsolutePath();

                    try (FileInputStream fileInputStream = new FileInputStream(fullPath)) {
                        System.out.println("PASSWORD:");
                        Scanner scanner = new Scanner(System.in);
                        String password = scanner.nextLine();

                        byte[] bytes = fileInputStream.readAllBytes();
                        PrivateKey privateKey = KeyExtractor.extractPrivateKey(bytes, password);

                        System.out.println(privateKey);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (NoSuchAlgorithmException ex) {
                        throw new RuntimeException(ex);
                    } catch (InvalidKeySpecException ex) {
                        throw new RuntimeException(ex);
                    } catch (InvalidAlgorithmParameterException ex) {
                        throw new RuntimeException(ex);
                    } catch (NoSuchPaddingException ex) {
                        throw new RuntimeException(ex);
                    } catch (IllegalBlockSizeException ex) {
                        throw new RuntimeException(ex);
                    } catch (BadPaddingException ex) {
                        throw new RuntimeException(ex);
                    } catch (InvalidKeyException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        ActionListener choosePDFListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("PDF Files", "pdf");
                fileChooser.addChoosableFileFilter(fileFilter);
                fileChooser.setAcceptAllFileFilterUsed(false);

                int openReturn = fileChooser.showOpenDialog(null);

                if (openReturn == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String fullPath = file.getAbsolutePath();

                    System.out.println(fullPath);
                }
            }
        };

        ActionListener returnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "MAIN");
            }
        };

        choosePDFButton.addActionListener(choosePDFListener);
        choosePDFButton2.addActionListener(choosePDFListener);
        returnButton.addActionListener(returnListener);
        returnButton2.addActionListener(returnListener);

        mainPanel.add(signButton);
        mainPanel.add(verifyButton);

        // remove later
        verifyPanel.add(choosePublicButton);
        verifyPanel.add(choosePDFButton);
        verifyPanel.add(returnButton);
        signPanel.add(choosePrivateButton);
        signPanel.add(choosePDFButton2);
        signPanel.add(returnButton2);

        frame.add(cards);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}