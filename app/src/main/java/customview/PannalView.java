package customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yjx.wuziqi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjinxiao on 2016/6/30.
 */
public class PannalView extends View {
    private int mPannaWidth;//棋盘的宽度
    private float mLineHeight;//行高(定义成float型，防止精度丢失)
    private Paint mPaint;
    private Paint mPiecePaint;//绘制棋子画笔
    private static final int MAX_LINE = 10;
    private static final float RATIO = 3 * 1.0f / 4;//棋子图片占行高的比例
    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;
    private List<Point> mWhitePieces = new ArrayList<>();//用来存储用户已经下子的点
    private List<Point> mBlackPieces = new ArrayList<>();
    private boolean mIsCurrentWhite = true;//当前轮到白子下（这里默认白子先手）
    private Point mCurClickPoint;//当前下子的点
    private boolean mIsGameOver;//标识游戏结束
    private boolean mIsWhiteWin;//是否是白子赢了
    private OnGameOverListener mListener;

    public void registerListner(OnGameOverListener listener) {
        this.mListener = listener;
    }

    public PannalView(Context context) {
        this(context, null);
    }

    public PannalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PannalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(0x44ff0000);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPiecePaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);

        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heigutMode = MeasureSpec.getMode(heightMeasureSpec);

        int squareWidth = Math.min(widthSize, heightSize);//取宽和高的最小值作为正方形棋盘的边长
        //针对MeasureMode = UNSPCIFIED的情况做特殊处理(棋盘View的宽高不允许设置为wrap_content)
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            squareWidth = heightSize;
        }else if (heigutMode == MeasureSpec.UNSPECIFIED) {
            squareWidth = widthSize;
        }
        setMeasuredDimension(squareWidth, squareWidth);
    }

    /**
     * 该方法仅在当前View的大小发生变化后才回调（比如绘制完成后）
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPannaWidth = w;
        mLineHeight =  (mPannaWidth * 1.0f / MAX_LINE);

        //修改棋子图片的大小,行高的3/4
        int pieceWidth = (int) (mLineHeight * RATIO);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pieceWidth, pieceWidth, false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, pieceWidth, pieceWidth, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPiece(canvas);
    }

    //绘制棋板
    private void drawBoard(Canvas canvas) {
        int pannalWidth = mPannaWidth;
        float lineHeight = mLineHeight;

        //绘制横线
        for (int i = 0; i < MAX_LINE; i++) {
            float startX =  (0.5f * lineHeight);
            float endX =  (pannalWidth - 0.5f * lineHeight);
            float startY =  ((0.5f + i) * lineHeight);
            float endY =  startY;
            canvas.drawLine(startX, startY, endX, endY, mPaint);
        }

        //绘制竖线
        for (int i = 0; i < MAX_LINE; i++) {
            float startX =  ((0.5f + i) * lineHeight);
            float endX =  startX;
            float startY =  (0.5f * lineHeight);
            float endY =  (pannalWidth - 0.5f * lineHeight);
            canvas.drawLine(startX, startY, endX, endY, mPaint);
        }
    }

    /**
     * 绘制棋子
     * @param canvas
     */
    private void drawPiece(Canvas canvas) {
        for (int i = 0; i < mWhitePieces.size(); i++) {
            Point point = mWhitePieces.get(i);
            float left = (point.x + (1 - RATIO) / 2) * mLineHeight;
            float top = (point.y + (1 - RATIO) / 2) * mLineHeight;
            canvas.drawBitmap(mWhitePiece, left, top, mPiecePaint);
        }
        for (int i = 0; i < mBlackPieces.size(); i++) {
            Point point = mBlackPieces.get(i);
            float left = (point.x + (1 - RATIO) / 2) * mLineHeight;
            float top = (point.y + (1 - RATIO) / 2) * mLineHeight;
            canvas.drawBitmap(mBlackPiece, left, top, mPiecePaint);
        }
    }

    /**
     * 校验是否连成五子
     * @return
     */
    private void verifyWin() {
        if (mIsCurrentWhite) {
            boolean win = checkHorizontalOrVerticalDone(mWhitePieces);
            if (win && mListener != null) {
                mListener.onGameOver(true);
                mIsGameOver = true;
                mIsWhiteWin = true;
            }
        }else {
            boolean win = checkHorizontalOrVerticalDone(mBlackPieces);
            if (win && mListener != null) {
                mListener.onGameOver(false);
                mIsGameOver = true;
                mIsWhiteWin = false;
            }
        }
    }

    /**
     * 校验横向/纵向是否有五子连成的
     * @param points
     * @return
     */
    private boolean checkHorizontalOrVerticalDone(List<Point> points) {
        int horizontalCount = 0;//横向计数
        int verticalCount = 0;//纵向计数
        for (int i = 0; i < MAX_LINE; i++) {//往左检查
            Point leftPoint = new Point(mCurClickPoint.x - (i + 1), mCurClickPoint.y);
            if (points.contains(leftPoint)) {
                horizontalCount++;
                continue;
            } else {
                break;
            }
        }
        for (int i = 0; i < MAX_LINE; i++) {//往右检查
            Point rightPoint = new Point(mCurClickPoint.x + (i + 1), mCurClickPoint.y);
            if (points.contains(rightPoint)) {
                horizontalCount++;
                continue;
            } else {
                break;
            }
        }
        for (int i = 0; i < MAX_LINE; i++) {//往上检查
            Point upPoint = new Point(mCurClickPoint.x, mCurClickPoint.y - (i + 1));
            if (points.contains(upPoint)) {
                verticalCount++;
                continue;
            } else {
                break;
            }
        }
        for (int i = 0; i < MAX_LINE; i++) {//往下检查
            Point belowPoint = new Point(mCurClickPoint.x, mCurClickPoint.y + (i + 1));
            if (points.contains(belowPoint)) {
                verticalCount++;
                continue;
            } else {
                break;
            }
        }
        if (horizontalCount >= 5 || verticalCount >= 5) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsGameOver) {
            return false;//游戏结束，不关心点击事件
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {//用户手指抬起时处理（不在ACTION_DOWN时处理，防止嵌套在ScrollView中时滚动时落子）
            int x = (int) event.getX();
            int y = (int) event.getY();
            mCurClickPoint = getProperPoint(x, y);
            if (mWhitePieces.contains(mCurClickPoint) || mBlackPieces.contains(mCurClickPoint)) {//当前点已经下过子了
                return false;
            }
            if (mIsCurrentWhite) {
                mWhitePieces.add(mCurClickPoint);
            }else {
                mBlackPieces.add(mCurClickPoint);
            }
            mIsCurrentWhite = !mIsCurrentWhite;
            invalidate();//请求重绘
            verifyWin();
        }
        return true;
    }

    /**
     * 根据用户点击的具体点创建像(0,1) (0,2)这样代表棋盘上的点
     * 为了方便绘制棋子
     * @param x
     * @param y
     * @return
     */
    private Point getProperPoint(int x, int y) {
        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
    }

    public interface OnGameOverListener {
        void onGameOver(boolean isWhiteWin);
    }
}
