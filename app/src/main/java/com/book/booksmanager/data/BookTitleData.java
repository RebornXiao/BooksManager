package com.book.booksmanager.data;

import java.util.List;

public class BookTitleData {

    /**
     * count : 20
     * start : 0
     * total : 46090
     * books : [{"url":"https://api.douban.com/v2/book/1320928","image":"https://img3.doubanio.com/mpic/s1331494.jpg","id":"1320928","title":"一生的文学珍藏（外国小说读本）"},{"url":"https://api.douban.com/v2/book/1039084","image":"https://img3.doubanio.com/mpic/s1333352.jpg","id":"1039084","title":"简.爱-学地漫读外国文学名著系列"},{"url":"https://api.douban.com/v2/book/1080773","image":"https://img3.doubanio.com/mpic/s1628955.jpg","id":"1080773","title":"外国文学简编(欧美部分)"},{"url":"https://api.douban.com/v2/book/1964013","image":"https://img1.doubanio.com/mpic/s1993569.jpg","id":"1964013","title":"母与子（二十世纪外国文学丛书/上中下）"},{"url":"https://api.douban.com/v2/book/1949664","image":"https://img3.doubanio.com/mpic/s7664210.jpg","id":"1949664","title":"未来主义·超现实主义（外国文学流派研究资料丛书）"},{"url":"https://api.douban.com/v2/book/1110605","image":"https://img3.doubanio.com/mpic/s6195441.jpg","id":"1110605","title":"20世纪外国文学专题"},{"url":"https://api.douban.com/v2/book/4286982","image":"https://img1.doubanio.com/mpic/s4324468.jpg","id":"4286982","title":"外国文学插图精鉴"},{"url":"https://api.douban.com/v2/book/3465798","image":"https://img3.doubanio.com/mpic/s5981755.jpg","id":"3465798","title":"20世纪外国文学专题十三讲"},{"url":"https://api.douban.com/v2/book/1047716","image":"https://img3.doubanio.com/mpic/s1035340.jpg","id":"1047716","title":"寻找另外一种声音\u2014\u2014我读外国文学"},{"url":"https://api.douban.com/v2/book/3784939","image":"https://img1.doubanio.com/mpic/s3828037.jpg","id":"3784939","title":"外国文学作品选（第三卷）"},{"url":"https://api.douban.com/v2/book/3209399","image":"https://img3.doubanio.com/mpic/s3384734.jpg","id":"3209399","title":"外国文学作品选"},{"url":"https://api.douban.com/v2/book/2109789","image":"https://img1.doubanio.com/mpic/s4391067.jpg","id":"2109789","title":"中国20世纪外国文学翻译史（上下）"},{"url":"https://api.douban.com/v2/book/3236207","image":"https://img3.doubanio.com/mpic/s3641605.jpg","id":"3236207","title":"张守义外国文学插图集"},{"url":"https://api.douban.com/v2/book/6753273","image":"https://img3.doubanio.com/mpic/s6913342.jpg","id":"6753273","title":"外国文学简编（欧美部分）"},{"url":"https://api.douban.com/v2/book/3196065","image":"https://img3.doubanio.com/mpic/s3506813.jpg","id":"3196065","title":"外国文学简编"},{"url":"https://api.douban.com/v2/book/1963186","image":"https://img1.doubanio.com/mpic/s3336228.jpg","id":"1963186","title":"外国鬼怪文学名作大观"},{"url":"https://api.douban.com/v2/book/2109474","image":"https://img3.doubanio.com/mpic/s2548025.jpg","id":"2109474","title":"简.爱-外国文学经典"},{"url":"https://api.douban.com/v2/book/3219809","image":"https://img3.doubanio.com/mpic/s4487972.jpg","id":"3219809","title":"外国文学作品选（第一卷 古代部分）"},{"url":"https://api.douban.com/v2/book/25773282","image":"https://img3.doubanio.com/mpic/s27142654.jpg","id":"25773282","title":"20世纪外国文学简史"},{"url":"https://api.douban.com/v2/book/4852275","image":"https://img1.doubanio.com/mpic/s4417257.jpg","id":"4852275","title":"外国文学鉴赏辞典大系·外国戏剧鉴赏辞典⑶（现当代卷）"}]
     */

    private int count;
    private int start;
    private int total;
    /**
     * url : https://api.douban.com/v2/book/1320928
     * image : https://img3.doubanio.com/mpic/s1331494.jpg
     * id : 1320928
     * title : 一生的文学珍藏（外国小说读本）
     */

    private List<BookInfo> books;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<BookInfo> getBooks() {
        return books;
    }

    public void setBooks(List<BookInfo> books) {
        this.books = books;
    }

}
