package top.mryan2005.managesysteminjava.BasicClass;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import top.mryan2005.managesysteminjava.SQLs.SQLLinker;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Entry {
    public SQLLinker sql; // 数据库连接
    public String currentHash;  // 当前哈希
    public int charId;  // 字符ID
    public String simplified_Chinese_character;     // 简体中文字符
    public String traditional_Chinese_character;    // 繁体中文字符
    public String Pronunciation_of_Wuzhou;         // 梧州话发音
    public String Pronunciation_of_Cangwu_Shiqiao;  // 苍梧石桥话发音
    public String Pronunciation_of_Mengshan;    // 蒙山话发音
    public String Heterozygous_Ancient_Texts_of_the_Same_Type;  // 同类异文
    public String Radical_simplified;   // 简体部首
    public String Radical_traditional;  // 繁体部首
    public String finalUpdateDate;  // 最后更新日期
    public int total_number_of_strokes_simplified;  // 简体笔画总数
    public int total_number_of_strokes_traditional; // 繁体笔画总数
    public int total_number_of_radical_strokes_simplified;  // 简体部首笔画总数
    public int total_number_of_radical_strokes_traditional; // 繁体部首笔画总数
    public String phrases;  // 词组
    public String html, dispHtml; // HTML代码
    public ArrayList<String> Contributors; // 贡献者
    public String generateHTML() {
        html = "<html><head>" +
                "<style type=\"text/css\">" +
                "body {font-family: 'Microsoft YaHei';}" +
                "h1 {font-size: 22px;}" +
                "h2 {font-size: 20px;}" +
                "h3 {font-size: 16px;}" +
                "p {font-size: 14px;}" +
                "</style>" +
                "</head><body>" +
                "<h1>" + simplified_Chinese_character + "</h1>" +
                "<h2>" + traditional_Chinese_character + "</h2>" +
//                "<p>词组：" + phrases + "</p>" +
                "<p>梧州话发音：" + Pronunciation_of_Wuzhou + "</p>" +
                "<p>苍梧石桥话发音：" + Pronunciation_of_Cangwu_Shiqiao + "</p>" +
                "<p>蒙山话发音：" + Pronunciation_of_Mengshan + "</p>" +
                "<p>同类异文：" + Heterozygous_Ancient_Texts_of_the_Same_Type + "</p>" +
                "<p>简体部首：" + Radical_simplified + "</p>" +
                "<p>繁体部首：" + Radical_traditional + "</p>" +
                "<p>简体笔画总数：" + total_number_of_strokes_simplified + "</p>" +
                "<p>繁体笔画总数：" + total_number_of_strokes_traditional + "</p>" +
                "<p>简体部首笔画总数：" + total_number_of_radical_strokes_simplified + "</p>" +
                "<p>繁体部首笔画总数：" + total_number_of_radical_strokes_traditional + "</p>" +
                "<p>最后更新日期：" + finalUpdateDate + "</p>" +
                "<hr />" +
                "<h3>贡献者</h3>";
        for(String contributor : Contributors) {
            html += "<p>@" + contributor + "</p>";
        }
        html += "<hr />";
        html += "</body></html>";
        return html;
    }

    public String generateCurrentHash() throws UnsupportedEncodingException {
        Base64 base64 = new Base64();
        currentHash = DigestUtils.md5Hex(base64.encode(html.getBytes("GBK")));
        return currentHash;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, SQLException {
        SQLLinker sql = new SQLLinker("SQL Server", "127.0.0.1", "1433", "sa", "123456", "testSQL");
        sql.runSQL("INSERT INTO entry.[main] (id, simplified_Chinese_character, traditional_Chinese_character, Pronunciation_of_Wuzhou, Pronunciation_of_Cangwu_Shiqiao, Pronunciation_of_Mengshan, Heterozygous_Ancient_Texts_of_the_Same_Type, Radical_simplified, Radical_traditional, total_number_of_strokes_simplified, total_number_of_strokes_traditional, total_number_of_radical_strokes_simplified, total_number_of_radical_strokes_traditional) VALUES (1, '中', '中', 'zhong', 'zhong', 'zhong', 'zhong', 'zhong', 'zhong', 1, 1, 1, 1)");
        sql.runSQL("INSERT INTO Users.[user] (id, username, password, level, role, name, avator) VALUES (1, 'admin', '123456', 0, 'admin', '管理员', 'default.jpg')");
        sql.runSQL("INSERT INTO Users.[permission] (id, permissionName, description) VALUES (0, 'admin', 'admin')");
        sql.runSQL("INSERT INTO entry.[history] (id, entryId, beforeChange, afterChange, operatorId, operationDate, hash) VALUES (1, 1, 'before', 'after', 1, GETDATE(), 'hash')");
        Entry entry = new Entry("中", sql);
        entry.viewEntry(entry);
    }
    
    public Entry(String chara, SQLLinker SQL) {
        sql = SQL;
        if(chara == null) {
            return;
        }
        try {
            ResultSet res = sql.runSQL("SELECT * " +
                    "FROM entry.[main] as main, entry.[history] as history " +
                    "WHERE main.id = history.entryId " +
                    "AND main.simplified_Chinese_character = '" + chara + "'");
            if(res == null) {
                return;
            }
            while(res.next()) {
                charId = res.getInt("id");
                simplified_Chinese_character = res.getString("simplified_Chinese_character");
                traditional_Chinese_character = res.getString("traditional_Chinese_character");
                Pronunciation_of_Wuzhou = res.getString("Pronunciation_of_Wuzhou");
                Pronunciation_of_Cangwu_Shiqiao = res.getString("Pronunciation_of_Cangwu_Shiqiao");
                Pronunciation_of_Mengshan = res.getString("Pronunciation_of_Mengshan");
                Heterozygous_Ancient_Texts_of_the_Same_Type = res.getString("Heterozygous_Ancient_Texts_of_the_Same_Type");
                Radical_simplified = res.getString("Radical_simplified");
                Radical_traditional = res.getString("Radical_traditional");
                finalUpdateDate = res.getString("finalUpdateDate");
                total_number_of_strokes_simplified = res.getInt("total_number_of_strokes_simplified");
                total_number_of_strokes_traditional = res.getInt("total_number_of_strokes_traditional");
                total_number_of_radical_strokes_simplified = res.getInt("total_number_of_radical_strokes_simplified");
                total_number_of_radical_strokes_traditional = res.getInt("total_number_of_radical_strokes_traditional");
                Contributors = new ArrayList<>();
                ResultSet res1 = sql.runSQL("SELECT username" +
                        "FROM Users.[user] as user" +
                        "WHERE id IN (SELECT operatorId" +
                        "FROM entry.[history]" +
                        "WHERE entryId in (SELECT id" +
                        "FROM entry.[main]" +
                        "WHERE simplified_Chinese_character = '" + chara + "'))");
                if(res1 == null) {
                    return;
                }
                while(res1.next()) {
                    Contributors.add(res.getString("username"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Entry() {}

    public void viewEntry(Entry entry) throws UnsupportedEncodingException {
        JFrame jDialog = new JFrame();
        jDialog.setSize(800, 600);
        jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        jDialog.setLayout(new FlowLayout());
        dispHtml = entry.generateHTML();
        entry.generateCurrentHash();
        dispHtml = dispHtml.replaceAll("</body></html>", "<p>当前哈希：" + entry.currentHash + "</p></body></html>");
        JLabel jLabel = new JLabel(dispHtml);
        jDialog.add(jLabel);
        JButton jButton = new JButton("编辑");
        jDialog.add(jButton);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editEntry(jDialog, 1);
                dispHtml = entry.generateHTML();
                try {
                    entry.generateCurrentHash();
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }
                dispHtml = dispHtml.replaceAll("</body></html>", "<p>当前哈希：" + entry.currentHash + "</p></body></html>");
                jLabel.setText(dispHtml);
                jLabel.paintImmediately(jLabel.getVisibleRect());
            }
        });
        jDialog.add(jButton);
        JButton jButton1 = new JButton("刷新");
        jDialog.add(jButton1);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispHtml = entry.generateHTML();
                try {
                    entry.generateCurrentHash();
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }
                dispHtml = dispHtml.replaceAll("</body></html>", "<p>当前哈希：" + entry.currentHash + "</p></body></html>");
                jLabel.setText(dispHtml);
                jLabel.paintImmediately(jLabel.getVisibleRect());
            }
        });
        jDialog.setVisible(true);
    }

    public void viewEntry(Entry entry, JFrame parentJFrame) throws UnsupportedEncodingException {
        JDialog jDialog = new JDialog(parentJFrame);
        jDialog.setSize(800, 600);
        jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        jDialog.setLayout(new FlowLayout());
        entry.generateHTML();
        entry.generateCurrentHash();
        JLabel jLabel = new JLabel(entry.generateHTML());
        jDialog.add(jLabel);
        JButton jButton = new JButton("编辑");
        jDialog.add(jButton);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editEntry(jDialog);
                entry.generateHTML();
                try {
                    entry.generateCurrentHash();
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }
                jLabel.setText(entry.generateHTML());
            }
        });
        jDialog.add(jButton);
        JButton jButton1 = new JButton("刷新");
        jDialog.add(jButton1);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entry.generateHTML();
                try {
                    entry.generateCurrentHash();
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }
                jLabel.setText(entry.generateHTML());
            }
        });
        jDialog.setVisible(true);
    }

    public void editEntry(JFrame parentJFrame, int operatorId) {
        JDialog jDialog = new JDialog(parentJFrame);
        jDialog.setSize(800, 600);
        jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        jDialog.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jDialog.add(new JLabel("简体中文字符"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldSimplifiedChineseCharacter = new JTextField(simplified_Chinese_character);
        jDialog.add(jTextFieldSimplifiedChineseCharacter, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("繁体中文字符"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldTraditionalChineseCharacter = new JTextField(traditional_Chinese_character);
        jDialog.add(jTextFieldTraditionalChineseCharacter, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("梧州话发音"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldPoWuzhou = new JTextField(Pronunciation_of_Wuzhou);
        jDialog.add(jTextFieldPoWuzhou, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("苍梧石桥话发音"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldPoCangwuShiqiao = new JTextField(Pronunciation_of_Cangwu_Shiqiao);
        jDialog.add(jTextFieldPoCangwuShiqiao, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("蒙山话发音"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldPoMengshan = new JTextField(Pronunciation_of_Mengshan);
        jDialog.add(jTextFieldPoMengshan, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("同类异文"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldHeterozygousAncientTextsOfTheSameType = new JTextField(Heterozygous_Ancient_Texts_of_the_Same_Type);
        jDialog.add(jTextFieldHeterozygousAncientTextsOfTheSameType, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("简体部首"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldRadicalSimplified = new JTextField(Radical_simplified);
        jDialog.add(jTextFieldRadicalSimplified, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("繁体部首"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldRadicalTraditional = new JTextField(Radical_traditional);
        jDialog.add(jTextFieldRadicalTraditional, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("偏旁部首笔画数（简体）"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldTotalNumberOfRadicalStrokesSimplified = new JTextField(String.valueOf(total_number_of_radical_strokes_simplified));
        jDialog.add(jTextFieldTotalNumberOfRadicalStrokesSimplified, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("偏旁部首笔画数（繁体）"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldTotalNumberOfRadicalStrokesTraditional = new JTextField(String.valueOf(total_number_of_radical_strokes_traditional));
        jDialog.add(jTextFieldTotalNumberOfRadicalStrokesTraditional, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("笔画数（简体）"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldTotalNumberOfStrokesSimplified = new JTextField(String.valueOf(total_number_of_strokes_simplified));
        jDialog.add(jTextFieldTotalNumberOfStrokesSimplified, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("笔画数（繁体）"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldTotalNumberOfStrokesTraditional = new JTextField(String.valueOf(total_number_of_strokes_traditional));
        jDialog.add(jTextFieldTotalNumberOfStrokesTraditional, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 1;
        JTable jTable = new JTable();
        TableColumn tableColumn = new TableColumn();
        tableColumn.setHeaderValue("贡献者");
        jTable.addColumn(tableColumn);
        jDialog.add(jTable, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        jDialog.add(jTable, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 1;
        JButton jButton = new JButton("保存");
        jDialog.add(jButton, gridBagConstraints);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                simplified_Chinese_character = jTextFieldSimplifiedChineseCharacter.getText();
                traditional_Chinese_character = jTextFieldTraditionalChineseCharacter.getText();
                Pronunciation_of_Wuzhou = jTextFieldPoWuzhou.getText();
                Pronunciation_of_Cangwu_Shiqiao = jTextFieldPoCangwuShiqiao.getText();
                Pronunciation_of_Mengshan = jTextFieldPoMengshan.getText();
                Heterozygous_Ancient_Texts_of_the_Same_Type = jTextFieldHeterozygousAncientTextsOfTheSameType.getText();
                Radical_simplified = jTextFieldRadicalSimplified.getText();
                Radical_traditional = jTextFieldRadicalTraditional.getText();
                total_number_of_radical_strokes_simplified = Integer.parseInt(jTextFieldTotalNumberOfRadicalStrokesSimplified.getText());
                total_number_of_radical_strokes_traditional = Integer.parseInt(jTextFieldTotalNumberOfRadicalStrokesTraditional.getText());
                total_number_of_strokes_simplified = Integer.parseInt(jTextFieldTotalNumberOfStrokesSimplified.getText());
                total_number_of_strokes_traditional = Integer.parseInt(jTextFieldTotalNumberOfStrokesTraditional.getText());
                sql.runSQL("UPDATE entry.[main]" +
                        "SET simplified_Chinese_character = '" + simplified_Chinese_character + "'," +
                        "traditional_Chinese_character = '" + traditional_Chinese_character + "'," +
                        "Pronunciation_of_Wuzhou = '" + Pronunciation_of_Wuzhou + "'," +
                        "Pronunciation_of_Cangwu_Shiqiao = '" + Pronunciation_of_Cangwu_Shiqiao + "'," +
                        "Pronunciation_of_Mengshan = '" + Pronunciation_of_Mengshan + "'," +
                        "Heterozygous_Ancient_Texts_of_the_Same_Type = '" + Heterozygous_Ancient_Texts_of_the_Same_Type + "'," +
                        "Radical_simplified = '" + Radical_simplified + "'," +
                        "Radical_traditional = '" + Radical_traditional + "'," +
                        "total_number_of_radical_strokes_simplified = " + total_number_of_radical_strokes_simplified + "," +
                        "total_number_of_radical_strokes_traditional = " + total_number_of_radical_strokes_traditional + "," +
                        "total_number_of_strokes_simplified = " + total_number_of_strokes_simplified + "," +
                        "total_number_of_strokes_traditional = " + total_number_of_strokes_traditional +
                        "WHERE id = " + charId);
                ResultSet lastId = sql.runSQL("SELECT MAX(id) FROM entry.[history]");
                int LastId;
                if(lastId == null) {
                    LastId = 0;
                } else {
                    try {
                        LastId = lastId.getInt(1);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                try {
                    sql.runSQL("INSERT INTO entry.[history] (id, beforeChange, afterChange, operatorId, operationDate, hash)" +
                            "VALUES (" + (LastId + 1) + ", '" + html + "', '" + generateHTML() + "', " + operatorId + ", GETDATE(), '" + generateCurrentHash() + "')");
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }
                jDialog.dispose();
            }
        });
        jDialog.setVisible(true);
    }

    public void editEntry(JDialog parentJDialog) {
        JDialog jDialog = new JDialog(parentJDialog);
        jDialog.setSize(800, 600);
        jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        jDialog.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jDialog.add(new JLabel("简体中文字符"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldSimplifiedChineseCharacter = new JTextField(simplified_Chinese_character);
        jDialog.add(jTextFieldSimplifiedChineseCharacter, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("繁体中文字符"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldTraditionalChineseCharacter = new JTextField(traditional_Chinese_character);
        jDialog.add(jTextFieldTraditionalChineseCharacter, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("梧州话发音"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldPoWuzhou = new JTextField(Pronunciation_of_Wuzhou);
        jDialog.add(jTextFieldPoWuzhou, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("苍梧石桥话发音"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldPoCangwuShiqiao = new JTextField(Pronunciation_of_Cangwu_Shiqiao);
        jDialog.add(jTextFieldPoCangwuShiqiao, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("蒙山话发音"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldPoMengshan = new JTextField(Pronunciation_of_Mengshan);
        jDialog.add(jTextFieldPoMengshan, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("同类异文"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldHeterozygousAncientTextsOfTheSameType = new JTextField(Heterozygous_Ancient_Texts_of_the_Same_Type);
        jDialog.add(jTextFieldHeterozygousAncientTextsOfTheSameType, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("简体部首"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldRadicalSimplified = new JTextField(Radical_simplified);
        jDialog.add(jTextFieldRadicalSimplified, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("繁体部首"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldRadicalTraditional = new JTextField(Radical_traditional);
        jDialog.add(jTextFieldRadicalTraditional, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("偏旁部首笔画数（简体）"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldTotalNumberOfRadicalStrokesSimplified = new JTextField(String.valueOf(total_number_of_radical_strokes_simplified));
        jDialog.add(jTextFieldTotalNumberOfRadicalStrokesSimplified, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("偏旁部首笔画数（繁体）"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldTotalNumberOfRadicalStrokesTraditional = new JTextField(String.valueOf(total_number_of_radical_strokes_traditional));
        jDialog.add(jTextFieldTotalNumberOfRadicalStrokesTraditional, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("笔画数（简体）"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldTotalNumberOfStrokesSimplified = new JTextField(String.valueOf(total_number_of_strokes_simplified));
        jDialog.add(jTextFieldTotalNumberOfStrokesSimplified, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 1;
        jDialog.add(new JLabel("笔画数（繁体）"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        JTextField jTextFieldTotalNumberOfStrokesTraditional = new JTextField(String.valueOf(total_number_of_strokes_traditional));
        jDialog.add(jTextFieldTotalNumberOfStrokesTraditional, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 1;
        JTable jTable = new JTable();
        TableColumn tableColumn = new TableColumn();
        tableColumn.setHeaderValue("贡献者");
        jTable.addColumn(tableColumn);
        jDialog.add(jTable, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        jDialog.add(jTable, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 1;
        JButton jButton = new JButton("保存");
        jDialog.add(jButton, gridBagConstraints);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simplified_Chinese_character = jTextFieldSimplifiedChineseCharacter.getText();
                traditional_Chinese_character = jTextFieldTraditionalChineseCharacter.getText();
                Pronunciation_of_Wuzhou = jTextFieldPoWuzhou.getText();
                Pronunciation_of_Cangwu_Shiqiao = jTextFieldPoCangwuShiqiao.getText();
                Pronunciation_of_Mengshan = jTextFieldPoMengshan.getText();
                Heterozygous_Ancient_Texts_of_the_Same_Type = jTextFieldHeterozygousAncientTextsOfTheSameType.getText();
                Radical_simplified = jTextFieldRadicalSimplified.getText();
                Radical_traditional = jTextFieldRadicalTraditional.getText();
                total_number_of_radical_strokes_simplified = Integer.parseInt(jTextFieldTotalNumberOfRadicalStrokesSimplified.getText());
                total_number_of_radical_strokes_traditional = Integer.parseInt(jTextFieldTotalNumberOfRadicalStrokesTraditional.getText());
                total_number_of_strokes_simplified = Integer.parseInt(jTextFieldTotalNumberOfStrokesSimplified.getText());
                total_number_of_strokes_traditional = Integer.parseInt(jTextFieldTotalNumberOfStrokesTraditional.getText());
                jDialog.dispose();
            }
        });
        jDialog.setVisible(true);
    }
}
