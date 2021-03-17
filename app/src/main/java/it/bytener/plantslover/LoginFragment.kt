package it.bytener.plantslover

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import it.bytener.plantslover.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        loginScreenAnimation()

        binding.textViewLogin.setOnClickListener {
            loginScreenAnimation()
        }
        binding.viewRegister.setOnClickListener {
            val extras = FragmentNavigatorExtras(binding.viewRegister to "register_view",
                binding.textViewRegister to "register_text_view")
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment, null, null, extras)
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

        anim(leafTop, 0f, leafTop.layoutParams.height * 0.9f)
        anim(leafBottom,  binding.root.layoutParams.height * 1.0f, binding.root.layoutParams.height - leafBottom.layoutParams.height * 0.9f)
    }

    private fun anim(view: View, startPosition: Float, endPosition: Float) {
        val translateAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, startPosition, endPosition).setDuration(550)
        translateAnimator.interpolator = FastOutSlowInInterpolator()
        val scaleAnimatorIn1 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.5f).setDuration(300)
        scaleAnimatorIn1.startDelay = 200
        //scaleAnimatorIn.interpolator = BounceInterpolator()
        val scaleAnimatorOut1 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.5f, 0.8f).setDuration(350)
        //scaleAnimatorOut.interpolator = BounceInterpolator()
        val scaleAnimatorIn2 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.8f, 1.1f).setDuration(400)
        //scaleAnimatorIn.interpolator = BounceInterpolator()
        val scaleAnimatorOut2 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.1f, 1f).setDuration(450)
        //scaleAnimatorOut2.interpolator = BounceInterpolator()

        val animatorSet = AnimatorSet()
        animatorSet.play(translateAnimator)
        animatorSet.playSequentially(scaleAnimatorIn1, scaleAnimatorOut1, scaleAnimatorIn2, scaleAnimatorOut2)
        animatorSet.startDelay = 500
        animatorSet.start()

    }
}