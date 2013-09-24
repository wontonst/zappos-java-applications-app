/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author RoyZheng
 */
public class InputPanel extends JPanel {

    JTextField m_jtf_email;
    JTextField m_jtf_productid;
    JTextField m_jtf_productname;
    JButton m_btn_productid;
    JButton m_btn_productname;

    public InputPanel(ActionListener al) {

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        this.m_btn_productid = new JButton("GO");
        this.m_btn_productname = new JButton("GO");
        this.m_btn_productid.addActionListener(al);
        this.m_btn_productname.addActionListener(al);

        this.m_jtf_productid = new JTextField();
        this.m_jtf_productname = new JTextField();
        this.m_jtf_email = new JTextField();
        m_jtf_productid.setColumns(20);
        m_jtf_productname.setColumns(20);
        m_jtf_email.setColumns(30);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Email"), gbc);
        gbc = (GridBagConstraints) gbc.clone();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        this.add(this.m_jtf_email, gbc);
        gbc = (GridBagConstraints) gbc.clone();
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("ProductID"), gbc);
        gbc = (GridBagConstraints) gbc.clone();
        gbc.gridx = 1;
        this.add(this.m_jtf_productid, gbc);
        gbc = (GridBagConstraints) gbc.clone();
        gbc.gridx = 2;
        this.add(this.m_btn_productid, gbc);
        gbc = (GridBagConstraints) gbc.clone();
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JLabel("ProductName"), gbc);
        gbc = (GridBagConstraints) gbc.clone();
        gbc.gridx = 1;
        this.add(this.m_jtf_productname, gbc);
        gbc = (GridBagConstraints) gbc.clone();
        gbc.gridx = 2;
        this.add(this.m_btn_productname, gbc);

    }

    public boolean productIdButton(Object b) {
        return b == this.m_btn_productid;
    }

    public boolean productNameButton(Object b) {
        return b == this.m_btn_productname;
    }

    public String getProductIdInput() {
        return this.m_jtf_productid.getText();
    }

    public String getProductNameInput() {
        return this.m_jtf_productname.getText();
    }
    public String getEmailInput(){
        return this.m_jtf_email.getText();
    }
}
