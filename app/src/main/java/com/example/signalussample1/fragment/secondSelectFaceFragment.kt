package com.example.signalussample1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            R.id.head_shadow->{
                navController.navigate(R.id.action_secondSelectFaceFragment_to_cameraFragment)
            }
            R.id.eye_shadow->{
                navController.navigate(R.id.action_secondSelectFaceFragment_to_cameraFragment)
            }
            R.id.mouth_shadow->{
                navController.navigate(R.id.action_secondSelectFaceFragment_to_cameraFragment)
            }
            R.id.nose_shadow->{
                navController.navigate(R.id.action_secondSelectFaceFragment_to_cameraFragment)
            }
            R.id.ear_left_shadow->{
                navController.navigate(R.id.action_secondSelectFaceFragment_to_cameraFragment)
            }
            R.id.ear_right_shadow->{
                navController.navigate(R.id.action_secondSelectFaceFragment_to_cameraFragment)
            }
            R.id.neck_shadow->{
                navController.navigate(R.id.action_secondSelectFaceFragment_to_cameraFragment)
            }
            R.id.back_btn->{navController.popBackStack()
            }
        }
    }

}