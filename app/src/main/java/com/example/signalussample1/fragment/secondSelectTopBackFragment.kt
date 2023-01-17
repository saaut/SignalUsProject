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
import kotlinx.android.synthetic.main.fragment_second_select_face.*
import kotlinx.android.synthetic.main.fragment_second_select_face.back_btn
import kotlinx.android.synthetic.main.fragment_second_select_top_back.*

/**
 * A simple [Fragment] subclass.
 */
class secondSelectTopBackFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_select_top_back, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        back_btn.setOnClickListener(this)
        back_shadow.setOnClickListener(this)

        to_front_btn.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.to_front_btn -> {
                navController.navigate(R.id.action_secondSelectTopBackFragment_to_secondSelectTopFragment2)//뒤->앞
            }
            R.id.back_shadow->{navigateWithValue("등")}
            R.id.back_btn -> {
                navController.popBackStack()
            }
        }
    }
    fun navigateWithValue(index : String){
        val bundle = bundleOf("body_part" to index)
        navController.navigate(R.id.action_secondSelectTopBackFragment_to_cameraFragment,bundle)
    }
}