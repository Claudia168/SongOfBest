package com.example.network.bean;

import java.util.List;

public class QQMusicDetailsBean {


    /**
     * pageToken : null
     * dataType : music
     * data : [{"languages":["其它"],"singers":[{"name":"莫文蔚","id":"000cISVf2QqLc6"}],"publishOrgs":[],"albumTitle":"空","hasMv":true,"catId1":null,"durationMin":4.1,"description":"","lyricid":"201845729","subTitle":"","title":"北极光 + 盛夏的果实","pressDate":"2007-11-01","mvId":"b0016hhnhqh","fileOptions":[{"url":null,"format":"m4a","id":"003UNy880mualv","kbps":320,"sizeM":0},{"url":"http://dl.stream.qqmusic.qq.com/C400003UNy880mualv.m4a?vkey=D3488A3100224FDA0F5DAED35ADF4185CC10C5E3A7ECE13A80C54AACF5F0E4DC8A145FCF4761E636CFCE2EFE7B5E51A4B9FB0E12E4CF4966&guid=2953817850&uin=0&fromtag=66","format":"m4a","id":"003UNy880mualv","kbps":128,"sizeM":3.76}],"coverUrl":"https://y.gtimg.cn/music/photo_new/T002R300x300M000001ZaCQY2OxVMg.jpg","albumId":"001ZaCQY2OxVMg","url":"https://y.qq.com/n/yqq/song/003UNy880mualv.html","lyrics":"[ti:北极光+盛夏的果实]\n[ar:莫文蔚]\n[al:   ]\n[by:]\n[offset:0]\n[00:00.00]北极光 + 盛夏的果实 - 莫文蔚 (Karen Mok)\n[00:01.58]词：黄伟文\n[00:03.17]曲：Meyna Co\n[00:04.75]编曲：张佳添\n[00:06.34]几多晚\n[00:08.32]\n[00:08.86]逝去了不返\n[00:11.41]那份憔悴已深陷发肤之间\n[00:17.10]\n[00:17.90]时间累积\n[00:20.39]这盛夏的果实\n[00:22.67]\n[00:23.70]回忆里寂寞的香气\n[00:27.51]\n[00:28.69]等一世为看一眼\n[00:31.60]不要再想你\n[00:34.47]虽然这并不是我本意\n[00:39.11]\n[00:41.12]声声叹\n[00:43.55]溶化了冰山\n[00:46.10]却未能够叫天为我睁开眼\n[00:51.81]\n[00:52.59]别用沉默\n[00:55.11]再去掩饰什么\n[00:57.40]\n[00:58.37]当结果是那么赤裸裸\n[01:02.27]\n[01:03.42]心中纵是有所盼\n[01:06.31]严寒没有减\n[01:09.19]风很冷我的手已渐蓝\n[01:13.66]\n[01:15.70]不要刻意说\n[01:20.33]\n[01:21.41]啊 越无常\n[01:25.08]\n[01:27.29]美景良辰未细赏\n[01:32.67]我已为你着凉\n[01:38.11]\n[01:44.63]你曾说过\n[01:46.50]\n[01:47.14]会永远爱我\n[01:49.63]也许承诺不过证明没把握\n[01:55.33]\n[01:56.21]莫道为了你\n[01:58.69]我享受着期望\n[02:01.04]\n[02:01.97]极地尽处有我靠的岸\n[02:05.73]\n[02:07.01]其实不必说什么\n[02:09.89]才能离开我\n[02:12.82]起码那些经过属于我\n[02:17.21]\n[02:42.50]几多晚\n[02:44.96]逝去了不返\n[02:47.57]不再见你你才会把我记起\n[02:53.24]\n[02:53.99]夜夜在冀盼\n[02:56.54]既凄艳又靡烂\n[02:58.88]\n[02:59.79]回忆里爱情的香气\n[03:03.73]\n[03:04.87]等一世为看一眼\n[03:07.72]如何又算贪\n[03:10.62]早知你爱不起\n[03:13.51]怨亦难\n[03:15.02]\n[03:17.17]不要刻意说\n[03:21.91]\n[03:22.92]你还爱我\n[03:26.59]\n[03:28.70]美景良辰未细赏\n[03:34.02]我已为你着凉\n[03:39.52]\n[03:40.29]如果你会梦见我\n[03:45.62]请你再抱紧我","writers":[{"name":"Meyna Co","id":"0032fmHO2UDnV3"}],"id":"201845729","catName1":null}]
     * appCode : qqmusic
     * retcode : 000000
     * hasNext : false
     */

    private Object pageToken;
    private String dataType;
    private String appCode;
    private String retcode;
    private boolean hasNext;
    private List<DataBean> data;

    public Object getPageToken() {   //当前页数
        return pageToken;
    }

    public void setPageToken(Object pageToken) {
        this.pageToken = pageToken;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * languages : ["其它"]
         * singers : [{"name":"莫文蔚","id":"000cISVf2QqLc6"}]
         * publishOrgs : []
         * albumTitle : 空
         * hasMv : true
         * catId1 : null
         * durationMin : 4.1
         * description :
         * lyricid : 201845729
         * subTitle :
         * title : 北极光 + 盛夏的果实
         * pressDate : 2007-11-01
         * mvId : b0016hhnhqh
         * fileOptions : [{"url":null,"format":"m4a","id":"003UNy880mualv","kbps":320,"sizeM":0},{"url":"http://dl.stream.qqmusic.qq.com/C400003UNy880mualv.m4a?vkey=D3488A3100224FDA0F5DAED35ADF4185CC10C5E3A7ECE13A80C54AACF5F0E4DC8A145FCF4761E636CFCE2EFE7B5E51A4B9FB0E12E4CF4966&guid=2953817850&uin=0&fromtag=66","format":"m4a","id":"003UNy880mualv","kbps":128,"sizeM":3.76}]
         * coverUrl : https://y.gtimg.cn/music/photo_new/T002R300x300M000001ZaCQY2OxVMg.jpg
         * albumId : 001ZaCQY2OxVMg
         * url : https://y.qq.com/n/yqq/song/003UNy880mualv.html
         * lyrics : [ti:北极光+盛夏的果实]
         [ar:莫文蔚]
         [al:   ]
         [by:]
         [offset:0]
         [00:00.00]北极光 + 盛夏的果实 - 莫文蔚 (Karen Mok)
         [00:01.58]词：黄伟文
         [00:03.17]曲：Meyna Co
         [00:04.75]编曲：张佳添
         [00:06.34]几多晚
         [00:08.32]
         [00:08.86]逝去了不返
         [00:11.41]那份憔悴已深陷发肤之间
         [00:17.10]
         [00:17.90]时间累积
         [00:20.39]这盛夏的果实
         [00:22.67]
         [00:23.70]回忆里寂寞的香气
         [00:27.51]
         [00:28.69]等一世为看一眼
         [00:31.60]不要再想你
         [00:34.47]虽然这并不是我本意
         [00:39.11]
         [00:41.12]声声叹
         [00:43.55]溶化了冰山
         [00:46.10]却未能够叫天为我睁开眼
         [00:51.81]
         [00:52.59]别用沉默
         [00:55.11]再去掩饰什么
         [00:57.40]
         [00:58.37]当结果是那么赤裸裸
         [01:02.27]
         [01:03.42]心中纵是有所盼
         [01:06.31]严寒没有减
         [01:09.19]风很冷我的手已渐蓝
         [01:13.66]
         [01:15.70]不要刻意说
         [01:20.33]
         [01:21.41]啊 越无常
         [01:25.08]
         [01:27.29]美景良辰未细赏
         [01:32.67]我已为你着凉
         [01:38.11]
         [01:44.63]你曾说过
         [01:46.50]
         [01:47.14]会永远爱我
         [01:49.63]也许承诺不过证明没把握
         [01:55.33]
         [01:56.21]莫道为了你
         [01:58.69]我享受着期望
         [02:01.04]
         [02:01.97]极地尽处有我靠的岸
         [02:05.73]
         [02:07.01]其实不必说什么
         [02:09.89]才能离开我
         [02:12.82]起码那些经过属于我
         [02:17.21]
         [02:42.50]几多晚
         [02:44.96]逝去了不返
         [02:47.57]不再见你你才会把我记起
         [02:53.24]
         [02:53.99]夜夜在冀盼
         [02:56.54]既凄艳又靡烂
         [02:58.88]
         [02:59.79]回忆里爱情的香气
         [03:03.73]
         [03:04.87]等一世为看一眼
         [03:07.72]如何又算贪
         [03:10.62]早知你爱不起
         [03:13.51]怨亦难
         [03:15.02]
         [03:17.17]不要刻意说
         [03:21.91]
         [03:22.92]你还爱我
         [03:26.59]
         [03:28.70]美景良辰未细赏
         [03:34.02]我已为你着凉
         [03:39.52]
         [03:40.29]如果你会梦见我
         [03:45.62]请你再抱紧我
         * writers : [{"name":"Meyna Co","id":"0032fmHO2UDnV3"}]
         * id : 201845729
         * catName1 : null
         */

        private String albumTitle;
        private boolean hasMv;
        private Object catId1;
        private double durationMin;
        private String description;
        private String lyricid;
        private String subTitle;
        private String title;
        private String pressDate;
        private String mvId;
        private String coverUrl;
        private String albumId;
        private String url;
        private String lyrics;
        private String id;
        private Object catName1;
        private List<String> languages;
        private List<SingersBean> singers;
        private List<?> publishOrgs;
        private List<FileOptionsBean> fileOptions;
        private List<WritersBean> writers;

        public String getAlbumTitle() {
            return albumTitle;
        }

        public void setAlbumTitle(String albumTitle) {
            this.albumTitle = albumTitle;
        }

        public boolean isHasMv() {
            return hasMv;
        }

        public void setHasMv(boolean hasMv) {
            this.hasMv = hasMv;
        }

        public Object getCatId1() {
            return catId1;
        }

        public void setCatId1(Object catId1) {
            this.catId1 = catId1;
        }

        public double getDurationMin() {
            return durationMin;
        }

        public void setDurationMin(double durationMin) {
            this.durationMin = durationMin;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLyricid() {
            return lyricid;
        }

        public void setLyricid(String lyricid) {
            this.lyricid = lyricid;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPressDate() {
            return pressDate;
        }

        public void setPressDate(String pressDate) {
            this.pressDate = pressDate;
        }

        public String getMvId() {
            return mvId;
        }

        public void setMvId(String mvId) {
            this.mvId = mvId;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLyrics() {
            return lyrics;
        }

        public void setLyrics(String lyrics) {
            this.lyrics = lyrics;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getCatName1() {
            return catName1;
        }

        public void setCatName1(Object catName1) {
            this.catName1 = catName1;
        }

        public List<String> getLanguages() {
            return languages;
        }

        public void setLanguages(List<String> languages) {
            this.languages = languages;
        }

        public List<SingersBean> getSingers() {
            return singers;
        }

        public void setSingers(List<SingersBean> singers) {
            this.singers = singers;
        }

        public List<?> getPublishOrgs() {
            return publishOrgs;
        }

        public void setPublishOrgs(List<?> publishOrgs) {
            this.publishOrgs = publishOrgs;
        }

        public List<FileOptionsBean> getFileOptions() {
            return fileOptions;
        }

        public void setFileOptions(List<FileOptionsBean> fileOptions) {
            this.fileOptions = fileOptions;
        }

        public List<WritersBean> getWriters() {
            return writers;
        }

        public void setWriters(List<WritersBean> writers) {
            this.writers = writers;
        }

        public static class SingersBean {
            /**
             * name : 莫文蔚
             * id : 000cISVf2QqLc6
             */

            private String name;
            private String id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class FileOptionsBean {
            /**
             * url : null
             * format : m4a
             * id : 003UNy880mualv
             * kbps : 320
             * sizeM : 0
             */

            private Object url;
            private String format;
            private String id;
            private int kbps;
            private int sizeM;

            public Object getUrl() {
                return url;
            }

            public void setUrl(Object url) {
                this.url = url;
            }

            public String getFormat() {
                return format;
            }

            public void setFormat(String format) {
                this.format = format;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getKbps() {
                return kbps;
            }

            public void setKbps(int kbps) {
                this.kbps = kbps;
            }

            public int getSizeM() {
                return sizeM;
            }

            public void setSizeM(int sizeM) {
                this.sizeM = sizeM;
            }
        }

        public static class WritersBean {
            /**
             * name : Meyna Co
             * id : 0032fmHO2UDnV3
             */

            private String name;
            private String id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
