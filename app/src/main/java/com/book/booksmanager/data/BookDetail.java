package com.book.booksmanager.data;

import java.util.List;

public class BookDetail {

    /**
     * max : 10
     * numRaters : 235
     * average : 8.9
     * min : 0
     */

    private RatingBean rating;
    /**
     * rating : {"max":10,"numRaters":235,"average":"8.9","min":0}
     * subtitle : 影响了我的二十篇小说
     * author : ["（奥）卡夫卡 等","苏童 选编"]
     * pubdate : 2005-05
     * tags : [{"count":84,"name":"苏童","title":"苏童"},{"count":63,"name":"外国文学","title":"外国文学"},{"count":48,"name":"小说","title":"小说"},{"count":40,"name":"一生的文学珍藏（外国小说读本）","title":"一生的文学珍藏（外国小说读本）"},{"count":25,"name":"中短篇小说","title":"中短篇小说"},{"count":24,"name":"文学","title":"文学"},{"count":20,"name":"经典","title":"经典"},{"count":14,"name":"文集","title":"文集"}]
     * origin_title :
     * image : https://img3.doubanio.com/mpic/s1331494.jpg
     * binding : 平装
     * translator : []
     * catalog : 1 阿内西阿美女皇后
     2 左撇子
     3 办公室
     4 勒弗戴先生的短暂外出
     5 潜水夫
     6 七层楼
     7 美女与怪物
     8 饥饿艺术家
     9 巨型收音机
     10 佩德罗·巴拉莫
     11 伤心咖啡馆之歌
     12 威克菲尔德
     13 万卡
     14 羊脂球
     15 傻瓜吉姆佩尔
     16 献给爱米丽的一条玫瑰花
     17 阿拉比
     18 第三者
     19 圣诞节忆旧
     20 马辔头
     * pages : 359
     * images : {"small":"https://img3.doubanio.com/spic/s1331494.jpg","large":"https://img3.doubanio.com/lpic/s1331494.jpg","medium":"https://img3.doubanio.com/mpic/s1331494.jpg"}
     * alt : https://book.douban.com/subject/1320928/
     * id : 1320928
     * publisher : 百花文艺出版社
     * isbn10 : 7530641786
     * isbn13 : 9787530641781
     * title : 一生的文学珍藏（外国小说读本）
     * url : https://api.douban.com/v2/book/1320928
     * alt_title :
     * author_intro : 苏童，江南苏州人氏。一九八零年考入北京师范大学中文系，据说在那里度过了最令他难忘的四个年头，而他的大名却仍旧在这所著名学府中被人一再提起。一九八三年投入小说创作，从此一发而不可收，从此中国当代文坛便再难抹去他的形象。他喜欢“新潮”，曾在其中尽情畅游。
     他也喜欢“传统”，喜欢琢磨中国的历史风云。有一天《妻妾成群》问世了，又经电影“大腕”张艺谋之手调弄成了著名的《大红灯笼高高挂》，最后角逐奥斯卡金像奖，虽然结果未尽人意，但中国电影界和文学界都给不大不小地“震”了一下。中国人由《大红灯笼高高挂》而知道《妻妾成群》，最后迷恋上了这位可爱的苏童，而苏童则从《妻妾成群》再一次出发，走上了自己独特的文学之路。
     * summary : 或许小说没有写什么问题，只有怎么写的问题，而怎么写对于作家来说是一个宽阔到无边无际的天问，对于读者来说是一个永远的诱惑。所有来自阅读的惊喜，终将回到不知名的阅读者身体内部或者心灵深处。另外，就像童话之于我们的儿女，我一直觉得短篇小说很像针对成年人的夜间故事。深夜挑灯，在临睡前借助一次轻松的阅读，摸一摸这个世界，让一天的生活始于平庸而终于辉煌，多好。
     * price : 35.0
     */

    private String subtitle;
    private String pubdate;
    private String origin_title;
    private String image;
    private String binding;
    private String catalog;
    private String pages;

    /**
     * small : https://img3.doubanio.com/spic/s1331494.jpg
     * large : https://img3.doubanio.com/lpic/s1331494.jpg
     * medium : https://img3.doubanio.com/mpic/s1331494.jpg
     */

    private ImagesBean images;
    private String alt;
    private String id;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String title;
    private String url;
    private String alt_title;
    private String author_intro;
    private String summary;
    private String price;
    private List<String> author;

    /**
     * count : 84
     * name : 苏童
     * title : 苏童
     */

    private List<TagsBean> tags;
    private List<?> translator;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<?> getTranslator() {
        return translator;
    }

    public void setTranslator(List<?> translator) {
        this.translator = translator;
    }

    public static class RatingBean {
        private String max;
        private String numRaters;
        private String average;
        private int min;

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getNumRaters() {
            return numRaters;
        }

        public void setNumRaters(String numRaters) {
            this.numRaters = numRaters;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean {
        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class TagsBean {
        private int count;
        private String name;
        private String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
