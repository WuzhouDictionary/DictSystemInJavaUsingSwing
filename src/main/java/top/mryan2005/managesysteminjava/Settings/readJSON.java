package top.mryan2005.managesysteminjava.Settings;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class readJSON {
    private JSONObject jsonContent;
    public readJSON(String path) throws IOException {
        File jsonFile = new File(path);
        if(jsonFile.exists()) {
            System.out.println("文件存在！");
            jsonContent = readJSONFile(jsonFile);
        } else {
            System.out.println("文件不存在！");
        }
    }
    public JSONObject readJSONFile(File file) throws IOException {
        String jsonString = new String(Files.readAllBytes(Paths.get(file.getPath())));
        return JSONObject.parseObject(jsonString);
    }
    public JSONObject getJSONContent() {
        return jsonContent;
    }
    public static void main(String[] args) throws IOException {
        readJSON readJSON = new readJSON("src/main/resources/config.json");
        JSONObject res = readJSON.getJSONContent();
    }
}
