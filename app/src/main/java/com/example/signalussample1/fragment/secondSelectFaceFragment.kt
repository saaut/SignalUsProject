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
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.android.synthetic.main.fragment_first_select.*
import kotlinx.android.synthetic.main.fragment_second_select_face.*
import kotlinx.android.synthetic.main.fragment_second_select_face.back_btn

/**
 * A simple [Fragment] subclass.
 */
class secondSelectFaceFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_select_face, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        back_btn.setOnClickListener(this)
        head_shadow.setOnClickListener(this)
        eye_shadow.setOnClickListener(this)
        mouth_shadow.setOnClickListener(this)
        nose_shadow.setOnClickListener(this)
        ear_left_shadow.setOnClickListener(this)
        ear_right_shadow.setOnClickListener(this)
        neck_shadow.setOnClickListener(this)

    }
    override fun onClick(v: View?){
        when(v?.id){
            R.id.head_shadow->{navigateWithValue("머리")}
            R.id.eye_shadow->{navigateWithValue("눈")}
            R.id.mouth_shadow->{navigateWithValue("입")}
            R.id.nose_shadow->{navigateWithValue("코")}
            R.id.ear_left_shadow->{navigateWithValue("왼쪽 귀")}
            R.id.ear_right_shadow->{navigateWithValue("오른쪽 귀")}
            R.id.neck_shadow->{navigateWithValue("목")}
            R.id.back_btn->{navController.popBackStack()
            }
        }
    }
    fun navigateWithValue(index : String){
        val bundle = bundleOf("body_part" to index)
        navController.navigate(R.id.action_secondSelectFaceFragment_to_cameraFragment,bundle)
    }

}