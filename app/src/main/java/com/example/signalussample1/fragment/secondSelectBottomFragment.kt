package com.example.signalussample1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.signalussample1.R
import kotlinx.android.synthetic.main.fragment_second_select_bottom.*
import kotlinx.android.synthetic.main.fragment_second_select_botttom_back.*
import kotlinx.android.synthetic.main.fragment_second_select_face.*
import kotlinx.android.synthetic.main.fragment_second_select_face.back_btn

/**
 * A simple [Fragment] subclass.
 */
class secondSelectBottomFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_select_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        back_btn.setOnClickListener(this)
        to_back_btn.setOnClickListener(this)
        pelvic_left_shadow.setOnClickListener(this)
        pelvic_right_shadow.setOnClickListener(this)
        leg_left_shadow.setOnClickListener(this)
        leg_right_shadow.setOnClickListener(this)
        foot_left_shadow.setOnClickListener(this)
        foot_right_shadow.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.to_back_btn -> {
                navController.navigate(R.id.action_secondSelectBottomFragment_to_secondSelectBotttomBackFragment2)//뒤->앞
            }
            R.id.pelvic_left_shadow->{navigateWithValue("왼쪽 골반")}
            R.id.pelvic_right_shadow->{navigateWithValue("오른쪽 골반")}
            R.id.leg_left_shadow->{navigateWithValue("왼쪽 다리")}
            R.id.leg_right_shadow->{navigateWithValue("오른쪽 다리")}
            R.id.foot_left_shadow->{navigateWithValue("왼쪽 발")}
            R.id.foot_right_shadow->{navigateWithValue("오른쪽 발")}
            R.id.back_btn -> {
                navController.popBackStack()
            }
        }
    }

    fun navigateWithValue(index : String){
        val bundle = bundleOf("body_part" to index)
        navController.navigate(R.id.action_secondSelectBottomFragment_to_cameraFragment,bundle)
    }
}
