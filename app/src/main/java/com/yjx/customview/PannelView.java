package com.yjx.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yjx.model.Piece;
import com.yjx.utils.Constants;
import com.yjx.utils.LogUtil;
import com.yjx.utils.Util;
import com.yjx.wuziqi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjinxiao on 2016/6/30.
 */
public class PannelView extends View {
    private int mPannaWidth;//棋盘的宽度
    private float mLineHeight;//行高(定义成float型，防止精度丢失)
    private Paint mPaint;
    private Paint mPiecePaint;//绘制棋子画笔
    private static final float RATIO = 3 * 1.0f / 4;//棋子图片占行高的比例
    private int mLineColorId = R.color.Gray;//线的颜色
    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;
    private ArrayList<Piece> mWhitePieces = new ArrayList<>();//用来存储用户已经下子的点
    private ArrayList<Piece> mBlackPieces = new ArrayList<>();
    private boolean mIsCurWhiteTurn = true;//当前轮到白子下（这里默认白子先手）
    private Piece mCurClickPiece;//当前已下子的点
    private boolean mIsGameOver;//标识游戏结束
    private boolean mIsWhiteWin;//是否是白子赢了
    private OnGameOverListener mGameOverListener;
    private OnTurnChangedListener mTurnChangedListener;
    private static final int STROKE_LINE_WIDTH = 2;//dp

    //数据保存与恢复相关常量
    private static final String DEFAULT_INSTANCE = "default_instance";
    private static final String WHITE_PIECES = "white_pieces";
    private static final String BLACK_PIECES = "black_pieces";
    private static final String IS_CUR_WHITE = "is_cur_white";
    private static final String CUR_CLICK_POINT = "cur_click_point";

    public void registerGameOverListner(OnGameOverListener listener) {
        this.mGameOverListener = listener;
    }

    public void registerTurnChangedListener(OnTurnChangedListener turnChangedListener) {
        this.mTurnChangedListener = turnChangedListener;
    }

    public boolean getIsCurWhiteTurn() {
        return mIsCurWhiteTurn;
    }

    /**
     * 悔棋
     */
    public void regretLastStep() {
        if (mIsCurWhiteTurn) {//当前轮到白方下子，即黑方要悔棋
            if (mBlackPieces != null && !mBlackPieces.isEmpty()) {
                mBlackPieces.remove(mBlackPieces.size() - 1);
                if (mWhitePieces != null && !mWhitePieces.isEmpty()) {
                    mCurClickPiece = mWhitePieces.get(mWhitePieces.size() - 1);
                }
                mIsCurWhiteTurn = !mIsCurWhiteTurn;
                notifyObserverTurnChanged();
                invalidate();
                return;
            }
        }else {
            if (mWhitePieces != null && !mWhitePieces.isEmpty()) {
                mWhitePieces.remove(mWhitePieces.size() - 1);
                if (mBlackPieces != null && !mBlackPieces.isEmpty()) {
                    mCurClickPiece = mBlackPieces.get(mBlackPieces.size() - 1);
                }
                mIsCurWhiteTurn = !mIsCurWhiteTurn;
                notifyObserverTurnChanged();
                invalidate();
                return;
            }
        }
    }

    public PannelView(Context context) {
        this(context, null);
    }

    public PannelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PannelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getContext().getResources().getColor(mLineColorId));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(Util.dp2px(STROKE_LINE_WIDTH, getContext()));

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
        mLineHeight =  (mPannaWidth * 1.0f / Constants.MAX_LINE);

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
        if (checkGameOver()) {
            mIsGameOver = true;
            if (mGameOverListener != null) {
                mGameOverListener.onGameOver(mIsWhiteWin);
            }
        }
    }

    //绘制棋板
    private void drawBoard(Canvas canvas) {
        int pannalWidth = mPannaWidth;
        float lineHeight = mLineHeight;

        //绘制横线
        for (int i = 0; i < Constants.MAX_LINE; i++) {
            float startX =  (0.5f * lineHeight);
            float endX =  (pannalWidth - 0.5f * lineHeight);
            float startY =  ((0.5f + i) * lineHeight);
            float endY =  startY;
            canvas.drawLine(startX, startY, endX, endY, mPaint);
        }

        //绘制竖线
        for (int i = 0; i < Constants.MAX_LINE; i++) {
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
            Piece point = mWhitePieces.get(i);
            float left = (point.x + (1 - RATIO) / 2) * mLineHeight;
            float top = (point.y + (1 - RATIO) / 2) * mLineHeight;
            canvas.drawBitmap(mWhitePiece, left, top, mPiecePaint);
        }
        for (int i = 0; i < mBlackPieces.size(); i++) {
            Piece point = mBlackPieces.get(i);
            float left = (point.x + (1 - RATIO) / 2) * mLineHeight;
            float top = (point.y + (1 - RATIO) / 2) * mLineHeight;
            canvas.drawBitmap(mBlackPiece, left, top, mPiecePaint);
        }
    }

    /**
     * 校验游戏是否结束
     * @return true: game over
     */
    private boolean checkGameOver() {
        if (mIsCurWhiteTurn) {//当前轮到白子下，即应该检验上一手黑子是否连成
            boolean blackWin = checkHorizontalOrVerticalDone(mBlackPieces);
            if (!blackWin) {
                blackWin = checkLeftOrRightOblique(mBlackPieces);
            }
            if (blackWin) {
                mIsWhiteWin = false;
                return true;
            }
        }else {
            boolean whiteWin = checkHorizontalOrVerticalDone(mWhitePieces);
            if (!whiteWin) {
                whiteWin = checkLeftOrRightOblique(mWhitePieces);
            }
            if (whiteWin) {
                mIsWhiteWin = true;
                return true;
            }
        }
        return false;
    }

    /**
     * 校验横向/纵向是否有五子连成的
     * @param points
     * @return
     */
    private boolean checkHorizontalOrVerticalDone(List<Piece> points) {
        if (points == null || points.isEmpty() || mCurClickPiece == null) {
            return false;
        }
        int horizontalCount = 1;//横向计数
        int verticalCount = 1;//纵向计数
        for (int i = 1; i < Constants.WIN_PIECES_NUM; i++) {//往左检查
            Piece leftPoint = new Piece(mCurClickPiece.x - i, mCurClickPiece.y);
            if (points.contains(leftPoint)) {
                horizontalCount++;
                continue;
            } else {
                break;
            }
        }
        for (int i = 1; i < Constants.WIN_PIECES_NUM; i++) {//往右检查
            Piece rightPoint = new Piece(mCurClickPiece.x + i, mCurClickPiece.y);
            if (points.contains(rightPoint)) {
                horizontalCount++;
                continue;
            } else {
                break;
            }
        }
        for (int i = 1; i < Constants.WIN_PIECES_NUM; i++) {//往上检查
            Piece upPoint = new Piece(mCurClickPiece.x, mCurClickPiece.y - i);
            if (points.contains(upPoint)) {
                verticalCount++;
                continue;
            } else {
                break;
            }
        }
        for (int i = 1; i < Constants.WIN_PIECES_NUM; i++) {//往下检查
            Piece belowPoint = new Piece(mCurClickPiece.x, mCurClickPiece.y + i);
            if (points.contains(belowPoint)) {
                verticalCount++;
                continue;
            } else {
                break;
            }
        }
        if (horizontalCount == Constants.WIN_PIECES_NUM || verticalCount == Constants.WIN_PIECES_NUM) {
            return true;
        }
        return false;
    }

    /**
     * 校验斜向是否连成
     * @return
     */
    private boolean checkLeftOrRightOblique(List<Piece> points) {
        if (points == null || points.isEmpty() || mCurClickPiece == null) {
            return false;
        }
        int leftObliqueCounts = 1;
        int rightObliqueCounts = 1;
        for (int i = 1; i < Constants.WIN_PIECES_NUM; i++) {//左斜向上检查
            Piece leftUpPoint = new Piece(mCurClickPiece.x + i, mCurClickPiece.y - i);
            if (points.contains(leftUpPoint)) {
                leftObliqueCounts++;
                continue;
            } else {
                break;
            }
        }
        for (int i = 1; i < Constants.WIN_PIECES_NUM; i++) {//左斜向下检查
            Piece leftDownPoint = new Piece(mCurClickPiece.x - i, mCurClickPiece.y + i);
            if (points.contains(leftDownPoint)) {
                leftObliqueCounts++;
                continue;
            } else {
                break;
            }
        }
        if (leftObliqueCounts == Constants.WIN_PIECES_NUM) {
            return true;
        }
        for (int i = 1; i < Constants.WIN_PIECES_NUM; i++) {//右斜向上检查
            Piece rightUpPoint = new Piece(mCurClickPiece.x - i, mCurClickPiece.y - i);
            if (points.contains(rightUpPoint)) {
                rightObliqueCounts++;
                continue;
            } else {
                break;
            }
        }
        for (int i = 1; i < Constants.WIN_PIECES_NUM; i++) {//右斜向下检查
            Piece rightDownPoint = new Piece(mCurClickPiece.x + i, mCurClickPiece.y + i);
            if (points.contains(rightDownPoint)) {
                rightObliqueCounts++;
                continue;
            } else {
                break;
            }
        }
        if (rightObliqueCounts == Constants.WIN_PIECES_NUM) {
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
            mCurClickPiece = getProperPoint(x, y);
            if (mWhitePieces.contains(mCurClickPiece) || mBlackPieces.contains(mCurClickPiece)) {//当前点已经下过子了
                return false;
            }
            if (mIsCurWhiteTurn) {
                mWhitePieces.add(mCurClickPiece);
            }else {
                mBlackPieces.add(mCurClickPiece);
            }
            mIsCurWhiteTurn = !mIsCurWhiteTurn;
            notifyObserverTurnChanged();
            invalidate();//请求重绘
        }
        return true;
    }

    /**
     * 通知所有监听者落子权交换
     */
    private void notifyObserverTurnChanged() {
        if (mTurnChangedListener != null) {
            mTurnChangedListener.onTurnChangeListener(mIsCurWhiteTurn);
        }
    }

    /**
     * 根据用户点击的具体点创建像(0,1) (0,2)这样代表棋盘上的点
     * 为了方便绘制棋子
     * @param x
     * @param y
     * @return
     */
    private Piece getProperPoint(int x, int y) {
        return new Piece((int) (x / mLineHeight), (int) (y / mLineHeight));
    }

    /**
     * 重新开始游戏
     */
    public void restart() {
        mWhitePieces.clear();
        mBlackPieces.clear();
        mIsGameOver = false;
        mIsWhiteWin = false;
        mCurClickPiece = null;
        mIsCurWhiteTurn = true;
        invalidate();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(DEFAULT_INSTANCE, super.onSaveInstanceState());
        bundle.putParcelableArrayList(WHITE_PIECES, mWhitePieces);
        bundle.putParcelableArrayList(BLACK_PIECES, mBlackPieces);
        bundle.putBoolean(IS_CUR_WHITE, mIsCurWhiteTurn);
        bundle.putParcelable(CUR_CLICK_POINT, mCurClickPiece);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            Parcelable defaultState = bundle.getParcelable(DEFAULT_INSTANCE);
            super.onRestoreInstanceState(defaultState);//系统默认保存的数据
            mWhitePieces = bundle.getParcelableArrayList(WHITE_PIECES);
            mBlackPieces = bundle.getParcelableArrayList(BLACK_PIECES);
            mIsCurWhiteTurn = bundle.getBoolean(IS_CUR_WHITE);
            mCurClickPiece = bundle.getParcelable(CUR_CLICK_POINT);
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public interface OnGameOverListener {
        void onGameOver(boolean isWhiteWin);
    }

    public interface  OnTurnChangedListener {
        void onTurnChangeListener(boolean isWhiteTurn);
    }
}
