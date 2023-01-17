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

/**
 * A simple [Fragment] subclass.
 */
class cameraFragment : Fragment(),View.OnClickListener {

    var body_part=""
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        body_part=arguments?.getString("body_part")?:""

        return inflater.inflate(R.layout.fragment_camera, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        translated.text=body_part
        //setTranslateText(body_part)

        back_btn.setOnClickListener(this)
        stt_btn.setOnClickListener(this)
    }
/*
    fun setTranslateText(index_body_part: Int) {
        when(index_body_part){
            1->{
                translated.text="엉덩이"
            }
        }

    }
*/

    override fun onClick(v: View?){
        when(v?.id){//view가 null이 아니면 안되는 코드. 물음표를 붙여줌으로써 null일 경우 전체는 null을 반환한다.
            R.id.stt_btn->{
                navController.navigate(R.id.action_cameraFragment_to_voiceRecordFragment)
            }
            R.id.back_btn->{navController.popBackStack()
            }
        }
    }
    }