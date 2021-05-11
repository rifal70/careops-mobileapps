package com.digimaster.digicourse.digicyber.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digimaster.digicourse.digicyber.Contract.PrefContract
import com.digimaster.digicourse.digicyber.R
import com.digimaster.digicourse.digicyber.SecuredPreference
import kotlinx.android.synthetic.main.fragment_detail_course.*


class DetailModuleFragment : Fragment() {

    var pref: SecuredPreference? = null
    var detail: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_detail_course, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = SecuredPreference(activity, PrefContract.PREF_EL)
        detail = pref!!.getString(PrefContract.module_desc, "")


        tvDetailCourseFrag.text = detail

        //Toast.makeText(activity,"this is toast message",Toast.LENGTH_SHORT).show()

    }
}
