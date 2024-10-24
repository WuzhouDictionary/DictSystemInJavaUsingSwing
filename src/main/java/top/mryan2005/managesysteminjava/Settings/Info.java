package top.mryan2005.managesysteminjava.Settings;

import top.mryan2005.managesysteminjava.ConnectToNet.POSTAndGET;

import java.io.IOException;
import java.util.Map;

import static java.lang.String.valueOf;

public class Info {
    public String Version = "1.0.0";
    public String Author = "Wuzhou Dictionary Organization";
    public String Description;
    public String License = "MIT";
    public String DataProvider;
    public Info() throws IOException {
        POSTAndGET postAndGet = new POSTAndGET();
        Map<String, Object> repoInfo = postAndGet.getRepoInfo();
        readJSON readJSON = new readJSON("src/main/resources/info.json");
        this.Version = readJSON.getJSONContent().getString("Version");
        this.Author = readJSON.getJSONContent().getString("Author");
        this.DataProvider = readJSON.getJSONContent().getString("DataProvider");
        this.Description = valueOf(repoInfo.get("description"));
        String license = valueOf(repoInfo.get("license"));
        if("Other".matches(license)) {
            this.License = "CC-BY-NC-ND";
        } else {
            this.License = license;
        }
    }
}
