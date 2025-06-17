package com.example.st109_pdf_reader.core.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment

// click
fun View.animateScaleEffect(scaleDownFactor: Float = 0.9f, duration: Long = 100L) {
    val scaleDownX = ObjectAnimator.ofFloat(this, "scaleX", 1f, scaleDownFactor)
    val scaleDownY = ObjectAnimator.ofFloat(this, "scaleY", 1f, scaleDownFactor)
    scaleDownX.duration = duration
    scaleDownY.duration = duration

    val scaleUpX = ObjectAnimator.ofFloat(this, "scaleX", scaleDownFactor, 1f)
    val scaleUpY = ObjectAnimator.ofFloat(this, "scaleY", scaleDownFactor, 1f)
    scaleUpX.duration = duration
    scaleUpY.duration = duration

    val animatorSet = AnimatorSet()
    animatorSet.play(scaleDownX).with(scaleDownY)
    animatorSet.play(scaleUpX).with(scaleUpY).after(scaleDownX)


    animatorSet.start()
}

fun View.animateScaleOutEffect(scaleUpFactor: Float = 1.1f, duration: Long = 100L) {
    val scaleUpX = ObjectAnimator.ofFloat(this, "scaleX", 1f, scaleUpFactor)
    val scaleUpY = ObjectAnimator.ofFloat(this, "scaleY", 1f, scaleUpFactor)
    scaleUpX.duration = duration
    scaleUpY.duration = duration

    val scaleDownX = ObjectAnimator.ofFloat(this, "scaleX", scaleUpFactor, 1f)
    val scaleDownY = ObjectAnimator.ofFloat(this, "scaleY", scaleUpFactor, 1f)
    scaleDownX.duration = duration
    scaleDownY.duration = duration

    val animatorSet = AnimatorSet()
    animatorSet.play(scaleUpX).with(scaleUpY)
    animatorSet.play(scaleDownX).with(scaleDownY).after(scaleUpX)

    animatorSet.start()
}


// Rung ngang
fun View.shakeView(duration: Long = 100, repeatCount: Int = 3, shakeDistance: Float = 5f) {
    val animator = ObjectAnimator.ofFloat(this, "translationX", -shakeDistance, shakeDistance)
    animator.duration = duration
    animator.repeatCount = repeatCount
    animator.repeatMode = ObjectAnimator.REVERSE

    // Thêm listener để đặt lại vị trí ban đầu sau khi animation kết thúc
    animator.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            // Đặt lại thuộc tính translationX về 0 để trở lại vị trí ban đầu
            this@shakeView.translationX = 0f
        }
    })

    animator.start()
}

// Phương thức để animate margin top cho một layout
fun animateLift(layout: View, liftBy: Int, duration: Long = 100) {
    val layoutParams = layout.layoutParams as ViewGroup.MarginLayoutParams
    val originalMarginBottom = layoutParams.bottomMargin

    // Animator thay đổi margin bottom
    val animator = ValueAnimator.ofInt(originalMarginBottom, originalMarginBottom + liftBy)
    animator.addUpdateListener { valueAnimator ->
        val margin = valueAnimator.animatedValue as Int
        layoutParams.bottomMargin = margin
        layout.layoutParams = layoutParams
    }
    animator.duration = duration
    animator.start()
}

// Phương thức để reset margin về ban đầu
fun resetPosition(layout: View, context: Context) {
    val layoutParams = layout.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.bottomMargin = pxToDpInt(context, 0)
    layout.layoutParams = layoutParams
}

// Đếm ngược 3 -> 1
//fun startCountdownAnimation(
//    context: Context,
//    textView: StrokeTextView,
//    textView2: TextView,
//    startNumber: Int = 3,
//    onDone: ((String) -> Unit)? = null
//) {
//    var currentNumber = startNumber
//    val handler = Handler(Looper.getMainLooper())
//
//    fun animateTextView() {
//        textView.text = currentNumber.toString()
//        textView.scaleX = 1f
//        textView.scaleY = 1f
//        textView.alpha = 1f
//
//        textView2.text = currentNumber.toString()
//        textView2.scaleX = 1f
//        textView2.scaleY = 1f
//        textView2.alpha = 1f
//
//        val textViewAnimations = AnimatorSet().apply {
//            playTogether(
//                ObjectAnimator.ofFloat(textView, "scaleX", 1f, 3f),
//                ObjectAnimator.ofFloat(textView, "scaleY", 1f, 3f),
//                ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f)
//            )
//            duration = 1000
//        }
//
//        val textView2Animations = AnimatorSet().apply {
//            playTogether(
//                ObjectAnimator.ofFloat(textView2, "scaleX", 1f, 3f),
//                ObjectAnimator.ofFloat(textView2, "scaleY", 1f, 3f),
//                ObjectAnimator.ofFloat(textView2, "alpha", 1f, 0f)
//            )
//            duration = 1000
//        }
//
//        val combinedAnimation = AnimatorSet().apply {
//            playTogether(textViewAnimations, textView2Animations)
//        }
//
//        combinedAnimation.addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
//                currentNumber--
//                if (currentNumber > 0) {
//                    handler.postDelayed({ animateTextView() }, 0)
//                } else {
//                    textView.scaleX = 1f
//                    textView.scaleY = 1f
//                    textView.alpha = 1f
//                    textView2.scaleX = 1f
//                    textView2.scaleY = 1f
//                    textView2.alpha = 1f
//
//                    textView.text = context.getString(R.string.go)
//                    textView2.text = context.getString(R.string.go)
//
//                    handler.postDelayed({ onDone?.invoke("Game Over") }, 1000)
//                }
//            }
//        })
//
//        combinedAnimation.start()
//    }
//
//    animateTextView()
//}

// Rotation
fun startRotationInfinity(view: View) {
    val rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, -360f).apply {
        duration = 500
        repeatCount = ObjectAnimator.INFINITE // Lặp vô hạn
        repeatMode = ObjectAnimator.RESTART // Bắt đầu lại sau mỗi vòng
    }

    // Bắt đầu animation
    rotationAnimator.start()
}

// zoom out 0 -> end -> onEnd
fun animateView(view: View, scaleEnd: Float, duration: Long, onEnd: (() -> Unit)? = null) {
    val scaleXAnimation = ObjectAnimator.ofFloat(view, View.SCALE_X, 0f, scaleEnd)
    val scaleYAnimation = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f, scaleEnd)

    AnimatorSet().apply {
        playTogether(scaleXAnimation, scaleYAnimation)
        this.duration = duration
        interpolator = AccelerateDecelerateInterpolator()
        onEnd?.let { doOnEnd { it() } }
        start()
    }
}
// zoom out 1 -> end -> vo han

//fun animateView(view: View, scaleStart: Float, scaleEnd: Float, duration: Long) {
//    val scaleXAnimation = ObjectAnimator.ofFloat(view, View.SCALE_X, scaleStart, scaleEnd)
//    val scaleYAnimation = ObjectAnimator.ofFloat(view, View.SCALE_Y, scaleStart, scaleEnd)
//
//    AnimatorSet().apply {
//        playTogether(scaleXAnimation, scaleYAnimation)
//        this.duration = duration
//        interpolator = AccelerateDecelerateInterpolator()
//        start()
//        doOnEnd {
//            if (isPlayingMix) {
//                animateViewStart(view, scaleStart, scaleEnd, duration)
//            }
//        }
//    }
//}

//fun animateViewStart(view: View, scaleStart: Float, scaleEnd: Float, duration: Long) {
//    val scaleXAnimation = ObjectAnimator.ofFloat(view, View.SCALE_X, scaleEnd, scaleStart)
//    val scaleYAnimation = ObjectAnimator.ofFloat(view, View.SCALE_Y, scaleEnd, scaleStart)
//
//    AnimatorSet().apply {
//        playTogether(scaleXAnimation, scaleYAnimation)
//        this.duration = duration
//        interpolator = AccelerateDecelerateInterpolator()
//        doOnEnd {
//            if (isPlayingMix) {
//                animateView(view, scaleStart, scaleEnd, duration)
//            }
//        }
//        start()
//    }
//}

// zoom out 0 -> end
fun animateViewIn(view: View, scaleStart: Float, duration: Long) {
    val scaleXAnimation = ObjectAnimator.ofFloat(view, View.SCALE_X, scaleStart, 1f)
    val scaleYAnimation = ObjectAnimator.ofFloat(view, View.SCALE_Y, scaleStart, 1f)

    AnimatorSet().apply {
        playTogether(scaleXAnimation, scaleYAnimation)
        this.duration = duration
        interpolator = AccelerateDecelerateInterpolator()

        start()
    }
}

// Rung ngang vo han
//fun View.shakeViewInfinite(duration: Long = 100, repeatCount: Int = 5, shakeDistance: Float = 10f, positionAnimator: Int) {
//    ObjectAnimator.ofFloat(this, "translationX", -shakeDistance, shakeDistance).apply {
//        this.duration = duration
//        this.repeatCount = repeatCount
//        this.repeatMode = ValueAnimator.REVERSE
//        addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
//                if (shakeAnimatorList[positionAnimator]){
//                    shakeViewInfinite(duration, repeatCount, shakeDistance, positionAnimator)
//                }else{
//                    stopShake(positionAnimator, endStop = {})
//                }
//            }
//        })
//        start()
//    }
//}

//fun View.stopShake(positionAnimator: Int, endStop: (() -> Unit)) {
//    shakeAnimatorList[positionAnimator] = false
//    this.translationX = 0f
//    endStop.invoke()
//}
// Chay chu
//fun setTextWithAnimationCoroutine(
//    textView: StrokeTextView,
//    stringResId: Int,
//    context: Context,
//    delay: Long = 500
//) {
//    val fullText = context.getString(stringResId)
//    var currentText = ""
//
//    CoroutineScope(Dispatchers.Main).launch {
//        for (char in fullText) {
//            currentText += char
//            textView.text = currentText
//            delay(delay) // Đợi trước khi hiển thị ký tự tiếp theo
//        }
//    }
//}
fun slideInFromLeft(view: LinearLayout, layoutParent: ConstraintLayout) {
    val animate = TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
        0.0f)
    animate.duration = 300
    view.visibility = View.VISIBLE
    layoutParent.visible()
    view.startAnimation(animate)
}

fun slideOutToLeft(view: LinearLayout, layoutParent: ConstraintLayout) {
    val animate = TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
        0.0f)
    animate.duration = 300
    animate.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation?) {
            view.visibility = View.GONE
            layoutParent.gone()
        }

        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationStart(animation: Animation?) {}
    })
    view.startAnimation(animate)
}

fun startFragmentSlideInFromRight(view: FrameLayout) {
    val animate = TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, 1.0f, // fromXDelta: bắt đầu từ bên phải ngoài màn hình
        Animation.RELATIVE_TO_PARENT, 0.0f, // toXDelta: kết thúc tại vị trí hiện tại
        Animation.RELATIVE_TO_PARENT, 0.0f, // fromYDelta
        Animation.RELATIVE_TO_PARENT, 0.0f  // toYDelta
    )
    animate.duration = 300
    view.startAnimation(animate)
}

fun backFragmentSlideInFromLeft(view: FrameLayout, onFinish: (() -> Unit)) {
    val animate = TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, 0.0f, // fromXDelta: bắt đầu từ bên phải ngoài màn hình
        Animation.RELATIVE_TO_PARENT, 1.0f, // toXDelta: kết thúc tại vị trí hiện tại
        Animation.RELATIVE_TO_PARENT, 0.0f, // fromYDelta
        Animation.RELATIVE_TO_PARENT, 0.0f  // toYDelta
    )
    animate.duration = 300
    animate.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation?) {
            onFinish.invoke()
        }

        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationStart(animation: Animation?) {}
    })
    view.startAnimation(animate)
}
