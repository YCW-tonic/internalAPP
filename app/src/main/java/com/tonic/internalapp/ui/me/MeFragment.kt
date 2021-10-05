package com.tonic.internalapp.ui.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.tonic.internalapp.R
import com.tonic.internalapp.databinding.FragmentMeBinding

class MeFragment : Fragment() {

    private lateinit var meViewModel: MeViewModel
    private var _binding: FragmentMeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        meViewModel =
                ViewModelProvider(this).get(MeViewModel::class.java)

        _binding = FragmentMeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textSlideshow
        meViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        /*val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.shohei_ohtani_1))
        imageList.add(SlideModel(R.drawable.shohei_ohtani_2))
        imageList.add(SlideModel(R.drawable.shohei_ohtani_3))
        imageList.add(SlideModel(R.drawable.shohei_ohtani_4))
        imageList.add(SlideModel(R.drawable.shohei_ohtani_5))
        imageList.add(SlideModel(R.drawable.shohei_ohtani_6))
        imageList.add(SlideModel(R.drawable.shohei_ohtani_7))

        val imageSlider = root.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)

        imageSlider.startSliding(3000) // with new period
        */
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}