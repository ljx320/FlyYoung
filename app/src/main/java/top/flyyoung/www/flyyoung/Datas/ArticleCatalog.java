package top.flyyoung.www.flyyoung.Datas;

/**
 * Created by 69133 on 2017/1/16.
 * Article/GetCatalog
 */

public class ArticleCatalog {

    private  int ID;
    private String CatalogName;
    private String CatalogShoet;
    private int CatalogLevel;
    private int CatalogSuiper;
    private  int Flags;
    private  int ArticleCount;

    public int getID() {
        return ID;
    }

    public int getArticleCount() {
        return ArticleCount;
    }

    public int getCatalogLevel() {
        return CatalogLevel;
    }

    public int getCatalogSuiper() {
        return CatalogSuiper;
    }

    public int getFlags() {
        return Flags;
    }

    public String getCatalogName() {
        return CatalogName;
    }

    public String getCatalogShoet() {
        return CatalogShoet;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public void setCatalogLevel(int catalogLevel) {
        CatalogLevel = catalogLevel;
    }

    public void setCatalogName(String catalogName) {
        CatalogName = catalogName;
    }

    public void setCatalogShoet(String catalogShoet) {
        CatalogShoet = catalogShoet;
    }

    public void setCatalogSuiper(int catalogSuiper) {
        CatalogSuiper = catalogSuiper;
    }

    public void setFlags(int flags) {
        Flags = flags;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
