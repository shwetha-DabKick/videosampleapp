package sample.sdk.dabkick.videosample;

import com.dabkick.videosdk.publicsettings.DabKickMedia;
import com.dabkick.videosdk.publicsettings.DabKickMediaProvider;
import com.dabkick.videosdk.publicsettings.DabKickMediaType;
import com.dabkick.videosdk.publicsettings.DabKickPhoto;
import com.dabkick.videosdk.publicsettings.DabKickVideo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iFocus on 05-04-2018.
 */

public class Util {

    public static DabKickMediaProvider createDabKickProvider(final DabKickVideo selectedVideoInfo) {

        final ArrayList<String> videoCategories = new ArrayList<>(Arrays.asList(
                "TiVo", "Comedy", "Action", "Animation", "Travel", "Arts", "SciFi"));
        final Map<String, ArrayList<DabKickVideo>> videoHolder = new HashMap<>();
        ArrayList<DabKickVideo> videos = Util.getDailyMailVideos();

        final ArrayList<DabKickVideo> videoInfo = new ArrayList<DabKickVideo>();
        videoInfo.add(selectedVideoInfo);

        videoHolder.put(videoCategories.get(0), videos);
        videoHolder.put(videoCategories.get(1), copyList(1, 5));
        videoHolder.put(videoCategories.get(2), copyList(2, 6));
        videoHolder.put(videoCategories.get(3), copyList(3, 7));
        videoHolder.put(videoCategories.get(4), copyList(4, 8));
        videoHolder.put(videoCategories.get(5), copyList(0, 4));
        videoHolder.put(videoCategories.get(6), copyList(1, 5));

        final ArrayList<String> photoCategories = new ArrayList<>(Arrays.asList(
                "My Photos", "Your Photos", "Our Photos"));
        final Map<String, ArrayList<DabKickPhoto>> photoHolder = new HashMap<>();

        ArrayList<DabKickPhoto> photos = getPhotos();
        photoHolder.put(photoCategories.get(0), photos);
        photoHolder.put(photoCategories.get(1), photos);
        photoHolder.put(photoCategories.get(2), photos);


        return new DabKickMediaProvider() {
            @Override
            public List<DabKickMedia> provideMedia(DabKickMediaType mediaType, String category, int offset) {
                if (mediaType == DabKickMediaType.VIDEO) {
                    int totalSize = videoHolder.get(category).size();
                    int endIndex = Math.min(totalSize, offset + 3);
                    List<DabKickMedia> list = new ArrayList<>();
                    list.addAll(videoHolder.get(category).subList(offset, endIndex));
                    return list;
                } else {
                    int totalSize = photoHolder.get(category).size();
                    int endIndex = Math.min(totalSize, offset + 3);
                    List<DabKickMedia> list = new ArrayList<>();
                    list.addAll(photoHolder.get(category).subList(offset, endIndex));
                    return list;
                }

            }

            @Override
            public List<String> provideCategories(DabKickMediaType mediaType, int offset) {
                if (mediaType == DabKickMediaType.VIDEO) {
                    if (offset == videoCategories.size()) {
                        // cannot provide any more video categories
                        return new ArrayList<>();
                    }
                    int endIndex = Math.min(videoCategories.size(), offset + 5);
                    return new ArrayList<>(videoCategories.subList(offset, endIndex));
                } else {
                    if (offset == photoCategories.size()) {
                        // cannot provide any more video categories
                        return new ArrayList<>();
                    }
                    int endIndex = Math.min(photoCategories.size(), offset + 5);
                    return new ArrayList<>(photoCategories.subList(offset, endIndex));
                }
            }

            @Override
            public List<DabKickMedia> startDabKickWithMedia() {
                List<DabKickMedia> smallerList = new ArrayList<>();
                if (videoInfo != null && videoInfo.size() > 0 && (videoInfo.get(0) != null)) {
                    smallerList.add(videoInfo.get(0));
                    videoInfo.clear();
                }
                return smallerList;
            }
        };
    }

    private static ArrayList<DabKickVideo> copyList(int start, int end) {
        ArrayList<DabKickVideo> result = new ArrayList<>(), list = getDailyMailVideos();

        for (int i = start; i < end; i++) {
            result.add(list.get(i));
        }

        return result;
    }

    private static ArrayList<DabKickPhoto> getPhotos() {
        String[] urls = new String[]{
                "http://1.bp.blogspot.com/-o_WoC3j8vvw/Uccil3irqgI/AAAAAAAAAE8/Bhk_heAdHus/s1600/Windows+Azure+Marketplace.png",
                "http://1npqm93jwgvp2odap33154j8-wpengine.netdna-ssl.com/wp-content/uploads/IMG_0170-665x392.jpg",
                "http://2.bp.blogspot.com/-2AgN9-PGj2I/Ty3Pmf1yUeI/AAAAAAAACdI/NQaQ8cnpvvw/w1200-h630-p-k-no-nu/Bing_Maps.jpg",
                "http://2.bp.blogspot.com/-C0KdW7S_LAs/VY_LL8YRrdI/AAAAAAAAAzQ/dn7WKSkcoKg/s1600/PDF%2BCreator%2BSetup%2BOptions.png",
                "http://2.bp.blogspot.com/-EKpj9EGy6XA/VZKYgVg7KBI/AAAAAAAAA0M/9uIkVGXweJM/s1600/Duck%2BSearch%2BAddon.png",
                "http://2.bp.blogspot.com/-JRVGc0JfuXg/VZKaN18qnOI/AAAAAAAAA0g/ewSEh8ukRac/s1600/Search%2BPrefs%2BDuck.png",
                "http://2.bp.blogspot.com/-gOKiv8RoWf4/Tzb-bdsqKWI/AAAAAAAACeM/57Eg0PGBgZY/s1600/BingMapsBirdsEye.jpg",
                "http://2.bp.blogspot.com/-puzCGHXlymQ/U9hsd1oASRI/AAAAAAAAALk/Sx_CxbBvOSg/s1600/iphone1.png",
                "http://2.bp.blogspot.com/-vZ8qiJeZvuE/T0BW4x1kUUI/AAAAAAAACgE/HGXxEvEiPXs/s1600/mobile-application-development.jpg",
                "http://3.bp.blogspot.com/-NVC9zsVMzvM/UTj3movLg7I/AAAAAAAABUA/TbN23d14ZqE/s1600/ios-technology-stack.png",
                "http://3.bp.blogspot.com/-RB1scRG_VpA/Tzb-ape0a9I/AAAAAAAACeE/OwHuBP0KJ5I/s1600/BingMapsAerial.jpg",
                "http://3.bp.blogspot.com/-Wle90ycFnKc/T0BJek9sQVI/AAAAAAAACf8/Ts1s4nK499Y/s1600/BingMaps3dCity.jpg",
                "http://3.bp.blogspot.com/-cR1rRnvLVWg/UEjNCK4GJjI/AAAAAAAAAVQ/OqDRXJ5Jmq8/s400/bing+Search.jpg",
                "http://3.bp.blogspot.com/-oVyjLtedIZA/T0YR8LffBJI/AAAAAAAABAw/7MUEG-e0exw/s1600/Objective_C_Programmin_Language.png",
                "http://4.bp.blogspot.com/-F1uo7Q-uHrM/VZKXEwIfyRI/AAAAAAAAA0A/7XM7TbzIfm0/s1600/FF-Options-Search.png",
                "http://4.bp.blogspot.com/-Yl9sW5F9EzM/VY_tDP9Rh6I/AAAAAAAAAzs/83H6i6I-kU4/s1600/Web%2BCompanion%2BUninstaller.png",
                "http://4.bp.blogspot.com/-d8n8CxHz7CE/U-tt7rCzdcI/AAAAAAAAAgQ/DwW4VkowjLU/s1600/OID11G-ldapbind.png",
                "http://4.bp.blogspot.com/-qt4K-eW4f5k/VZLXGEvTx2I/AAAAAAAAA04/_EihGGdQB-g/s1600/about-newtab-bing.png",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2009/02/resume-search-java-tx-no-tilde.jpg",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2009/02/resume-search-zip-code-radius-tx.jpg",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2011/01/LinkedIn_Advanced_Search_Operators_Chart_Small.png",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2011/01/LinkedIn_Advanced_Search_Operators_Example_Search_1.png",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2011/01/LinkedIn_Save_This_Search_Window.png",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2013/04/Monster-logo-small1-242x300.jpg",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2013/07/Bing-Proximity-Search-Used-to-Work.png",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2013/07/Exalead-iOS-proximity-search-results-1.png",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2013/07/Google-Around-Proximity-Operator-OpenGL-Maya-1.png",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2013/08/Stack-Overflow-4-Great-Answers.jpg",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2013/08/Stack-Overflow-High-Reputation.jpg",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2013/08/Stack-Overflow-Main.jpg",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2013/08/Stack-Overflow-Reputation-Search-1.jpg",
                "http://4587-presscdn-0-49.pagely.netdna-cdn.com/wp-content/uploads/2013/08/Stack-Overflow-X-Ray-Search-1.jpg",
                "http://68.media.tumblr.com/3295ed8fe4bf10853aa613725d599eec/tumblr_inline_o8gubz34k21tqkg6h_540.png",
                "http://a2apple.com/beta/wp-content/themes/a2a/uploads/698328.png",
                "http://adamborek.com/wp-content/uploads/2017/06/pexels-photo-131050-1.jpg",
                "http://alpascual.com/files/media/image/WindowsLiveWriter/TheOrchardProjectasthenewblogengine_E8F8/image_2.png",
                "http://appleinsider.com/articles/16/06/27/safari-10-brings-fast-native-app-extensions-to-the-macos-browser-web-content",
                "http://ars.els-cdn.com/content/image/1-s2.0-S0952197615000329-gr1.jpg",
                "http://ars.els-cdn.com/content/image/1-s2.0-S1751157711000988-gr1.jpg",
                "http://ars.els-cdn.com/content/image/1-s2.0-S1751157711000988-gr2.jpg",
                "http://ars.els-cdn.com/content/image/1-s2.0-S1751157711000988-gr3.jpg",
                "http://ashishkakkad.com/wp-content/uploads/2015/10/Populated-Table-via-Alamofire-and-SwiftyJSON.png",
                "http://blog.oskoui-oskoui.com/wp-content/uploads/2015/11/Screen-Shot-2015-11-24-at-11.02.59-AM.png",
                "http://blogcdn.spyfu.com/wp-content/uploads/2016/05/02-Headline.png",
                "http://c179631.r31.cf0.rackcdn.com/Infographic_Programming_Rackspace_Final_Version.png",
                "http://cdn.aliengearholsters.com/media/wysiwyg/real-world-concealed-carry.jpg",
                "http://cdn.ientry.com/sites/webpronews/article_pics/TIOBEindex.png",
                "http://cdn.iphoneincanada.ca/wp-content/uploads/2017/03/tiobe-index-2017-swift.jpg",
                "http://cdn.osxdaily.com/wp-content/uploads/2014/12/change-safari-search-engine-default-ios.png",
                "http://cdniphone.i-culture.nl/wp-content/uploads/2010/05/bing-iphone.jpg",
                "http://cocoadocs.org/docsets/DZNPhotoPickerController/2.0/preview.png",
                "http://codeinobjc.com/wp-content/uploads/2016/05/Screen-Shot-2016-05-31-at-9.28.32-AM.png",
                "http://codeinobjc.com/wp-content/uploads/2016/05/cropped-vintage-1170656_1920-2.jpg",
                "http://codrspace.com/site_media/media/6c3a7fa474fe1.jpg",
                "http://codrspace.com/site_media/media/eda6646a74e81.jpg",
                "http://computerscijournal.org/wp-content/uploads/2014/06/Vol07_No1_Compa_Sushm_Fig1.jpg",
                "http://computerscijournal.org/wp-content/uploads/2014/06/Vol07_No1_Compa_Sushm_T1.jpg"
        };

        ArrayList<DabKickPhoto> res = new ArrayList<>();
        for (String s : urls) {
            res.add(new DabKickPhoto(s));
        }
        return res;
    }

    static ArrayList<DabKickVideo> getDailyMailVideos() {

        DabKickVideo detaila = new DabKickVideo("Sylvester", "Chiffon Cardigan", "40000","http://qvc.scene7.com/is/image/QVC/pic/hp/tsv_20180216.jpg?qlt=95,1&$aemtsvimage$ ",
                "http://qvc0.content.video.llnw.net/smedia/4826bb17d50c481b98a9e427e1c29c79/Y-/AwwQn_RUR5K0hivvTyecrE_Av5AHFfD0YaJ-RbrYU/a301149-18021616.mp4");
//        DabKickVideo detail0 = new DabKickVideo("Sylvester", "Vitamix 16-in-1", "40000","https://www.superfood.nl/media/catalog/product/cache/2/image/9df78eab33525d08d6e5fb8d27136e95/v/i/vitamix_5200_blender-high_speed-red.jpg",
//                "http://qvc0.content.video.llnw.net/smedia/4826bb17d50c481b98a9e427e1c29c79/hp/uLr2CRCk2kuQ_5zlQmWeYspJYmmwWRgZLO71eEVdI/ppe-k46761-vitamix-explorian-blender.mp4");
        DabKickVideo detail1 = new DabKickVideo("Sylvester", "Mission Impossible", "40000","http://cdn2-www.craveonline.com/assets/uploads/2015/07/Mission-Impossible-Tom-Cruise.jpg",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4");
        DabKickVideo detail2 = new DabKickVideo("Sylvester", "Elephants Dream", "40000","https://www.transformingthechurch.org/elephants_dream_title_658w.jpg",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
        DabKickVideo detail3 = new DabKickVideo("Sylvester", "Bull Run", "40000","https://s.hdnux.com/photos/26/01/16/5772198/6/920x920.jpg",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4");
        DabKickVideo detail4 = new DabKickVideo("Sylvester", "Smoking Tire", "40000","http://www.ridelust.com/wp-content/uploads/2011/08/Picture-13-600x305.jpg",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4");
        DabKickVideo detail5 = new DabKickVideo("Sylvester", "Mission Impos. Trailer", "40000","https://vignette.wikia.nocookie.net/missionimpossible/images/d/da/Slider-TV.jpg/revision/latest/scale-to-width-down/590?cb=20110713140930",
                "http://video.dailymail.co.uk/video/1418450360/2014/10/1418450360_3860569858001_Mission-Impossible-1-Trailer.mp4");
        DabKickVideo detail6 = new DabKickVideo("Sylvester", "Soccer Highlights", "40000","https://www.mercurynews.com/wp-content/uploads/2017/04/sjm-quakes-0307-002.jpg?w=452",
                "https://manifest.prod.boltdns.net/manifest/v1/hls/v5/clear/5530036774001/134ce722-22c4-44af-a8a7-111300e4867c/10s/master.m3u8?fastly_token=NWM1ZGQ3YzlfNDA2ODU0NmY1NjIyZTkyNGQxNjk3NThjYjNmYmJmMDY4OTk5ZjRmMzc5OTM2NDlmZDM2OWE0NGY1MzczYzQxNQ%3D%3D");
        DabKickVideo detail7 = new DabKickVideo("Sylvester", "Football Highlights", "40000","http://img.bleacherreport.net/img/images/photos/003/052/463/df9e69c46443349f3ccc6905b2053094_crop_north.jpg?1410055466&w=630&h=420",
                "https://media.gannett-cdn.com/35553589001/35553589001_5642705023001_5642552128001.mp4");
        DabKickVideo detail8 = new DabKickVideo("Sylvester", "Elon @ TED", "40000","https://pi.tedcdn.com/r/pl.tedcdn.com/social/ted-logo-fb.png?v=wAff13s",
                "https://download.ted.com/talks/ElonMusk_2017-600k.mp4");

        return new ArrayList<>(Arrays.asList(detaila, detail1, detail2, detail3, detail4, detail5, detail6, detail7, detail8));
    }
}
