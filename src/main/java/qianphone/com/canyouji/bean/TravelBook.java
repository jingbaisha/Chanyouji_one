package qianphone.com.canyouji.bean;

/**
 * Created by Admin on 2016/11/9.
 */
public class TravelBook {
    private String bookUrl;
    private String title;
    private String headImage;
    private String userName;
    private String userHeadImg;
    private String startTime;
    private String routeDays;
    private String bookImgNum;
    private String viewCount;
    private String likeCount;
    private String commentCount;
    private String text;
    private String elite;
    public TravelBook(String bookUrl, String title, String headImage, String userName, String userHeadImg, String startTime, String routeDays, String bookImgNum, String viewCount, String likeCount, String commentCount, String text, String elite) {
        this.bookUrl = bookUrl;
        this.title = title;
        this.headImage = headImage;
        this.userName = userName;
        this.userHeadImg = userHeadImg;
        this.startTime = startTime;
        this.routeDays = routeDays;
        this.bookImgNum = bookImgNum;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.text = text;
        this.elite = elite;
    }



    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRouteDays() {
        return routeDays;
    }

    public void setRouteDays(String routeDays) {
        this.routeDays = routeDays;
    }

    public String getBookImgNum() {
        return bookImgNum;
    }

    public void setBookImgNum(String bookImgNum) {
        this.bookImgNum = bookImgNum;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getElite() {
        return elite;
    }

    public void setElite(String elite) {
        this.elite = elite;
    }
}
