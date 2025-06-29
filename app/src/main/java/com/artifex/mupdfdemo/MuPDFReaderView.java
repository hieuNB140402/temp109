package com.artifex.mupdfdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;

public class MuPDFReaderView extends ReaderView {
	public enum Mode {Viewing, Selecting, Drawing}
	//enum Mode {Viewing, Selecting, Drawing}

	private final Context mContext;
	private boolean mLinksHighlighted = false;
	private Mode mMode = Mode.Viewing;
	private boolean tapDisabled = false;
	private int tapPageMargin;
	private GestureDetector gestureDetector;
	private float selectStartX = -1;
	private float selectStartY = -1;

	private final Handler longPressHandler = new Handler();
	private Runnable longPressRunnable;
	private static final int LONG_PRESS_TIMEOUT = 300;
	private boolean isLongPressTriggered = false;

	protected void onTapMainDocArea() {
	}

	protected void onDocMotion() {
	}

	protected void onHit(Hit item) {
	};

	public void setLinksHighlighted(boolean b) {
		mLinksHighlighted = b;
		resetupChildren();
	}

	public void setMode(Mode m) {
		mMode = m;
	}
	
	private void setup() {
		gestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public void onLongPress(MotionEvent e) {
				mMode = Mode.Selecting;

				selectStartX = e.getX();
				selectStartY = e.getY();

				MuPDFView pageView = (MuPDFView) getDisplayedView();
				if (pageView != null) {
					pageView.selectText(selectStartX, selectStartY, selectStartX, selectStartY);
				}
			}
		});

	//public MuPDFReaderView(Activity act) {
		//super(act);
		//mContext = act;
		
		// Get the screen size etc to customise tap margins.
		// We calculate the size of 1 inch of the screen for tapping.
		// On some devices the dpi values returned are wrong, so we
		// sanity check it: we first restrict it so that we are never
		// less than 100 pixels (the smallest Android device screen
		// dimension I've seen is 480 pixels or so). Then we check
		// to ensure we are never more than 1/5 of the screen width.
		DisplayMetrics dm = new DisplayMetrics();
		//act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);

		tapPageMargin = (int) dm.xdpi;
		if (tapPageMargin < 80)
			tapPageMargin = 80;
		if (tapPageMargin > dm.widthPixels / 6)
			tapPageMargin = dm.widthPixels / 6;
	}
	
	public MuPDFReaderView(Context context) {
		super(context);
		mContext = context;
		setup();
	}

	public MuPDFReaderView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext = context;
		setup();
	}

	public boolean onSingleTapUp(MotionEvent e) {
		LinkInfo link = null;

		if (mMode == Mode.Viewing && !tapDisabled) {
			MuPDFView pageView = (MuPDFView) getDisplayedView();
			Hit item = pageView.passClickEvent(e.getX(), e.getY());
			onHit(item);
			if (item == Hit.Nothing) {
				if (/*mLinksEnabled && */pageView != null && (link = pageView.hitLink(e.getX(), e.getY())) != null) {
					link.acceptVisitor(new LinkInfoVisitor() {
						@Override
						public void visitInternal(LinkInfoInternal li) {
							// Clicked on an internal (GoTo) link
							//TODO: goto page in landscape mode
							setDisplayedViewIndex(li.pageNumber);
						}

						@Override
						public void visitExternal(LinkInfoExternal li) {
							Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(li.url));
							mContext.startActivity(intent);
						}

						@Override
						public void visitRemote(LinkInfoRemote li) {
							// Clicked on a remote (GoToR) link
						}
					});
				} else if (e.getX() < tapPageMargin) {
					super.smartMoveBackwards();
				} else if (e.getX() > super.getWidth() - tapPageMargin) {
					super.smartMoveForwards();
				//} else if (e.getY() < tapPageMargin) {
				//	super.smartMoveBackwards();
				//} else if (e.getY() > super.getHeight() - tapPageMargin) {
				//	super.smartMoveForwards();
				} else {
					onTapMainDocArea();
				}
			}
		}
		return super.onSingleTapUp(e);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return super.onDown(e);
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		MuPDFView pageView = (MuPDFView) getDisplayedView();
		switch (mMode) {
		case Viewing:
			if (!tapDisabled)
				onDocMotion();
			return super.onScroll(e1, e2, distanceX, distanceY);
		case Selecting:
			if (pageView != null && selectStartX >= 0 && selectStartY >= 0) {
				pageView.selectText(selectStartX, selectStartY, e2.getX(), e2.getY());
			}
			return true;
		default:
			return true;
		}
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		switch (mMode) {
		case Viewing:
			return super.onFling(e1, e2, velocityX, velocityY);
		default:
			return true;
		}
	}

	public boolean onScaleBegin(ScaleGestureDetector d) {
		// Disabled showing the buttons until next touch.
		// Not sure why this is needed, but without it
		// pinch zoom can make the buttons appear
		tapDisabled = true;
		return super.onScaleBegin(d);
	}

	public boolean onTouchEvent(MotionEvent event) {

		if ( mMode == Mode.Drawing ) {
			float x = event.getX();
			float y = event.getY();
			switch (event.getAction())
			{
				case MotionEvent.ACTION_DOWN:
					touch_start(x, y);
					break;
				case MotionEvent.ACTION_MOVE:
					touch_move(x, y);
					break;
				case MotionEvent.ACTION_UP:
					touch_up();
					break;
			}
		} else {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					isLongPressTriggered = false;
					selectStartX = event.getX();
					selectStartY = event.getY();
					longPressRunnable = new Runnable() {
						@Override
						public void run() {
							isLongPressTriggered = true;
							mMode = Mode.Selecting;
							MuPDFView pageView = (MuPDFView) getDisplayedView();
							if (pageView != null) {
								pageView.selectText(selectStartX, selectStartY, selectStartX, selectStartY);
							}
						}
					};
					longPressHandler.postDelayed(longPressRunnable, LONG_PRESS_TIMEOUT);
					break;

				case MotionEvent.ACTION_MOVE:
					// Optional: hủy nếu di chuyển quá xa
					float dx = Math.abs(event.getX() - selectStartX);
					float dy = Math.abs(event.getY() - selectStartY);
					if ((dx > 20 || dy > 20) && !isLongPressTriggered) {
						longPressHandler.removeCallbacks(longPressRunnable);
					}
					break;

				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					longPressHandler.removeCallbacks(longPressRunnable);
					if (mMode == Mode.Selecting) {
						mMode = Mode.Viewing;
						selectStartX = -1;
						selectStartY = -1;
					}
					break;
			}
			if (gestureDetector != null) {
				gestureDetector.onTouchEvent(event);
			}
		}
			
		return super.onTouchEvent(event);
	}
	
	private float mX, mY;

	private static final float TOUCH_TOLERANCE = 2;

	private void touch_start(float x, float y) {

		MuPDFView pageView = (MuPDFView)getDisplayedView();
		if (pageView != null)
		{
			pageView.startDraw(x, y);
		}
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {

		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
		{
			MuPDFView pageView = (MuPDFView)getDisplayedView();
			if (pageView != null)
			{
				pageView.continueDraw(x, y);
			}
			mX = x;
			mY = y;
		}
	}

	private void touch_up() {
		// NOOP
	}
	
	protected void onChildSetup(int i, View v) {
		//TODO: page number in landscape
		if (SearchTaskResult.get() != null && SearchTaskResult.get().pageNumber == i) {
			((MuPDFView) v).setSearchBoxes(SearchTaskResult.get().searchBoxes);
			((MuPDFView) v).setSearchBoxesPrim(SearchTaskResult.get().searchBoxesPrim);
		}
		else {
			((MuPDFView) v).setSearchBoxes(null);
			((MuPDFView) v).setSearchBoxesPrim(null);
		}

		((MuPDFView) v).setLinkHighlighting(mLinksHighlighted);

		((MuPDFView) v).setChangeReporter(new Runnable() {
			public void run() {
				applyToChildren(new ViewMapper() {
					@Override
					public void applyToView(View view) {
						((MuPDFView) view).update();
					}
				});
			}
		});
	}

	protected void onMoveToChild(int i) {
		if (SearchTaskResult.get() != null && SearchTaskResult.get().pageNumber != i) {
			SearchTaskResult.set(null);
			resetupChildren();
		}
	}

	@Override
	protected void onMoveOffChild(int i) {
		View v = getView(i);
		if (v != null)
			((MuPDFView) v).deselectAnnotation();
	}

	protected void onSettle(View v) {
		// When the layout has settled ask the page to render
		// in HQ
		((MuPDFView) v).updateHq(false);
	}

	protected void onUnsettle(View v) {
		// When something changes making the previous settled view
		// no longer appropriate, tell the page to remove HQ
		((MuPDFView) v).removeHq();
	}

	@Override
	protected void onNotInUse(View v) {
		((MuPDFView) v).releaseResources();
	}

	@Override
	protected void onScaleChild(View v, Float scale) {
		((MuPDFView) v).setScale(scale);
	}
}
