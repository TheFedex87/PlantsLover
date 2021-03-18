package it.bytener.plantslover

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import it.bytener.plantslover.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding : FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        registerScreenAnimation()
        //binding.leafTop.translationY = binding.leafTop.layoutParams.height * 0.9f
        //binding.leafBottom.translationY = -binding.leafBottom.layoutParams.height * 0.9f
    }

    private fun registerScreenAnimation() {
        val leafTop: View = binding.leafTop
        leafTop.pivotX = 0f
        leafTop.pivotY = 0f

        val leafBottom: View = binding.leafBottom
        leafBottom.pivotX = 0f
        leafBottom.pivotY = binding.root.layoutParams.height + leafBottom.layoutParams.height * 1.0f

        // val translateAnim = AnimationUtils.loadAnimation(requireActivity(), R.anim.translate_anim)
        // val scaleAnim = AnimationUtils.loadAnimation(requireActivity(), R.anim.scale_animation)

        animIn(leafTop, 0f, leafTop.layoutParams.height * 0.9f)
        animIn(leafBottom,  binding.root.layoutParams.height * 1.0f, binding.root.layoutParams.height - leafBottom.layoutParams.height * 0.9f)

        fadeInTitle(binding.textViewTitleRegister)
    }

    private fun animIn(view: View, startPosition: Float, endPosition: Float) {
        val translateAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, startPosition, endPosition).setDuration(450)
        translateAnimator.interpolator = FastOutSlowInInterpolator()

        val scaleAnimatorIn1 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.5f).setDuration(200)
        scaleAnimatorIn1.startDelay = 100
        //scaleAnimatorIn.interpolator = BounceInterpolator()

        val scaleAnimatorOut1 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.5f, 0.8f).setDuration(250)
        //scaleAnimatorOut.interpolator = BounceInterpolator()

        val scaleAnimatorIn2 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.8f, 1.1f).setDuration(300)
        //scaleAnimatorIn.interpolator = BounceInterpolator()

        val scaleAnimatorOut2 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.1f, 1f).setDuration(350)
        //scaleAnimatorOut2.interpolator = BounceInterpolator()

        val animatorSet = AnimatorSet()
        animatorSet.play(translateAnimator)
        animatorSet.playSequentially(scaleAnimatorIn1, scaleAnimatorOut1, scaleAnimatorIn2, scaleAnimatorOut2)
        animatorSet.startDelay = 0
        animatorSet.start()

    }

    private fun fadeInTitle(view: View) {
        val fadeOutAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f).setDuration(400)
        fadeOutAnimator.start()
    }
}