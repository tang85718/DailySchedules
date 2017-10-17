package co.stringstech.notice;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;


import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.io.IOException;

import co.stringstech.notice.util.FileUtil;
import timber.log.Timber;

import static android.content.ContentValues.TAG;

/**
 * Created by tangxuyao on 2017/9/12.
 * SmartBot
 */

public class SmartBot implements SpeechSynthesizerListener {
    private final int INIT = 1;

    private final int RELEASE = 11;

    public interface ISpeak {
        void onFinished();
    }

    private SpeechSynthesizer mSpeechSynthesizer;
    private ISpeak iSpeak;

    private HandlerThread hThread;
    private Handler tHandler;
    private String destPath;
    private AssetManager assets;
    private Context context;

    private String textFilename;
    private String modelFilename;

    public SmartBot(Context ctx) {
        this.context = ctx;
        this.destPath = FileUtil.createTmpDir(context);
        this.assets = ctx.getApplicationContext().getAssets();

        setupOfflineVoice();

        initThread();
        runInHandlerThread(INIT);
    }

    public void setupOfflineVoice() {
        String text = "bd_etts_text.dat";
        String model = "bd_etts_speech_female.dat";

        try {
            textFilename = copyAssetsFile(text);
            modelFilename = copyAssetsFile(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean init() {
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        // 设置context
        mSpeechSynthesizer.setContext(context);
        // 设置语音合成状态监听器
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // 设置在线语音合成授权，需要填入从百度语音官网申请的api_key和secret_key
        mSpeechSynthesizer.setApiKey("K3WVTjncwUUOww83D0YNbt2q", "P3AyD7eFUpA2bgG6ZQORyjN85QEq3AbA");
        // 设置离线语音合成授权，需要填入从百度语音官网申请的app_id
        mSpeechSynthesizer.setAppId("9954546");
        // 设置语音合成文本模型文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, textFilename);
        // 设置语音合成声音模型文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, modelFilename);
        // 设置语音合成声音授权文件
//        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, "file:///android_asset/temp_license");
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "4");
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9"); // 0-9
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "4"); // 0-9
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "6"); // 0-9
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);

        // 获取语音合成授权信息
        AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.MIX);
        // 判断授权信息是否正确，如果正确则初始化语音合成器并开始语音合成，如果失败则做错误处理
        if (authInfo.isSuccess()) {
            mSpeechSynthesizer.initTts(TtsMode.MIX);
            Timber.i("语音合成模块: 初始化成功");
            return true;
        }

        String errorMsg = authInfo.getTtsError().getDetailMessage();
        // 授权失败
        mSpeechSynthesizer.release();
        mSpeechSynthesizer = null;
        Timber.e("语音合成模块: 初始化失败-%s", errorMsg);
        return false;
    }

    private void runInHandlerThread(int action) {
        runInHandlerThread(action, null);
    }

    private void runInHandlerThread(int action, Object obj) {
        Message msg = Message.obtain();
        msg.what = action;
        msg.obj = obj;
        tHandler.sendMessage(msg);
    }

    protected void initThread() {
        hThread = new HandlerThread("SmartBot-thread");
        hThread.start();
        tHandler = new Handler(hThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case INIT:
                        init();
                        break;
                    case RELEASE:
                        break;
                }

            }
        };
    }

    private String copyAssetsFile(String sourceFilename) throws IOException {
        String destFilename = destPath + "/" + sourceFilename;
        FileUtil.copyFromAssets(assets, sourceFilename, destFilename, false);
        Log.i(TAG, "文件复制成功：" + destFilename);
        return destFilename;
    }

    public String getName() {
        return "爱丽丝";
    }

    public void speak(String text, ISpeak is) {
        iSpeak = is;
        mSpeechSynthesizer.speak(text);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        mSpeechSynthesizer.release();
        mSpeechSynthesizer = null;
    }

    @Override
    public void onSynthesizeStart(String s) {
    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
    }

    @Override
    public void onSynthesizeFinish(String s) {
        Timber.i("onSynthesizeFinish: %s", s);
    }

    @Override
    public void onSpeechStart(String s) {
    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {
        Timber.i("onSpeechProgressChanged: %s, %d", s, i);
    }

    @Override
    public void onSpeechFinish(String s) {
        if (iSpeak != null) {
            iSpeak.onFinished();
        }

        Timber.i("onSpeechFinish: %s", s);
    }

    @Override
    public void onError(String s, SpeechError speechError) {
        Timber.e("%d, %s", speechError.code, speechError.description);
    }
}
