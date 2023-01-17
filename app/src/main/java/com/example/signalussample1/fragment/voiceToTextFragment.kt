package com.example.signalussample1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.signalussample1.R
import kotlinx.android.synthetic.main.fragment_second_select_face.*
import kotlinx.android.synthetic.main.fragment_second_select_face.back_btn
import kotlinx.android.synthetic.main.fragment_voice_to_text.*

/**
 * A simple [Fragment] subclass.
 */
class voiceToTextFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voice_to_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        back_btn.setOnClickListener(this)
        again_txt.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back_btn -> {
                navController.popBackStack()
            }
            R.id.again_txt->{
                navController.popBackStack()
            }
        }
    }
}