/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos.gui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import zappos.Zappos;

/**
 *
 * @author RoyZheng
 */
public class Frame extends JFrame implements ActionListener {

    InputPanel m_inputPanel;
    Zappos main;
    CardLayout m_layout;
    JPanel panel;

    public Frame(Zappos z) {
        this.m_layout = new CardLayout();
        this.main = z;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.panel = new JPanel();
        this.panel.setLayout(this.m_layout);
        this.panel.add(this.m_inputPanel = new InputPanel(this), "INPUT");
        this.panel.add(new LoadingPanel(), "LOADING");
        this.add(this.panel);
        this.pack();
        this.setVisible(true);
    }

    public void showLoadingScreen() {
        this.m_layout.show(this.panel, "LOADING");
    }

    public void showInputScreen() {
        this.m_layout.show(this.panel, "INPUT");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.m_inputPanel.productIdButton(e.getSource())) {
            this.showLoadingScreen();
            System.out.println("PIDBTN");
            String pid = this.m_inputPanel.getProductIdInput();
            if (!this.checkEmail()) {
                this.showInputScreen();
                return;
            }
            if ("".equals(pid)) {
                JOptionPane.showMessageDialog(this, "Please enter a value for product ID.");
            }
            try {
                this.main.usrProductIdRequest(this.m_inputPanel.getEmailInput(), Integer.parseInt(pid));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Product ID may only be an integer value.");
            }
        } else if (this.m_inputPanel.productNameButton(e.getSource())) {
            this.m_layout.show(this.panel, "LOADING");
            System.out.println("PNBTN");
            String pn = this.m_inputPanel.getProductNameInput();
            if (!this.checkEmail()) {
                this.m_layout.show(this.panel, "INPUT");
                return;
            }
            if ("".equals(pn)) {
                JOptionPane.showMessageDialog(this, "Please enter a value for product name.");
            }
            this.main.usrProductNameRequest(this.m_inputPanel.getEmailInput(), pn);
        }
    }

    public boolean checkEmail() {
        if (this.m_inputPanel.getEmailInput().equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter your email in the email field.");
            return false;
        }
        Pattern rfc2822 = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
if(!rfc2822.matcher(this.m_inputPanel.getEmailInput()).matches()) {
            JOptionPane.showMessageDialog(this, "Invalid email address given.");
            return false;
}

        return true;
    }
}
