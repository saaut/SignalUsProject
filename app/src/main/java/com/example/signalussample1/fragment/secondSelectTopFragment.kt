package com.example.signalussample1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.signalussample1.R
import kotlinx.android.synthetic.main.fragment_second_select_bottom.*
import kotlinx.android.synthetic.main.fragment_second_select_bottom.to_back_btn
import kotlinx.android.synthetic.main.fragment_second_select_face.*
import kotlinx.android.synthetic.main.fragment_second_select_face.back_btn
import kotlinx.android.synthetic.main.fragment_second_select_top.*

/**
 * A simple [Fragment] subclass.
 */
class secondSelectTopFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_select_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        back_btn.setOnClickListener(this)
        shoulder_shadow.setOnClickListener(this)
        arm_left_shadow.setOnClickListener(this)
        arm_right_shadow.setOnClickListener(this)
        chest_shadow.setOnClickListener(this)
        stomach_shadow.setOnClickListener(this)
        hand_left_shadow.setOnClickListener(this)
        hand_right_shadow.setOnClickListener(this)
        to_back_btn.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.to_back_btn -> {
                navController.navigate(R.id.action_secondSelectTopFragment2_to_secondSelectTopBackFragment)//front->back
            }
            R.id.shoulder_shadow -> {
                navController.navigate(R.id.action_secondSelectTopFragment2_to_cameraFragment)//front->back
            }
            R.id.arm_left_shadow -> {
                navController.navigate(R.id.action_secondSelectTopFragment2_to_cameraFragment)//front->back
            }
            R.id.arm_right_shadow -> {
            navController.navigate(R.id.action_secondSelectTopFragment2_to_cameraFragment)//front->back
            }
            R.id.chest_shadow -> {
                navController.navigate(R.id.action_secondSelectTopFragment2_to_cameraFragment)//front->back
            }
            R.id.stomach_shadow -> {
                navController.navigate(R.id.action_secondSelectTopFragment2_to_cameraFragment)//front->back
            }
            R.id.hand_left_shadow -> {
                navController.navigate(R.id.action_secondSelectTopFragment2_to_cameraFragment)//front->back
            }
            R.id.hand_right_shadow -> {
                navController.navigate(R.id.action_secondSelectTopFragment2_to_cameraFragment)//front->back
            }
            R.id.back_btn -> {
                navController.popBackStack()
            }
        }
    }
}