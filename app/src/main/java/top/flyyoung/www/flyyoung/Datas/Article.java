package top.flyyoung.www.flyyoung.Datas;

/**
 * Created by 69133 on 2017/1/13.
 * Article/PostImage
 * Article/Post
 */

public class Article {
    private  int ID;
    private  int ArticleCatalogID;
    private  String ArticleTitle;
    private String  ArticleDescription;
    private  String  CreateDate;
    private  String  ArticleImage;
    private String  ArticleWords;
    private int  ReadCount;
    private String CreateTime ;
    private String ArticleCatalog;
    private String ArticleContent;
    private  Boolean ShowCustomer;

    public int getArticleCatalogID() {
        return ArticleCatalogID;
    }

    public void setArticleCatalogID(int articleCatalogID) {
        ArticleCatalogID = articleCatalogID;
    }

    public void setReadCount(int readCount) {
        ReadCount = readCount;
    }

    public void setArticleWords(String articleWords) {
        ArticleWords = articleWords;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public void setArticleTitle(String articleTitle) {
        ArticleTitle = articleTitle;
    }

    public void setArticleImage(String articleImage) {
        ArticleImage = articleImage;
    }

    public void setArticleDescription(String articleDescription) {
        ArticleDescription = articleDescription;
    }

    public void setArticleContent(String articleContent) {
        ArticleContent = articleContent;
    }

    public void setArticleCatalog(String articleCatalog) {
        ArticleCatalog = articleCatalog;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getArticleCatalog() {
        return ArticleCatalog;
    }

    public int getID() {
        return ID;
    }

    public int getReadCount() {
        return ReadCount;
    }

    public String getArticleContent() {
        return ArticleContent;
    }

    public String getArticleDescription() {
        return ArticleDescription;
    }

    public String getArticleImage() {
        return ArticleImage;
    }

    public String getArticleTitle() {
        return ArticleTitle;
    }

    public String getArticleWords() {
        return ArticleWords;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public Boolean getShowCustomer() {
        return ShowCustomer;
    }

    public void setShowCustomer(Boolean showCustomer) {
        ShowCustomer = showCustomer;
    }
}
