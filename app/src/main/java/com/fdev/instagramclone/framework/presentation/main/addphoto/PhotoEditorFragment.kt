package com.fdev.instagramclone.framework.presentation.main.addphoto

import android.graphics.Bitmap
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentPhotoEditorBinding
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment
import com.fdev.instagramclone.util.SpaceItemDecoration
import com.fdev.instagramclone.util.printLogD
import com.zomato.photofilters.FilterPack
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.utils.ThumbnailItem
import com.zomato.photofilters.utils.ThumbnailsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PhotoEditorFragment : BaseMainFragment(R.layout.fragment_photo_editor) , FilterListAdapter.Interaction{

    private var _binding : FragmentPhotoEditorBinding? = null

    private val binding
        get() = _binding!!

    private var _requestManager : RequestManager? = null

    private val requestManager
        get() = _requestManager!!

    private lateinit var filterAdapter : FilterListAdapter

    private lateinit var currentImage : Bitmap

    private lateinit var defaultImage : Bitmap


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPhotoEditorBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{
            val image = it.get(PHOTO_BUNDLE_KEY)
            if(image is Bitmap){
                initGlide()
                defaultImage = image
                changeCurrentImage(defaultImage)
                initFilter(image)
            }else{
                printLogD("PhotoEditorFragment", "Image is not bitmap")
            }
        }?:   printLogD("PhotoEditorFragment", "Argument is null")
    }


    private fun initFilter(imageToFilter: Bitmap) {
        CoroutineScope(Dispatchers.Default).launch {

            val thumbnailList = ArrayList<ThumbnailItem>()

            ThumbnailsManager.clearThumbs();
            thumbnailList.clear();

            val thumbnailItem = ThumbnailItem()
            thumbnailItem.image = imageToFilter
            thumbnailItem.filterName = getString(R.string.filter_normal)
            ThumbnailsManager.addThumb(thumbnailItem)

            val filters: List<Filter> = FilterPack.getFilterPack(activity)
            for (filter in filters) {
                val thumbnailItem = ThumbnailItem()
                thumbnailItem.filter = filter
                thumbnailItem.filterName = filter.name
                thumbnailItem.image = imageToFilter
                ThumbnailsManager.addThumb(thumbnailItem)
            }
            thumbnailList.addAll(ThumbnailsManager.processThumbs(activity))

            withContext(Dispatchers.Main) {
                initRecyclerView(thumbnailList)
            }
        }
    }

    private fun initRecyclerView(thumbnailList: ArrayList<ThumbnailItem>) {
        filterAdapter = FilterListAdapter(requestManager, this)
        binding.filterRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@PhotoEditorFragment.context,
                    LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            val space = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f,
                    resources.displayMetrics).toInt()
            addItemDecoration(SpaceItemDecoration(space))
            adapter = filterAdapter

        }

        filterAdapter.submitList(thumbnailList)
    }


    private fun initGlide() {
        val requestOptions = RequestOptions
                .placeholderOf(R.drawable.default_image)
                .error(R.drawable.ic_baseline_error_outline_24)

        activity?.let {
            _requestManager = Glide.with(it)
                    .applyDefaultRequestOptions(requestOptions)
        }
    }

    override fun onItemSelected(filter : Filter) {
        currentImage = defaultImage.copy(Bitmap.Config.ARGB_8888 , true)
        changeCurrentImage(filter.processFilter(currentImage))
    }

    private fun changeCurrentImage(bitmap: Bitmap) {
        currentImage = bitmap
        requestManager
                .load(currentImage)
                .into(binding.photoEditorImageview)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.filterRecyclerview.adapter = null
        _requestManager = null
    }



}