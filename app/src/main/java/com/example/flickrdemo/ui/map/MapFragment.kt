package com.example.flickrdemo.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.flickrdemo.databinding.FragmentMapBinding
import com.example.flickrdemo.viewmodels.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MapFragment : Fragment(), OnMapReadyCallback {
    private val viewModel: MapViewModel by viewModels()
    private var _binding: FragmentMapBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        binding.mapViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mapView?.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        viewModel.navigateToResults.observe(viewLifecycleOwner,
            { navigate ->
                if (navigate == true) {
                    val navController = findNavController()
                    navController.navigate(
                        MapFragmentDirections.actionSecondFragmentToFirstFragment(
                            viewModel.lat.value.toString(),
                            viewModel.lon.value.toString()
                        )
                    )
                    viewModel.doneSearching()
                }

            }
        )


        return binding.root

    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onMapReady(p0: GoogleMap) {
        if (viewModel.checkLatLon()) {
            p0.addMarker(
                    MarkerOptions().position(viewModel.returnLatLon()).title("current position")
            )
        }
        p0.setOnMapClickListener { latLng ->
            p0.clear()
            p0.addMarker(
                MarkerOptions().position(latLng).title("current position")
            )
            p0.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            viewModel.saveCoords(latLng.latitude.toString(), latLng.longitude.toString())
        }

    }

}
