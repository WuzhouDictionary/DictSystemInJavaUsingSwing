package top.mryan2005.managesysteminjava.BasicClass;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import java.util.ArrayList;

public class Entry {
    public String currentHash;  // 当前哈希
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
    public String html; // HTML代码
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
        html += "<p>当前哈希：" + currentHash + "</p>";
        html += "</body></html>";
        return html;
    }

    public String generateCurrentHash() {
        Base64 base64 = new Base64();
        currentHash = DigestUtils.md5Hex(base64.encode(html.getBytes()));
        return currentHash;
    }

    public static void main(String[] args) {
        Entry entry = new Entry();
        entry.simplified_Chinese_character = "你";
        entry.traditional_Chinese_character = "你";
        entry.Pronunciation_of_Wuzhou = "ni";
        entry.Pronunciation_of_Cangwu_Shiqiao = "ni";
        entry.Pronunciation_of_Mengshan = "ni";
        entry.Heterozygous_Ancient_Texts_of_the_Same_Type = "你";
        entry.Radical_simplified = "人";
        entry.Radical_traditional = "人";
        entry.finalUpdateDate = "2024-11-08";
        entry.total_number_of_strokes_simplified = 7;
        entry.total_number_of_strokes_traditional = 7;
        entry.total_number_of_radical_strokes_simplified = 2;
        entry.total_number_of_radical_strokes_traditional = 2;
        entry.Contributors = new ArrayList<>();
        entry.Contributors.add("mryan2005");
        entry.Contributors.add("gungbbogedding");
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        entry.generateHTML();
        entry.generateCurrentHash();
        frame.add(new JLabel(entry.generateHTML()));
        frame.setVisible(true);
    }
}
