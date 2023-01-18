package com.example.signalussample1.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.signalussample1.MainActivity
import com.example.signalussample1.R
import kotlinx.android.synthetic.main.fragment_main_title.*
import java.util.*
import kotlin.concurrent.timer
/**
 * A simple [Fragment] subclass.
 */
class MainTitleFragment : Fragment() {

    lateinit var navController : NavController
    lateinit var mainActivity: MainActivity
    var second=0
    var timerTask : Timer? =null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_title, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        timerTask=timer(period =1000, initialDelay = 1000){
             second++
            if(second==2) {
                timerTask?.cancel()
                mainActivity.runOnUiThread {
                    navController.navigate(R.id.action_mainTitleFragment_to_firstSelectFragment)
                }
            }




        }

        maincharacter_img.setOnClickListener { navController.navigate(R.id.action_mainTitleFragment_to_firstSelectFragment) }
    }
}

