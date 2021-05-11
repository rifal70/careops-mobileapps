package com.digimaster.digicourse.digicyber.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digimaster.digicourse.digicyber.R
import kotlinx.android.synthetic.main.fragment_my_learn.*

/**
 * A simple [Fragment] subclass.
 */
class LearnFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        (context as AppCompatActivity?)!!.supportActionBar!!.title = "Learn"

        return inflater.inflate(R.layout.fragment_my_learn, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rlMerahLearn.setOnClickListener {
            var fragment: Fragment?
            fragment = AssessFragment()

            fragmentManager!!.beginTransaction().replace(R.id.screen_area,fragment).commit()

        }

        rlKuningLearn.setOnClickListener {
            var fragment: Fragment?
            fragment = LibraryFragment()

            fragmentManager!!.beginTransaction().replace(R.id.screen_area, fragment as LibraryFragment).commit()

        }

    }



}