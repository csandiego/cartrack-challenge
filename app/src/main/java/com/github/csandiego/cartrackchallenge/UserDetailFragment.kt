package com.github.csandiego.cartrackchallenge

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.csandiego.cartrackchallenge.databinding.UserDetailBinding
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import javax.inject.Inject
import javax.inject.Provider


class UserDetailFragment @Inject constructor(provider: Provider<UserDetailViewModel>) : Fragment() {

    private val viewModel by viewModels<UserDetailViewModel> {
        object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return provider.get() as T
            }
        }
    }

    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            arguments?.getInt(ARG_ITEM_ID)?.let {
                if (it > -1) {
                    viewModel.loadUser(it)
                }
            }
        }
        Mapbox.getInstance(requireContext(), getString(R.string.mapbox_access_token))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = UserDetailBinding.inflate(inflater, container, false).run {
        viewModel = this@UserDetailFragment.viewModel
        this@UserDetailFragment.mapView = mapView
        root
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { mapboxMap ->

            val builder = Style.Builder().fromUri(Style.MAPBOX_STREETS)

            viewModel.user?.address?.geo?.let {
                builder
                    .withImage(
                        "ICON_ID",
                        BitmapFactory.decodeResource(
                            resources,
                            R.drawable.mapbox_marker_icon_default
                        )
                    )
                    .withSource(
                        GeoJsonSource(
                            "SOURCE_ID",
                            FeatureCollection.fromFeatures(
                                listOf(
                                    Feature.fromGeometry(
                                        Point.fromLngLat(it.lng!!.toDouble(), it.lat!!.toDouble())
                                    )
                                )
                            )
                        )
                    )
                    .withLayer(
                        SymbolLayer("LAYER_ID", "SOURCE_ID")
                            .withProperties(
                                PropertyFactory.iconImage("ICON_ID"),
                                iconAllowOverlap(true),
                                iconOffset(arrayOf(0f, -9f))
                            )
                    )
                val ll = LatLng(it.lat!!.toDouble(), it.lng!!.toDouble())
                mapboxMap.cameraPosition = CameraPosition.Builder()
                    .target(ll)
                    .zoom(2.5)
                    .build()
            }
            mapboxMap.setStyle(builder)
        }
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onDestroy()
    }
}