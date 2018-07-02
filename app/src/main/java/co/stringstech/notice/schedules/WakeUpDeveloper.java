package co.stringstech.notice.schedules;

import java.util.ArrayList;
import java.util.Calendar;

import co.stringstech.notice.App;
import co.stringstech.notice.Broadcaster;
import timber.log.Timber;

/**
 * Created by tangxuyao on 2017/9/12.
 * WakeUpDeveloper
 */

public class WakeUpDeveloper extends BaseSchedule {
    private App app;

    public WakeUpDeveloper(App app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "WakeupDeveloper";
    }

    @Override
    public void executeNow() {
        Broadcaster alice = new Broadcaster(app);
        app.musicPlayer.duck();
//        String text = String.format(Locale.getDefault(),
//                "大家好，请开发组准备2点半的站会, 记得站会时不要害羞.",
//                smartBot.getName()
//        );

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        int days = calendar.get(Calendar.DAY_OF_YEAR);
//
//        ArrayList<String> seg = new ArrayList<>();
//        seg.add("泰戈尔说过：像一支和顽强的崖口进行搏斗的狂奔的激流你应该不顾一切纵身跳进那陌生的、不可知的命运，然后，以大无畏的英勇把它完全征服，不管有多少困难向你挑衅");
//        seg.add("萨迪说过：事业常成于坚忍，毁于急躁。我在沙漠中曾亲眼看见，匆忙的旅人落在从容的后边；疾驰的骏马落在后头，缓步的骆驼继续向前");
//        seg.add("孟子说过: 天将降大任于斯人也，必先苦其心志，劳其筋骨，饿其体肤，行拂乱其所为，所以动心忍性，增益其所不能");
//        seg.add("莎士比亚说过：不应当急于求成，应当去熟悉自己的研究对象，锲而不舍，时间会成全一切。凡事开始最难，然而更难的是何以善终");
//        seg.add("华罗庚说过：时间是由分秒积成的，善于利用零星时间的人，才会做出更大的成绩来");
//        seg.add("雨果说过：书籍便是这种改造灵魂的工具。人类所需要的，是富有启发性的养料。而阅读，则正是这种养料");
//        seg.add("苏轼说过：古之立大事者，不惟有超世之才，亦必有坚忍不拔之志");
//        seg.add("尼赫鲁说过：一个人能在战场上制胜千军，但只有战胜自己才是最伟大的胜利者");
//        seg.add("爱迪生说过：天才是百分之一的灵感加百分之九十九的汗水");
//
//        int index = days % seg.size();
//        String text = seg.get(index);

        alice.speak("温馨 提示，请开发组准备2点半的站会.", () -> app.musicPlayer.unduck());
//        smartBot.speak(String.format(Locale.getDefault(), "休息好了, 清醒大脑开始工作哟～, 伟大的%s", text), () -> musicPlayer.unduck());
    }
}
