package vctextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import gu.china.com.vertical_scroll_textview.R;

/**
 * Created by GuJiaJia on 2017/6/21.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class VerticalScrollTextView extends TextSwitcher implements ViewSwitcher.ViewFactory,View.OnClickListener {

    private Context context;

    // inAnimation,outAnimation分别构成翻页的进出动画
    private ScrollAnimation inAnimation;
    private ScrollAnimation outAnimation;
    private ArrayList<Hot> hotList;
    private int startPosition = 0;
    private String content;
    private Timer mTimer;
    long mSwitchTime = 3000;
    private float tvHeight;
    private float tvSize;
    private int tvColor;
    private String tvHint;
    private ItemOnClickListener itemOnClickListener;

    public VerticalScrollTextView(Context context) {
        this(context, null);
    }

    public VerticalScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.vertical_text);
        if (typedArray != null) {
            tvSize = typedArray.getDimension(R.styleable.vertical_text_vTextSize, 12);
            tvColor = typedArray.getColor(R.styleable.vertical_text_vTextColor, ContextCompat.getColor(context, R.color.font_main));
            tvHeight = typedArray.getDimension(R.styleable.vertical_text_vTextHeight, 120);
            if (typedArray.hasValue(R.styleable.vertical_text_vTextHint)) {
                tvHint = typedArray.getString(R.styleable.vertical_text_vTextHint);
            } else {
                tvHint = "";
            }
        }
        typedArray.recycle();
        this.context = context;
        init();
    }

    private void init() {
        hotList = new ArrayList<>();
        setFactory(this);
        inAnimation = createAnim(-90, 0, true, true);
        outAnimation = createAnim(0, 90, false, true);
        setInAnimation(inAnimation);
        setOutAnimation(outAnimation);
    }

    private ScrollAnimation createAnim(float start, float end, boolean turnIn,
                                       boolean turnUp) {
        final ScrollAnimation rotation = new ScrollAnimation(start, end,
                turnIn, turnUp);
        rotation.setDuration(500);
        rotation.setFillAfter(false);
        rotation.setInterpolator(new AccelerateInterpolator());

        return rotation;
    }

    // 这里返回的TextView，就是我们看到的View
    @Override
    public View makeView() {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.LEFT);
        textView.setPadding(16, 16, 16, 16);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvSize);
        textView.setTextColor(tvColor);
        textView.setGravity(Gravity.CENTER_VERTICAL);// gravity center_vertical
        textView.setText(tvHint);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setHeight((int) tvHeight);
        textView.setOnClickListener(this);
        return textView;
    }

    public void next() {
        if (getInAnimation() != inAnimation) {
            setInAnimation(inAnimation);
        }
        if (getOutAnimation() != outAnimation) {
            setOutAnimation(outAnimation);
        }
    }

    //设置数据
    public void setList(ArrayList<Hot> list) {
        hotList.clear();
        hotList.addAll(list);
        startPosition = 0;
        stop();//这样可以兼容更新数据
        start();
    }

    //获取下一个显示的position
    private int getPosition() {
        if (hotList == null) {
            stop();
            content = "";
            return -1;
        }
        if (startPosition >= hotList.size()) {
            startPosition = 0;
        }
        if (startPosition < hotList.size()) {
            content = hotList.get(startPosition).getContent();
        } else {
            content = "";
        }
        return startPosition;
    }


    public void start() {
        if (hotList == null) {
            return;
        }
        if (hotList.size() == 1) {
            setText(hotList.get(0).getContent());
            return;
        }
        if (mTimer == null && hotList.size() > 1 && mSwitchTime > 0) {
            mTimer = new Timer();
            handler.sendEmptyMessage(0);
            mTimer.schedule(new TimerTask() {
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, mSwitchTime, mSwitchTime);
        }
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener){
        this.itemOnClickListener = itemOnClickListener;
    }
    public void stop() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    getPosition();
                    next();
                    startPosition++;
                    setText(content);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        if(itemOnClickListener != null){
            itemOnClickListener.onClick(startPosition-1,hotList.get(startPosition-1));
        }
    }

    public interface ItemOnClickListener{
        void onClick(int position, Hot hot);
    }
    class ScrollAnimation extends Animation {
        private final float mFromDegrees;
        private final float mToDegrees;
        private float mCenterX;
        private float mCenterY;
        private final boolean mTurnIn;
        private final boolean mTurnUp;
        private Camera mCamera;

        public ScrollAnimation(float fromDegrees, float toDegrees,
                               boolean turnIn, boolean turnUp) {
            mFromDegrees = fromDegrees;
            mToDegrees = toDegrees;
            mTurnIn = turnIn;
            mTurnUp = turnUp;
        }

        @Override
        public void initialize(int width, int height, int parentWidth,
                               int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mCenterY = getHeight() / 2;
            mCenterX = getWidth() / 2;
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            final float fromDegrees = mFromDegrees;
            float degrees = fromDegrees
                    + ((mToDegrees - fromDegrees) * interpolatedTime);

            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final int derection = mTurnUp ? 1 : -1;

            final Matrix matrix = t.getMatrix();

            camera.save();
            if (mTurnIn) {
                camera.translate(0.0f, derection * mCenterY
                        * (interpolatedTime - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, derection * mCenterY
                        * (interpolatedTime), 0.0f);
            }
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }
}
