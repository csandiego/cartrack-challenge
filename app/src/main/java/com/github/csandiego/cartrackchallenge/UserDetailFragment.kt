package com.github.csandiego.cartrackchallenge

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.github.csandiego.cartrackchallenge.databinding.UserDetailBinding
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private val viewModel by activityViewModels<UserViewModel>()

    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(requireContext(), getString(R.string.mapbox_access_token))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = UserDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        mapView = binding.mapView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { mapboxMap ->
            viewModel.user.observe(viewLifecycleOwner) { user ->
                activity?.findViewById<Toolbar>(R.id.toolbar)?.title = user.name
                user.address?.geo?.let {
                    val lat = it.lat?.toDouble() ?: 0.0
                    val lng = it.lng?.toDouble() ?: 0.0
                    showInMap(mapboxMap, lat, lng)
                }
            }
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

    private fun showInMap(mbm: MapboxMap, lat: Double, lng: Double) {
        val builder = Style.Builder().fromUri(Style.MAPBOX_STREETS)
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
                                Point.fromLngLat(lng, lat)
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
        val position = CameraPosition.Builder()
            .target(LatLng(lat, lng))
            .zoom(2.5)
            .build()

        with(mbm) {
            cameraPosition = position
            setStyle(builder)
        }
    }
}