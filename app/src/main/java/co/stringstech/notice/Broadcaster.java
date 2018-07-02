package co.stringstech.notice;

import android.content.Context;
import android.os.Bundle;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import timber.log.Timber;

/**
 * Created by tangxuyao on 2017/10/17.
 * Broadcaster
 */

public class Broadcaster implements InitListener, SynthesizerListener {

    private final String name = "爱丽丝";
    private SpeechSynthesizer synthesizer;

    private long createTime = 0;

    public interface OnCompleteListener {
        void onFinished();
    }

    private OnCompleteListener onCompleteListener;

    /**
     * 发言人id
     * <item>xiaoyan</item> 女
     * <item>xiaoyu</item> 男
     * <item>catherine</item> 女-英文
     * <item>henry</item>
     * <item>vimary</item> 女 非中文
     * <item>vixy</item>
     * <item>xiaoqi</item> 女
     * <item>vixf</item> 男 - 超专业广播腔
     * <item>xiaomei</item>
     * <item>xiaolin</item>
     * <item>xiaorong</item> 女 非普通话
     * <item>xiaoqian</item> 女 普通
     * <item>xiaokun</item> 男 - 非普通话
     * <item>xiaoqiang</item> 男 - 长沙
     * <item>vixying</item> 女 - 大东北
     * <item>xiaoxin</item> 男 - 小新
     * <item>nannan</item> 小女孩
     * <item>vils</item> 男
     * <p>
     * <p>
     * <item>neutral</item>
     * <item>happy</item>
     * <item>sad</item>
     * <item>angry</item>
     * <p>
     * language_entries
     * 普通话 - mandarin
     * 粤语 - cantonese
     * 英语 - en_us
     */

    public Broadcaster(Context context) {
        createTime = System.currentTimeMillis();
        synthesizer = SpeechSynthesizer.createSynthesizer(context, this);

        synthesizer.setParameter(SpeechConstant.PARAMS, null);
        synthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
//        synthesizer.setParameter(SpeechConstant.ENGINE_MODE, SpeechConstant.TYPE_CLOUD);

//        synthesizer.setParameter(SpeechConstant.EMOT, "happy");
        synthesizer.setParameter(SpeechConstant.VOICE_NAME, "nannan");
        //设置合成语速
        synthesizer.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        synthesizer.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        synthesizer.setParameter(SpeechConstant.VOLUME, "90");

        //设置播放器音频流类型
        synthesizer.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        synthesizer.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");


        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//        synthesizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
//        synthesizer.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }

    public void speak(String text, OnCompleteListener listener) {
        this.onCompleteListener = listener;
        this.synthesizer.startSpeaking(text, Broadcaster.this);
    }



    public String getName() {
        return name;
    }

    @Override
    public void onInit(int i) {
        long elapse = System.currentTimeMillis() - createTime;

        if (ErrorCode.SUCCESS == i) {
            Timber.i("%s：初始化成功, 耗时(%d)ms", name, elapse);
        } else {
            Timber.e("%s：初始化失败, 耗时(%d)ms - %d", name, elapse, i);
        }

    }

    @Override
    public void onSpeakBegin() {
        Timber.i("播音员：开始播音");
    }

    @Override
    public void onBufferProgress(int i, int i1, int i2, String s) {

    }

    @Override
    public void onSpeakPaused() {

    }

    @Override
    public void onSpeakResumed() {

    }

    @Override
    public void onSpeakProgress(int percent, int beginPos, int endPos) {
        Timber.i("播音员：播音进度 %d-%d-%d", percent, beginPos, endPos);
        if (percent >= 98) {
            ThreadUtil.runDelay(500, () -> {
                if (onCompleteListener != null) {
                    onCompleteListener.onFinished();
                }
            });
        }
    }

    @Override
    public void onCompleted(SpeechError speechError) {
        Timber.i("播音员：播音完成 %s", speechError.toString());
//        if (speaking != null) {
//            speaking.onFinished();
//        }
    }

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {
        Timber.i("播音员：onEvent %d-%d-%d", i, i1, i2);
    }
}
