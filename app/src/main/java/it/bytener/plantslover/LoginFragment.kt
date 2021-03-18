package it.bytener.plantslover

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import it.bytener.plantslover.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

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

        binding = FragmentLoginBinding.bind(view)
        loginScreenAnimation()

        binding.textViewLogin.setOnClickListener {
            loginScreenAnimation()
        }
        binding.viewRegister.setOnClickListener {
            animOut(binding.leafTop, binding.leafTop.layoutParams.height * 0.9f, 0f) {}
            animOut(
                binding.leafBottom,
                binding.root.layoutParams.height - binding.leafBottom.layoutParams.height * 0.9f,
                binding.root.layoutParams.height * 1.0f
            ) {
                val extras = FragmentNavigatorExtras(
                    binding.constraintlayoutUserData to "user_data",
                    binding.textViewForgotPassword to "forgot_password"
                )
                findNavController().navigate(
                    R.id.action_loginFragment_to_registerFragment,
                    null,
                    null,
                    extras
                )
                //findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun loginScreenAnimation() {
        val leafTop: View = binding.leafTop
        leafTop.pivotX = 0f
        leafTop.pivotY = 0f

        val leafBottom: View = binding.leafBottom
        leafBottom.pivotX = 0f
        leafBottom.pivotY = binding.root.layoutParams.height + leafBottom.layoutParams.height * 1.0f

        // val translateAnim = AnimationUtils.loadAnimation(requireActivity(), R.anim.translate_anim)
        // val scaleAnim = AnimationUtils.loadAnimation(requireActivity(), R.anim.scale_animation)

        animIn(leafTop, 0f, leafTop.layoutParams.height * 0.9f)
        animIn(
            leafBottom,
            binding.root.layoutParams.height * 1.0f,
            binding.root.layoutParams.height - leafBottom.layoutParams.height * 0.9f
        )
    }

    private fun animIn(view: View, startPosition: Float, endPosition: Float) {
        val translateAnimator =
            ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, startPosition, endPosition)
                .setDuration(450)
        translateAnimator.interpolator = FastOutSlowInInterpolator()

        val scaleAnimatorIn1 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.5f).setDuration(200)
        scaleAnimatorIn1.startDelay = 100
        //scaleAnimatorIn.interpolator = BounceInterpolator()

        val scaleAnimatorOut1 =
            ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.5f, 0.8f).setDuration(250)
        //scaleAnimatorOut.interpolator = BounceInterpolator()

        val scaleAnimatorIn2 =
            ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.8f, 1.1f).setDuration(300)
        //scaleAnimatorIn.interpolator = BounceInterpolator()

        val scaleAnimatorOut2 =
            ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.1f, 1f).setDuration(350)
        //scaleAnimatorOut2.interpolator = BounceInterpolator()

        val animatorSet = AnimatorSet()
        animatorSet.play(translateAnimator)
        animatorSet.playSequentially(
            scaleAnimatorIn1,
            scaleAnimatorOut1,
            scaleAnimatorIn2,
            scaleAnimatorOut2
        )
        animatorSet.startDelay = 300
        animatorSet.start()

        fadeInTitle()
        slideInLoginTextView()
    }

    private fun animOut(
        view: View,
        startPosition: Float,
        endPosition: Float,
        callback: () -> Unit
    ) {
        val scaleAnimatorIn = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.5f).setDuration(100)
        scaleAnimatorIn.interpolator = FastOutSlowInInterpolator()

        val scaleAnimatorOut = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.5f, 1f).setDuration(300)
        scaleAnimatorOut.interpolator = FastOutSlowInInterpolator()
        scaleAnimatorOut.startDelay = 100

        val translateAnimator =
            ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, startPosition, endPosition)
                .setDuration(400)
        translateAnimator.interpolator = FastOutSlowInInterpolator()
        translateAnimator.startDelay = 200
        translateAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                callback()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })

        val animatorSet = AnimatorSet()
        animatorSet.play(scaleAnimatorIn)
        animatorSet.play(scaleAnimatorOut)
        animatorSet.play(translateAnimator)
        animatorSet.start()

        fadeOutTitle()
        slideOutRegisterButton()
    }

    private fun fadeInTitle() {
        val fadeOutAnimator =
            ObjectAnimator.ofFloat(binding.textViewLogin, View.ALPHA, 0f, 1f).setDuration(500)
        fadeOutAnimator.start()
    }

    private fun fadeOutTitle() {
        val fadeOutAnimator =
            ObjectAnimator.ofFloat(binding.textViewLogin, View.ALPHA, 1f, 0f).setDuration(400)
        fadeOutAnimator.start()
    }

    private fun slideInLoginTextView() {
        val slideViewLoginInAnimator = ObjectAnimator.ofFloat(
            binding.viewRegister,
            View.TRANSLATION_X,
            0f,
            binding.viewRegister.layoutParams.width * 1.0f
        ).setDuration(400)
        slideViewLoginInAnimator.start()
        val slideButtonLoginInAnimator = ObjectAnimator.ofFloat(
            binding.textViewRegister,
            View.TRANSLATION_X,
            0f,
            binding.viewRegister.layoutParams.width * 1.0f
        ).setDuration(400)
        slideButtonLoginInAnimator.start()
    }

    private fun slideOutRegisterButton() {
        val translateViewAnimator = ObjectAnimator.ofFloat(
            binding.viewRegister,
            View.TRANSLATION_X,
            binding.viewRegister.layoutParams.width * 1.0f,
            0f
        ).setDuration(400)
        translateViewAnimator.start()
        val translateTextAnimator = ObjectAnimator.ofFloat(
            binding.textViewRegister,
            View.TRANSLATION_X,
            binding.viewRegister.layoutParams.width * 1.0f,
            0f
        ).setDuration(400)
        translateTextAnimator.start()
    }
}