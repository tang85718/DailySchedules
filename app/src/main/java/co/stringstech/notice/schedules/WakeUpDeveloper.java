package co.stringstech.notice.schedules;

import java.util.ArrayList;
import java.util.Calendar;

import co.stringstech.notice.SmartBot;
import co.stringstech.notice.MusicPlayer;
import co.stringstech.notice.ThreadUtil;
import timber.log.Timber;

/**
 * Created by tangxuyao on 2017/9/12.
 * WakeUpDeveloper
 */

public class WakeUpDeveloper extends BaseSchedule {
    private SmartBot smartBot;
    private MusicPlayer musicPlayer;

    public WakeUpDeveloper(SmartBot smartBot, MusicPlayer musicPlayer) {
        this.smartBot = smartBot;
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void executeNow() {
        musicPlayer.duck();
//        String text = String.format(Locale.getDefault(),
//                "大家好，请开发组准备2点半的站会, 记得站会时不要害羞.",
//                smartBot.getName()
//        );

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int days = calendar.get(Calendar.DAY_OF_YEAR);

        ArrayList<String> seg = new ArrayList<>();
        seg.add("泰戈尔说过：像一支和顽强的崖口进行搏斗的狂奔的激流你应该不顾一切纵身跳进那陌生的、不可知的命运，然后，以大无畏的英勇把它完全征服，不管有多少困难向你挑衅");
        seg.add("萨迪说过：事业常成于坚忍，毁于急躁。我在沙漠中曾亲眼看见，匆忙的旅人落在从容的后边；疾驰的骏马落在后头，缓步的骆驼继续向前");
        seg.add("孟子说过: 天将降大任于斯人也，必先苦其心志，劳其筋骨，饿其体肤，行拂乱其所为，所以动心忍性，增益其所不能");
        seg.add("莎士比亚说过：不应当急于求成，应当去熟悉自己的研究对象，锲而不舍，时间会成全一切。凡事开始最难，然而更难的是何以善终");
        seg.add("高尔基说过：不要慨叹生活的痛苦！慨叹是弱者");
        seg.add("雨果说过：书籍便是这种改造灵魂的工具。人类所需要的，是富有启发性的养料。而阅读，则正是这种养料");
        seg.add("马罗说过：永远不要因承认错误而感到羞耻，因为承认错误也可以解释作你今天更聪敏");
        seg.add("尼赫鲁说过：一个人能在战场上制胜千军，但只有战胜自己才是最伟大的胜利者");
        seg.add("爱迪生说过：天才是百分之一的灵感加百分之九十九的汗水");

        int index = days % seg.size();
        String text = seg.get(index);

        Timber.d("WakeUpDeveloper:%d, %d, %d, %s", days, index, seg.size(), text);

//        ThreadUtil.run(() -> smartBot.speak(text, () -> musicPlayer.unduck()));
        smartBot.speak(text, () -> musicPlayer.unduck());
    }
}
