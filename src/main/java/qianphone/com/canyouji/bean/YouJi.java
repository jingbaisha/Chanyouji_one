package qianphone.com.canyouji.bean;

/**
 * Created by Admin on 2016/12/6.
 */

public class YouJi {

    private String id;
    private String name;
    private String photos_count;
    private String start_date;
    private String end_date;
    private String days;
    private String level;
    private String views_count;
    private String comments_count;
    private String likes_count;
    private String source;
    private String front_cover_photo_url;
    private boolean featured;

    private User user;

    public YouJi(String id, String name, String photos_count, String start_date, String end_date, String days, String level, String views_count, String comments_count, String likes_count, String source,String front_cover_photo_url, boolean featured, User user) {
        this.id = id;
        this.name = name;
        this.photos_count = photos_count;
        this.start_date = start_date;
        this.end_date = end_date;
        this.days = days;
        this.level = level;
        this.views_count = views_count;
        this.comments_count = comments_count;
        this.likes_count = likes_count;
        this.source = source;
        this.front_cover_photo_url = front_cover_photo_url;
        this.featured = featured;
        this.user = user;
    }

    public String getFront_cover_photo_url() {
        return front_cover_photo_url;
    }

    public void setFront_cover_photo_url(String front_cover_photo_url) {
        this.front_cover_photo_url = front_cover_photo_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotos_count() {
        return photos_count;
    }

    public void setPhotos_count(String photos_count) {
        this.photos_count = photos_count;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getViews_count() {
        return views_count;
    }

    public void setViews_count(String views_count) {
        this.views_count = views_count;
    }

    public String getComments_count() {
        return comments_count;
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }

    public String getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(String likes_count) {
        this.likes_count = likes_count;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User {
        private String uid;
        private String uname;
        private String head_url;

        public User(String uid, String uname, String head_url) {
            this.uid = uid;
            this.uname = uname;
            this.head_url = head_url;
        }

        public String getUid() {
            return uid;
        }

        public String getUname() {
            return uname;
        }

        public String getHead_url() {
            return head_url;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }
    }
}
